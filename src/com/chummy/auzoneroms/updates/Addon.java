package com.chummy.auzoneroms.updates;

public class Addon {

    private String mTitle;
    private String mDesc;
    private String mUpdatedOn;
    private int mFilesize;
    private String mDownloadLink;
    private int mId;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String input) {
        mTitle = input;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String input) {
        mDesc = input;
    }

    public String getUpdatedOn() {
        return mUpdatedOn;
    }

    public void setUpdatedOn(String input) {
        mUpdatedOn = input;
    }

    public String getDownloadLink() {
        return mDownloadLink;
    }

    public void setDownloadLink(String input) {
        mDownloadLink = input;
    }

    public int getFilesize() {
        return mFilesize;
    }

    public void setFilesize(int input) {
        mFilesize = input;
    }

    public int getId() {
        return mId;
    }

    public void setId(int input) {
        mId = input;
    }
}
