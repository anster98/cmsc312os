package cmsc312os;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class CPU//represents 1 CORE
{
	private ProcessF current;
	private LTScheduler LTS;
	private IO io;
	private Memory memory;
	
	public CPU (LTScheduler LTS, IO io, Memory mem)
	{
		this.LTS=LTS;
		this.io=io;
		memory = mem;
	}
	
//	public void run() //MULTITHREADING IMPLEMENTATION
//	{
//		try {
//			execute();
//		} catch (IOException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public void execute() throws IOException //50ms cycles ROUND ROBIN
, InterruptedException
	{
		//current=process; //sets current process instance var
		
		if ((int)(Math.random()*100)==5) //random external IO interrupt
		{
			Thread.sleep(100);
		}
		current.PCB.setState("RUNNING");
		
		current.PCB.setFrames(memory.demandPage(current.PCB.getFrames()));
		
		memory.useRegisters(); //simulates register use
		int burst=0;
		
		FileReader fr = new FileReader(current.getCode());
		BufferedReader br= new BufferedReader(fr);
		String nextLine=br.readLine();
		
		for (int i=0; i<current.PCB.getProgramCounter(); i++) //resumes current progress for process
		{
			nextLine = br.readLine();
		}
		
		if (current.PCB.getTempcycles()!=0) //CPU cycle halfway completed
		{
			burst+=current.PCB.getTempcycles();
			current.PCB.setTempcycles(0);
			current.PCB.incrementPC();
			System.out.println(nextLine + " is now fully complete"); 
			nextLine=br.readLine();
		}
			
//		StringTokenizer st = new StringTokenizer (nextLine);
		
		while (nextLine != null) //has something to do
		{
			StringTokenizer st = new StringTokenizer (nextLine);
			String token = st.nextToken();
			if (token.equals("CPU")) //CPU Burst
			{
				
				burst += Integer.valueOf(st.nextToken());
				
				
				if (burst>=50) //burst is over, needs to wait for next cycle to execute.
				{
					if (burst >50) //full CPU burst cannot complete this time
					{
						current.PCB.setTempcycles(burst-50);
						System.out.println(nextLine + " is partially complete.");
						
					}
					else //full CPU burst is done, so end cycle
					{
						current.PCB.incrementPC();
						System.out.println(nextLine + " is compelete");
					}
					nextLine = null;
					endCycle(0); //flag for more bursts
				}
				
				else { //burst not over
					System.out.println(nextLine + " is compelete");
					nextLine = br.readLine();
					current.PCB.incrementPC();} //increment program counter
			}
			else if (token.equals("IO")) // IO Burst
			{
				io.IoQ.add(current);
				current.PCB.setState("WAITING");
				current=null; //remove from CPU
				return;
			}
			
			else if (token.equals("fork")) //fork child
			{
				Random rdm = new Random();
				if (rdm.nextBoolean()) //single level child. PARENT WAITS FOR CHILD TO FINISH
				{
					fork(new ProcessF(100, new File ("process100.txt")), false);
					
				}
				else //this child will fork another child. Multi-level
				{
					fork (new ProcessF(50, new File ("process50.txt")), false);
				}
				System.out.println("child forked");
				nextLine= br.readLine();
			}
			
			else if (token.equals("cs")) //enter critical section
			{
				LTS.block(current);
				current.PCB.incrementPC();
				System.out.println("Process " +current.PCB.getPid()+ " is entering the critical section");
				return;
			}
			
			else if (token.equals("cse")) //exit critical section
			{
				LTS.signal();
				current.PCB.incrementPC();
				System.out.println("Process " +current.PCB.getPid()+ " is exiting the critical section");
				LTS.add(current, false);
				return;
			}
			else if (token.equals("sendmsg"))
			{
				current.PCB.incrementPC();
				if (!LTS.ReadyQ.isEmpty())
				{
					System.out.println("Sending message");
					current.sendMsg(LTS.ReadyQ.get(0), "hello");
				}
				else
				{
					System.out.println("No recipients for message");
				}
				
				nextLine=br.readLine();
			}
			else if (token.equals("sendmail"))
			{
				current.sendMail(memory.m, "Hello");
				current.PCB.incrementPC();
				System.out.println("Sending mail");
				nextLine=br.readLine();
			}
			
			else if (token.equals("getmail"))
			{
				current.getMail(memory.m);
				current.PCB.incrementPC();
				System.out.println("Getting mail");
				nextLine=br.readLine();
			}
			else if (token.equals("resource"))
			{
				int max = Integer.valueOf((st.nextToken()));
				if (memory.requestResources(max)==0)
				{
					current.PCB.incrementPC();
					nextLine=br.readLine();
				}
				else
				{
					nextLine=null;
					endCycle(0);
				}
				
			}
			else //process over
			{
				System.out.println(nextLine);
				nextLine = null;
				endCycle(1); //flag for yield
			}
		}
	}
	
//	public void executePreemptive (ProcessF process) throws IOException 
//	{
//		ProcessF kickedout = current;
//		LTS.add(kickedout);
//		execute(process);
//	}

	public void endCycle(int status) throws IOException //end cpu burst
	{
//		current.setCycles(current.getCycles()-50);
		
		if(status==1) //process is complete via YIELD
		{
			if (current.getParent()!= null && current.getParent().PCB.getState()=="WAITING") //if parent was waiting
			{
				LTS.add(current.getParent(), false); //add parent back to ready queue
			}
			current.exit();
			memory.freemainMem(current.PCB.getFrames()); //free memory
			current=null;
		}
		else //needs another cycle
		{
			LTS.add(current, false);
			current=null;
		}
	}

	public void fork(ProcessF child, boolean wait) throws IOException//create child process
	{
		child.setParent(current);
		current.addChild(child);
		if (wait==true)
		{
			current.PCB.setState("WAITING");
			current=null;
			LTS.add(child, true);
		}
		else
		{
			LTS.add(child, true);
		}
	}
	
	public ProcessF getCurrent() {
		return current;
	}

	public void setCurrent(ProcessF current) {
		this.current = current;
	}

}
