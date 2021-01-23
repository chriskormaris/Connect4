package gui;

import java.net.URL;

final public class ResourceLoader {
	
	public static URL load(String path) {
		
		URL input = ResourceLoader.class.getResource(path);
		if (input == null) {
			input = ResourceLoader.class.getResource("/" + path);
		}
		
		return input;
	}

}
