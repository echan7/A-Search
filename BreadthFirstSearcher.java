import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD
  
		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

		// ...
		//initalize every boolean value to false
		for(int i =0; i< explored.length;i++){
			for(int j =0; j<explored[i].length;j++){
				explored[i][j] = false;
			}
		}
		
		/**
		 * initialize start state and check if its goal, if it is return true;
		 * add the start state to queue, set the explored value to true and set 
		 * maximum size of frontier =1; 
		 */
		Square startPos = maze.getPlayerSquare();
		State startState = new State(startPos, null, 0, 0);
		if(startState.isGoal(maze)){
			return true;
		}

		// Queue implementing the Frontier list

		LinkedList<State> queue = new LinkedList<State>();
		queue.add(startState);
		explored[startState.getX()][startState.getY()] = true;
		int explore = 1;
		int maxFront = 1;


		while (!queue.isEmpty()) {
			/**
			 * set currrent state to the pop value of queue, set the explore
			 * value to true. Check if its goal, if it is, set the gvalue, depth, nodes expand
			 * and maxfrontier size; Also, retrieve the path and set it to '.'
			 */
			State curr = queue.pop();
			int rows = curr.getX();
			int cols = curr.getY();
			explored[rows][cols] = true;

			if(curr.isGoal(maze)){
				//System.out.println("TRUE");
				this.cost = curr.getGValue();
				this.maxDepthSearched = curr.getDepth();
				this.noOfNodesExpanded = exploration(explored);
				this.maxSizeOfFrontier = maxFront;

				//System.out.println(exploration(explored));
				
				while(curr.getParent() != null){
					curr = curr.getParent();
					if(curr.equals(startState)){
						break;
					}
					maze.setOneSquare(curr.getSquare(), '.');
				}
				return true;
			}

			/**
			 * else for every successor of current node, if it has not been visited and not in frontier, 
			 * add it to the queue
			 */
			ArrayList<State> succ = curr.getSuccessors(explored, maze);
			explore++;
			for(int i = 0; i < succ.size(); i++){
				int row = succ.get(i).getX();
				int col = succ.get(i).getY();

				if(!queue.contains(succ.get(i)) && explored[row][col] == false){
					//explored[row][col] = true;
					queue.add(succ.get(i));
				}
			}
			//check max front every iteration
			if(queue.size() > maxFront){
				maxFront = queue.size();
			}
			// TODO return true if find a solution
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),

			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use queue.pop() to pop the queue.
			// use queue.add(...) to add elements to queue
		}

		// TODO return false if no solution
		//System.out.println("FALSE");
		return false;
	}

	/**
	 * private function to calculate the number of expanded nodes
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
