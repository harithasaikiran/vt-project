package com.projectmgmt.vt.domain;

//Simple POJO for the rule check interval
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class RuleInterval {

	public RuleInterval() {

	}

	public RuleInterval(long intervalValue, TimeUnits intervalUnit) {
		this.intervalValue = intervalValue;
		this.intervalUnit = intervalUnit;
	}

	Long intervalValue;
	TimeUnits intervalUnit;

	public Long getIntervalValue() {
		return intervalValue;
	}

	public void setIntervalValue(Long intervalValue) {
		this.intervalValue = intervalValue;
	}

	public TimeUnits getIntervalUnit() {
		return intervalUnit;
	}

	public void setIntervalUnit(TimeUnits intervalUnit) {
		this.intervalUnit = intervalUnit;
	}

}
