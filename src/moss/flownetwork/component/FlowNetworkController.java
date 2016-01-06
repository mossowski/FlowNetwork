package moss.flownetwork.component;

import java.io.FileNotFoundException;

import moss.flownetwork.utils.FileReader;

public class FlowNetworkController {
	
	private String fileName;

	public FlowNetworkController(String aFileName) {
		fileName = aFileName;
	}
	
	// ----------------------------------------------------------------------
	
	/**
	 * @throws FileNotFoundException
	 */
	public void run() throws FileNotFoundException {
		FileReader fileReader = new FileReader(fileName);
		fileReader.checkDataSize();
		FlowNetwork flowNetwork = new FlowNetwork();
		fileReader.loadData();
		flowNetwork.printMatrix();

		boolean run = true;
		while (run) {
			run = flowNetwork.vertexesMarking();
		}

		flowNetwork.printResult();
	}

}
