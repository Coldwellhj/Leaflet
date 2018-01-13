package leaflet.miaoa.qmsh.leaflet.ui.personaluser.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.kevin.loopview.AdLoopView;

import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.Indicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.base.BaseFragment;
import leaflet.miaoa.qmsh.leaflet.bean.HomePageAdvDataBean;
import leaflet.miaoa.qmsh.leaflet.bean.ListActivityBean;
import leaflet.miaoa.qmsh.leaflet.ui.adapter.HomeAdvIndicatorAdapter;
import leaflet.miaoa.qmsh.leaflet.ui.adapter.HomeProductAdapter;
import leaflet.miaoa.qmsh.leaflet.ui.adapter.HomeShopAdapter;
import leaflet.miaoa.qmsh.leaflet.ui.widget.MaxRecyclerView;

import static leaflet.miaoa.qmsh.leaflet.Login.WelcomeActivity.Usertel;
import static leaflet.miaoa.qmsh.leaflet.bean.Https.coinIndex;
import static leaflet.miaoa.qmsh.leaflet.bean.Https.slideShow;
import static leaflet.miaoa.qmsh.leaflet.bean.Https.storeIndex;
import static leaflet.miaoa.qmsh.leaflet.ui.personaluser.PersonalUserHomePageActivity.start;


/**
 * 个人用户主页 页面
 */
public class PersonalHomeFragment extends BaseFragment implements View.OnClickListener{

    private View view;

//    UltimateRecyclerView mUltimateRecyclerView;
//    WrapRecyclerView mWrapRecyclerView;
    LinearLayout ll_near_shop,ll_near_coupon,ll_online_shop,ll_fans_exclusive;
    HomeProductAdapter homeProductAdapter;
    HomeShopAdapter homeShopAdapter;

//    TmallFooterLayout secondFooterLayout;
    PullToRefreshScrollView pullToRefreshScrollView;
    MaxRecyclerView recyclerView,recyclerView_youhuiquan,recyclerView_shop;

    // 顶部广告轮转大图
    AdLoopView mAdLoopView;
    Context mContext;
    private View bannnerAdv;
    private List<HomePageAdvDataBean> getAdvList = new ArrayList<HomePageAdvDataBean>();
    private List<ListActivityBean.Head_adv> head_advList = new ArrayList<ListActivityBean.Head_adv>();
    private List<ListActivityBean.Product> productList = new ArrayList<ListActivityBean.Product>();
    private List<ListActivityBean.Shop> shopList = new ArrayList<ListActivityBean.Shop>();
    private BannerComponent bannerComponent;
    private HomeAdvIndicatorAdapter homeadvindicatorAdapter;

    int page = 1;

    Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initAdvList();

                    homeadvindicatorAdapter.notifyDataSetChanged();
                break;
//                case 2:
//                    hAdapter = new HomeAdapter(mContext,productList);
//                    mWrapRecyclerView.setAdapter(hAdapter);
//                    hAdapter.notifyDataSetChanged();
//
//                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_home,null);
        mContext = this.getContext();
        httpAvd();
        return view;
    }
    /**
     * 初始化View
     */
    @Override
    protected void setFindViewById(View view) {
        pullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.main_scrollView);
        recyclerView = (MaxRecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView_youhuiquan = (MaxRecyclerView) view.findViewById(R.id.recyclerView_youhuiquan);
        recyclerView_shop = (MaxRecyclerView) view.findViewById(R.id.recyclerView_shop);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.banner_viewPager);
        Indicator indicator = (Indicator) view.findViewById(R.id.guide_indicator);
        ll_near_shop = (LinearLayout)  view.findViewById(R.id.near_shop);
        ll_near_coupon = (LinearLayout) view.findViewById(R.id.near_coupon);
        ll_online_shop = (LinearLayout) view.findViewById(R.id.online_shop);
        ll_fans_exclusive = (LinearLayout) view.findViewById(R.id.fans_exclusive);
        bannerComponent = new BannerComponent(indicator, viewPager, true);
        homeadvindicatorAdapter = new HomeAdvIndicatorAdapter(getActivity(), getAdvList);
        bannerComponent.setAdapter(homeadvindicatorAdapter);
        bannerComponent.setAutoPlayTime(2500);
    }

    @Override
    protected void setListener() {
//            near_discount.setOnClickListener(this);
//            online_shop.setOnClickListener(this);
    }

    @Override
    protected void setControl() {



        httpCoinIndex();
        httpStoreIndex();
//商品
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setNestedScrollingEnabled(false);// 解决ScrollView嵌套RecyclerView导致滑动不流畅的问题
        homeProductAdapter = new HomeProductAdapter(getActivity(),productList);
        recyclerView.setAdapter(homeProductAdapter);
        homeProductAdapter.notifyDataSetChanged();
        homeProductAdapter.setOnItemClickListener(new HomeProductAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                Toast.makeText(getActivity(), "商品"+position, Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(getActivity(),PersonalHomegetGoodsByIdActivity.class);
                intent.putExtra("cId",productList.get(position).getcId());
                startActivity(intent);
            }
        });
