package org.ideasforchange.imenno.logic;

import java.util.Arrays;
import java.util.List;

public class NameDay {

	private List<String> names;
	private DayOfYear day;

	public NameDay(List<String> names, DayOfYear day) {
		super();
		this.names = names;
		this.day = day;
	}
	
	public NameDay(String[] names, DayOfYear day) {
		super();
		this.names = Arrays.asList(names);
		this.day = day;
	}		
	
	public List<String> getNames() {
		return names;
	}

	public DayOfYear getDay() {
		return day;
	}

}
