import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// ...
		//initialize all boolean to false in explored
		for(int i =0; i<explored.length;i++){
			for(int j=0; j< explored[i].length;j++){
				explored[i][j] = false;
			}
		}
		
		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();

		// TODO initialize the root state and add
		// to frontier list
		// ...
		/**
		 * create a start positon then check if its goal, if it isnt, then get the distance to goal and
		 * create an instant of statefvaluepair then add to the frontier. fvalue is determine by
		 * gvalue + distance to goal. Set the explore square to true;
		 */
		Square startPos = maze.getPlayerSquare();
		State startState = new State(startPos, null, 0, 0);
		if(startState.isGoal(maze)){
			return true;
		}
		double fVal = this.getDistance(startState.getX(), startState.getY());
		StateFValuePair startFront = new StateFValuePair(startState, startState.getGValue()+fVal);
		int maxFront = 0;
		frontier.add(startFront);
		explored[startState.getX()][startState.getY()] = true;


		while (!frontier.isEmpty()) {
			//chabge this
			/**
			 * set current pair to the first pop of the frontier, set its explored value to true.
			 * Check if its goal state, if so, set the gvalue, depth, number of explored nodes, 
			 * maximum size of frontier. Moreover, get the path and set it to '.'. return true;
			 */
			StateFValuePair curr = frontier.poll();
			int row = curr.getState().getX();
			int col = curr.getState().getY();
			explored[row][col] = true;
			if(curr.getState().isGoal(maze)){
				this.cost = curr.getState().getGValue();
				this.maxDepthSearched = curr.getState().getDepth();
				this.noOfNodesExpanded = exploration(explored);
				this.maxSizeOfFrontier = maxFront;

				State currState = curr.getState();
				while(currState.getParent() != null){
					currState = currState.getParent();
					if(currState.equals(startState)){
						break;
					}
					maze.setOneSquare(currState.getSquare(), '.');
				}

				return true;
			}
			
			/**
			 * other wise get the successors of the current node, for each succesors, check if 
			 * frontier or explored contain the state, if it doesnt, create an instant of new pair
			 * thenadd to frontier. Otherwise if frontier contains the pair, then iterate the frontier
			 * get the current same state, compare its gvalue and replace with the one with smaller gvalue 
			 */
			ArrayList<State> succ = curr.getState().getSuccessors(explored, maze);

			for(int i =0; i< succ.size();i++){
				int x = succ.get(i).getX();
				int y = succ.get(i).getY();
				//check condition one: both fron and explore not the same
				if(!frontier.contains(succ.get(i)) && explored[x][y] == false ){
					StateFValuePair newFront = new StateFValuePair(succ.get(i), succ.get(i).getGValue()+getDistance(succ.get(i).getX(), succ.get(i).getY()));
					frontier.add(newFront);
					//check condition 2, only if frontier has the same node
				}else if(frontier.contains(succ.get(i))){
					Iterator<StateFValuePair> itr = frontier.iterator();
					while(itr.hasNext()){
						StateFValuePair tmp = itr.next();
						if(tmp.getState().equals(succ.get(i))){
							int old = tmp.getState().getGValue();
							int newer = succ.get(i).getGValue();
							if(old > newer ){
								frontier.remove(tmp);
								StateFValuePair newFront = new StateFValuePair(succ.get(i), succ.get(i).getGValue()+getDistance(succ.get(i).getX(), succ.get(i).getY()));
								frontier.add(newFront);
							}
							break;
						}
					}
				}
			}

			//to keep track of the max size of frontier after every loop
			if(frontier.size() > maxFront){
				maxFront = frontier.size();
			}
			// TODO return true if a solution has been found
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use frontier.poll() to extract the minimum stateFValuePair.
			// use frontier.add(...) to add stateFValue pairs
		}

		// TODO return false if no solution
		return false;
	}
	/**
	 * private function to get the distance of the node to goal state
	 * h(n) funciton
	 * @param x
	 * @param y
	 * @return
	 */
	private double getDistance(int x, int y){
		int yCoord = Math.abs(y - maze.getGoalSquare().Y);
		int xCoord = Math.abs(x - maze.getGoalSquare().X);
		double distanceH = Math.sqrt((yCoord * yCoord)+(xCoord*xCoord));
		return distanceH;
	}

	/**
	 * private function to calculate number of expanded nodes.
	 * aka explored nodes
	 * @param explored
	 * @return
	 */
	private int exploration(boolean[][] explored){
		int explore =0;
		for(int i =0; i< explored.length;i++){
			for(int j=0; j<explored[i].length;j++){
				if(explored[i][j] == true){
					explore++;
				}
			}
		}
		return explore;
	}
}


