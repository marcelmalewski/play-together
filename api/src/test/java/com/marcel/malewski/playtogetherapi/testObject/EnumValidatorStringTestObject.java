package com.marcel.malewski.playtogetherapi.testObject;

import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.PrivacyLevel;
import com.marcel.malewski.playtogetherapi.validation.enums.ValidEnum;

public record EnumValidatorStringTestObject(
	@ValidEnum(enumClass = PrivacyLevel.class)
	String testField
) {
}
