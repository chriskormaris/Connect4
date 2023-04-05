package com.chriskormaris.connect4.gui.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.net.URL;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceLoader {

	public static URL load(String path) {
		URL input = ResourceLoader.class.getResource(path);
		if (input == null) {
			input = ResourceLoader.class.getResource("/" + path);
		}
		return input;
	}

}
