import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static int T_SUPPORT = 3;
	public static int T_CONFIDENCE = 65;
	HashSet<Function> functions = new HashSet<Function>();
	public static void main(String[] args){
		int support = T_SUPPORT;
		double confidence = T_CONFIDENCE/100;
		String fileName = "";
		if (args.length > 0){
			int i = 0;
			while(i < args.length){
				switch(i){
				case 0:
					fileName = args[0];
					break;
				case 1:
					support = Integer.parseInt(args[1]);
					break;
				case 2:
					confidence = Double.parseDouble(args[2])/100;
					break;
				default:
					System.out.println("Error: Wrong Number of Input Arguments");
					System.exit(-1);
				}
				i++;
			}
		}else {
			System.out.println("Error: Arument needs to contain CallGraph file name");
			System.exit(-1);
		}
		//CallGraph callGraph = new CallGraph();
		Parse(fileName);
		
	}//end of main method
	
	private static void Parse(String fileName){
		Pattern nodePattern = Pattern.compile("Call graph node for function: '(.*?)'<<.*>>  #uses=(\\d*).*$");
		Pattern functionPattern = Pattern.compile("CS<(.*)> calls function '(.*?)'.*$");

		String callerName;
		String calleeName;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String currentLine = null;
			currentLine = br.readLine();
			//skip the <<null function>> node
			while(!currentLine.isEmpty()){
				currentLine = br.readLine();
			}
			
			while((currentLine = br.readLine()) != null){
				Matcher nodeMacher = nodePattern.matcher(currentLine);
				if(nodeMacher.find()){
					callerName = nodeMacher.group(1);
					Function caller = new Function(callerName.hashCode(),callerName);
					System.out.println(caller);
				}
				Matcher functionMatcher = functionPattern.matcher(currentLine);
				if(functionMatcher.find()){
					calleeName = functionMatcher.group(2);
					Function callee = new Function(calleeName.hashCode(),calleeName);
					System.out.println(callee);

				}
			}//end of while
			br.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
		}		
	}//end of parse
	
	
}//end of class
