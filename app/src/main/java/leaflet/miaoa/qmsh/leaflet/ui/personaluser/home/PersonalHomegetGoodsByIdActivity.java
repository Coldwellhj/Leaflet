package leaflet.miaoa.qmsh.leaflet.ui.personaluser.home;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.FixedIndicatorView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.bean.HomePageAdvDataBean;
import leaflet.miaoa.qmsh.leaflet.bean.ListActivityBean;
import leaflet.miaoa.qmsh.leaflet.ui.adapter.GoodRulesAdapter;
import leaflet.miaoa.qmsh.leaflet.ui.adapter.HomeAdvIndicatorAdapter;
import leaflet.miaoa.qmsh.leaflet.ui.widget.MaxRecyclerView;
import leaflet.miaoa.qmsh.leaflet.ui.widget.ObservableScrollView;
import leaflet.miaoa.qmsh.leaflet.utils.CallPhone;

import static leaflet.miaoa.qmsh.leaflet.bean.Https.getCoinById;
import static leaflet.miaoa.qmsh.leaflet.utils.DateUtils.getStr1FromDate;
import static leaflet.miaoa.qmsh.leaflet.utils.DateUtils.getStr2FromDate;
import static leaflet.miaoa.qmsh.leaflet.utils.DateUtils.getTimeFromLong;

public class PersonalHomegetGoodsByIdActivity extends Activity implements ObservableScrollView.ScrollViewListener {

    private RelativeLayout iv_back;
    private RelativeLayout head;
    private ViewPager banner_viewPager;
    private FixedIndicatorView guide_indicator;
    private TextView cNowPrice, cNowPrice_mid;
    private TextView tv3, tv3_mid;
    private View topPanel, middlePanel;
    private RelativeLayout pay, pay_mid;
    private int topHeight;
    private ObservableScrollView scrollView;
    private TextView shop_address;
    private TextView shop_name;
    private ImageView iv2;
    private ImageView tel;
    private TextView goods_name;
    private TextView tv2;
    private TextView tv_goods_introduce;
    private TextView term_of_validity;
    private TextView usingTime;
    private MaxRecyclerView recyclerView_use_of_rules;
    private String cId="";
    private Activity activity;
    private List<HomePageAdvDataBean> getAdvList = new ArrayList<HomePageAdvDataBean>();
    private List<ListActivityBean.Goods_shopping_mall> goods_shopping_malls_list = new ArrayList<ListActivityBean.Goods_shopping_mall>();
    private List<String>advList  = new ArrayList<String>();
    private List<String>rulesList  = new ArrayList<String>();
    private HomeAdvIndicatorAdapter homeadvindicatorAdapter;
    private GoodRulesAdapter goodRulesAdapter;
    private BannerComponent bannerComponent;
    private CallPhone callPhone;

    Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initAdvList();

                    homeadvindicatorAdapter.notifyDataSetChanged();
                    cNowPrice.setText(goods_shopping_malls_list.get(0).getcNowPrice());
                    cNowPrice_mid.setText(goods_shopping_malls_list.get(0).getcNowPrice());
                    tv3.setText("¥"+goods_shopping_malls_list.get(0).getcFormerPrice());
                    tv3_mid.setText("¥"+goods_shopping_malls_list.get(0).getcFormerPrice());
                    shop_name.setText(goods_shopping_malls_list.get(0).getsName());
                    shop_address.setText(goods_shopping_malls_list.get(0).getsAddress());
                    goods_name.setText(goods_shopping_malls_list.get(0).getcName());
                    tv_goods_introduce.setText(goods_shopping_malls_list.get(0).getcIntro());
                    term_of_validity.setText(getStr2FromDate(getTimeFromLong(goods_shopping_malls_list.get(0).getcUploadTime()))+"至"+getStr2FromDate(getTimeFromLong(goods_shopping_malls_list.get(0).getcCheckTime())));
                    usingTime.setText(getStr1FromDate(getTimeFromLong(goods_shopping_malls_list.get(0).getBeginTime()))+"至"+getStr1FromDate(getTimeFromLong(goods_shopping_malls_list.get(0).getEndTime())));
                    callPhone=new CallPhone(activity,goods_shopping_malls_list.get(0).getsLegalPhone());
                    break;

            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_homeget_goods_by_id);
        Intent intent =getIntent();
        cId=intent.getStringExtra("cId");
        activity=this;
        httpGetCoinById();

        initView();

    }

    private void initView() {
        scrollView = (ObservableScrollView) findViewById(R.id.scrollView);
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        head = (RelativeLayout) findViewById(R.id.head);
        banner_viewPager = (ViewPager) findViewById(R.id.banner_viewPager);
        guide_indicator = (FixedIndicatorView) findViewById(R.id.guide_indicator);
        topPanel = findViewById(R.id.topPanel);
        cNowPrice = (TextView) topPanel.findViewById(R.id.cNowPrice);
        tv3 = (TextView) topPanel.findViewById(R.id.tv3);
        pay = (RelativeLayout) topPanel.findViewById(R.id.pay);
        middlePanel = findViewById(R.id.middlePanel);
        cNowPrice_mid = (TextView) middlePanel.findViewById(R.id.cNowPrice);
        tv3_mid = (TextView) middlePanel.findViewById(R.id.tv3);
        pay_mid = (RelativeLayout) middlePanel.findViewById(R.id.pay);
        scrollView.setScrollViewListener(this);
        shop_address = (TextView) findViewById(R.id.shop_address);

        shop_name = (TextView) findViewById(R.id.shop_name);
        iv2 = (ImageView) findViewById(R.id.iv2);
        tel = (ImageView) findViewById(R.id.tel);

        goods_name = (TextView) findViewById(R.id.goods_name);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv_goods_introduce = (TextView) findViewById(R.id.tv_goods_introduce);
        term_of_validity = (TextView) findViewById(R.id.term_of_validity);
        usingTime = (TextView) findViewById(R.id.usingTime);
        recyclerView_use_of_rules = (MaxRecyclerView) findViewById(R.id.recyclerView_use_of_rules);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone.requestPermission();
            }
        });

        bannerComponent = new BannerComponent(guide_indicator, banner_viewPager, true);
        homeadvindicatorAdapter = new HomeAdvIndicatorAdapter(this, getAdvList);
        bannerComponent.setAdapter(homeadvindicatorAdapter);
        bannerComponent.setAutoPlayTime(2500);
