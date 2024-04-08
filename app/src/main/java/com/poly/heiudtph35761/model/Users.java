package com.poly.heiudtph35761.model;

public class Users {
    private String _id,username,password,email,phone,avatar,available;
    private String createAt,updateAt;


    public Users() {
    }

    public Users( String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users(String _id, String username, String password, String email, String phone, String avatar, String available, String createAt, String updateAt) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.available = available;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
