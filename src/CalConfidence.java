import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CalConfidence {

	public void PairConfidence(HashMap<Integer, HashSet<Integer>>graph, HashMap<Integer, String> functions,
			int support, double confidence){

		for(Integer keyFoo: graph.keySet()){
			String calleeFoo = functions.get(keyFoo);
			Set<Integer> callersFoo = graph.get(keyFoo);
			int supportFoo = callersFoo.size();
			
			for(Integer keyBar: graph.keySet()){
				String calleeBar = functions.get(keyBar);
				
				if(keyFoo != keyBar){

					Set<Integer> callersBar = graph.get(keyBar);
					Set<Integer> tmp = new HashSet<Integer>();
					tmp.addAll(callersFoo);
					tmp.retainAll(callersBar);					
					int supportPair = tmp.size();
					
					if(supportPair >= support){
						
						double confidenceFoo = (double)supportPair/supportFoo;

						if(confidenceFoo >= confidence){
							Set<Integer> tmpFoo = new HashSet<Integer>();
							tmpFoo.addAll(callersFoo);
							tmpFoo.removeAll(tmp);
							if(!tmpFoo.isEmpty()){
								for(Integer id:tmpFoo){
									String callerFoo = functions.get(id);
									int compare = calleeFoo.compareTo(calleeBar);
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