//店铺
        recyclerView_shop.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView_shop.setNestedScrollingEnabled(false);
        homeShopAdapter = new HomeShopAdapter(getActivity(),shopList);
        recyclerView_shop.setAdapter(homeShopAdapter);
        homeShopAdapter.notifyDataSetChanged();
        homeShopAdapter.setOnItemClickListener(new HomeShopAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                Toast.makeText(getActivity(), "店铺"+position, Toast.LENGTH_SHORT).show();
            }
        });
//        int color = getResources().getColor(R.color.gray);
//        recyclerView.addItemDecoration(new GridItemDecoration(getActivity(), 8, color) {
//
//        });
//        List<OnlineGoods> list = new ArrayList<>();
//        list.add(new OnlineGoods("我是标题我是标题我是标题1","11.5","9.5","10000+"));
//        list.add(new OnlineGoods("我是标题我是标题我是标题2","11.5","9.5","10000+"));
//        list.add(new OnlineGoods("我是标题我是标题我是标题3","11.5","9.5","10000+"));
//        list.add(new OnlineGoods("我是标题我是标题我是标题4","11.5","9.5","10000+"));
//        list.add(new OnlineGoods("我是标题我是标题我是标题5","11.5","9.5","10000+"));
//        list.add(new OnlineGoods("我是标题我是标题我是标题6","11.5","9.5","10000+"));
//        gvAdapter = new PersonalHomeGVAdapter(getActivity(),list);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new OnlineGoodsDecoration(2,Tools.dip2px(getContext(),9),true));
//        recyclerView.setAdapter(gvAdapter);
//        go_back_top.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                srcoll_view.scrollTo(0,0);
//            }
//        });





    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.near_discount:
//                Intent intent = new Intent(getContext(), NearDiscountActivity.class);
//                getActivity().startActivity(intent);
//                break;
//            case R.id.online_shop:
//                Intent intent2 = new Intent(getContext(), OnlineShopActivity.class);
//                getActivity().startActivity(intent2);
//                break;
        }
    }

    private void initAdvList() {
        for(int i=0;i<head_advList.size();i++){
            getAdvList.add(new HomePageAdvDataBean("1", "1", "1", "1", "1", "1", "1",head_advList.get(i).getHpImgs() ,""+i, head_advList.get(i).getLianjie()));

        }
    }
    private void httpAvd(){
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        final StringRequest stringRequest = new StringRequest(
                slideShow  ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray =new JSONArray(response);
                            ListActivityBean listActivityBean=new ListActivityBean();
                  //          int length = jsonArray.length();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String hpImgs=jsonObject.getString("hpImgs");
                                String lianjie=jsonObject.getString("lianjie");
                                ListActivityBean.Head_adv head_adv=listActivityBean.new Head_adv();
                                head_adv.setHpImgs(hpImgs);
                                head_adv.setLianjie(lianjie);
                                head_advList.add(i,head_adv);
                            }
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
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
    private void httpCoinIndex(){
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        final StringRequest stringRequest = new StringRequest(
                coinIndex+"?uNum="+Usertel  ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray =new JSONArray(response);
                            ListActivityBean listActivityBean=new ListActivityBean();
                            //          int length = jsonArray.length();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String cId=jsonObject.getString("cId");
                                String cName=jsonObject.getString("cName");
                                String cIntro=jsonObject.getString("cIntro");
                                String cNowPrice=jsonObject.getString("cNowPrice");
                                String cFormerPrice=jsonObject.getString("cFormerPrice");
                                String cCover=jsonObject.getString("cCover");
                                String cSales=jsonObject.getString("cSales");
                                ListActivityBean.Product product=listActivityBean.new Product();
                                product.setcId(cId);
                                product.setcName(cName);
                                product.setcCover(cCover);
                                product.setcFormerPrice(cFormerPrice);
                                product.setcIntro(cIntro);
                                product.setcNowPrice(cNowPrice);
                                product.setcSales(cSales);
                                productList.add(i,product);
                                homeProductAdapter.notifyDataSetChanged();
                            }
//                            Message message = new Message();
//                            message.what = 2;
//                            handler.sendMessage(message);
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
    private void httpStoreIndex(){
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        final StringRequest stringRequest = new StringRequest(
                storeIndex+"?start="+start  ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray =new JSONArray(response);
                            ListActivityBean listActivityBean=new ListActivityBean();
                            //          int length = jsonArray.length();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String sName=jsonObject.getString("sName");
                                Long beginTime=jsonObject.getLong("beginTime");
                                Long endTime=jsonObject.getLong("endTime");
                                String distance=jsonObject.getString("distance");
                                String sLogo=jsonObject.getString("sLogo");
                                String sAddress=jsonObject.getString("sAddress");
                                ListActivityBean.Shop shop=listActivityBean.new Shop();
                                shop.setsLogo(sLogo);
                                shop.setsName(sName);
                                shop.setBeginTime(beginTime);
                                shop.setEndTime(endTime);
                                shop.setDistance(distance);
                                shop.setsAddress(sAddress);
                                shopList.add(i,shop);
                                homeShopAdapter.notifyDataSetChanged();
                            }
//                            Message message = new Message();
//                            message.what = 2;
//                            handler.sendMessage(message);
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
}
