package leaflet.miaoa.qmsh.leaflet.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import leaflet.miaoa.qmsh.leaflet.R;


/**
 * Created by Administrator on 2017/9/11.
 */

public abstract class BaseFragmentActivity  extends FragmentActivity {

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //清空Fragment
        if (savedInstanceState != null){
            savedInstanceState.putParcelable("android.support:fragments",null);
        }
        super.onCreate(savedInstanceState);
        context = BaseFragmentActivity.this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //竖屏
        setContentView();
        setFindViewById();
        setListener();
        setControl();
        initWindow();
    }

    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.title));
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    /**设置布局xml*/
    protected abstract void setContentView();

    /**获取view组件*/
    protected abstract void setFindViewById();

    /**设置监听*/
    protected abstract void setListener();

    /**主代码逻辑*/
    protected abstract void setControl();
}
