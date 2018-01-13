package leaflet.miaoa.qmsh.leaflet.base;

import android.app.Application;


/**
 * Created by Administrator on 2017/9/11.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    @Override
    public void onCreate() {
//        Fresco.initialize(this);
        super.onCreate();
//        instance = this;
//        ZXingLibrary.initDisplayOpinion(this);
//        VCamera.setVideoCachePath( Environment.getExternalStorageDirectory().getAbsolutePath() + "/videoRecord/");
//        // 开启log输出,ffmpeg输出到logcat
//        VCamera.setDebugMode(true);
//        // 初始化拍摄SDK，必须
//        VCamera.initialize(this);

    }

    public static MyApplication getInstance(){
        return instance;
    }
}
