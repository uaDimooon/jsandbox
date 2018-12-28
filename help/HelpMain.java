import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class HelpMain {
	private String helpFileName_;

	public static void main(final String[] args) {
		System.out.println("Hello World");
		if (args.length != 1) {
			System.out.println("Please provide exact one help file name");
			return;
		}
		final HelpMain help = new HelpMain(args[0]);
		String userInput = null;
		do {
			userInput = help.askTopic();
			if ("stop".equals(userInput)) break;
			if (!help.on(userInput)) {
				System.out.println("No topic: " +  userInput + " found.");
				continue;
			}
		} while (!"stop".equals(userInput));
		System.out.println("OK, stop help lookup");
	}
	
	public HelpMain(final String fileName) {
		helpFileName_ = fileName;
	}
	
	public String askTopic() {
		final BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("\nProvide a search topic: ");
			return userReader.readLine();
		} catch (final IOException io) {
			System.out.println("Problem with reading user's input");
			return "stop";
		}
	}
	
	public boolean on(final String topic) {
		try (final BufferedReader helpReader = new BufferedReader(new FileReader(helpFileName_))) {
			String line = null;
			int symbol = -1;
			do {
				symbol = helpReader.read();
				if (symbol != '#') continue;
				line = helpReader.readLine();
				if (topic.equals(line)) {
					do {
						line = helpReader.readLine();
						if (line == null || "".equals(line.trim()) || '#' == line.charAt(0)) break;
						System.out.println(line);
					} while (true);
					return true;
				}
			} while (symbol != -1);
			return false;
		} catch (final IOException io) {
			System.out.println("Problem with reading help file");
			return false;
		}
	}
}
