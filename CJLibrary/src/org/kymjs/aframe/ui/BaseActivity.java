/*
 * Copyright (c) 2014, KJFrameForAndroid 张涛 (kymjs123@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kymjs.aframe.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Application BaseActivity,you should inherit it for your Activity<br>
 * 
 * <b>创建时间</b> 2014-5-28
 * 
 * @author kymjs(kymjs123@gmail.com)
 * @version 1.2
 */
public abstract class BaseActivity extends KJFrameActivity {
    /**
     * 当前Activity状态
     */
    public static enum ActivityState {
        RESUME, PAUSE, STOP, DESTROY
    }

    /**
     * Activity显示方向
     */
    public static enum ScreenOrientation {
        HORIZONTAL, VERTICAL, AUTO
    }

    public Activity aty;
    /** Activity状态 */
    public ActivityState activityState = ActivityState.DESTROY;
    // 是否允许全屏
    private boolean mAllowFullScreen = false;
    // 是否启用框架的退出界面
    private boolean mOpenBackListener = true;

    // 屏幕方向
    private ScreenOrientation orientation = ScreenOrientation.VERTICAL;

    /**
     * 是否全屏显示本Activity，全屏后将隐藏状态栏，默认不全屏（若修改必须在构造方法中调用）
     * 
     * @param allowFullScreen
     *            是否允许全屏
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * 修改屏幕显示方向，默认竖屏锁定（若修改必须在构造方法中调用）
     * 
     * @param orientation
     */
    public void setScreenOrientation(ScreenOrientation orientation) {
        this.orientation = orientation;
    }

    /**
     * 是否启用返回键监听，若启用，则在显示最后一个Activity时将弹出退出对话框。默认启用（若修改必须在构造方法中调用）
     * 
     * @param openBackListener
     */
    public void setBackListener(boolean openBackListener) {
        this.mOpenBackListener = openBackListener;
    }

    /**
     * @return 返回是否启用返回键监听
     */
    protected boolean getBackListener() {
        return this.mOpenBackListener;
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Class<?> cls) {
        showActivity(aty, cls);
        aty.finish();
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Intent it) {
        showActivity(aty, it);
        aty.finish();
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Class<?> cls, Bundle extras) {
        showActivity(aty, cls, extras);
        aty.finish();
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Intent it) {
        aty.startActivity(it);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }

    /***************************************************************************
     * 
     * print Activity callback methods
     * 
     ***************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        aty = this;
        switch (orientation) {
        case HORIZONTAL:
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            break;
        case VERTICAL:
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            break;
        case AUTO:
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
            break;
        }

        if (mAllowFullScreen) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState = ActivityState.RESUME;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ActivityState.PAUSE;
    }

    @Override
    protected void onStop() {
        super.onResume();
        activityState = ActivityState.STOP;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityState = ActivityState.DESTROY;
    }
}
