package leaflet.miaoa.qmsh.leaflet.ui.merchantHomePage.news;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.Indicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.base.BaseFragment;
import leaflet.miaoa.qmsh.leaflet.bean.ListActivityBean;
import leaflet.miaoa.qmsh.leaflet.ui.adapter.HomeAdvIndicatorAdapter;
import leaflet.miaoa.qmsh.leaflet.ui.adapter.SellerNewsAdapter;
import leaflet.miaoa.qmsh.leaflet.ui.adapter.SwipeMenuAdapter;

import static leaflet.miaoa.qmsh.leaflet.Login.WelcomeActivity.Usertel;
import static leaflet.miaoa.qmsh.leaflet.bean.Https.sellerNews;


/**
 * 商家-消息 页面
 */
public class SellerNewsFragment extends BaseFragment implements View.OnClickListener{

    private PullToRefreshListView lv_main;
    private ListView listView;
    private SellerNewsAdapter sellerNewsAdapter;
    /**每一页展示多少条数据*/
    private static int REQUEST_COUNT = 10;
    /**已经获取到多少条数据了*/
    private static int mCurrentCounter = 0;
    View view;
    private static List<ListActivityBean.SellerNews> sellerNewsList = new ArrayList<ListActivityBean.SellerNews>();
    private LRecyclerView mRecyclerView = null;

    private SwipeMenuAdapter mDataAdapter = null;

    private PreviewHandler mHandler = new PreviewHandler(this);
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    private boolean isRefresh = false;

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_seller_news, null);

        return view;
    }

    @Override
    protected void setFindViewById(View view) {
        mRecyclerView = (LRecyclerView) view.findViewById(R.id.list);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setControl() {
        mDataAdapter = new SwipeMenuAdapter(getContext());
        initNewsData();
//        mDataAdapter.setDataList(dataList);
        mDataAdapter.setOnDelListener(new SwipeMenuAdapter.onSwipeListener() {
            @Override
            public void onDel(int pos) {
//                Toast.makeText(SwipeDeleteActivity.this, "删除:" + pos, Toast.LENGTH_SHORT).show();

                //RecyclerView关于notifyItemRemoved的那点小事 参考：http://blog.csdn.net/jdsjlzx/article/details/52131528
                mDataAdapter.getDataList().remove(pos);
                mDataAdapter.notifyItemRemoved(pos);//推荐用这个

                if(pos != (mDataAdapter.getDataList().size())){ // 如果移除的是最后一个，忽略 注意：这里的mDataAdapter.getDataList()不需要-1，因为上面已经-1了
                    mDataAdapter.notifyItemRangeChanged(pos, mDataAdapter.getDataList().size() - pos);
                }
                //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
            }

            @Override
            public void onTop(int pos) {//置顶功能有bug，后续解决
//                TLog.error("onTop pos = " + pos);
                ListActivityBean.SellerNews sellerNews = mDataAdapter.getDataList().get(pos);

                mDataAdapter.getDataList().remove(pos);
                mDataAdapter.notifyItemRemoved(pos);
                mDataAdapter.getDataList().add(0, sellerNews);
                mDataAdapter.notifyItemInserted(0);


                if(pos != (mDataAdapter.getDataList().size())){ // 如果移除的是最后一个，忽略
                    mDataAdapter.notifyItemRangeChanged(0, mDataAdapter.getDataList().size() - 1,"jdsjlzx");
                }

                mRecyclerView.scrollToPosition(0);

            }
        });
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

//        mLRecyclerViewAdapter.addHeaderView(new SampleHeader(this));

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
                mCurrentCounter = 0;
                isRefresh = true;
                initNewsData();
            }
        });

        mRecyclerView.refresh();
    }

    @Override
    public void onClick(View view) {

    }



    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }
    private void addItems(List<ListActivityBean.SellerNews> list) {

        mDataAdapter.addAll(list);
        mCurrentCounter += list.size();

    }
    //网络请求
    private void initNewsData(){
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(
                sellerNews+"?uNum="+ Usertel,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                              JSONArray jsonArray =new JSONArray(response);
                            ListActivityBean listActivityBean=new ListActivityBean();
                            //          int length = jsonArray.length();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String mnHandleName=jsonObject.getString("mnHandleName");
                                String mnOperation=jsonObject.getString("mnOperation");
                                String mnKinds=jsonObject.getString("mnKinds");
                                Long mnTime=jsonObject.getLong("mnTime");
                                String mnStatus=jsonObject.getString("mnStatus");
                                ListActivityBean.SellerNews sellerNews=listActivityBean.new SellerNews();
                                sellerNews.setIsRead(mnStatus);
                                sellerNews.setNewsTheme(mnKinds);
                                sellerNews.setNewsContent(mnHandleName+","+mnOperation);
                                sellerNews.setNewsTime(mnTime);
                                sellerNewsList.add(i,sellerNews);
                                REQUEST_COUNT=sellerNewsList.size();

//                                mDataAdapter.notifyDataSetChanged();
                                mHandler.sendEmptyMessage(-1);
                            }


                        } catch (Exception e) {
                            Toast.makeText(getActivity(),"网络加载失败",Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"网络加载失败",Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(-3);
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(4000,// 默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(stringRequest);
    }
    private static class PreviewHandler extends Handler {

        private WeakReference<SellerNewsFragment> ref;

        PreviewHandler(SellerNewsFragment sellerNewsFragment) {
            ref = new WeakReference<>(sellerNewsFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            final SellerNewsFragment sellerNewsFragment = ref.get();
            if (sellerNewsFragment == null ) {
                return;
            }
            switch (msg.what) {

                case -1:
                    if(sellerNewsFragment.isRefresh){
                        sellerNewsFragment.mDataAdapter.clear();
                        mCurrentCounter = 0;
                    }

                    int currentSize = sellerNewsFragment.mDataAdapter.getItemCount();

//                    //模拟组装10个数据
//                    ArrayList<ItemModel> newList = new ArrayList<>();
//                    for (int i = 0; i < 10; i++) {
//                        if (newList.size() + currentSize >= TOTAL_COUNTER) {
//                            break;
//                        }
//
//                        ItemModel item = new ItemModel();
//                        item.id = currentSize + i;
//                        item.title = "item" + (item.id);
//
//                        newList.add(item);
//                    }


                    sellerNewsFragment.addItems(sellerNewsList);
                    sellerNewsFragment.mRecyclerView.refreshComplete(REQUEST_COUNT);
                    sellerNewsFragment.notifyDataSetChanged();
                    break;
                case -2:
                    sellerNewsFragment.notifyDataSetChanged();
                    break;
                case -3:
                    sellerNewsFragment.mRecyclerView.refreshComplete(REQUEST_COUNT);
                    sellerNewsFragment.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

}
