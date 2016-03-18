import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* Class Description: Calculating the confidence of each pair of two functions */
public class CalConfidence {

	/* function to calculate */
	public void PairConfidence(HashMap<Integer, HashSet<Integer>> graph,  // The hashmap of each callee'id with its callers'ids
			HashMap<Integer, String> functions,     // Hashmap functions used for finding function name by its ID(hashcode)
			int support,           // The support parameter from the input
			double confidence)     // The confidence parameter from the input
	{
		for(Integer keyFoo: graph.keySet())                 // The former function(Foo)
		{
			String calleeFoo = functions.get(keyFoo);       // Finding function's name by its id
			Set<Integer> callersFoo = graph.get(keyFoo);    // Former function's callers
			int supportFoo = callersFoo.size();             // The number of times this callee function appears
			
			for(Integer keyBar: graph.keySet())             // The later function(Bar)
			{
				String calleeBar = functions.get(keyBar);
				if(keyFoo != keyBar)                        // different two functions
				{
					Set<Integer> callersBar = graph.get(keyBar);      // The later function's caller 
					Set<Integer> tmp = new HashSet<Integer>();        // Initialize a temporary set 
					tmp.addAll(callersFoo);                           // Is equal to former function's callers
					tmp.retainAll(callersBar);					      // tmp is the intersection of two functions' callers
					int supportPair = tmp.size();                     // the number of time two functions appears together
					
					if(supportPair >= support)        // satisfy the minimal requirement for support
					{
						double confidenceFoo = (double)supportPair/supportFoo; // The confidence based on former function
						if(confidenceFoo >= confidence)                        // satisfy the minimal requirement for confidence
						{
							Set<Integer> tmpFoo = new HashSet<Integer>();    // Initialize a temporary set 
							tmpFoo.addAll(callersFoo);                       // is equal to the callers of former function
							tmpFoo.removeAll(tmp);                           // tmpFoo only calls former function but not later one
							if(!tmpFoo.isEmpty()){
								for(Integer id:tmpFoo){
									String callerFoo = functions.get(id);
									int compare = calleeFoo.compareTo(calleeBar);   // sort the pair functions' names
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
