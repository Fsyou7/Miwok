package com.example.android.miwok;

/**
 * {@Link Word} represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 */
public class Word {

    /*Default translation for the word.*/
    private String mDefaultTranslation;

    /*Miwok translation for the word.*/
    private String mMiwokTranslation;

    /*Reference to the image file*/
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /*Reference to the audio file*/
    private int mAudioResourceId;

    private static final int NO_IMAGE_PROVIDED = -1;

    /*Create a new word object
    *
    * @param defaultTranslation is the word in a language that the user is already familiar with.
    *                           (such as English)
    * @param miwokTranslation is the word in the Miwok language.
    * */

    public Word (String defaultTranslation, String miwokTranslation, int audioResource){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResource;
    }


    /*Create a new word object
    *
    * @param defaultTranslation is the word in a language that the user is already familiar with.
    *                           (such as English)
    * @param miwokTranslation is the word in the Miwok language.
    *
    * @param imageResourceId is the drawable resource ID of the image associated with the word
    * */
    public Word (String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResource){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResource;
    }

    /*
    * Get the default translation of the word
    * */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /*
    * Get the Miwok translation of the word
    * */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /*
    * Get the audio resource ID of the word
    * */
    public int getAudioResourceId() {
        return mAudioResourceId;
    }

    /*
    * Get the image resource ID of the word
    * */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /*
    * Returns whether or not there is an image for this word.
    * */
    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }
}
