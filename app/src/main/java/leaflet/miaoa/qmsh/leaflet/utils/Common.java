package leaflet.miaoa.qmsh.leaflet.utils;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class Common {
    //判断字符串是否为空
    public static boolean isNOT_Null(String str) {
        try {
            if (str == null) {
                return false;
            }
            if (str == "") {
                return false;
            }
            if (str.equals("")) {
                return false;
            }

            if (str.equals("null")) {
                return false;
            }
            if(str.equals("undefined")){
                return false;
            }

            return true;
        } catch (Exception e) {

            return false;
        }

    }




}
