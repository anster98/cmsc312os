package cmsc312os;

public class Frame 
{
	boolean free = true;
	boolean refBit = false; //2nd chance algorithm
	ProcessF assigned;

	public ProcessF getAssigned() {
		return assigned;
	}

	public void setAssigned(ProcessF assigned) {
		this.assigned = assigned;
	}

	public boolean isRefBit() {
		return refBit;
	}

	public void setRefBit(boolean refBit) {
		this.refBit = refBit;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}
	

}
