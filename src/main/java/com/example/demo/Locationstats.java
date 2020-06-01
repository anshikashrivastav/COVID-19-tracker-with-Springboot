package com.example.demo;

public class Locationstats {
	
	private String state;
	private String country;
	private int latesttotal;
	private int difffromprevday;
	
	public int getDifffromprevday() {
		return difffromprevday;
	}
	public void setDifffromprevday(int difffromprevday) {
		this.difffromprevday = difffromprevday;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatesttotal() {
		return latesttotal;
	}
	public void setLatesttotal(int a) {
		this.latesttotal = a;
	}
	@Override
	public String toString() {
		return "Locationstats [state=" + state + ", country=" + country + ", latesttotal=" + latesttotal + "]";
	} 

}
