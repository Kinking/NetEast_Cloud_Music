package com.geek_dream.musicbean;

public class MusicDetail {
	private String singer;
	private String music;
	private boolean isDld = true;
	private String hqOrsq = "sq";//������hq����sq����no  ʾ��Ч�����Գ�ʼΪSQ
	
	
	
	
	public MusicDetail(String singer, String music) {
		super();
		this.singer = singer;
		this.music = music;
	}
	public MusicDetail(String singer, String music, boolean isDld) {
		super();
		this.singer = singer;
		this.music = music;
		this.isDld = isDld;
	}
	public MusicDetail(String singer, String music, boolean isDld, String hqOrsq) {
		super();
		this.singer = singer;
		this.music = music;
		this.isDld = isDld;
		this.hqOrsq = hqOrsq;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getMusic() {
		return music;
	}
	public void setMusic(String music) {
		this.music = music;
	}
	public boolean isDld() {
		return isDld;
	}
	public void setDld(boolean isDld) {
		this.isDld = isDld;
	}
	public String getHqOrsq() {
		return hqOrsq;
	}
	public void setHqOrsq(String hqOrsq) {
		this.hqOrsq = hqOrsq;
	}
	
}
