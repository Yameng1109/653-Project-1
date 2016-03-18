public class Function {

	private int id;
	private String name;
	
	public Function(int functionId, String functionName) 
	{
		// initialize function object with ID and functinName
		id = functionId;
		name = functionName;
	}
	
	//return the function's name
	public String getName()
	{
		return name;
	}
	
	//return the hashcode of the function
	public int getId()
	{
		return id;
	}
	
	//Using the hashcode to find function's Name
	public String findName(Integer functionId){
		if(functionId == id)
			return name;
		else 
			return null;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
	    return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof Function)) //if the object is not a function object
			return false;
	    return (name.equals(((Function)obj).getName()));
	}
	
	
}
