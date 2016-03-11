
public class Function {

	private int id;
	private String name;
	
	public Function(int functionId, String functionName){
		id = functionId;
		name = functionName;
	}
	
	public String getName(){
		return name;
	}
	
	public int getId(){
		return id;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
	    return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof Function)) return false;
	    return (name.equals(((Function)obj).getName()) );
	}
	
	
}
