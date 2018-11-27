package cmsc312os;
import java.io.IOException;
import java.util.LinkedList;

public class STScheduler implements Runnable//pulls processes from ready queue into CPU to execute
{
	private LTScheduler LTS; //LTScheduler working with this STS
	private CPU cpu1; //CPU working with this scheduler
	private IO io;
	
	public STScheduler(LTScheduler LTS, CPU cpu1, IO io) //constructor
	{
		this.LTS=LTS;
		this.cpu1=cpu1;
		this.io=io;
		
	}
	
	public void run() //multithreading
	{
		try {
			sendToCPU();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendToCPU () throws IOException//continually sends processes to CPU
, InterruptedException
	{
//		long begin = System.nanoTime();
//		int count=0; //keeps track of number of processes that executed
		while (!LTS.ReadyQ.isEmpty()) //serve all foreground first
		{
			ProcessF current = LTS.ReadyQ.remove(0);
			System.out.println("Loading process to CPU");
			cpu1.setCurrent(current);
//			Thread th = new Thread (cpu1); //runs process thru multithreading
//			th.start();
			cpu1.execute();
			io.run();
			LTS.checkSemaQ();
			LTS.checkMemFullQ();
		}
		
		while (!LTS.BackQ.isEmpty()) //then all background
		{
			LTS.ReadyQ.add(LTS.BackQ.remove(0));
		}
		if (!LTS.ReadyQ.isEmpty())
		{
			sendToCPU();
		}
//		if (LTS.ReadyQ.isEmpty() && LTS.BackQ.isEmpty())
//		{
//			System.out.println("Throughput:" +count+ " proceses in " + (System.nanoTime()-begin) + "nanoseconds");
//		}
	}
	
}
