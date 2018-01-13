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
public class GoodRulesAdapter extends RecyclerView.Adapter<GoodRulesAdapter.ViewHolder> {
    private List<String> goodrulesList = new ArrayList<String>();
    Context mContext;

    public GoodRulesAdapter(Context mContext, List<String> goodrulesList) {
        this.mContext=mContext;
        this.goodrulesList = goodrulesList;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview_use_of_rules,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {


        viewHolder.rules.setText(goodrulesList.get(position));


        viewHolder.itemView.setTag(position);
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return goodrulesList.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView rules;

        public ViewHolder(View view){
            super(view);

            rules = (TextView) view.findViewById(R.id.tv_rules);
        }


    }

}
