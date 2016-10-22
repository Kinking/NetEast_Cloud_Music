package com.geek_dream.musicbean;

import java.io.Serializable;

/**
 * Created by huangzhiyuan on 16/8/18.
 */
public class ListToMain implements Serializable{

    String title;
    String singer;
    boolean listisPlay;
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
    public boolean isListisPlay() {
        return listisPlay;
    }
    public void setListisPlay(boolean listisPlay) {
        this.listisPlay = listisPlay;
    }

}
