package cmsc312os;

public class Process1 extends ProcessMatrix
{

	public void fork(ProcessF child) //create child process
	{
		child.setParent(this);
		addChild(child);
		//waitt()
	}
	
	public void waitt() //set status to waiting
	{
		this.PCB.setState("WAITING");
	}
	
	public void exit() //kill process AND child processes
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
	}
	
	public void abort(int i) //kills child named by index in ArrayListj
	{
		getChildren().get(i).exit();
	}
	
	public void run ()
	{
		PCB.setState("RUNNING");
		//set memory, program counter, etc...
		
	}
	
}
