import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CalConfidence {

	public void PairConfidence(HashSet<String> functions,
			HashMap<String, HashSet<String>> graph, 
			int support, double confidence){

		for(String calleeFoo: graph.keySet()){
			Set<String> callersFoo = graph.get(calleeFoo);
			int supportFoo = callersFoo.size();
			
			for(String calleeBar: graph.keySet()){
				
				if(calleeFoo != calleeBar){

					Set<String> callersBar = graph.get(calleeBar);
					Set<String> tmp = new HashSet<String>();
					tmp.addAll(callersFoo);
					tmp.retainAll(callersBar);					
					int supportPair = tmp.size();
					
					if(supportPair >= support){
						
						double confidenceFoo = (double)supportPair/supportFoo;

						if(confidenceFoo >= confidence){
							Set<String> tmpFoo = new HashSet<String>();
							tmpFoo.addAll(callersFoo);
							tmpFoo.removeAll(tmp);
							if(!tmpFoo.isEmpty()){
								for(String callerFoo:tmpFoo){
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
