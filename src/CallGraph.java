import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CallGraph {

	private HashMap<Integer, String> functions = new HashMap<Integer, String>();
	
	public void addToFunctionList(String newFunction){
		int newId = newFunction.hashCode();
		functions.put(newId, newFunction);
	}
	
	public HashMap<Integer, String> getFunctions(){
		return functions;
	}
	
}
