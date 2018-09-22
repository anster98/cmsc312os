package cmsc312os;
import java.util.ArrayList;
import java.io.File;

public class ProcessControlBlock 
{
	private String state = "NEW"; 
	private int pid;
	private boolean cooperating = false;
	private int priority;
	private int ProgramCounter;
	
	//private ArrayList <Register>???
	private String memMin; //min and max locations in memory
	private String memMax;
	private int CPU; //which cpu is being used
	private int elapTime=0;
	private int timeLim;
	//private ArrayList<File> files = new ArrayList <File>(0); //keep track of files being used
	//private ArrayList<Object> IOdevices = new ArrayList <Object>(0); //keep track of IO devices
	
	
	public ProcessControlBlock(int pid) //constructor
	{
		this.pid=pid;
	}
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
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getProgramCounter() {
		return ProgramCounter;
	}
	public void setProgramCounter(int programCounter) {
		ProgramCounter = programCounter;
	}
	public String getMemMin() {
		return memMin;
	}
	public void setMemMin(String memMin) {
		this.memMin = memMin;
	}
	public String getMemMax() {
		return memMax;
	}
	public void setMemMax(String memMax) {
		this.memMax = memMax;
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
}
