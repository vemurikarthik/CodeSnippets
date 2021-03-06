package yn.regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	public static void main(String[] args) {
		matches();
		findBannedWords();
		negativeLookBehind();
		patternforsinglequote();
		urlPartMatcher();
		escapeDoubleQuote();
		embeddedModifiers();
		matcher();
		replaceTextBetweenParentheses();
		regexforfieldset();
		replaceSpanInAHTML();
		//regexCausingStackOverflowException();
		
		punctuationRegex();
		primeRegex(7);
		
		//reluctant quantifiers  example
		reluctantQuant();
		
		//content -type regex
		contentType();
		
		splitwithpipe();
	}

	private static void matches() {
		String link = "https://pic.twitter.com/fdsa/fasdfdads";
		System.out.println("starts with twitter.com " + link.matches("^http(s)?://(pic.)?twitter.com.*"));
	}

	private static void findBannedWords() {
		final ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("fuck");
		keywords.add("shit");
		keywords.add("asshole");
		
		String input = "what the fuck";
		
		String bannedRegex = "";
		for (String keyword: keywords){
			bannedRegex = bannedRegex + ".*" + keyword + ".*" + "|";
		}
		
		Pattern pt = Pattern.compile(bannedRegex.substring(0, bannedRegex.length()-1));
	    Matcher mt = pt.matcher(input);
	    if (mt.matches()) {
	        System.out.println("message can't be processed because it doesn't satisfy the rule ");
	   }
	}
	
	private static void negativeLookBehind() {
		String x = "dr. fucker.";
		System.out.println(x.replaceAll("(?<!mr|mrs|ms|dr)(\\s*[\\.\\?\\!]\\s*)","\n"));		
	}

	private static void splitwithpipe() {
		String s = "asadsdas357902||190||RUE RACHELLE||ST|||LES C�DRES|J7T1J9|QC";
		for (int i = 0; i < 10; i++) {
			String a[] = s.split("asadsdas357902||", i);	
			System.out.println(Arrays.toString(a));
		}
	}

	private static void replaceSpanInAHTML() {
        String s = "noise <span title=\"whatever\">something I want to preserve</span>...";
        s = s.replaceAll("<span\\s+[^>]*title=(['\"])(.*?)\\1[^>]*>(.*?)</span>", "($3)");
        System.out.println(s);		
	}

	private static void embeddedModifiers() {
		//embedded modifiers
		String ftw = "f\nftw";
		System.out.println(ftw.matches("(?si).*Ftw.*"));
		Pattern pattern = Pattern.compile(".*ftw.*", Pattern.DOTALL);
		Matcher matcher = pattern.matcher("f\nftw");
		System.out.println(matcher.matches());		
	}

	private static void urlPartMatcher() {
		String URL = "https://localhost:8080/sbs/01.00/sip/dreamworks/v/01.00/cui/print/$fwVer/{$fwVer}/$lang/en/$model/{$model}/$region/us/$imageBg/{$imageBg}/$imageH/{$imageH}/$imageSz/{$imageSz}/$imageW/{$imageW}/movie/Kung_Fu_Panda_two/categories/3D_Pix/item/{item}/_back/2?$uniqueID={$uniqueID}";
		System.out.println("Found ? " + URL.contains("{item}"));
		
		Pattern pattern = Pattern.compile("/\\{\\w+\\}/");
		Matcher matcher = pattern.matcher(URL);
		if (matcher.find()) {
			System.out.println(matcher.group(0));	
		} else {
			System.out.println("Match not found");
		}
		
		String url = "http://localhost/htc/android/htc-incredible/259164-gpid";
		System.out.println(url.substring(url.lastIndexOf('/')+1).split("-")[0]);
		Pattern regex = Pattern.compile("/(\\d+)\\-gpid$"); 
		Matcher tagmatch = regex.matcher( url );
		tagmatch.find();
		System.out.println(tagmatch.group(1));		
	}

	private static void escapeDoubleQuote() {
		System.out.println("afa'adff\\asdf".replaceAll("([\\'\\\\])", "\\\\$1"));		
	}

	private static void patternforsinglequote() {
		String input = "abcd'efg'hij";
		Matcher matcher = Pattern.compile("'.*'").matcher(input);
		System.out.println("Found ? " + matcher.find() + 
				           "\nFound what ? "+ matcher.group());
	}

	private static void contentType() {
		Pattern regex = Pattern.compile("Content-Type.*?(?=^\\s*\n?\r?$)", 
                Pattern.DOTALL | Pattern.MULTILINE);
		Matcher matcher = regex.matcher("Content-Type: text/plain;\ncharset=\"us-ascii\"\nContent-Transfer-Encoding: 7bit");
	}

	private static void reluctantQuant() {
		//  /(.*?)(abc).*/.exec('babcabcdef')
		//  ["babcabcdef", "b", "abc"]
		//  /(.*)(abc).*/.exec('babcabcdef')
		//  ["babcabcdef", "babc", "abc"]		
	}

	private static boolean primeRegex(int n) {
		 return !new String(new char[n]).matches(".?|(..+?)\\1+");
	}

	private static void punctuationRegex() {
		String s = "-_;.()";
		System.out.println(s.replaceAll("[\\p{Z}\\p{P}&&[^-_]]", "C"));
	}

	public static void matcher() {
		Pattern p = Pattern.compile("t(est)");
		String candidateString = "This is a test. This is another test.";
		Matcher matcher = p.matcher(candidateString);
		// Find group number 0 of the first find
		matcher.find();
		String group_0 = matcher.group(0);
		String group_1 = matcher.group(1);
		System.out.println("Group 0 " + group_0);
		System.out.println("Group 1 " + group_1);
		System.out.println(candidateString);
		System.out.println(matcher.replaceAll("$1"));
		
		matcher.reset("new test");
		System.out.println(matcher.find());
		
		
		Pattern p1 = Pattern.compile("(?:[,\\. ]*(&|and) ?)");	
		Matcher matcher1 = p1.matcher(", and ");
		System.out.println("some kate question" + matcher1.find());
		
	}

	public static void replaceTextBetweenParentheses() {
		String patternStr = "\\((\\w+)\\)";
		String replaceStr = "<$1>";
		Pattern pattern = Pattern.compile(patternStr);

		CharSequence inputStr = "a (b c) d (ef) g";
		Matcher matcher = pattern.matcher(inputStr);
		String output = matcher.replaceAll(replaceStr);
		System.out.println(output);
	}

	private static void regexCausingStackOverflowException() {
		StringBuilder subjectStringBuffer = new StringBuilder();
		String subjectString = "A ";
		for (int i = 0; i < 800; i++) {
			subjectStringBuffer.append(subjectString);
		}
		subjectStringBuffer.append(subjectString);
		System.out.println(subjectStringBuffer.toString());
		System.out.println(subjectStringBuffer.length());
		long start = System.currentTimeMillis();
		boolean foundMatch = subjectStringBuffer.toString().matches(
			    "(?ix)      # Case-insensitive, multiline regex:\n" +
			    "^          # Start of string\n" +
			    "(?!        # Assert that it's impossible to match\n" +
			    " .*        # any number of characters\n" +
			    " (?:--|__) # followed by -- or __\n" +
			    ")          # End of lookahead assertion\n" +
			    "[A-Z0-9]+  # Match A-Z, a-z or 0-9\n" +
			    "(?:        # Match the following:\n" +
			    " [\\ ]     # a single space\n" +
			    " [\\w-]+   # followed by one or more alnums or -\n" +
			    ")*         # any number of times\n" +
			    "$          # End of string");		
		System.out.println(("Time taken by the regex " + (System.currentTimeMillis() - start)/1000));
		System.out.println("Q: Is match found? A: " + foundMatch);
	}

	private static void regexforfieldset() {
		Pattern pattern = Pattern.compile("<fieldset[^>]*>[^<]*<input.+?value\\s*=\\s*\\\"([^\\\"]*)\\\"");
		Matcher matcher = pattern.matcher("<fieldset style=\"display:none\"><input type=\"hidden\" name=\"buddyname\" value=\"5342test\" /></fieldset>");
		if (matcher.find()) 
			System.out.println(matcher.group(1));
		else
			System.out.println("Input html does not have a fieldset");		
	}
}
