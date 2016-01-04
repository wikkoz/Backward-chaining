package Events;

/**
* 
* 
* @author Kamil Zieliñski
*/
public class ButtonEvent extends ApplicationEvent
{
	//String,witch includes data txt from View
	String dataString;
	
	public ButtonEvent(String dataString)
	{
		super();
		this.dataString = dataString;
	}

	public String getDataString()
	{
		return dataString;
	}

	public void setDataString(String dataString)
	{
		this.dataString = dataString;
	}
}
