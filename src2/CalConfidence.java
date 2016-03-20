import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* Class Description: Calculating support and confidence of each pair of two functions */
public class CalConfidence {

	public void PairConfidence(HashSet<String> functions,		// Hashset of functions' name
			HashMap<String, HashSet<String>> graph, 	// The hashmap of each callee with caller set
			int support,	// The support parameter from the input arguments
			double confidence){		// The confidence parameter from the input arguments

		/* For pair(Foo, Bar), each iteration do
		 * calculate support and confidence of Foo
		 * find bug locations of Foo
		 */
		for(String calleeFoo: graph.keySet()){
			Set<String> callersFoo = graph.get(calleeFoo);
			int supportFoo = callersFoo.size();
			
			for(String calleeBar: graph.keySet()){				
				if(calleeFoo != calleeBar){
					/* Compare caller sets of Foo and Bar
					 * The number of common nodes is the pair(Foo,Bar) support
					 */
					Set<String> callersBar = graph.get(calleeBar);
					Set<String> tmp = new HashSet<String>();
					tmp.addAll(callersFoo);
					
					// Set the common nodes
					tmp.retainAll(callersBar);					
					int supportPair = tmp.size();
					
					if(supportPair >= support){
						double confidenceFoo = (double)supportPair/supportFoo;// Confidence of Foo on pair(Foo,Bar)

						if(confidenceFoo >= confidence){
							// The nodes not in common set are labeled as bugs
							Set<String> tmpFoo = new HashSet<String>();
							tmpFoo.addAll(callersFoo);
							tmpFoo.removeAll(tmp);		//Set of nodes not in common set
							if(!tmpFoo.isEmpty()){
								for(String callerFoo:tmpFoo){
									int compare = calleeFoo.compareTo(calleeBar);		// Sort the pair functions' names
									if(compare < 0){
										System.out.printf("bug: %s in %s, pair: (%s, %s), support: %d, confidence: %.2f%%\n", 
												calleeFoo, callerFoo, calleeFoo, calleeBar, supportPair, confidenceFoo*100);
									}else{
										System.out.printf("bug: %s in %s, pair: (%s, %s), support: %d, confidence: %.2f%%\n", 
												calleeFoo, callerFoo, calleeBar, calleeFoo, supportPair, confidenceFoo*100);
									}
								}
							}
						}	
					}					
				}
			}//end of inner for
		}//end of outer for
	}
	
}
