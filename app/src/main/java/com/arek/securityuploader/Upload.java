package com.arek.securityuploader;

/**
 * Created by LENOVO-PC on 3/4/2018.
 */

public class Upload
{
    public String iUri;
    private String mkey;

     public Upload()
     {

     }

    public Upload(String iUri)
    {
        this.iUri = iUri;
    }


    public String getiUri() {
        return iUri;
    }

    public void setiUri(String iUri) {
        this.iUri = iUri;
    }
    public String getKey()
    {
        return  mkey;
    }
    public void setKey(String key)
    {
        mkey=key;
    }

}
