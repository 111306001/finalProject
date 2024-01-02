public class Keyword {
	public String name;
    public int count;
    public float weight;
    
    public Keyword(String name, int count){
		this.name = name;
		this.count = count;
		this.weight = 1;
    }
    
    @Override
    public String toString(){
    	return "["+name+","+count+","+weight+"]";
    }

    public int getCount()
    {
    	return count;
    }
    
    public String getName()
    {
    	return name;
    }
    
    public float getWeight()
    {
    	return weight;
    }
}