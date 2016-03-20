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
	
	// functions<functionID, functionName>: A HashMap with functionID as key and functionName as value
	//static HashMap<Integer, String> functions = new HashMap<Integer, String>();
	static HashSet<String> functions = new HashSet<String>();

	// graph<calleeId, Set<callerID>>: A HashMap with calleeID as key and a HashSet of callerID as value
	//static HashMap<Integer, HashSet<Integer>> graph = new HashMap<Integer, HashSet<Integer>>();
	static HashMap<String, HashSet<String>> graph = new HashMap<String, HashSet<String>>();

	public static void main(String[] args){
		int support = T_SUPPORT;
		double confidence = T_CONFIDENCE/100;
		String fileName = "";
		// Get bitcode file's name and values for T_SUPPORT and T_CONFIDENCE from input arguments
		if (args.length > 0){
			fileName = args[0];
			
			switch(args.length){
			case 1:
				break;
			case 3:
				support = Integer.parseInt(args[1]);
				confidence = Double.parseDouble(args[2])/100;
				break;
			default:
				System.out.println("Error: Wrong Number of Input Arguments");
				System.exit(-1);
			}

		}else {
			System.out.println("Error: Argument needs to contain at least callgraph file name");
			System.exit(-1);
		}

		//Parse each line of call graph 
		CallGraph callGraph = new CallGraph();
		Parse(fileName,callGraph);
		
		//Calculate confidences and detect bugs
		CalConfidence con = new CalConfidence();
		con.PairConfidence(functions, graph, support, confidence);
		
	}//end of main method
	
	// This method parses each line of the input callgraph file
	// Store each function's name(String) and ID(hashCode) into functions
	// Map each calleeID and its callersID and stored them into graph 
	static void Parse(String fileName, CallGraph callgraph){
		Pattern nodePattern = Pattern.compile("Call graph node for function: '(.*?)'<<.*>>  #uses=(\\d*).*$");
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
				//Store node function's name
				nodeMacher = nodePattern.matcher(currentLine);
				if(nodeMacher.find()){
					callerName = nodeMacher.group(1);
					callgraph.addToFunctionSet(callerName);
				}
				//Store each function's callers
				functionMatcher = functionPattern.matcher(currentLine);
				if(functionMatcher.find()){
					calleeName = functionMatcher.group(1);
					callgraph.addToFunctionSet(calleeName);
					callgraph.createGraph(calleeName, callerName);
				}
			}//end of while
			callgraph.addfathercallers();
			
			br.close();
			
		}catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
		}
		
		//Get all functions and their callers
		functions = callgraph.getFunctions();		
		graph = callgraph.getGraph();		
	}//end of parse
	
}//end of class
