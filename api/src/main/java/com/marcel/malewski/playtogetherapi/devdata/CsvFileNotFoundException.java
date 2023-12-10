package com.marcel.malewski.playtogetherapi.devdata;

public class CsvFileNotFoundException extends RuntimeException {
	public CsvFileNotFoundException(String filePath) {
		super("Csv file not found, file path: " + filePath);
	}
}
