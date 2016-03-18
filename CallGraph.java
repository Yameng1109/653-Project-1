import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*class description: Initial the graph and function hashmaps */
public class CallGraph {
	// <HashMap>functions stores ID as key, function's name as values
	private HashMap<Integer, String> functions = new HashMap<Integer, String>();
	// <HashMap> graph stores callee's id as key, caller's id as values
	private HashMap<Integer, HashSet<Integer>> graph = new HashMap<Integer, HashSet<Integer>>();
	
	/* initial function object with its name and its name's hashcode as ID */
	public void addToFunctionSet(String newFunction)
	{
		int newId = newFunction.hashCode();   
		functions.put(newId, newFunction);
	}
	
	/* initial the graph hashmap with function's id */
	public void createGraph(String calleeName, String callerName)
	{
		int calleeId = calleeName.hashCode();
		int callerId = callerName.hashCode();
		
		if (!graph.containsKey(calleeId))  //The callee key does not exist initial the key and its value
		{
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
