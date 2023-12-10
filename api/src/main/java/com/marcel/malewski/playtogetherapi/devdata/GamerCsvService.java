package com.marcel.malewski.playtogetherapi.devdata;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class GamerCsvService {
	public List<GamerCsvRecord> convertCSV(File csvFile, String csvFilePath) {
		try {
			return new CsvToBeanBuilder<GamerCsvRecord>(new FileReader(csvFile))
				.withType(GamerCsvRecord.class)
				.build().parse();
		} catch (FileNotFoundException e) {
			throw new CsvFileNotFoundException(csvFilePath);
		}
	}
}
