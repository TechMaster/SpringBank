package com.xbank.beans;

public class Account {

	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Account(int i, String s) {
		this.id = i;
		this.name = s;
	}
	
	public Account() {
	}
	
	@Override
	public String toString() {
		return "[id="+id+",name="+name+"]";
	}
}
