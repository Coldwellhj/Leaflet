package leaflet.miaoa.qmsh.leaflet.ui.personaluser;


import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.base.BaseFragmentActivity;
import leaflet.miaoa.qmsh.leaflet.ui.adapter.ViewPagerAdapter;
import leaflet.miaoa.qmsh.leaflet.ui.personaluser.home.PersonalHomeFragment;
import leaflet.miaoa.qmsh.leaflet.utils.DateUtils;


import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;

import java.util.ArrayList;
import java.util.List;



/**
 * 个人用户主界面
 */
public class PersonalUserHomePageActivity extends BaseFragmentActivity implements View.OnClickListener{

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private ImageView ivRedMoney,ivHome,ivMy;
    private TextView tvRedMoney,tvHome,tvMy;
    private LinearLayout llRedMoney,llHome,llMy;
    private List<ImageView> ivList = new ArrayList<>();
    private List<TextView> tvList = new ArrayList<>();
    private List<Fragment> listFragment = new ArrayList<>();

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    public static String start="";
    Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //加载fragment
                    listFragment.add(new PersonalHomeFragment());
//        listFragment.add(new RedMoneyFragment());
//        listFragment.add(new SellerMyFragment());
                    adapter= new ViewPagerAdapter(getSupportFragmentManager(),listFragment);
                    viewPager.setAdapter(adapter);
                    break;

            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_personal_user_home_page);
        //初始化定位
        initLocation();
        startLocation();
    }

    @Override
    protected void setFindViewById() {
        ivRedMoney = (ImageView) findViewById(R.id.iv_personal_user_red_money);
        tvRedMoney = (TextView) findViewById(R.id.tv_personal_user_red_money);
        ivHome = (ImageView) findViewById(R.id.iv_personal_user_home);
        tvHome = (TextView) findViewById(R.id.tv_personal_user_home);
        ivMy = (ImageView) findViewById(R.id.iv_personal_user_my);
        tvMy = (TextView) findViewById(R.id.tv_personal_user_my);
        llRedMoney = (LinearLayout) findViewById(R.id.ll_personal_user_red_money);
        llHome = (LinearLayout) findViewById(R.id.ll_personal_user_home);
        llMy = (LinearLayout) findViewById(R.id.ll_personal_user_my);
        viewPager = (ViewPager) findViewById(R.id.vp_personal_user_home_viewpager);
        viewPager.setOffscreenPageLimit(0);
    }

    @Override
    protected void setListener() {
        llRedMoney.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llMy.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                clearAllStyle();
                if (position == 0){
                    ivList.get(position).setImageResource(R.mipmap.seller_home_checked);
                    tvList.get(position).setTextColor(Color.parseColor("#f23030"));
                } else if (position == 1){
                    ivList.get(position).setImageResource(R.mipmap.bribery_money_bright);
                    tvList.get(position).setTextColor(Color.parseColor("#f23030"));
                } else {
                    ivList.get(position).setImageResource(R.mipmap.seller_my_checked);
                    tvList.get(position).setTextColor(Color.parseColor("#f23030"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void setControl() {



        ivList.add(ivHome);
        ivList.add(ivRedMoney);
        ivList.add(ivMy);
        tvList.add(tvHome);
        tvList.add(tvRedMoney);
        tvList.add(tvMy);
        clearAllStyle();
        ivHome.setImageResource(R.mipmap.seller_home_checked);
        tvHome.setTextColor(Color.parseColor("#f23030"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_personal_user_home:
                clearAllStyle();
                ivHome.setImageResource(R.mipmap.seller_home_checked);
                tvHome.setTextColor(Color.parseColor("#f23030"));
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_personal_user_red_money:
                clearAllStyle();
                ivRedMoney.setImageResource(R.mipmap.bribery_money_bright);
                tvRedMoney.setTextColor(Color.parseColor("#f23030"));
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_personal_user_my:
                clearAllStyle();
                ivMy.setImageResource(R.mipmap.seller_my_checked);
                tvMy.setTextColor(Color.parseColor("#f23030"));
                viewPager.setCurrentItem(2);
                break;
        }
    }

    //清除所有样式
    private void clearAllStyle() {
        ivRedMoney.setImageResource(R.mipmap.bribery_money_dark);
        ivHome.setImageResource(R.mipmap.seller_home_uncheck);
        ivMy.setImageResource(R.mipmap.seller_my_unchecked);
        tvRedMoney.setTextColor(Color.parseColor("#5d5f6a"));
        tvHome.setTextColor(Color.parseColor("#5d5f6a"));
        tvMy.setTextColor(Color.parseColor("#5d5f6a"));
    }

    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }
    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
//        //根据控件的选择，重新设置定位参数
//        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }
    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }
    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if(location.getErrorCode() == 0){
//                    sb.append("定位成功" + "\n");
//                    sb.append("定位类型: " + location.getLocationType() + "\n");
//                    sb.append("经    度    : " + location.getLongitude() + "\n");
//                    sb.append("纬    度    : " + location.getLatitude() + "\n");
//                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
//                    sb.append("提供者    : " + location.getProvider() + "\n");
//
//                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
//                    sb.append("角    度    : " + location.getBearing() + "\n");
//                    // 获取当前提供定位服务的卫星个数
//                    sb.append("星    数    : " + location.getSatellites() + "\n");
//                    sb.append("国    家    : " + location.getCountry() + "\n");
//                    sb.append("省            : " + location.getProvince() + "\n");
//                    sb.append("市            : " + location.getCity() + "\n");
//                    sb.append("城市编码 : " + location.getCityCode() + "\n");
//                    sb.append("区            : " + location.getDistrict() + "\n");
//                    sb.append("区域 码   : " + location.getAdCode() + "\n");
//                    sb.append("地    址    : " + location.getAddress() + "\n");
//                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
//                    //定位完成的时间
//                    sb.append("定位时间: " + DateUtils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");

                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
//                sb.append("***定位质量报告***").append("\n");
//                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启":"关闭").append("\n");
//                sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
//                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
//                sb.append("****************").append("\n");
                //定位之后的回调时间
//                sb.append("回调时间: " + DateUtils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                //解析定位结果，
//                String result = sb.toString();

//                Toast.makeText(PersonalUserHomePageActivity.this,"定位结果："+result,Toast.LENGTH_SHORT).show();
                start=location.getLongitude()+","+location.getLatitude();
                stopLocation();
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
//                tvResult.setText(result);
            } else {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
                Toast.makeText(PersonalUserHomePageActivity.this,"定位失败，loc is null",Toast.LENGTH_SHORT).show();
//                tvResult.setText("定位失败，loc is null");
            }
        }
    };
    /**
     * 获取GPS状态的字符串
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode){
        String str = "";
        switch (statusCode){
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }
}
