package cmsc312os;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class IO 
{
	public ArrayList<ProcessF> IoQ = new ArrayList <ProcessF>();
	private LTScheduler LTS;
	
	public IO(LTScheduler LTS)
	{
		this.LTS=LTS;
	}
	
	void run() throws IOException
	{
		if (IoQ.size()>0)
		{
			ProcessF current=IoQ.remove(0);
			FileReader fr = new FileReader(current.getCode());
			BufferedReader br= new BufferedReader(fr);
			String nextLine=br.readLine();
			
			for (int i=0; i<current.PCB.getProgramCounter(); i++) //resumes current progress for process
			{
				nextLine = br.readLine();
			}
			StringTokenizer st = new StringTokenizer (nextLine);
			
			st.nextToken(); //skip text, we know it says IO
//			int burst=0;
//			burst+=Integer.valueOf(st.nextToken());
			
			current.PCB.incrementPC();
			System.out.println(nextLine + " is complete");
			LTS.add(current, false);
			current.PCB.setState("READY");
			
		}
	}
}
