package com.fxn.pix;


import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class Options implements Serializable {
    private int countImage = 1;
    private int countVideo = 1;
    private int requestCode = 0;
    private String path = "/DCIM/Camera";
    private int height = 0, width = 0;
    private boolean frontFacing = false;
    private int videoDurationLimitinSeconds = 40;
    private boolean excludeVideos = false;
    private boolean excludeImages = false;
    private boolean cameraDisabled = false;
    private boolean selectImageOrVideo = false;
    public static final int SCREEN_ORIENTATION_UNSET = -2;
    public static final int SCREEN_ORIENTATION_UNSPECIFIED = -1;
    public static final int SCREEN_ORIENTATION_LANDSCAPE = 0;
    public static final int SCREEN_ORIENTATION_PORTRAIT = 1;
    public static final int SCREEN_ORIENTATION_USER = 2;
    public static final int SCREEN_ORIENTATION_BEHIND = 3;
    public static final int SCREEN_ORIENTATION_SENSOR = 4;
    public static final int SCREEN_ORIENTATION_NOSENSOR = 5;
    public static final int SCREEN_ORIENTATION_SENSOR_LANDSCAPE = 6;
    public static final int SCREEN_ORIENTATION_SENSOR_PORTRAIT = 7;
    public static final int SCREEN_ORIENTATION_REVERSE_LANDSCAPE = 8;
    public static final int SCREEN_ORIENTATION_REVERSE_PORTRAIT = 9;
    public static final int SCREEN_ORIENTATION_FULL_SENSOR = 10;
    public static final int SCREEN_ORIENTATION_USER_LANDSCAPE = 11;
    public static final int SCREEN_ORIENTATION_USER_PORTRAIT = 12;
    public static final int SCREEN_ORIENTATION_FULL_USER = 13;
    public static final int SCREEN_ORIENTATION_LOCKED = 14;
    private ArrayList<String> preSelectedUrls = new ArrayList<>();

    @ScreenOrientation
    private int screenOrientation = SCREEN_ORIENTATION_UNSPECIFIED;

    private Options() {
    }

    public static Options init() {
        return new Options();
    }

    public int getVideoDurationLimitinSeconds() {
        return videoDurationLimitinSeconds;
    }

    public Options setVideoDurationLimitinSeconds(int videoDurationLimitinSececonds) {
        this.videoDurationLimitinSeconds = videoDurationLimitinSececonds;
        return this;
    }

    public ArrayList<String> getPreSelectedUrls() {
        return preSelectedUrls;
    }

    public Options setPreSelectedUrls(ArrayList<String> preSelectedUrls) {
        check();
        this.preSelectedUrls = preSelectedUrls;
        return this;
    }

    public boolean isExcludeVideos() {
        return excludeVideos;
    }

    public Options setExcludeVideos(boolean excludeVideos) {
        this.excludeVideos = excludeVideos;
        return this;
    }

    public boolean isExcludeImages() {
        return excludeImages;
    }

    public Options setExcludeImages(boolean excludeImages) {
        this.excludeImages = excludeImages;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isFrontFacing() {
        return this.frontFacing;
    }

    public Options setFrontFacing(boolean frontFacing) {
        this.frontFacing = frontFacing;
        return this;
    }

    public boolean isCameraDisabled() {
        return this.cameraDisabled;
    }

    public Options setCameraDisabled(boolean cameraDisabled) {
        this.cameraDisabled = cameraDisabled;
        return this;
    }

    private void check() {
        if (this == null) {
            throw new NullPointerException("call init() method to initialise Options class");
        }
    }

    public int getCountImage() {
        return countImage;
    }

    public Options setCountImage(int countImage) {
        check();
        this.countImage = countImage;
        return this;
    }

    public int getCountVideo() {
        return countVideo;
    }

    public Options setCountVideo(int countVideo) {
        check();
        this.countVideo = countVideo;
        return this;
    }

    public boolean isSelectImageOrVideo() {
        return selectImageOrVideo;
    }

    public Options setSelectImageOrVideo(boolean selectImageOrVideo) {
        this.selectImageOrVideo = selectImageOrVideo;
        return this;
    }

    public int getRequestCode() {
        if (this.requestCode == 0) {
            throw new NullPointerException("requestCode in Options class is null");
        }
        return requestCode;
    }

    public Options setRequestCode(int requestcode) {
        check();
        this.requestCode = requestcode;
        return this;
    }

    public String getPath() {
        return this.path;
    }

    public Options setPath(String path) {
        check();
        this.path = path;
        return this;
    }

    public int getScreenOrientation() {
        return screenOrientation;
    }

    public Options setScreenOrientation(@ScreenOrientation int screenOrientation) {
        check();
        this.screenOrientation = screenOrientation;
        return this;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenOrientation {
    }

}
