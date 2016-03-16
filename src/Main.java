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
	// functions<functionID, functionName>: A HashMap with functionID as key and functionName as value
	static HashMap<Integer, String> functions = new HashMap<Integer, String>();
	// graph<calleeId, Set<callerID>>: A HashMap with calleeID as key and a HashSet of callerID as value
	static HashMap<Integer, HashSet<Integer>> graph = new HashMap<Integer, HashSet<Integer>>();
	
	public static void main(String[] args){
		int support = T_SUPPORT;
		double confidence = T_CONFIDENCE/100;
		String fileName = "";
		
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
			System.out.println("Error: Arument needs to contain at least callgraph file name");
			System.exit(-1);
		}

		CallGraph callGraph = new CallGraph();
		Parse(fileName,callGraph);
		CalConfidence con = new CalConfidence();
		con.PairConfidence(graph, functions, support, confidence);
	}//end of main method
	
	// This method parses each line of the input callgraph file
	// Store each function's name(String) and ID(hashCode) into functions
	// Map each calleeID and its callersID and stored them into graph 
	static void Parse(String fileName, CallGraph callgraph){
		Pattern nodePattern = Pattern.compile("Call graph node for function: '(.*?)'<<.*>>  #uses=(\\d*).*$");
		Pattern functionPattern = Pattern.compile("CS<(.*)> calls function '(.*?)'.*$");

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
				Matcher nodeMacher = nodePattern.matcher(currentLine);
				if(nodeMacher.find()){
					callerName = nodeMacher.group(1);
					callgraph.addToFunctionSet(callerName);
				}
				Matcher functionMatcher = functionPattern.matcher(currentLine);
				if(functionMatcher.find()){
					calleeName = functionMatcher.group(2);
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
