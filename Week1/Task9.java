
	import java.io.*;
	import java.util.*;
import java.util.Scanner;
	public class Task9  {
	    public static void analyzeLogFile(String inputFile, String outputFile, List<String> keywords) throws IOException {
	        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

	            Map<String, Integer> counts = new HashMap<>();
	            for (String keyword : keywords) counts.put(keyword, 0);

	            String line;
	            while ((line = reader.readLine()) != null) {
	                for (String keyword : keywords) {
	                    if (line.contains(keyword)) counts.put(keyword, counts.get(keyword) + 1);
	                }
	            }

	            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
	                writer.write(entry.getKey() + ": " + entry.getValue());
	                writer.newLine();
	            }
	        }
	    }

	    public static void main(String[] args) throws IOException {
	    	Scanner sc=new Scanner(System.in);
	    	System.out.println("Enter keywords for report ('ERROR','INFO','WARNING'):");
	    	String keyword1=sc.nextLine();
	    	String keyword2=sc.nextLine();
	        analyzeLogFile("C:\\Users\\Lenovo\\Desktop\\logs.txt", "C:\\Users\\Lenovo\\Desktop\\report.txt", Arrays.asList(keyword1, keyword2));
	    }
	}


