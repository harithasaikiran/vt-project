package com.projectmgmt.vt.domain;

import java.util.Arrays;

public enum AlarmRecurrenceEnum implements EnumClass<Character> {

	CONTINUOUS('1'), ONCE_PER_ASSET('2'), ONCE('3');

	private char alarmRecurrence;

	AlarmRecurrenceEnum(char alarmRecurrence) {
		this.alarmRecurrence = alarmRecurrence;
	}

	@Override
	public Character getId() {
		return alarmRecurrence;
	}

	public static AlarmRecurrenceEnum getEnumById(char key) {
		return Arrays.stream(values()).filter(e -> e.getId().equals(key)).findFirst().orElse(null);
	}

}
