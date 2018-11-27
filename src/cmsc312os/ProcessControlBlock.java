package cmsc312os;
import java.util.ArrayList;
import java.io.File;

public class ProcessControlBlock 
{
	private String state = "NEW"; 
	private int pid;
	private boolean cooperating = false;
	private int ProgramCounter=0; //starts at  first line
	private int tempcycles=0;
//	private boolean preemptive; //needs to execute NOW

	//private ArrayList <Register>???
	private ArrayList <Integer> frames= new ArrayList <Integer> ();
	private int CPU; //which cpu is being used
	private int elapTime=0;
	private int timeLim=50; //50 ms?? for example
	//private ArrayList<File> files = new ArrayList <File>(0); //keep track of files being used
	//private ArrayList<Object> IOdevices = new ArrayList <Object>(0); //keep track of IO devices
	

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public boolean isCooperating() {
		return cooperating;
	}
	public void setCooperating(boolean cooperating) {
		this.cooperating = cooperating;
	}

	public int getProgramCounter() {
		return ProgramCounter;
	}
	public void setProgramCounter(int programCounter) {
		ProgramCounter = programCounter;
	}
	public void incrementPC()
	{
		ProgramCounter++;
	}

	public int getCPU() {
		return CPU;
	}
	public void setCPU(int cPU) {
		CPU = cPU;
	}
	public int getElapTime() {
		return elapTime;
	}
	public void setElapTime(int elapTime) {
		this.elapTime = elapTime;
	}
	public int getTimeLim() {
		return timeLim;
	}
	public void setTimeLim(int timeLim) {
		this.timeLim = timeLim;
	}

	public int getTempcycles() {
		return tempcycles;
	}
	public void setTempcycles(int tempcycles) {
		this.tempcycles = tempcycles;
	}
	public ArrayList<Integer> getFrames() {
		return frames;
	}
	public void setFrames(ArrayList<Integer> frames) {
		this.frames = frames;
	}
}
