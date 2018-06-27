package com.arek.securityuploader;

/**
 * Created by LENOVO-PC on 3/5/2018.
 */

public class UploadVideo
{
    public String vUri;
    private String vkey;

    public UploadVideo()
    {

    }

    public UploadVideo(String vUri)
    {
        this.vUri = vUri;
    }


    public String getvUri() {
        return vUri;
    }

    public void setvUri(String vUri) {
        this.vUri = vUri;
    }
    public String getKey()
    {
        return  vkey;
    }
    public void setKey(String key)
    {
        vkey=key;
    }

}
