package com.geek_dream.musicbean;

public class MusicList {
	private int id_img;
	private String name_list;
	public int getId_img() {
		return id_img;
	}
	public void setId_img(int id_img) {
		this.id_img = id_img;
	}
	public String getName_list() {
		return name_list;
	}
	public void setName_list(String name_list) {
		this.name_list = name_list;
	}
	public MusicList(int id_img, String name_list) {
		super();
		this.id_img = id_img;
		this.name_list = name_list;
	}
}
