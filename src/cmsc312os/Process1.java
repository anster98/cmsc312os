package cmsc312os;

import java.io.File;

public class Process1 extends ProcessMatrix
{

	protected String message; //stores a message
	protected int s; //semaphore
	protected int priority = (int) (Math.random()*9);
	protected CPU cpu1;
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}
	
	public void incrS()
	{
		this.s++;
	}

	public void waitt() //set status to waiting
	{
		this.PCB.setState("WAITING");
		//should do more stuff here
	}
	
	public void exit() //kill process AND child processes. 
	{
		if (getChildrenCount()==0)
		{
			this.PCB.setState("TERMINATED");
		}
		else 
		{
			for (int i=0; i<getChildrenCount(); i++)
			{
				abort(i);
			}
			this.PCB.setState("TERMINATED");
		}
		/////////////////////////////////////////////////////////
		// need to clear from memory?
		/////////////////////////////////////////////////////////
	}
	
	public void abort(int i) //kills child named by index in ArrayListj
	{
		getChildren().get(i).exit();
	}
	
	public void sendMsg (ProcessF receiver, String str) //simulates interprocess communication
	{
		receiver.recMsg(str);
	}
	
	public void recMsg (String str) //receives message and stores it
	{
		message=str;
	}
	
	void getMail(Mailbox m) //gets mail
	{
		message=m.getMail();
	}
	
	void sendMail(Mailbox m, String str) //sends mail
	{
		m.sendMail(str);
	}

	public CPU getCpu() {
		return cpu1;
	}

	public void setCpu(CPU cpu1) {
		this.cpu1 = cpu1;
	}

	
}
