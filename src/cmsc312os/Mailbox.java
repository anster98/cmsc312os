package cmsc312os;

public class Mailbox 
{
	private String data;
	
	String getMail()
	{
		return data;
	}
	
	void sendMail(String str)
	{
		data=str;
	}
	
}
