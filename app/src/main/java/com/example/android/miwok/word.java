package com.example.android.miwok;


public class word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int imageResourceId = No_Image_Provided;
    private static final int No_Image_Provided = -1;
    private int mAudioResourceId;

    public word(String defaultTranslation, String miwokTranslation,int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId=audioResourceId;
    }

    public word(String defaultTranslation, String miwokTranslation, int ImageResourceID, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        imageResourceId = ImageResourceID;
        mAudioResourceId=audioResourceId;
    }

    public String get_Miwok_Translation() {
        return mMiwokTranslation;
    }


    public String get_Default_Translation() {
        return mDefaultTranslation;
    }


    public int get_image_resource_id() {
        return imageResourceId;
    }


    public boolean HasImage() {
        return imageResourceId != No_Image_Provided;
    }


public int getmAudioResourceId()
{
    return mAudioResourceId;
}
}
