package com.geek_dream.bean;

/**
 * Created by huangzhiyuan on 16/8/11.
 */
public class Contact {


    private String name;
    private String sex;
    private String age;
    private String img;
    private String userid;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public Contact(String name, String sex, String age, String img,
                   String userid) {
        super();

        this.name = name;
        this.sex = sex;
        this.age = age;
        this.img = img;
        this.userid = userid;
    }
    public Contact() {
        super();
        // TODO Auto-generated constructor stub
    }



}
