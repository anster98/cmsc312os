package cmsc312os;
import java.util.ArrayList;

public abstract class ProcessMatrix 
{
	
	private ArrayList<ProcessF> children = new ArrayList<ProcessF> (0); //stores all children
	private ProcessF parent; //keep track of parent
	public ProcessControlBlock PCB;

	public ProcessF getParent() {
		return parent;
	}

	public void setParent(ProcessF parent) {
		this.parent = parent;
	}

	public int getChildrenCount() {
		int size= children.size();
		return size;
	}
	
	public void addChild(ProcessF child)
	{
		children.add(child);
	}
	
	public ArrayList<ProcessF> getChildren ()
	{
		return children;
	}

	public void fork(ProcessF child) {} //create child process
	
	public void exit () {} //kill process
	
	public void waitt() {} //issued when waiting for termination of child processes
	
	public void abort(int i) {} //kill child
	
	public void exec() {} //called after fork to replace a process' memory space w new program

	
	
	
	
	
}
