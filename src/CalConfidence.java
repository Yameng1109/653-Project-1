import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CalConfidence {

	public void PairConfidence(HashMap<Integer, HashSet<Integer>>graph, HashMap<Integer, String> functions,
			int support, double confidence){

		for(Integer keyFoo: graph.keySet()){
			String calleeFoo = functions.get(keyFoo);
			for(Integer keyBar: graph.keySet()){
				String calleeBar = functions.get(keyBar);
				if(keyFoo != keyBar){
					Set<Integer> callersFoo = graph.get(keyFoo);
					//System.out.println(callersFoo);
					Set<Integer> callersBar = graph.get(keyBar);
					int supportFoo = callersFoo.size();
					int supportBar = callersBar.size();
					Set<Integer> tmp = new HashSet<Integer>();
					tmp.addAll(callersFoo);
					tmp.retainAll(callersBar);
					//System.out.println(tmp);
					int supportPair = tmp.size();
					if(supportPair >= support){
						double confidenceFoo = (double)supportPair/supportFoo;
						//System.out.println(confidenceFoo);
						double confidenceBar = (double)supportPair/supportBar;
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
									//System.out.println("bug: "+calleeFoo +" in "+ callerFoo +" pair: ("
									//		calleeFoo + ", " + calleeBar +"), support: "+supportFoo+" confidence: "%.2f%%\n",
									//		, , , confidenceFoo);
									
								}
							}
						}
					/*
						if(confidenceBar >= confidence){
							Set<Integer> tmpBar = new HashSet<Integer>();
							tmpBar.addAll(callersBar);
							
							if(!tmpBar.isEmpty()){
								for(Integer id:tmpBar){
									String callerBar = functions.get(id);
									System.out.printf("bug: %s in %s, pair: (%s, %s), support: %d, confidence: %.2f%%\n", 
											calleeBar, callerBar, calleeBar, calleeFoo, supportPair, confidenceBar);
								}
							}
						}
						*/
						
						
					}					
				}
			}//end of inner for
		}//end of outer for
	}
	
}
