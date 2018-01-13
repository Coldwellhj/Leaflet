package leaflet.miaoa.qmsh.leaflet.ui.merchantHomePage.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.base.BaseFragment;
import leaflet.miaoa.qmsh.leaflet.bean.ListActivityBean;

import leaflet.miaoa.qmsh.leaflet.utils.Common;
import leaflet.miaoa.qmsh.leaflet.utils.DateUtils;
import leaflet.miaoa.qmsh.leaflet.utils.MPChartHelper;

import static leaflet.miaoa.qmsh.leaflet.bean.Https.slideShow;
import static leaflet.miaoa.qmsh.leaflet.bean.Https.vermicelli_chart;


/**
 * 粉丝增长折线统计图显示
 */
public class FansFragment extends BaseFragment {

    //    private WebView webView;
    private LineChart lineChart;
    private List<String> xAxisValues;
    private Map<Integer,List<Float>> yAxisValues2;

    public  String tel="";
    private List<String> shopId = new ArrayList<String>();
    private List<String> shopName = new ArrayList<String>();
    private List<ListActivityBean.Fans_increate> fans_increatesList = new ArrayList<ListActivityBean.Fans_increate>();
    private List<ListActivityBean.Fans_increate> fans_increatesList_remove = new ArrayList<ListActivityBean.Fans_increate>();//去重后的集合
    private List<List<Float>> yAxisValues;
    public  List<Integer> LineColorList=new ArrayList<Integer>();
    Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    fans_increatesList_remove=removeDuplicate(fans_increatesList);
                    for(int i=0;i<fans_increatesList_remove.size();i++){
                        shopId.add(fans_increatesList.get(i).getId());
                        shopName.add(fans_increatesList.get(i).getStoreName());
                    }

                    initData();
                    MPChartHelper.setLinesChart(lineChart,xAxisValues,yAxisValues,shopName,false,LineColorList);
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like_counts, null);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        tel =sharedPreferences.getString("tel", "");
        return view;
    }


    @Override
    protected void setFindViewById(View view) {
//        webView = (WebView) view.findViewById(R.id.wv_seller_home_webview);
        lineChart=(LineChart)view.findViewById(R.id.lineChart);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setControl() {
        httpFansIncreate();
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(false);
//        webView.setHorizontalScrollBarEnabled(false);//水平不显示  
//        webView.setVerticalScrollBarEnabled(false);//垂直不显示
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.loadUrl("file:///android_asset/index.html");
////        webView.loadUrl("http://www.baidu.com");
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });




    }
    private void httpFansIncreate(){
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        final StringRequest stringRequest = new StringRequest(
                vermicelli_chart+"?uNum="+ tel ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray =new JSONArray(response);
                            ListActivityBean listActivityBean=new ListActivityBean();
                            //          int length = jsonArray.length();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id=jsonObject.getString("increaseId");
                                String fanCount=jsonObject.getString("fanCount");
                                Long increaseTime=jsonObject.getLong("increaseTime");

                                String storeName=jsonObject.getString("storeName");
                                ListActivityBean.Fans_increate fans_increate=listActivityBean.new Fans_increate();
                                fans_increate.setId(id);
                                fans_increate.setFanCount(fanCount);
                                fans_increate.setIncreaseTime(increaseTime);
                                fans_increate.setStoreName(storeName);
                                fans_increatesList.add(i,fans_increate);

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

    private void initData(){
        xAxisValues = new ArrayList<>();
//        List<Float> yAxisValues1 = new ArrayList<>();
//        List<Float> yAxisValues2 = new ArrayList<>();
//        List<Float> yAxisValues3 = new ArrayList<>();
//        List<Float> yAxisValues4 = new ArrayList<>();
//        List<Float> yAxisValues5 = new ArrayList<>();
//        List<Float> yAxisValues6 = new ArrayList<>();
//        List<Float> yAxisValues7 = new ArrayList<>();




        yAxisValues = new ArrayList<>();

        for(int n=0;n<fans_increatesList_remove.size();n++){
            List<Float> yAxisValues1 = new ArrayList<>();
            int x= new Random().nextInt(255)%(255+1);
            int y= new Random().nextInt(255)%(255+1);
            int z= new Random().nextInt(255)%(255+1);

            LineColorList.add( n,Color.rgb(x,y,z));
            for(int i=0;i<7;i++){

                    xAxisValues.add(DateUtils.getStringFromDate(getDay(i - 6)));


                    for(int m=0;m<fans_increatesList.size();m++){


                        if(shopId.get(n).equals(fans_increatesList.get(m).getId())){
                            //时间和服务器的时间比较
                        if(DateUtils.getStringFromDate(getDay(i-6)).equals(DateUtils.getStringFromDate(DateUtils.getTimeFromLong(fans_increatesList.get(m).getIncreaseTime())))){


                            yAxisValues1.add(Float.parseFloat(fans_increatesList.get(m).getFanCount()));


                        }else {
                            yAxisValues1.add(0f);
                        }
                        }
                    }

            }
            yAxisValues.add(yAxisValues1);

        }











    }
    //获取时间
    public static Date getDay(int i) {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, i);
        Date date = calendar.getTime();
        return date;
    }

    //集合去重
    public   static   List<ListActivityBean.Fans_increate>  removeDuplicate(List<ListActivityBean.Fans_increate> list)   {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )   {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )   {
                if  (list.get(j).getId().equals(list.get(i).getId()))   {
                    list.remove(j);
                }
            }
        }
       return list;
    }

}
