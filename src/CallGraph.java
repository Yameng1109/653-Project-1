import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CallGraph {

	private HashMap<Integer, String> functions = new HashMap<Integer, String>();
	private HashMap<Integer, HashSet<Integer>> graph = new HashMap<Integer, HashSet<Integer>>();
	
	public void addToFunctionSet(String newFunction){
		int newId = newFunction.hashCode();
		functions.put(newId, newFunction);
	}
	
	public void createGraph(String calleeName, String callerName){
		int calleeId = calleeName.hashCode();
		int callerId = callerName.hashCode();
		if (!graph.containsKey(calleeId)){
			graph.put(calleeId, new HashSet<Integer>());
		}
		graph.get(calleeId).add(callerId);		
	}
	
	public HashMap<Integer, String> getFunctions(){
		return functions;
	}
	
	public HashMap<Integer, HashSet<Integer>> getGraph(){
		return graph;
	}
	
}
