import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CallGraph {

	private HashSet<String> functions = new HashSet<String>();
	private HashMap<String, HashSet<String>> graph = new HashMap<String, HashSet<String>>();
	private HashMap<String, HashSet<String>> graphcopy = new HashMap<String, HashSet<String>>();
	
	public void addToFunctionSet(String newFunction){
		functions.add(newFunction);
	}
	
	public void createGraph(String calleeName, String callerName){
		if (!graph.containsKey(calleeName)){
			graph.put(calleeName, new HashSet<String>());
			graphcopy.put(calleeName, new HashSet<String>());
		}
		graph.get(calleeName).add(callerName);	
		graphcopy.get(calleeName).add(callerName);	
	}
	
	public void addfathercallers(){
		/*for(String calleeName : graphcopy.keySet()){
			System.out.printf("ee:%s\n",calleeName);
			for(String callerName : graphcopy.get(calleeName)){
				System.out.printf("er:%s\n",callerName);
			}
			System.out.printf("\n");
		}*/

		for(String calleeName : graphcopy.keySet()){
			//System.out.printf("ee:%s\n",calleeName);
			for(String callerName : graphcopy.get(calleeName)){
				//System.out.printf("er:%s\n",callerName);
				if(graphcopy.containsKey(callerName)){
					Set<String> fathercallers = graphcopy.get(callerName);		
					if(!fathercallers.isEmpty()){
						for(String fathercallerName : graphcopy.get(callerName)){
							if (graphcopy.containsKey(fathercallerName)){
								//System.out.printf("fer:%s\n",fathercallerName);
								graph.get(calleeName).add(fathercallerName);	
							}
						}
					}
				}
			}
			//System.out.printf("\n");
		}
		
		/*for(String calleeName : graph.keySet()){
			System.out.printf("ee:%s\n",calleeName);
			for(String callerName : graph.get(calleeName)){
				System.out.printf("er:%s\n",callerName);
			}
			System.out.printf("\n");
		}*/
	}
	
	public HashSet<String> getFunctions(){
		return functions;
	}
	
	public HashMap<String, HashSet<String>> getGraph(){
		return graph;
	}
	
}
