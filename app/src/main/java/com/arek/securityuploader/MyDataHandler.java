package com.arek.securityuploader;

/**
 * Created by LENOVO-PC on 2/22/2018.
 */

public class MyDataHandler
{
    String name;
    String password;
    public MyDataHandler()
    {

    }

    public MyDataHandler(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
