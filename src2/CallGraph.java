import java.util.HashMap;
import java.util.HashSet;

/*Class Description: create callgraph by adding nodes and edges*/
public class CallGraph {
	//graphs<callee,Set<callers>>
	private HashMap<String, HashSet<String>> graph = new HashMap<String, HashSet<String>>();
	
	//Add an edge between callee and caller
	public void createGraph(String calleeName, String callerName){
		if (!graph.containsKey(calleeName)){
			graph.put(calleeName, new HashSet<String>());
		}
		graph.get(calleeName).add(callerName);	
	}
	
	//Add an edge between callee and fathercaller
	public void addfathercallers(String calleeName){
		HashSet <String> fathercallers = new HashSet <String>();

		//System.out.printf("ee:%s\n",calleeName);
		for(String callerName : graph.get(calleeName)){
			//System.out.printf("er:%s\n",callerName);
			if(graph.containsKey(callerName)){
					for(String fathercallerName : graph.get(callerName)){
						if (graph.containsKey(fathercallerName)){
							//System.out.printf("fer:%s\n",fathercallerName);
							fathercallers.add(fathercallerName); 
						}
					}
					addfathercallers(callerName);
			}
		}

		for(String fathercaller : fathercallers){
			graph.get(calleeName).add(fathercaller);
		}
	}
	
	public HashMap<String, HashSet<String>> getGraph(){
			return graph;
	}
	
}
