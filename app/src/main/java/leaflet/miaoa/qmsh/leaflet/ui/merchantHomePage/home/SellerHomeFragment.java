package leaflet.miaoa.qmsh.leaflet.ui.merchantHomePage.home;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.base.BaseFragment;
import leaflet.miaoa.qmsh.leaflet.ui.adapter.ViewPagerAdapter;
import leaflet.miaoa.qmsh.leaflet.ui.merchantHomePage.home.redlable.MakingRedPacketsActivity;


/**
 * 主页
 */
public class SellerHomeFragment extends BaseFragment implements View.OnClickListener{

    private List<TextView> tvList = new ArrayList<>();
    private List<TextView> underlineList = new ArrayList<>();
    private List<Fragment> listFragment = new ArrayList<>();
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TextView tvFans;
    private LinearLayout llVideo,llCoupon,llFans,llDiscount,llScan,llShop;

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_home,null);
        return view;
    }

    @Override
    protected void setFindViewById(View view) {
        llVideo = (LinearLayout) view.findViewById(R.id.ll_seller_home_video);
        llCoupon = (LinearLayout) view.findViewById(R.id.ll_seller_home_coupon);
        llFans = (LinearLayout) view.findViewById(R.id.ll_seller_home_fans);
        llDiscount = (LinearLayout) view.findViewById(R.id.ll_seller_home_discount);
        llScan = (LinearLayout) view.findViewById(R.id.ll_seller_home_scan);
        llShop = (LinearLayout) view.findViewById(R.id.ll_seller_home_shop);
        tvFans = (TextView) view.findViewById(R.id.tv_seller_home_fans);
        viewPager = (ViewPager) view.findViewById(R.id.vp_seller_home_viewpager);
        viewPager.setOffscreenPageLimit(0);
    }

    @Override
    protected void setListener() {
        llVideo.setOnClickListener(this);
        llCoupon.setOnClickListener(this);
        llFans.setOnClickListener(this);
        llDiscount.setOnClickListener(this);
        llScan.setOnClickListener(this);
        llShop.setOnClickListener(this);
        tvFans.setOnClickListener(this);
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
        tvList.add(tvFans);
        clearAllStyle();


//        listFragment.add(new LikeCountsFragment());
        listFragment.add(new FansFragment());
        adapter = new ViewPagerAdapter(getChildFragmentManager(),listFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
//            case R.id.tv_seller_home_likecount:
//                clearAllStyle();
//                tvLikeCount.setTextColor(Color.parseColor("#e75356"));
//                underline1.setVisibility(View.VISIBLE);
//                viewPager.setCurrentItem(0);
//                break;
//            case R.id.tv_seller_home_fans:
//                clearAllStyle();
//                tvFans.setTextColor(Color.parseColor("#e75356"));
//                underline2.setVisibility(View.VISIBLE);
//                viewPager.setCurrentItem(1);
//                break;
//            //红包广告
            case R.id.ll_seller_home_video:
//                intent.setClass(getActivity(), MediaRecorderActivity.class);
//                startActivityForResult(intent, 7001);
                intent.setClass(getActivity(), MakingRedPacketsActivity.class);
                startActivity(intent);
                break;
//            //优惠券
//            case R.id.ll_seller_home_coupon:
//                intent.setClass(getActivity(), IssueCouponsActivity.class);
//                startActivity(intent);
//                break;
//            //粉丝圈
//            case R.id.ll_seller_home_fans:
//                intent.setClass(getActivity(), FandomActivity.class);
//                startActivity(intent);
//                break;
//            //在线商城
//            case R.id.ll_seller_home_discount:
//                intent.setClass(getActivity(), OnlineShopActivity.class);
//                startActivity(intent);
//                break;
//            //扫一扫
//            case R.id.ll_seller_home_scan:
////                intent.setClass(getActivity(), CaptureActivity.class);
////                startActivityForResult(intent,1);
//                break;
//            //我的店铺
//            case R.id.ll_seller_home_shop:
//                intent.setClass(getActivity(), MyShopActivity.class);
//                startActivity(intent);
//                break;
        }
    }


    private void clearAllStyle() {

        tvFans.setTextColor(Color.parseColor("#333333"));
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//        }


//    }
}
