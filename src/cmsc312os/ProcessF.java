package cmsc312os;

public class ProcessF extends Process1
{
	public ProcessF(int pid) 
	{
		this.PCB = new ProcessControlBlock (pid);
		// TODO Auto-generated constructor stub
	}
}
