import java.net.URL;

final public class ResourceLoader {
	
	public static URL load(String path) {
		//InputStream input = ResourceLoader.class.getResourceAsStream(path);
		URL input = ResourceLoader.class.getResource(path);
		if (input == null) {
			input = ResourceLoader.class.getResource("/"+path);
		}
		//String in = input.getPath();
		//System.out.println("in:" + in);
		return input;
	}

}
