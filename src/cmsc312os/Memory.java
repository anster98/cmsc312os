package cmsc312os;

import java.util.ArrayList;

public class Memory 
{
	ArrayList <Frame> mainMem = new ArrayList<Frame>(1024); //2048 MB memory divided into 2 mb pages = 1024 pages
	ArrayList <Frame> secMem = new ArrayList <Frame> (2048); //secondary memory is 2x as big
	ArrayList <Frame> cache = new ArrayList <Frame> (32);
	int []registers = new int[8];
	ArrayList <Resource> resources = new ArrayList<Resource> (5); //5 fake resources
	
	Mailbox m = new Mailbox();
	
	public Memory() //fills all memory list objects
	{
		for (int i=0; i<1024; i++)
		{
			mainMem.add(new Frame());
		}
		for (int i=0; i<2048; i++)
		{
			secMem.add(new Frame());
		}
		for (int i=0; i<32; i++)
		{
			cache.add(new Frame());
		}
		for (int i=0; i<5; i++) //fills with 5 objects
		{
			resources.add(new Resource());
		}
		
		
	}
	
	public ArrayList<Integer> getFrames(int pages) //returns all in secondary memory
	{
		ArrayList <Integer> assigned = new ArrayList <Integer>(); //stores indices of assigned frames
		int requested = pages;
		for (int i=0; i<secMem.size(); i++)
		{
			if (secMem.get(i).isFree())
			{
				assigned.add(i);
				secMem.get(i).setFree(false);
				pages--;
			}
			if (pages==0)
			{
				break;
			}
		}
		
		if (assigned.size()==requested) //successful
		{
			System.out.println("Loaded to memory");
			return assigned;
		}
		else //no more space
		{
			System.out.println("Memory full, added to waiting queue");
			freeSecMem(assigned);
			
			return null;
		}
	}
	
	public void freeSecMem (ArrayList<Integer> assigned)
	{
		for (int i: assigned)
		{
			secMem.get(i).setFree(true);
		}
	}
	
	public void freemainMem (ArrayList<Integer> assigned)
	{
		for (int i: assigned)
		{
			mainMem.get(i).setFree(true);
		}
	} 
	
	public ArrayList<Integer> demandPage(ArrayList<Integer> assigned) //moves all pages from secondary to main memory
	{
		ArrayList <Integer> processMem = new ArrayList <Integer>();
		int i=0;
		while (i<assigned.size())
		{
			if ((int)(Math.random()*100)==5) //1% chance of needing to use cache
			{
				cacheAdd();
				i++;
			}
			else
			{
				for (int j=0; j<mainMem.size();j++)
				{
					if (mainMem.get(j).isFree())
					{
						processMem.add(j);
						mainMem.get(j).setRefBit(true);
						i++;
					}
					else if (!mainMem.get(j).isRefBit()) //reference bit is 0
					{
						processMem.add(j);
						mainMem.get(j).setRefBit(true);
					}
					else //reference bit is 1
					{
						mainMem.get(j).setRefBit(false);
					}
					
					if (i==assigned.size())
					{
						freeSecMem(assigned); //clears it from secondary memory
						return processMem;
					}
				}
//				if (i<assigned.size()) //need to go thru memory again
//				{
//					System.out.println("System is no longer in a safe state"); //deadlock avoidance algorithm
//				}
			}
		}
		
		//this shouldnt really ever happen
		freeSecMem(assigned); //clears it from secondary memory
		return processMem;
	}
	
	public void cacheAdd() //add to cache
	{
		for (int i=0; i<cache.size(); i++) //look for free cache page
		{
			if (cache.get(i).isFree())
			{
				cache.get(i).setFree(false);
				return;
			}
		}
		cache.get((int)(Math.random()*32)).setFree(false); //kick random cache frame out
		System.out.println("cache used");
		
	}
	
	public void useRegisters() //simulate register usage
	{
		for (int i=0; i<8; i++)
		{
			registers[i] = (int)(Math.random() *100);
		}
	}
	
	public int requestResources (int max) throws InterruptedException //banker's algorithm. returns 0 for success, 1 for fail
	{
		int available=0; //keeps track of # of avail resources
		int requested=max; //store for later
		for (Resource r : resources)
		{
			if (r.isFree())
			{
				available++;
			}
		}
		if (max>=available) //enough resources
		{
			System.out.println("Access to resources granted");
			for (Resource r : resources)
			{
				if (r.isFree()&& max>0)
				{
					r.setFree(false);
					max--;
				}
			}
			System.out.println("Returning resources after 500 ms");
			for (Resource r : resources) //returning resources
			{
				if (!r.isFree()&& requested>0)
				{
					r.setFree(true);
					requested--;
				}
			}
			return 0;
		}
		else //not enough resources at this time
		{
			System.out.println("Access to resources denied");
			return 1;
		}
	}
	
}
