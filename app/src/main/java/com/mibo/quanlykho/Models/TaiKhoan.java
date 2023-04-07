package com.mibo.quanlykho.Models;

public class TaiKhoan {
    private String username;
    private String password;
    private boolean admin;

    public TaiKhoan() {
    }

    public TaiKhoan(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        admin = admin;
    }
}
