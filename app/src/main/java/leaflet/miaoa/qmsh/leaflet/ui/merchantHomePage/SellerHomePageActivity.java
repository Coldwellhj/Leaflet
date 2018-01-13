package leaflet.miaoa.qmsh.leaflet.ui.merchantHomePage;


import android.support.annotation.IdRes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.base.BaseFragmentActivity;
import leaflet.miaoa.qmsh.leaflet.ui.merchantHomePage.home.SellerHomeFragment;
import leaflet.miaoa.qmsh.leaflet.ui.merchantHomePage.news.SellerNewsFragment;


/**
 * 商家主页面
 */
public class SellerHomePageActivity extends BaseFragmentActivity implements View.OnClickListener{

    public static final String FRAGMENT_SELLER_NEWS = "SellerNewsFragment";
    public static final String FRAGMENT_SELLER_HOME = "SellerHomeFragment";
    public static final String FRAGMENT_SELLER_MY = "SellerMyFragment";
    private RadioGroup rgMain;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private FragmentTransaction currentTransaction = null;
    private Fragment currentFragment;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_seller_home_page);
    }

    @Override
    protected void setFindViewById() {
        rgMain = (RadioGroup) findViewById(R.id.rg_seller_main);
    }

    @Override
    protected void setListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                Fragment fragment = null;
                currentTransaction = manager.beginTransaction();
                boolean isNew = false;
                switch (checkedId){
                    case R.id.rb_seller_mian_message:
                        fragment = manager.findFragmentByTag(FRAGMENT_SELLER_NEWS);
                        if (fragment == null){
                            fragment = new SellerNewsFragment();
                            currentTransaction.detach(currentFragment);
                            currentTransaction.add(R.id.fl_seller_main,fragment,FRAGMENT_SELLER_NEWS);
                            isNew = true;
                        }
                        if (currentFragment != fragment){
                            fragment.setMenuVisibility(true);
                            fragment.setUserVisibleHint(true);
                            currentFragment.setMenuVisibility(false);
                            currentFragment.setUserVisibleHint(false);
                            if (!isNew){
                                currentTransaction.detach(currentFragment);
                                currentTransaction.attach(fragment);
                            }
                        }
                        currentFragment = fragment;
                        currentTransaction.commitAllowingStateLoss();
                        break;
                    case R.id.rb_seller_mian_home:
                        fragment = manager.findFragmentByTag(FRAGMENT_SELLER_HOME);
                        if (fragment == null){
                            fragment = new SellerHomeFragment();
                            currentTransaction.detach(currentFragment);
                            currentTransaction.add(R.id.fl_seller_main,fragment,FRAGMENT_SELLER_HOME);
                            isNew = true;
                        }
                        if (currentFragment != fragment){
                            fragment.setMenuVisibility(true);
                            fragment.setUserVisibleHint(true);
                            currentFragment.setMenuVisibility(false);
                            currentFragment.setUserVisibleHint(false);
                            if (!isNew){
                                currentTransaction.detach(currentFragment);
                                currentTransaction.attach(fragment);
                            }
                        }
                        currentFragment = fragment;
                        currentTransaction.commitAllowingStateLoss();
                        break;
                    case R.id.rb_seller_main_my:
//                        fragment = manager.findFragmentByTag(FRAGMENT_SELLER_MY);
//                        if (fragment == null){
//                            fragment = new SellerMyFragment();
//                            currentTransaction.detach(currentFragment);
//                            currentTransaction.add(R.id.fl_seller_main,fragment,FRAGMENT_SELLER_MY);
//                            isNew = true;
//                        }
//                        if (currentFragment != fragment){
//                            fragment.setMenuVisibility(true);
//                            fragment.setUserVisibleHint(true);
//                            currentFragment.setMenuVisibility(false);
//                            currentFragment.setUserVisibleHint(false);
//                            if (!isNew){
//                                currentTransaction.detach(currentFragment);
//                                currentTransaction.attach(fragment);
//                            }
//                        }
//                        currentFragment = fragment;
//                        currentTransaction.commitAllowingStateLoss();
                        break;
                }
            }
        });
    }

    @Override
    protected void setControl() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        Fragment fragment = new SellerHomeFragment();
        transaction.add(R.id.fl_seller_main, fragment,FRAGMENT_SELLER_HOME);
        currentFragment = fragment;
        transaction.commitAllowingStateLoss();

//        Date date = new Date("1515081600000");
//        System.out.println(date);
    }

    @Override
    public void onClick(View view) {

    }
}
