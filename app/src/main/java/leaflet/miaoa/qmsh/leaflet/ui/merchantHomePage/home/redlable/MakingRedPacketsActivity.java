package leaflet.miaoa.qmsh.leaflet.ui.merchantHomePage.home.redlable;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.base.BaseActivity;
import leaflet.miaoa.qmsh.leaflet.bean.ListActivityBean;
import leaflet.miaoa.qmsh.leaflet.ui.widget.MyPopupWindow;
import leaflet.miaoa.qmsh.leaflet.ui.widget.NoslidingListView;
import leaflet.miaoa.qmsh.leaflet.utils.Common;

import static leaflet.miaoa.qmsh.leaflet.Login.WelcomeActivity.Usertel;
import static leaflet.miaoa.qmsh.leaflet.bean.Https.slideShow;
import static leaflet.miaoa.qmsh.leaflet.bean.Https.toaddCoin;
import static leaflet.miaoa.qmsh.leaflet.bean.Https.toaddadver;


/**
 *制作红包广告页面
 */
public class MakingRedPacketsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iBack;
    private TagFlowLayout mFlowLayout;
    private LayoutInflater mInflater;
    private List<String> tagList = new ArrayList<>();
    private MyPopupWindow myPopupWindow;

    private TextView tvNext,totalMoney;
    private EditText et_price,et_personNum,et_time;
    private String price,personNum,time;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private RelativeLayout rl_chooseAddress;
    Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mFlowLayout.setAdapter(new TagAdapter<String>(tagList) {
                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            TextView tv = (TextView) mInflater.inflate(R.layout.tv,mFlowLayout,false);
                            tv.setText(s);
                            return tv;
                        }

                    });
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_making_red_packets);
        mInflater = LayoutInflater.from(MakingRedPacketsActivity.this);
    }

    @Override
    protected void setFindViewById() {
        iBack = (ImageView) findViewById(R.id.iv_making_red_packets_back);
        mFlowLayout = (TagFlowLayout) findViewById(R.id.tl_making_red_packets_tagflowlayout);
        tvNext = (TextView) findViewById(R.id.tv_making_red_packets_next);
        totalMoney = (TextView) findViewById(R.id.totalMoney);
        et_price = (EditText) findViewById(R.id.et_price);
        et_personNum = (EditText) findViewById(R.id.et_personNum);
        et_time = (EditText) findViewById(R.id.et_time);
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        rl_chooseAddress = (RelativeLayout) findViewById(R.id.rl_chooseAddress);

    }

    @Override
    protected void setListener() {
        iBack.setOnClickListener(this);
        tvNext.setOnClickListener(this);

        rl_chooseAddress.setOnClickListener(this);
    }

    @Override
    protected void setControl() {
        et_price.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                if(Common.isNOT_Null(et_time.getText().toString().trim())==false){
//                    Toast.makeText(MakingRedPacketsActivity.this,"请输入投放天数",Toast.LENGTH_SHORT);
                }else if(Common.isNOT_Null(et_personNum.getText().toString().trim())==false){
//                    Toast.makeText(MakingRedPacketsActivity.this,"请输入每天覆盖人数",Toast.LENGTH_SHORT);
                }else if(et_price.getText().toString().trim().endsWith(".")){

                }else if(Common.isNOT_Null(et_price.getText().toString().trim())==true){
                    totalMoney.setText(String.valueOf(Integer.parseInt(et_time.getText().toString().trim())*Integer.parseInt(et_personNum.getText().toString().trim())*Double.parseDouble(et_price.getText().toString().trim())));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听

            }
        });
        et_time.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                if(Common.isNOT_Null(et_price.getText().toString().trim())==false){
//                    Toast.makeText(MakingRedPacketsActivity.this,"请输入投放天数",Toast.LENGTH_SHORT);
                }else if(Common.isNOT_Null(et_personNum.getText().toString().trim())==false){
//                    Toast.makeText(MakingRedPacketsActivity.this,"请输入每天覆盖人数",Toast.LENGTH_SHORT);
                }else if(Common.isNOT_Null(et_time.getText().toString().trim())==true){
                    totalMoney.setText(String.valueOf(Integer.parseInt(et_time.getText().toString().trim())*Integer.parseInt(et_personNum.getText().toString().trim())*Double.parseDouble(et_price.getText().toString().trim())));

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听

            }
        });
        et_personNum.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                if(Common.isNOT_Null(et_price.getText().toString().trim())==false){
//                    Toast.makeText(MakingRedPacketsActivity.this,"请输入投放天数",Toast.LENGTH_SHORT);
                }else if(Common.isNOT_Null(et_time.getText().toString().trim())==false){
//                    Toast.makeText(MakingRedPacketsActivity.this,"请输入每天覆盖人数",Toast.LENGTH_SHORT);
                }else if(Common.isNOT_Null(et_personNum.getText().toString().trim())==true){
                    totalMoney.setText(String.valueOf(Integer.parseInt(et_time.getText().toString().trim())*Integer.parseInt(et_personNum.getText().toString().trim())*Double.parseDouble(et_price.getText().toString().trim())));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听

            }
        });


        Toaddadver();




    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.iv_making_red_packets_back:
                finish();
                break;
            case R.id.tv_making_red_packets_next:
                showSelectedResult();
//                intent.setClass(this,ChooseVideoActivity.class);
//                startActivity(intent);
                break;
            case R.id.rl_chooseAddress:
                myPopupWindow = new MyPopupWindow(MakingRedPacketsActivity.this, itemsOnClick);
                myPopupWindow.showAtLocation(this.findViewById(R.id.tv_making_red_packets_next),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                intent.setClass(this,ChooseVideoActivity.class);
//                startActivity(intent);
                break;
        }
    }
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.cancle:
//                    Log.i(TAG, "保存线路");
                    myPopupWindow.dismiss();
                    break;
                case R.id.confirm:
                    break;

            }

        }

    };




    private void showSelectedResult() {
        Toast.makeText(MakingRedPacketsActivity.this, "当前选中:"+mCurrentProviceName+","+mCurrentCityName+","
                +mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
    }



    private void Toaddadver(){
        RequestQueue mQueue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(
                toaddadver  ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray =new JSONArray(response);
                            ListActivityBean listActivityBean=new ListActivityBean();
                            //          int length = jsonArray.length();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String userlike=jsonObject.getString("userlike");

                                tagList.add(i,userlike);

                            }
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        } catch (Exception e) {

                            Toast.makeText(MakingRedPacketsActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MakingRedPacketsActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(4000,// 默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(stringRequest);
    }

}
