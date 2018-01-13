package leaflet.miaoa.qmsh.leaflet.Login;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.base.BaseActivity;

import static leaflet.miaoa.qmsh.leaflet.bean.Https.getCode;
import static leaflet.miaoa.qmsh.leaflet.bean.Https.isExist;
import static leaflet.miaoa.qmsh.leaflet.bean.Https.register;


/**
 * 注册页面
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iBack;
    private TextView tvLogin,tvGetNum,tv_register;
    private CountDownTimer countDownTimer;
    private EditText et_register_phonenumber;
    private EditText et_Code,et_password,et_password_confirm;

    private String strCode;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void setFindViewById() {
        tvLogin = (TextView) findViewById(R.id.tv_register_login);
        tv_register = (TextView) findViewById(R.id.tv_register);
        iBack = (ImageView) findViewById(R.id.iv_register_back);
        tvGetNum = (TextView) findViewById(R.id.tv_register_getnum);
        et_register_phonenumber = (EditText) findViewById(R.id.et_register_phonenumber);
        et_Code = (EditText) findViewById(R.id.et_Code);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_confirm = (EditText) findViewById(R.id.et_password_confirm);
        et_register_phonenumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                if(et_register_phonenumber.getText().toString().length()==11){
                    RequestQueue mQueue = Volley.newRequestQueue(RegisterActivity.this);
                    StringRequest stringRequest = new StringRequest(
                            isExist + "?uNum=" + et_register_phonenumber.getText().toString().trim()  ,
//
                            new Response.Listener<String>() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onResponse(String response) {
                                    try {
                                       if("2".equals(response)){
                                           Toast.makeText(RegisterActivity.this, "账号已经存在", Toast.LENGTH_SHORT).show();
                                       }

                                    } catch (Exception e) {
                                        Toast.makeText(RegisterActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();

                        }
                    });
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(4000,// 默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    mQueue.add(stringRequest);
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
    }

    @Override
    protected void setListener() {
        tvLogin.setOnClickListener(this);
        iBack.setOnClickListener(this);
        tvGetNum.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    protected void setControl() {

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.tv_register_login:
                intent.setClass(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_register_back:
                finish();
                break;
            case R.id.tv_register_getnum:
                final String telString = et_register_phonenumber.getText().toString().trim();
                if (TextUtils.isEmpty(telString) || telString.length() != 11) {
                    Toast.makeText(RegisterActivity.this, "请输入有效的手机号", Toast.LENGTH_SHORT).show();
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
                    RequestQueue mQueue = Volley.newRequestQueue(RegisterActivity.this);
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
                                            Toast.makeText(RegisterActivity.this, "验证码获取失败", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(RegisterActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();

                        }
                    });
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(4000,// 默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    mQueue.add(stringRequest);
                }


                break;
            case R.id.tv_register:
                if(et_register_phonenumber.getText().toString().trim().length()!=11){
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();

                }else if (!et_Code.getText().toString().trim().equals(strCode)){
                Toast.makeText(RegisterActivity.this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();

                }else if (et_password.getText().toString().trim().length()<6||et_password.getText().toString().trim().length()>15){
                    Toast.makeText(RegisterActivity.this, "请输入6-15位的密码", Toast.LENGTH_SHORT).show();

                }else if (!et_password.getText().toString().trim().equals(et_password_confirm.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this, "请输入相同的密码", Toast.LENGTH_SHORT).show();

                }else {
                    RequestQueue mQueue = Volley.newRequestQueue(RegisterActivity.this);
                    StringRequest stringRequest = new StringRequest(
                            register + "?uNum=" + et_register_phonenumber.getText().toString().trim()+"&uPassword="+et_password.getText().toString().trim()  ,
//
                            new Response.Listener<String>() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        if("true".equals(response)){
                                            Intent intent = new Intent(RegisterActivity.this,PasswordLoginFragment.class);
                                            startActivity(intent);
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(RegisterActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();

                        }
                    });
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(4000,// 默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    mQueue.add(stringRequest);

                }

                break;
        }
    }

}
