package leaflet.miaoa.qmsh.leaflet.ui.merchantHomePage.news;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import leaflet.miaoa.qmsh.leaflet.R;
import leaflet.miaoa.qmsh.leaflet.base.BaseActivity;
import leaflet.miaoa.qmsh.leaflet.bean.ListActivityBean;
import leaflet.miaoa.qmsh.leaflet.ui.merchantHomePage.SellerHomePageActivity;


/**
 * 消息详情页
 */
public class SellerNewsDetailsActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iBack,ivJumpToHome;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_seller_news_details);
    }

    @Override
    protected void setFindViewById() {
        iBack = (ImageView) findViewById(R.id.iv_seller_news_details_back);
        ivJumpToHome = (ImageView) findViewById(R.id.iv_seller_news_details_home);
    }

    @Override
    protected void setListener() {
        iBack.setOnClickListener(this);
        ivJumpToHome.setOnClickListener(this);
    }

    @Override
    protected void setControl() {

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.iv_seller_news_details_back:
                finish();
                break;
            case R.id.iv_seller_news_details_home:
                intent.setClass(this, SellerHomePageActivity.class);
                startActivity(intent);
                break;
        }
    }

}
