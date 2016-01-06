package moss.flownetwork.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import moss.flownetwork.component.FlowNetwork;

public class FileReader {

	private Scanner fileScanner;
	private Scanner sizeScanner;
	private File file;
	private boolean valid;

	// ----------------------------------------------------------------------

	public FileReader(String fileName) throws FileNotFoundException {
		file = new File(fileName);
		fileScanner = new Scanner(file);
		sizeScanner = new Scanner(file);
		valid = false;
	}

	// ----------------------------------------------------------------------

	/**
	 * Loads file data to matrix
	 */
	public void loadData() {

		int ySize = 0;
		fileScanner.nextLine();

		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			lineScanner.useDelimiter(" ");
			int xSize = 0;

			while (lineScanner.hasNextInt()) {
				if (FlowNetwork.size == xSize) {
					System.out.println("Wrong matrix size!");
					System.exit(0);
				}

				int nextInt = lineScanner.nextInt();
				if (nextInt >= 0)
					FlowNetwork.matrix[ySize][xSize] = nextInt;
				else {
					System.out.println("Number cannot be less than zero!");
					System.exit(0);
				}
				xSize++;
			}

			ySize++;
			lineScanner.close();
		}

		fileScanner.close();

		if (FlowNetwork.size != ySize) {
			System.out.println("Wrong matrix size!");
			System.exit(0);
		}
		
		checkData(0);
		
		if (!valid) {
			System.out.println("Wrong matrix!");
			System.exit(0);
		}
	}

	// ----------------------------------------------------------------------

	/**
	 * Checks data size
	 */
	public void checkDataSize() {
		int size = sizeScanner.nextInt();
		FlowNetwork.size = size;

		sizeScanner.close();
	}
	
	// ----------------------------------------------------------------------

	/**
	 * Checks data
	 * @param vertex
	 */
	public void checkData(int vertex) {
		for (int i = vertex; i < FlowNetwork.matrix.length; i++) {
				if (FlowNetwork.matrix[vertex][i] > 0) {
					if (i ==  FlowNetwork.matrix.length - 1) {
						valid = true;
					}
					else
						checkData(i);
				}
		}
	}

}
