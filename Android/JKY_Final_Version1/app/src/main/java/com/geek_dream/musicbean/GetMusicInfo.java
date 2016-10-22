package com.geek_dream.musicbean;

/**
 * Created by huangzhiyuan on 16/8/18.
 */
public class GetMusicInfo {

    long id; // 音乐id
    String title;// 音乐标题
    String artist;// 艺术家
    long duration;// 时长
    long size; // 文件大小
    String url;// 音乐的路径


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
