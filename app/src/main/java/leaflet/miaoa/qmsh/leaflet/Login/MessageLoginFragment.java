package leaflet.miaoa.qmsh.leaflet.Login;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.base.BaseFragment;
import leaflet.miaoa.qmsh.leaflet.ui.merchantHomePage.SellerHomePageActivity;
import leaflet.miaoa.qmsh.leaflet.ui.personaluser.PersonalUserHomePageActivity;
import leaflet.miaoa.qmsh.leaflet.utils.Common;

import static leaflet.miaoa.qmsh.leaflet.Login.WelcomeActivity.Usertel;
import static leaflet.miaoa.qmsh.leaflet.bean.Https.getCode;
import static leaflet.miaoa.qmsh.leaflet.bean.Https.loginMsg;


/**
 * 短信验证码登录页面
 */
public class MessageLoginFragment extends BaseFragment implements View.OnClickListener{

    private TextView tvGetNum,tv_confirm_msg,et_num,et_Code;
    private CountDownTimer countDownTimer;
    private String strCode, tel="";
    private int jumpFlag = 1;

    private ImageView iv_img;
    public ToggleButton mTogBtn;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_login,null);
        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();//获取编辑器


        tel=sharedPreferences.getString("tel", "");
        return view;
    }

    @Override
    protected void setFindViewById(View view) {
        tvGetNum = (TextView) view.findViewById(R.id.tv_register_getnum);
        et_num = (TextView) view.findViewById(R.id.et_num);
        et_Code = (TextView) view.findViewById(R.id.et_Code);
        tv_confirm_msg = (TextView) view.findViewById(R.id.tv_confirm_msg);
        if(Common.isNOT_Null(tel)==true){
            et_num.setText(tel);
        }
        iv_img = (ImageView) view.findViewById(R.id.iv_img);
        mTogBtn = (ToggleButton) view.findViewById(R.id.mTogBtn); // 获取到控件
        mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //选中
                    jumpFlag = 0;
                }else{
                    //未选中
                    jumpFlag = 1;
                }
            }
        });// 添加监听事件

//        ivPerson = (ImageView) view.findViewById(R.id.iv_password_login_person);
//        ivSeller = (ImageView) view.findViewById(R.id.iv_password_login_seller);
    }

    @Override
    protected void setListener() {
        tvGetNum.setOnClickListener(this);
        tv_confirm_msg.setOnClickListener(this);

    }

    @Override
    protected void setControl() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_register_getnum:
                final String telString = et_num.getText().toString().trim();
                if (TextUtils.isEmpty(telString) || telString.length() != 11) {
                    Toast.makeText(getActivity(), "请输入有效的手机号", Toast.LENGTH_SHORT).show();

                }else {
                    if (countDownTimer == null){
                        countDownTimer = new CountDownTimer(60000,1000) {
                            @Override
                            public void onTick(long l) {
                                String time = l/1000+"s";
                                tvGetNum.setText(time);
                                tvGetNum.setTextColor(Color.parseColor("#999999"));


                            }

                            @Override
                            public void onFinish() {
                                tvGetNum.setText("获取验证码");
                                tvGetNum.setTextColor(Color.parseColor("#333333"));
                                countDownTimer = null;
                            }
                        }.start();
                    }
                    RequestQueue mQueue = Volley.newRequestQueue(getActivity());
                    StringRequest stringRequest = new StringRequest(
                            getCode + "?uNum=" + telString + "&action=send" ,
//
                            new Response.Listener<String>() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        strCode=response.replaceAll("[^0-9]","");
                                        String isSuccess=response.replaceAll("[^a-z]","");
                                        if("false".equals(isSuccess)){
                                            Toast.makeText(getActivity(), "验证码获取失败", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "请检查网络设置", Toast.LENGTH_SHORT).show();

                        }
                    });
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(4000,// 默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    mQueue.add(stringRequest);

                }
                break;
            case R.id.tv_confirm_msg:
                if(et_num.getText().toString().trim().length()!=11){
                    Toast.makeText(getActivity(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();

                }else if (!et_Code.getText().toString().trim().equals(strCode)){
                    Toast.makeText(getActivity(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();

                }else {

                    if (jumpFlag == 1){


                        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
                        StringRequest stringRequest = new StringRequest(
                                loginMsg + "?uNum=" + et_num.getText().toString().trim()+"&umark="+jumpFlag  ,
//
                                new Response.Listener<String>() {
                                    @SuppressLint("WrongConstant")
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            if("true".equals(response)){
                                                editor.putString("tel", et_num.getText().toString().trim());
                                                editor.putInt("umark", jumpFlag);
                                                editor.putString("islogin", "true");
                                                editor.commit();
                                                Usertel= et_num.getText().toString().trim();
                                                Intent intent = new Intent(getActivity(),PersonalUserHomePageActivity.class);
                                                startActivity(intent);
                                            }

                                        } catch (Exception e) {
                                            Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), "请检查网络设置", Toast.LENGTH_SHORT).show();

                            }
                        });
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(4000,// 默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        mQueue.add(stringRequest);
                    }
                    else if (jumpFlag == 0){
                        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
                        StringRequest stringRequest = new StringRequest(
                                loginMsg + "?uNum=" + et_num.getText().toString().trim()+"&umark="+jumpFlag  ,
//
                                new Response.Listener<String>() {
                                    @SuppressLint("WrongConstant")
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            if("true".equals(response)){
                                                editor.putString("tel",et_num.getText().toString().trim());
                                                editor.putInt("umark", jumpFlag);
                                                editor.commit();
                                                Usertel= et_num.getText().toString().trim();
                                                Intent intent = new Intent(getActivity(),SellerHomePageActivity.class);
                                                startActivity(intent);
                                            }

                                        } catch (Exception e) {
                                            Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), "请检查网络设置", Toast.LENGTH_SHORT).show();

                            }
                        });
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(4000,// 默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        mQueue.add(stringRequest);

                    } else {
                        Toast.makeText(getActivity(), "请选择登录类型", Toast.LENGTH_SHORT).show();
                    }


                }
                break;

        }
    }


}