//规则字段还没写

        recyclerView_use_of_rules.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView_use_of_rules.setNestedScrollingEnabled(false);
        goodRulesAdapter = new GoodRulesAdapter(this,rulesList);
        recyclerView_use_of_rules.setAdapter(goodRulesAdapter);
        goodRulesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;//状态栏高度

        int titleBarHeight = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();//标题栏高度
        topHeight = titleBarHeight + statusBarHeight;
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        int[] location = new int[2];
        pay_mid.getLocationOnScreen(location);
        int locationY = location[1];
        Log.e("locationY", locationY + "   " + "topHeight的值是：" + topHeight);

        if (locationY <= topHeight && (topPanel.getVisibility() == View.GONE || topPanel.getVisibility() == View.INVISIBLE)) {
            topPanel.setVisibility(View.VISIBLE);
        }

        if (locationY > topHeight && topPanel.getVisibility() == View.VISIBLE) {
            topPanel.setVisibility(View.GONE);
        }

    }

    /**
     * 注册权限申请回调
     * @param requestCode 申请码
     * @param permissions 申请的权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case  CallPhone.RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    callPhone.callPhone();
                }
                else
                {
                    // Permission Denied
                    Toast.makeText(PersonalHomegetGoodsByIdActivity.this, "打电话权限被禁止！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void initAdvList() {
        for(int i=0;i<advList.size();i++){
            getAdvList.add(new HomePageAdvDataBean("1", "1", "1", "1", "1", "1", "1",advList.get(i) ,""+i, ""));

        }
    }



    private void httpGetCoinById(){
        RequestQueue mQueue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(
                getCoinById+"?cId="+cId  ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            ListActivityBean listActivityBean=new ListActivityBean();

                                String cName=jsonObject.getString("cName");
                                Long beginTime=jsonObject.getLong("beginTime");
                                Long endTime=jsonObject.getLong("endTime");
                                Long cUploadTime=jsonObject.getLong("cUploadTime");
                                Long cCheckTime=jsonObject.getLong("cCheckTime");
                                String cIntro=jsonObject.getString("cIntro");
                                String cImgs=jsonObject.getString("cImgs");
                                String sName=jsonObject.getString("sName");
                                String cNowPrice=jsonObject.getString("cNowPrice");
                                String cFormerPrice=jsonObject.getString("cFormerPrice");
                                String sAddress=jsonObject.getString("sAddress");
                                String cDirection=jsonObject.getString("cDirection");

                                String sLegalPhone=jsonObject.getString("sLegalPhone");

                                String[] str=cImgs.split("<-->");
                                for(int j=0;j<str.length;j++){
                                    advList.add(j,str[j]);
                                }
                                //使用规则
                                if (cDirection.equals("null")){

                                }else {
                                    String[] rulesstr=cDirection.split("<-->");
                                    for(int j=0;j<rulesstr.length;j++){
                                        rulesList.add(j,rulesstr[j]);
                                    }
                                }

                                goodRulesAdapter.notifyDataSetChanged();
                                ListActivityBean.Goods_shopping_mall goods_shopping_mall=listActivityBean.new Goods_shopping_mall();
                                goods_shopping_mall.setcName(cName);
                                goods_shopping_mall.setcNowPrice(cNowPrice);
                                goods_shopping_mall.setcFormerPrice(cFormerPrice);
                                goods_shopping_mall.setsName(sName);
                                goods_shopping_mall.setBeginTime(beginTime);
                                goods_shopping_mall.setEndTime(endTime);
                                goods_shopping_mall.setcUploadTime(cUploadTime);
                                goods_shopping_mall.setcCheckTime(cCheckTime);
                                goods_shopping_mall.setcIntro(cIntro);
                                goods_shopping_mall.setsAddress(sAddress);
                                goods_shopping_mall.setsLegalPhone(sLegalPhone);
                                goods_shopping_malls_list.add(goods_shopping_mall);

//                            }
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        } catch (Exception e) {

                            Toast.makeText(PersonalHomegetGoodsByIdActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PersonalHomegetGoodsByIdActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(4000,// 默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(stringRequest);
    }

}
