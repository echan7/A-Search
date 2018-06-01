/**
 * A data-structure to keep (state,fValue) pairs. This class will helpful to add
 * states to priority queues. You can add a state with the f() value to a queue
 * by adding (state,f() value) pair to the priority queue.
 * 
 * You do not need to change this class.
 */
public class StateFValuePair implements Comparable<StateFValuePair> {
	private State state;
	private double fValue;

	public StateFValuePair(State state, double fValue) {
		this.state = state;
		this.fValue = fValue;
	}

	public State getState() {
		return state;
	}

	public double getFValue() {
		return fValue;
	}

	@Override
	public int compareTo(StateFValuePair o) {
		if (this.fValue > o.fValue)
			return 1;
		else if (this.fValue < o.fValue)
			return -1;

		return 0;
	}

	/**
	 * override function to check if coordinates match
	 */
	public boolean equals(Object o){
		if(o instanceof State){
			if(this.getState().getSquare().X == ((State) o).getSquare().X && this.getState().getSquare().Y == ((State) o).getSquare().Y){
				return true;
			}
		}
		return false;
	}
}
