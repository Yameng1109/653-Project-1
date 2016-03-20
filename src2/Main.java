import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	// Set default values for T_SUPPORT and T_CONFIDENCE
	public static int T_SUPPORT = 3;
	private static double T_CONFIDENCE = 65;
	
	// <HashMap> graph with callees' names as keys, the set of its callers' names as values
	static HashMap<String, HashSet<String>> graph = new HashMap<String, HashSet<String>>();

	/* Main function */
	public static void main(String[] args){
		int support = T_SUPPORT;
		double confidence = T_CONFIDENCE/100;
		String fileName = "";
		// Get bitcode file's name and values for T_SUPPORT and T_CONFIDENCE from input arguments
		if (args.length > 0){
			fileName = args[0];
			
			switch(args.length){
			case 1:				// filename with default value of T_SUPPORT, T_CONFIDENCE
				break;
			case 3:				// filename with T_SUPPORT, T_CONFIDENCE
				support = Integer.parseInt(args[1]);
				confidence = Double.parseDouble(args[2])/100;
				break;	
			default:			// Illegal input format
				System.out.println("Error: Wrong Number of Input Arguments");
				System.exit(-1);
			}

		}else {			// without any arguments
			System.out.println("Error: Arument needs to contain at least callgraph file name");
			System.exit(-1);
		}

		//Parse each line of call graph 
		CallGraph callGraph = new CallGraph();
		Parse(fileName,callGraph);
		
		//Calculate confidences and detect bugs
		CalConfidence con = new CalConfidence();
		con.PairConfidence(graph, support, confidence);
		
	}//end of main method
	
	/* Parsing each line of the file, identify callers and callees */
	static void Parse(String fileName, CallGraph callgraph){
		// Pattern of caller
		Pattern nodePattern = Pattern.compile("Call graph node for function: '(.*?)'<<.*>>  #uses=(\\d*).*$");
		// Pattern of callee
		Pattern functionPattern = Pattern.compile("CS<.*> calls function '(.*?)'.*$");
		Matcher nodeMacher, functionMatcher;
		
		String callerName = "";
		String calleeName;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String currentLine = null;
			currentLine = br.readLine();
			
			//skip the <<null function>> node
			while(!currentLine.isEmpty()){
				currentLine = br.readLine();
			}
			 
			while((currentLine = br.readLine()) != null){
				//Store caller's name
				nodeMacher = nodePattern.matcher(currentLine);
				if(nodeMacher.find()){
					callerName = nodeMacher.group(1);
				}
				//Store callee's callers
				functionMatcher = functionPattern.matcher(currentLine);
				if(functionMatcher.find()){
					calleeName = functionMatcher.group(1);
					callgraph.createGraph(calleeName, callerName);		//Map callee and caller
				}
			}//end of while
			callgraph.addfathercallers();
			
			br.close();
			
		}catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
		}
		
		//Get all functions and their callers
		graph = callgraph.getGraph();		
	}//end of parse
	
}//end of class
