import java.util.Scanner;
public class Task5 {
	    public static String reverseString(String str) {
	        return new StringBuilder(str).reverse().toString();
	    }

	    public static int countOccurrences(String text, String sub) {
	        int count = 0, index = 0;
	        while ((index = text.indexOf(sub, index)) != -1) {
	            count++;
	            index += sub.length();
	        }
	        return count;
	    }

	    public static String splitAndCapitalize(String str) {
	        String[] words = str.split(" ");
	        StringBuilder result = new StringBuilder();
	        for (String word : words) {
	            result.append(Character.toUpperCase(word.charAt(0)))
	                  .append(word.substring(1))
	                  .append(" ");
	        }
	        return result.toString().trim();
	    }

	    public static void main(String[] args) {
	    	Scanner sc=new Scanner(System.in);
	    	System.out.println("Enter string to be reversed");
	    	String revString=sc.nextLine();
	    	System.out.println(reverseString(revString));
	    	System.out.println("Enter String :");
	    	String countOccurrence=sc.nextLine();
	    	System.out.println("Enter string that has to be counted:");
	    	String countOfString=sc.nextLine();
	    	 System.out.println(countOccurrences(countOccurrence, countOfString));
	        System.out.println("Enter string to split:");
	        String capitalSplitString=sc.nextLine();
	        System.out.println(splitAndCapitalize(capitalSplitString));
	    }
	}

