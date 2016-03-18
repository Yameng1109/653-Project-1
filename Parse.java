import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* Class description: Parsing the graph information into useful information */
public class Parse {

	public Parse(String fileName){
		// Caller
		Pattern nodePattern = Pattern.compile("Call graph node for function: '(.*?)'<<.*>> #uses=(\\d*).*$"); 
		// Callee
		Pattern functionPattern = Pattern.compile("CS<(.*)> calls function '(.*?)'.*$");
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String currentLine = null;
			while((currentLine = br.readLine()) != null){
				Matcher nodeMacher = nodePattern.matcher(currentLine);
				if(nodeMacher.find())
				{ }
				Matcher functionMatcher = functionPattern.matcher(currentLine);
				if(functionMatcher.find())
				{ }
			}//end of while
			br.close();			
		}catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
