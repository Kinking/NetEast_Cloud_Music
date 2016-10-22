package com.geek_dream.musicbean;

import java.io.Serializable;

/**
 * Created by huangzhiyuan on 16/8/18.
 */
public class MainToMusic implements Serializable{


    boolean mainisPlay=false;// 是否在播放，默认没有在播放
    String title;// 歌曲名
    String singer;// 歌手名
    int fmode;// 目前的播放模式，默认为顺序播放
    int current=0;// 目前的播放曲目
    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isMainisPlay() {
        return mainisPlay;
    }

    public void setMainisPlay(boolean mainisPlay) {
        this.mainisPlay = mainisPlay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getFmode() {
        return fmode;
    }

    public void setFmode(int fmode) {
        this.fmode = fmode;
    }


}
