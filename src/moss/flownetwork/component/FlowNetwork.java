package moss.flownetwork.component;

public class FlowNetwork {

	public static int[][] matrix;
	public static int[][] marks;
	public static int size;
	public static int[] heap;
	public static int heapSize;
	
	public static int maximumFlow;
	
	public static final int NOT_MARKED = -999;
	public static final int SOURCE = -999999;
	public static final int INFINITY = 999999;
	public static final int MINUS = -1;
	public static final int PLUS = 1;
	public static final int NO_SIGN = 0;

	// ----------------------------------------------------------------------

	public FlowNetwork() {
		matrix = new int[size][size];
		marks = new int[size][3];
		heap = new int[size];
		heapSize = 0;
		maximumFlow = 0;
	}

	// ----------------------------------------------------------------------

	public boolean vertexesMarking() {
		// reset mark values
		resetMarks();
		// add source to heap
		heap[0] = 0;
		heapSize = 1;
		// setting marks for source
		marks[0][0] = SOURCE;
		marks[0][1] = INFINITY;
		marks[0][2] = MINUS;

		int currentVertex = 0;
		// end of the heap
		int sink = size - 1;

		// end if reach sink
		while (heap[currentVertex] != sink) {
			for (int i = 0; i < size; i++) {
				// not marked vertexes
				if (marks[i][0] == NOT_MARKED) {
					// check for edge
					if (matrix[heap[currentVertex]][i] > 0) {

						// set beginning of edge
						marks[i][0] = heap[currentVertex];

						// set value
						marks[i][1] = Math.min(matrix[heap[currentVertex]][i], marks[heap[currentVertex]][1]);
						
						// set sign + or -
						if (heap[currentVertex] < i)
							marks[i][2] = PLUS;
	                    else
	                    	marks[i][2] = MINUS;
						
						// add to heap
						heap[heapSize] = i;
						heapSize++;
					}
				}
			}
			
			// end if no more vertexes to mark
			if (heapSize == currentVertex + 1) {
				return false;
			}

			currentVertex++;
		}

		// update graph after setting marks
		updateGraph();

		return true;
	}

	// ----------------------------------------------------------------------

	/**
	 * Update Graph
	 */
	public void updateGraph() {
		int lastVertex = heapSize - 1;
		int begin = heap[lastVertex];
		int end = marks[heap[lastVertex]][0];

		int currentFlow = marks[heap[lastVertex]][1];

		// end if reach source
		while (end != SOURCE) {
			matrix[begin][end] += currentFlow;
			matrix[end][begin] -= currentFlow;
			begin = end;
			end = marks[begin][0];
		}

		// add current flow to maximum
		maximumFlow += currentFlow;
	}

	// ----------------------------------------------------------------------

	public void resetMarks() {
		// resets marks values
		for (int i = 0; i < marks.length; i++) {
			marks[i][0] = NOT_MARKED;
			marks[i][1] = 0;
			marks[i][2] = NO_SIGN;
		}
	}
	
	// ----------------------------------------------------------------------

	public int[] setS() {
		int[] S = new int[heapSize];
		for (int i = 0; i < heapSize; i++) {
			S[i] = heap[i];
		}
		return S;
	}
	
	// ----------------------------------------------------------------------

	public int[] setT() {
		int[] T = new int[size - heapSize];

		int i = 0;
		int j = 0;
		while (i != size) {
			if (marks[i][0] == NOT_MARKED) {
				T[j] = i;
				j++;
			}
			i++;
		}
		return T;
	}

	// ----------------------------------------------------------------------

	public void printMatrix() {
		System.out.println("----------Data----------");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}

	// ----------------------------------------------------------------------

	public void printResult() {
		System.out.println("Maximum Flow : " + maximumFlow);
		System.out.println("----Minimum Cut----");
		
		// contain source
		int[] S = setS();
		// contain sink
		int[] T = setT();	
		
		printArray(S, "S");
		printArray(T, "T");
		printEdges(S, T);
	}
	
	// ----------------------------------------------------------------------

	public void printArray(int[] array, String name) {
		System.out.print(name + " = { ");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println("}");
	}
	
	// ----------------------------------------------------------------------

	public void printEdges(int[] S, int[] T) {
		for (int i = 0; i < S.length; i++) {
			for (int j = 0; j < T.length; j++) {
				if (matrix[T[j]][S[i]] > 0 && T[j] >  S[i]) {
					System.out.println("(" + S[i] + "," + T[j] + ")");
				}
			}
		}
	}

}
