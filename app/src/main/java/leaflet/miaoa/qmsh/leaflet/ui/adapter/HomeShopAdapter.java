package leaflet.miaoa.qmsh.leaflet.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.bean.ListActivityBean;

import static leaflet.miaoa.qmsh.leaflet.utils.DateUtils.getStr1FromDate;
import static leaflet.miaoa.qmsh.leaflet.utils.DateUtils.getTimeFromLong;


/**

 */
public class HomeShopAdapter extends RecyclerView.Adapter<HomeShopAdapter.ViewHolder> implements View.OnClickListener{
    private List<ListActivityBean.Shop> shopList = new ArrayList<ListActivityBean.Shop>();
    Context mContext;
    private  OnItemClickListener mOnItemClickListener = null;
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public HomeShopAdapter(Context mContext, List<ListActivityBean.Shop> shopList) {
        this.mContext=mContext;
        this.shopList = shopList;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_shop,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Glide.with(mContext)
                .load(shopList.get(position).getsLogo())
                .into(viewHolder.iv_home_shops);
        viewHolder.position = position;
        viewHolder.shop_name.setText(shopList.get(position).getsName());
        viewHolder.tv_distance.setText(shopList.get(position).getDistance()+"km");
        viewHolder.shop_time.setText("营业时间 "+getStr1FromDate(getTimeFromLong(shopList.get(position).getBeginTime()))+"--"+getStr1FromDate(getTimeFromLong(shopList.get(position).getEndTime())));
        viewHolder.shop_address.setText("地址："+shopList.get(position).getsAddress());

        viewHolder.itemView.setTag(position);
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return shopList.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_home_shops;
        public TextView shop_name;
        public TextView tv_distance;
        public TextView shop_time;
        public TextView shop_address;
        int position;
        public ViewHolder(View view){
            super(view);
            iv_home_shops = (ImageView) view.findViewById(R.id.iv_home_shops);
            shop_name = (TextView) view.findViewById(R.id.shop_name);
            tv_distance = (TextView) view.findViewById(R.id.tv_distance);
            shop_time = (TextView) view.findViewById(R.id.shop_time);
            shop_address = (TextView) view.findViewById(R.id.shop_address);
        }


    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
