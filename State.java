import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

	private Square square;
	private State parent;

	// Maintain the gValue (the distance from start)
	// You may not need it for the BFS but you will
	// definitely need it for AStar
	private int gValue;

	// States are nodes in the search tree, therefore each has a depth.
	private int depth;

	/**
	 * @param square
	 *            current square
	 * @param parent
	 *            parent state
	 * @param gValue
	 *            total distance from start
	 */
	public State(Square square, State parent, int gValue, int depth) {
		this.square = square;
		this.parent = parent;
		this.gValue = gValue;
		this.depth = depth;
	}

	/**
	 * @param visited
	 *            explored[i][j] is true if (i,j) is already explored
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {
		// FILL THIS METHOD
		/**
		 * Initialize an arraylist and initialize boolean for all successors
		 */
		ArrayList<State> ambilSuccessors = new ArrayList<State>();
		char[][] lapisanMaze = maze.getMazeMatrix();
		int curX = this.getX();
		int curY = this.getY();
		// TODO check all four neighbors (up, right, down, left)
		//check up
		boolean validUp = true;
		boolean validRight = true;
		boolean validDown = true;
		boolean validLeft = true;
		/**
		 * for every successor, check if it went ourofbounds or reach a wall, if either is true, 
		 * set validity to false;
		 */
		if(outOfBounds(curX-1, curY, maze) || lapisanMaze[curX-1][curY] == '%' /*|| explored[curX-1][curY] == true*/){
			validUp = false;
		}
		if(outOfBounds(curX, curY+1, maze) || lapisanMaze[curX][curY+1] == '%' /*|| explored[curX][curY+1] == true*/){
			validRight = false;
		}
		if(outOfBounds(curX+1, curY, maze) || lapisanMaze[curX+1][curY] == '%' /*|| explored[curX+1][curY] == true*/){
			validDown = false;
		}
		if(outOfBounds(curX, curY-1, maze) || lapisanMaze[curX][curY-1] == '%' /*|| explored[curX][curY-1] == true*/){
			validLeft = false;
		}
		// TODO remember that each successor's depth and gValue are
		// +1 of this object.
		/**
		 * for every successor with validity = true, initialize an instant of state representing the 
		 * square then add into the successor list
		 */
		if(validLeft == true){
			Square persegiKiri = new Square(curX, curY-1);
			State kiri = new State(persegiKiri, this, gValue+1, depth+1);
			ambilSuccessors.add(kiri);
		}
		if(validDown == true){
			Square persegiBawah = new Square(curX+1, curY);
			State bawah = new State(persegiBawah, this, gValue+1, depth+1);
			ambilSuccessors.add(bawah);
		}
		if(validRight == true){
			Square persegiKanan = new Square(curX, curY+1);
			State kanan = new State(persegiKanan, this, gValue+1, depth+1);
			ambilSuccessors.add(kanan);
		}
		if(validUp == true){
			Square persegiAtas = new Square(curX-1, curY);
			State atas = new State(persegiAtas, this, gValue+1, depth+1);
			ambilSuccessors.add(atas);
		}
		//return the list
		return ambilSuccessors;
	}

	/**
	 * private method to check if square went out of bounds
	 * @param x
	 * @param y
	 * @param maze
	 * @return
	 */
	private boolean outOfBounds(int x, int y, Maze maze){
		int maxCol = maze.getNoOfCols();
		int maxRow = maze.getNoOfRows();
		//check if coordinates is larger or equal to max col or row
		if(x >= maxRow || y >= maxCol){
			return true;
		}
		return false;
	}

	/**
	 * @return x coordinate of the current state
	 */
	public int getX() {
		return square.X;
	}

	/**
	 * @return y coordinate of the current state
	 */
	public int getY() {
		return square.Y;
	}

	/**
	 * @param maze initial maze
	 * @return true is the current state is a goal state
	 */
	public boolean isGoal(Maze maze) {
		if (square.X == maze.getGoalSquare().X
				&& square.Y == maze.getGoalSquare().Y)
			return true;

		return false;
	}

	/**
	 * @return the current state's square representation
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * @return parent of the current state
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * You may not need g() value in the BFS but you will need it in A-star
	 * search.
	 * 
	 * @return g() value of the current state
	 */
	public int getGValue() {
		return gValue;
	}

	/**
	 * @return depth of the state (node)
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * override function to check if coordinates match
	 */
	public boolean equals(Object o){
		if(o instanceof State){
			if(this.getSquare().X == ((State) o).getSquare().X && this.getSquare().Y == ((State) o).getSquare().Y){
				return true;
			}
		}
		return false;
	}
}
