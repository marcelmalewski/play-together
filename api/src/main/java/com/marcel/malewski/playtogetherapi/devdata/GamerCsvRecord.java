package com.marcel.malewski.playtogetherapi.devdata;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GamerCsvRecord {
	@CsvBindByName
	String login;
	@CsvBindByName
	String password;
	@CsvBindByName
	String email;
	@CsvBindByName
	String birthdateAsString;
	@CsvBindByName
	String playingTimeStartAsString;
	@CsvBindByName
	String playingTimeEndAsString;
	@CsvBindByName
	Long platformId;
}
