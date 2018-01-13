package leaflet.miaoa.qmsh.leaflet.Login;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import static leaflet.miaoa.qmsh.leaflet.bean.Https.loginpsd;


/**
 * 密码登录页面
 */
public class PasswordLoginFragment extends BaseFragment implements View.OnClickListener{

    private TextView tvForgetPassword,tvLogin;
    private EditText et_num,et_psd;
    private CheckBox checkboxButton;

//    private ImageView ivPerson,ivSeller;
    private int jumpFlag = 1;
    public ToggleButton mTogBtn;
    private String isLoginstr = "", tel="";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_login,null);
        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();//获取编辑器

        tel=sharedPreferences.getString("tel", "");

//        final int umark=sharedPreferences.getInt("umark", 0);
//        if(Common.isNOT_Null(tel)==true){
//            RequestQueue mQueue = Volley.newRequestQueue(getActivity());
//            StringRequest stringRequest = new StringRequest(
//                    passwordGet+"?uNum="+tel,
//                    new Response.Listener<String>() {
//                        @SuppressLint("WrongConstant")
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                if (Common.isNOT_Null(response)==true){
//
//                                    et_num.setText(tel);
//                                    et_psd.setText(response);
//                                    checkboxButton.setChecked(true);
//                                if (umark==0){
//                                    clearAllStyle();
//                                    ivSeller.setImageResource(R.mipmap.radiobutton_choosed);
//                                    jumpFlag = 0;
//                                }else if(umark==1){
//                                    clearAllStyle();
//                                    ivPerson.setImageResource(R.mipmap.radiobutton_choosed);
//                                    jumpFlag = 1;
//                                }
//                                }
//
//
//                            } catch (Exception e) {
//                                Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(getActivity(), "请检查网络设置", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(4000,// 默认超时时间，应设置一个稍微大点儿的，例如本处的500000
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            mQueue.add(stringRequest);
//        }


        return view;
    }

    @Override
    protected void setFindViewById(View view) {
        tvForgetPassword = (TextView) view.findViewById(R.id.tv_imessage_login_forgetpassword);
        tvLogin = (TextView) view.findViewById(R.id.tv_password_login);
        et_num = (EditText) view.findViewById(R.id.et_num);
        et_psd = (EditText) view.findViewById(R.id.et_psd);

//        ivPerson = (ImageView) view.findViewById(R.id.iv_password_login_person);
//        ivSeller = (ImageView) view.findViewById(R.id.iv_password_login_seller);
        checkboxButton = (CheckBox) view.findViewById(R.id.checkBoxLogin);
        if(Common.isNOT_Null(tel)==true){
            et_num.setText(tel);
        }
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
//        et_num.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // 输入的内容变化的监听
//                if(et_num.getText().toString().equals(tel)&&"true".equals(isLogin)){
//                    et_psd.setText("******");
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                // 输入前的监听
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // 输入后的监听
//
//            }
//        });

    }

    @Override
    protected void setListener() {
        tvForgetPassword.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

    }

    @Override
    protected void setControl() {
        clearAllStyle();
    }



    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.tv_imessage_login_forgetpassword:
                intent.setClass(getActivity(),ResetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_password_login:
                    final String telString = et_num.getText().toString().trim();
                    String psdString = et_psd.getText().toString().trim();
                final boolean CheckBoxLogin = checkboxButton.isChecked();
                //记住密码
                if (CheckBoxLogin)
                {
                    isLoginstr="y";
                }else {
                    isLoginstr="n";
                }

                    if (TextUtils.isEmpty(telString) || telString.length() != 11) {
                        Toast.makeText(getActivity(), "请输入有效的手机号", Toast.LENGTH_SHORT).show();

                    } else {



                        if (TextUtils.isEmpty(psdString)) {
                            Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();

                        } else {



                            if (jumpFlag == 1){


                            RequestQueue mQueue = Volley.newRequestQueue(getActivity());
                            StringRequest stringRequest = new StringRequest(
                                    loginpsd + "?uNum=" + telString + "&uPassword="+psdString + "&umark=" + jumpFlag + "&isLogin=" + isLoginstr ,
//                        doInUser+"?uname="+tel,
                                    new Response.Listener<String>() {
                                        @SuppressLint("WrongConstant")
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                String result[]=response.split(",");
                                                if ("false".equals(result[0])&&"true".equals(result[1])) {
                                                    editor.putString("tel", telString);
                                                    editor.putInt("umark", jumpFlag);
                                                    editor.putString("islogin", "true");
                                                    editor.commit();
                                                    Usertel=telString;
                                                    Intent intent = new Intent(getActivity(), PersonalUserHomePageActivity.class);
                                                    startActivity(intent);

                                                } else if("true".equals(result[0])){
                                                    Toast.makeText(getActivity(), "该帐号已被拉黑！", Toast.LENGTH_SHORT).show();
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
                                        loginpsd + "?uNum=" + telString + "&uPassword="+psdString + "&umark=" + jumpFlag + "&isLogin=" + isLoginstr ,
//                        doInUser+"?uname="+tel,
                                        new Response.Listener<String>() {
                                            @SuppressLint("WrongConstant")
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    String result[]=response.split(",");
                                                    if ("false".equals(result[0])&&"true".equals(result[1])) {
                                                        editor.putString("tel", telString);
                                                        editor.putInt("umark", jumpFlag);
                                                        editor.commit();
                                                        Usertel=telString;
                                                        Intent intent = new Intent(getActivity(), SellerHomePageActivity.class);
                                                        startActivity(intent);

                                                    } else if("true".equals(result[0])){
                                                        Toast.makeText(getActivity(), "该帐号已被拉黑！", Toast.LENGTH_SHORT).show();
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
                }




                break;

        }
    }

    private void clearAllStyle() {

    }
}
