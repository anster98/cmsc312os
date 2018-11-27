package cmsc312os;

import java.io.File;
import java.io.IOException;

public class ProcessF extends Process1
{
	private int cycles; //number of cycles remaining in CPU. NOT USED ANYMORE!!!
	private int memorySize;
	private File code;
	private int s; //semaphore
	
//	//MULTITHREADED METHOD
//	public synchronized void run()
//	{
//		try {
//			cpu1.run(this);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	public File getCode() {
		return code;
	}

	public void setCode(File code) {
		this.code = code;
	}

	public int getCycles() {
		return cycles;
	}

	public void setCycles(int cycles) {
		this.cycles = cycles;
	}

	public int getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(int memorySize) {
		this.memorySize = memorySize;
	}
	public ProcessF (int cycles, File code)
	{
		this.cycles=cycles;
		this.code=code;
		PCB=new ProcessControlBlock();
	}
}
