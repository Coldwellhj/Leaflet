package leaflet.miaoa.qmsh.leaflet.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.squareup.picasso.Picasso;

import java.util.List;

import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.bean.HomePageAdvDataBean;


/**
 * Created by Administrator on 2016/10/26.
 */

public class HomeAdvIndicatorAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter{
    Context context ;
    List<HomePageAdvDataBean> advList;
    RequestQueue mRequestQueue;
    public HomeAdvIndicatorAdapter(Context context, List<HomePageAdvDataBean> advList) {
        this.context = context;
        this.advList = advList;
    }

    @Override
    public int getCount() {
        return  advList.size();
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container)
    {
        if(convertView==null){
            convertView= View.inflate(context, R.layout.tab_guide,null);
        }

        return convertView;
    }

    @Override
    public View getViewForPage(final int position, View convertView, ViewGroup container) {
        if(convertView==null){
            convertView=new ImageView(context);
            convertView.setLayoutParams(new ViewGroup.LayoutParams(200,200));
        }
        final ImageView imageView =(ImageView)convertView;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =new Intent(context, WB_pUrlActivity.class);
//                intent.putExtra("pUrl",advList.get(position%advList.size()).goods_id);
//                intent.putExtra("downloadUrl",advList.get(position%advList.size()).downloadUrl);
//                context.startActivity(intent);

            }
        });
        imageView.setScaleType(ImageView.ScaleType.FIT_XY  );
//        imageView.setImageResource(R.drawable.ceshi);
        //用Glide出现图片显示不全问题
//        Glide.with(context).load( advList.get(position%advList.size()).img_url).asBitmap().centerCrop().placeholder(R.drawable.goods_background).error(R.drawable.goods_background).into(imageView);
        Picasso.with(context).load( advList.get(position%advList.size()).img_url).into(imageView);
//        RequestQueue mQueue = Volley.newRequestQueue(context);
//        ImageRequest imageRequest = new ImageRequest(
//                advList.get(position%advList.size()).img_url,
//                new Response.Listener<Bitmap>() {
//                    @Override
//                    public void onResponse(Bitmap response) {
//                        imageView.setImageBitmap(response);
//                    }
//                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        mQueue.add(imageRequest);
        Log.i("advlist",advList+"");
        return convertView;
    }

}
