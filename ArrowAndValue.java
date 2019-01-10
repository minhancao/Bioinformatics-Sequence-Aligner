public class ArrowAndValue {
	public String arrow;
	public int value;
	public ArrowAndValue(String a, int b)
	{
		arrow = a;
		value = b;
	}
	
	public ArrowAndValue()
	{
		
	}
	
	public void setArrow(String a)
	{
		arrow = a;
	}
	
	public void setValue(int b)
	{
		value = b;
	}
	
	public void setArrowAndValue(String a, int b)
	{
		arrow = a;
		value = b;
	}
	
	public String getArrow()
	{
		return arrow;
	}
	
	public int getValue()
	{
		return value;
	}

}