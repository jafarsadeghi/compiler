
import java.io.*;

public class main {
	public static void main(String[] args) throws IOException {
		try {
			String inputFileName = null;
			String outputFileName = null;
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					if (args[i].equals("-i")) {
						inputFileName = args[i + 1];
					}
					if (args[i].equals("-o")) {
						outputFileName = args[i + 1];
					}
				}
			}
			Reader reader = null;
			Writer writer = null;
			if (inputFileName != null)
				reader = new FileReader("tests/" + inputFileName);
			if (outputFileName != null)
				writer = new FileWriter( "out/" + outputFileName);
			// Read with reader and write the output with writer.

		}
		catch(Exception e) {
			return;
		}
	}
}
