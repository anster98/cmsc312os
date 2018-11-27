package cmsc312os;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class LTScheduler //decides which processes will go into ready queue based on Shortest job first
{
	
	public ArrayList<ProcessF> ReadyQ = new ArrayList<ProcessF> (); //Ready Q, to be passed to STScheduler
	
	public ArrayList <ProcessF> BackQ = new ArrayList <ProcessF> (); //background processes only
	
	public ArrayList <ProcessF> SemaQ = new ArrayList <ProcessF>(); //waiting Queue for critical section
	
	public ArrayList <ProcessF> MemFullQ = new ArrayList <ProcessF>(); //loads here when memory is full
	
	private Memory memory;
        //private GUI gui;
	
	private int nextPid=0;
	
	public LTScheduler (Memory mem)
	{
		memory=mem;
                //this.gui=gui;
	}
	
	public void add(ProcessF process, boolean firstTime) throws IOException //add process into queue. FIRST TIME ONLY
	{
		if (firstTime)
		{
			process.PCB.setPid(nextPid);  //assign PID
			nextPid++;
			process.PCB.setFrames(memory.getFrames((int)((Math.random()*10)+1))); //random number of frames needed
			if (process.PCB.getFrames()==null)
			{
				MemFullQ.add(process);
				return;
			}
		}
		process.PCB.setState("READY");
		
		// to take out priority uncomment from here to end marker to take out priority scheduling
		//PRIORITY SCHEDULING
		if (ReadyQ.isEmpty()) //empty queue
		{
			ReadyQ.add(process);
			return;
		}
		else //need to compare
		{
			for (int i=0; i<ReadyQ.size(); i++)
			{
				if (process.getPriority()<=ReadyQ.get(i).getPriority()) //compares new process priority time to list
				{
					ReadyQ.add(i, process);
					return;
				}
			}
                        //To ignore priority comment from here upwards to other comment
			ReadyQ.add(process); //lowest priority. 
		}
                
	}
	
	public void refresh() //checks to see if processes have been waiting too long
	{
		for (int i=0; i<ReadyQ.size(); i++)
		{
			if (ReadyQ.get(i).PCB.getElapTime() > 100) //waiting for longer than 100 ms
			{
				ProcessF temp = ReadyQ.get(i);
				ReadyQ.remove(temp);
				ReadyQ.add(0,temp); //push to front of queue
			}
		}
	}
	
	public void block (ProcessF process) //add process to semaphore waiting queue
	{
		SemaQ.add(process);
		process.PCB.setState("WAITING");
		process.setS(-(SemaQ.size()-1)); //TODO: may be wrong!
	}
	
	public void signal () throws IOException //used for semaphore waiting queue
	{
		for (ProcessF process : SemaQ)
		{
			process.incrS();
		}
	}
	
	public void checkSemaQ() throws IOException //moves first process from SemaQ into ready queue
	{
		if (!SemaQ.isEmpty() && SemaQ.get(0).getS()==0)
		{
			ReadyQ.add(SemaQ.remove(0));
		}
	}
	
	public void addBGProcesses (int count) //add some number of background processes
	{
		for (int i=0; i<count; i++)
		{
			ProcessF process = new ProcessF(100, new File("process100.txt"));
			process.PCB.setPid(nextPid);  //assign PID
			nextPid++;
			process.PCB.setFrames(memory.getFrames((int)((Math.random()*10)+1))); //random number of frames needed
			process.PCB.setState("READY");
			BackQ.add(process);
		}
	}
	
	public void checkMemFullQ() throws IOException //moves first process from memoryfullQ into ready queue
	{
		if (!MemFullQ.isEmpty())
		{
			add(MemFullQ.remove(0), true);
		}
	}
	
	
	
	
}
