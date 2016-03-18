import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	// Set default value for T_SUPPORT and T_CONFIDENCE
	public static int T_SUPPORT = 3;
	private static double T_CONFIDENCE = 65;
	
	// <HashMap> functions with functions' ids(hashcode) as keys, names as values
	static HashMap<Integer, String> functions = new HashMap<Integer, String>();
	// <HashMap> graph with callees' ids(hashcode) as keys, its callers' ids as values
	static HashMap<Integer, HashSet<Integer>> graph = new HashMap<Integer, HashSet<Integer>>();
	
	/* Main function */
	public static void main(String[] args){
		int support = T_SUPPORT;
		double confidence = T_CONFIDENCE/100;
		String fileName = "";
		
		if (args.length > 0)     // The input
		{
			fileName = args[0];   
			switch(args.length){
			case 1:              // filename with default value of T_SUPPORT, T_CONFIDENCE
				break;
			case 3:              // filename with T_SUPPORT, T_CONFIDENCE
				support = Integer.parseInt(args[1]);
				confidence = Double.parseDouble(args[2])/100;
				break;
			default:             // Illegal input format
				System.out.println("Error: Wrong Number of Input Arguments");
				System.exit(-1);
			}
		}
		else    // without any input
		{
			System.out.println("Error: Arugment needs to contain at least callgraph file name");
			System.exit(-1);
		}

		CallGraph callGraph = new CallGraph();
		Parse(fileName,callGraph);
		CalConfidence con = new CalConfidence();
		con.PairConfidence(graph, functions, support, confidence);
	} //end of main method
	
	/* Parsing each line of the file, identify callers and callees */ 
	static void Parse(String fileName, CallGraph callgraph){
		// Ignore the <<null function>> part
		// Pattern of caller
		Pattern nodePattern = Pattern.compile("Call graph node for function: '(.*?)'<<.*>>  #uses=(\\d*).*$");
		// Pattern of callee
		Pattern functionPattern = Pattern.compile("CS<(.*)> calls function '(.*?)'.*$");
		String callerName = "";
		String calleeName;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String currentLine = null;
			currentLine = br.readLine();
			while(!currentLine.isEmpty())      // Until read the first line of content
			{
				currentLine = br.readLine();
			}
			
			while((currentLine = br.readLine()) != null)      // Not the end of the file
			{
				Matcher nodeMacher = nodePattern.matcher(currentLine);
				if(nodeMacher.find())              // Matches caller pattern
				{
					callerName = nodeMacher.group(1);           // caller's name is the first regular expression
					callgraph.addToFunctionSet(callerName);
				}
				Matcher functionMatcher = functionPattern.matcher(currentLine);
				if(functionMatcher.find())
				{
					calleeName = functionMatcher.group(2);      // callee's name is the second regular expression
					callgraph.addToFunctionSet(calleeName);
					callgraph.createGraph(calleeName, callerName);
				}
			}//end of while
			
			br.close();
			
		}catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
		}
		
		functions = callgraph.getFunctions();		
		graph = callgraph.getGraph();
		
	}//end of parse		
}//end of class
