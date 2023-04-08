package com.mibo.quanlykho.Models;

public class TaiKhoan {

    private String user;
    private String pass;
    private String name;
    private String old;
    private String local;
    private String quen;

    private String uid;


    public TaiKhoan() {
    }

    public TaiKhoan(String user, String pass, String name, String old, String local, String quen, String uid) {
        this.user = user;
        this.pass = pass;
        this.name = name;
        this.old = old;
        this.local = local;
        this.quen = quen;
        this.uid = uid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getQuen() {
        return quen;
    }

    public void setQuen(String quen) {
        this.quen = quen;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
