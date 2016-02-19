package mh.shiftcalendaram.templates;



public class AlternativeShifts{
	

	
	String kind;
	int position;
	String month;
	String year;
	int custom;
	String color;
	
	public AlternativeShifts(String kind, int position, String month, String year, int custom, String color)
	{
		this.kind = kind;
		this.position = position;
		this.month = month;
		this.year = year;
		this.custom = custom;
		this.color = color;
	}
	
	public String getKind()
	{
		return kind;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public String getMonth()
	{
		return month;
	}
	
	public String getYear()
	{
		return year;
	}
	
	public int getCustom()
	{
		return custom;
	}
	
	public String getColor()
	{
		return color;
	}
	
	

}
