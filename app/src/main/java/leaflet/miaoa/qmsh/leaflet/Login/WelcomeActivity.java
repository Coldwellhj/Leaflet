package leaflet.miaoa.qmsh.leaflet.Login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.ui.merchantHomePage.SellerHomePageActivity;
import leaflet.miaoa.qmsh.leaflet.ui.personaluser.PersonalUserHomePageActivity;


/**
 * Create by asus on 2016/11/3.
 */

public class WelcomeActivity extends AppCompatActivity {
    public static String Usertel="";
    String[] permissions = { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;

            //验证是否许可权限
//	            for (String str : permissions) {
//	                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//	                    //申请权限
//	                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
            //
//	                }
            //
//	            }
            checkPermission(this);

        }else{
            new Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            SharedPreferences sharedPreferences = WelcomeActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);

                            String isLogin =sharedPreferences.getString("islogin", "false");
                            int umark =sharedPreferences.getInt("umark", -1);
                            Usertel =sharedPreferences.getString("tel", "");

                            if("true".equals(isLogin)&&umark==1){
                                Intent intent =new Intent(WelcomeActivity.this,PersonalUserHomePageActivity.class);

                                startActivity(intent);
                            }else if("true".equals(isLogin)&&umark==0) {
                                Intent intent = new Intent(WelcomeActivity.this, SellerHomePageActivity.class);

                                startActivity(intent);
                            }else {
                                Intent intent =new Intent(WelcomeActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        }
                    }, 2000);
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onLoginSuccess or onLoginFailed
                                    SharedPreferences sharedPreferences = WelcomeActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);

                                    String isLogin =sharedPreferences.getString("islogin", "false");
                                    int umark =sharedPreferences.getInt("umark", -1);
                                    Usertel =sharedPreferences.getString("tel", "");

                                    if("true".equals(isLogin)&&umark==1){
                                        Intent intent =new Intent(WelcomeActivity.this,PersonalUserHomePageActivity.class);

                                        startActivity(intent);
                                    }else if("true".equals(isLogin)&&umark==0) {
                                        Intent intent = new Intent(WelcomeActivity.this, SellerHomePageActivity.class);

                                        startActivity(intent);
                                    }else {
                                        Intent intent =new Intent(WelcomeActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    finish();
                                }
                            }, 2000);
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onLoginSuccess or onLoginFailed
                                    SharedPreferences sharedPreferences = WelcomeActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);

                                    String isLogin =sharedPreferences.getString("islogin", "false");
                                    int umark =sharedPreferences.getInt("umark", -1);
                                    Usertel =sharedPreferences.getString("tel", "");

                                    if("true".equals(isLogin)&&umark==1){
                                        Intent intent =new Intent(WelcomeActivity.this,PersonalUserHomePageActivity.class);

                                        startActivity(intent);
                                    }else if("true".equals(isLogin)&&umark==0) {
                                        Intent intent = new Intent(WelcomeActivity.this, SellerHomePageActivity.class);

                                        startActivity(intent);
                                    }else {
                                        Intent intent =new Intent(WelcomeActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    finish();
                                }
                            }, 2000);
                }
                return;
            }
        }
    }
    public  void checkPermission(Activity object) {


        int ACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(object, Manifest.permission.ACCESS_COARSE_LOCATION);
        int ACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(object, Manifest.permission.ACCESS_FINE_LOCATION);



        int PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED;

        if (ACCESS_COARSE_LOCATION != PERMISSION_GRANTED||ACCESS_FINE_LOCATION != PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(object,
                    permissions,
                    1);

        }else {
            new Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            SharedPreferences sharedPreferences = WelcomeActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);

                            String isLogin =sharedPreferences.getString("islogin", "false");
                            int umark =sharedPreferences.getInt("umark", -1);
                            Usertel =sharedPreferences.getString("tel", "");

                            if("true".equals(isLogin)&&umark==1){
                                Intent intent =new Intent(WelcomeActivity.this,PersonalUserHomePageActivity.class);

                                startActivity(intent);
                            }else if("true".equals(isLogin)&&umark==0) {
                                Intent intent = new Intent(WelcomeActivity.this, SellerHomePageActivity.class);

                                startActivity(intent);
                            }else {
                                Intent intent =new Intent(WelcomeActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        }
                    }, 2000);
        }

    }
}
