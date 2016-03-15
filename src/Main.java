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
	public static int T_SUPPORT = 3;
	private static double T_CONFIDENCE = 65;
	static HashMap<Integer, String> functions = new HashMap<Integer, String>();
	static HashMap<Integer, HashSet<Integer>> graph = new HashMap<Integer, HashSet<Integer>>();
	
	public static void main(String[] args){
		int support = T_SUPPORT;
		double confidence = T_CONFIDENCE/100;

		String fileName = "";
		if (args.length > 0){
			int i = 0;
			while(i < args.length){
				switch(i){
				case 0:
					fileName = args[0];
					break;
				case 1:
					support = Integer.parseInt(args[1]);
					break;
				case 2:
					confidence = Double.parseDouble(args[2])/100;
					break;
				default:
					System.out.println("Error: Wrong Number of Input Arguments");
					System.exit(-1);
				}
				i++;
			}
		}else {
			System.out.println("Error: Arument needs to contain CallGraph file name");
			System.exit(-1);
		}
		//System.out.println(confidence);
		CallGraph callGraph = new CallGraph();
		Parse(fileName,callGraph);
		CalConfidence con = new CalConfidence(graph, functions, support, confidence);
	}//end of main method
	
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
					//Function caller = new Function(callerName.hashCode(),callerName);
					//functions.add(caller);
					callgraph.addToFunctionSet(callerName);
					//System.out.println(caller.getId()+caller.getName());
				}
				Matcher functionMatcher = functionPattern.matcher(currentLine);
				if(functionMatcher.find()){
					calleeName = functionMatcher.group(2);
					//Function callee = new Function(calleeName.hashCode(),calleeName);
					//functions.add(callee);
					callgraph.addToFunctionSet(calleeName);
					callgraph.createGraph(calleeName, callerName);
					//System.out.println(callee.getId()+callee.getName());

				}
			}//end of while
			br.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
		}
		
		functions = callgraph.getFunctions();
/*		
		for( Map.Entry<Integer, String> entry:functions.entrySet()){
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
*/		
		graph = callgraph.getGraph();
		//System.out.println(graph);
		
	}//end of parse
	
	
}//end of class
