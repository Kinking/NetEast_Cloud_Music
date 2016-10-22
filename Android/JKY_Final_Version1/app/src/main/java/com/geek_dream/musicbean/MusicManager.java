package com.geek_dream.musicbean;


public class MusicManager {
	private int id_img;
	private String function;
	private int num;
	public MusicManager(int id_img, String function, int num) {
		super();
		this.id_img = id_img;
		this.function = function;
		this.num = num;
	}
	public int getId_img() {
		return id_img;
	}
	public void setId_img(int id_img) {
		this.id_img = id_img;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getNum() {
		return num+"";
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	


}
