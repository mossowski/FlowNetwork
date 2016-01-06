package moss.flownetwork.application;

import java.io.FileNotFoundException;
import java.util.Scanner;

import moss.flownetwork.component.FlowNetworkController;

public class Application {

	public static void main(String[] args) throws FileNotFoundException {
		
		System.out.print("Data : ");
		Scanner in = new Scanner(System.in);
		String fileName = in.next();
		in.close();
		
		FlowNetworkController flowNetwork = new FlowNetworkController(fileName);
		flowNetwork.run();
	}

}
