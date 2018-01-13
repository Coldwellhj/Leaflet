package leaflet.miaoa.qmsh.leaflet.Login;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.base.BaseFragmentActivity;
import leaflet.miaoa.qmsh.leaflet.ui.adapter.ViewPagerAdapter;


/**
 * 登录界面
 */
public class LoginActivity extends BaseFragmentActivity implements View.OnClickListener{

    private List<TextView> tvList = new ArrayList<>();
    private List<TextView> underlineList = new ArrayList<>();
    private List<Fragment> listFragment = new ArrayList<>();
    private TextView tvPassword,tvMessage,underline1,underline2,tvRegister;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void setFindViewById() {
        tvRegister = (TextView) findViewById(R.id.tv_login_register);
        tvPassword = (TextView) findViewById(R.id.tv_password_login);
        tvMessage = (TextView) findViewById(R.id.tv_imessage_login);
        underline1 = (TextView) findViewById(R.id.tv_login_underline1);
        underline2 = (TextView) findViewById(R.id.tv_login_underline2);
        viewPager = (ViewPager) findViewById(R.id.vp_login_viewpager);
        viewPager.setOffscreenPageLimit(0);
    }

    @Override
    protected void setListener() {
        tvRegister.setOnClickListener(this);
        tvPassword.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                clearAllStyle();
                tvList.get(position).setTextColor(Color.parseColor("#e75356"));
                underlineList.get(position).setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    @Override
    protected void setControl() {
        tvList.add(tvPassword);
        tvList.add(tvMessage);
        underlineList.add(underline1);
        underlineList.add(underline2);
        clearAllStyle();
        tvPassword.setTextColor(Color.parseColor("#e75356"));
        underline1.setVisibility(View.VISIBLE);
        //密码登录
        listFragment.add(new MessageLoginFragment());
        //验证码登录
        listFragment.add(new PasswordLoginFragment());
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),listFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.tv_password_login:
                clearAllStyle();
                tvPassword.setTextColor(Color.parseColor("#e75356"));
                underline1.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_imessage_login:
                clearAllStyle();
                tvMessage.setTextColor(Color.parseColor("#e75356"));
                underline2.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_login_register:
                intent.setClass(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void clearAllStyle() {
        underline1.setVisibility(View.INVISIBLE);
        underline2.setVisibility(View.INVISIBLE);
        tvPassword.setTextColor(Color.parseColor("#333333"));
        tvMessage.setTextColor(Color.parseColor("#333333"));
    }
}
