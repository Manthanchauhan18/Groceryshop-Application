package com.example.groceryshopmanthanchauhan;

public class User
{
    String uid;
    String email;
    String pass;
    String name;
    String addr;
    String mobile;

   public User(){

   }

    public User(String uid, String email, String pass, String name, String addr, String mobile)
    {
        this.uid = uid;
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.addr = addr;
        this.mobile = mobile;
    }

    public String toString()
    {
        return "FId = " + uid + "\n Email = " + email + "\n Password = " + pass + "\n Name = " + name + "\n Address = " + addr + "\n Mobile No = " + mobile + "\n";
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
