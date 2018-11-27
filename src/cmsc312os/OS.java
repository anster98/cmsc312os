package cmsc312os;
//TODO: fix critical section
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class OS {

	public static void main(String[] args) throws IOException, InterruptedException
	{
		
		Memory memory = new Memory();
                GUI gui = new GUI();
		gui.start();
                
		LTScheduler LTS = new LTScheduler(memory); //CORE 1
		IO io = new IO(LTS);
		CPU cpu1 = new CPU(LTS, io, memory);
		STScheduler STS = new STScheduler(LTS, cpu1,io);
		
		LTScheduler LTS2 = new LTScheduler(memory); //CORE2
		IO io2 = new IO(LTS2);
		CPU cpu2 = new CPU(LTS2, io2, memory);
		STScheduler STS2 = new STScheduler(LTS2, cpu2,io2);
		
		
		Scanner scan = new Scanner (System.in);
		System.out.println("Add a process? Y or N");
		String add = scan.nextLine();
		
		while (add.equalsIgnoreCase("y"))
		{
				
				LTS.addBGProcesses(20); //add 20 background processes to cpu1
				LTS2.addBGProcesses(20); //add to cpu2
				
				System.out.println("How many for each core?");
				int quantity = scan.nextInt();
				scan.nextLine();
				int random=0;
				
				for (int i=0; i<quantity;i++)
				{
					random = (int)Math.random()*3;
					{
						if (random==0)
						{
							LTS.add((new ProcessF(50, new File("process50.txt"))), true);
							LTS2.add((new ProcessF(50, new File("process50.txt"))), true);
							System.out.println("Process created");
						}
						else if (random==1)
						{
							LTS.add(new ProcessF(100, new File("process100.txt")), true);
							LTS2.add(new ProcessF(100, new File("process100.txt")), true);
							System.out.println("process created");
						}
						else
						{
							LTS.add(new ProcessF(150, new File("process150.txt")), true);
							LTS2.add(new ProcessF(150, new File("process150.txt")), true);
							System.out.println("process created");
						}
					}
				}
                                
				
//				if (quantity%2==0)
//				{
//					for (int i=0; i<quantity; i++)
//					{
//						LTS.add((new ProcessF(50, new File("process50.txt"))), true);
//						LTS2.add((new ProcessF(50, new File("process50.txt"))), true);
//						System.out.println("Process created");
//					}
//				}
//				else 
//				{
//					for (int i=0; i<quantity; i++)
//					{
//						LTS.add(new ProcessF(100, new File("process100.txt")), true);
//						LTS2.add(new ProcessF(100, new File("process100.txt")), true);
//						System.out.println("process created");
//					}
//				}
                       gui.addRows(LTS.ReadyQ);
//			GUI gui = new GUI (LTS.ReadyQ);
//                        gui.start();
			long begin = System.nanoTime();
					
			Thread th = new Thread (STS);
			th.start();
			Thread th2 = new Thread (STS2);
			th2.start();
			
			while (th.isAlive() || th2.isAlive())
			{}
			long end = System.nanoTime();
			
			
			
			Thread.sleep(1000);
			System.out.println("Throughput: " +(quantity*2 + 40) + " processes in " + (end-begin) + " nanoseconds");
			System.out.println("Add a process? Y or N");
			add = scan.nextLine();
		}
	}
}
