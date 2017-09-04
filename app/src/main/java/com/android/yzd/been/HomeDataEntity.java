package com.android.yzd.been;

import java.util.List;

/**
 * Created by Administrator on 2016/12/15 0015.
 */

public class HomeDataEntity {


    /**
     * not_read : 0
     * advert_list : [{"ad_pic":"http://yzd.txunda.com/Uploads/Advert/2016-12-05/58457716a1ea8.png"},{"ad_pic":"http://yzd.txunda.com/Uploads/Advert/2016-12-05/58457667ee2cb.png"}]
     * good_goods_list : [{"goods_id":"10","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png"},{"goods_id":"9","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png"},{"goods_id":"8","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png"},{"goods_id":"7","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png"},{"goods_id":"6","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png"}]
     * recommend_goods_list : [{"goods_id":"10","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png","goods_name":"美涂士强力乳胶漆 墙","goods_price":"10.00","sales":"0"},{"goods_id":"9","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png","goods_name":"美涂士强力乳胶漆 墙","goods_price":"10.00","sales":"0"},{"goods_id":"8","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png","goods_name":"美涂士强力乳胶漆 墙","goods_price":"10.00","sales":"0"},{"goods_id":"7","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png","goods_name":"美涂士强力乳胶漆 墙","goods_price":"10.00","sales":"0"},{"goods_id":"6","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png","goods_name":"美涂士强力乳胶漆 墙","goods_price":"10.00","sales":"0"},{"goods_id":"5","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png","goods_name":"美涂士强力乳胶漆 墙","goods_price":"10.00","sales":"0"}]
     */

    private String not_read;
    private List<AdvertListBean> advert_list;
    private List<GoodGoodsListBean> good_goods_list;
    private List<RecommendGoodsListBean> recommend_goods_list;

    public String getNot_read() {
        return not_read;
    }

    public void setNot_read(String not_read) {
        this.not_read = not_read;
    }

    public List<AdvertListBean> getAdvert_list() {
        return advert_list;
    }

    public void setAdvert_list(List<AdvertListBean> advert_list) {
        this.advert_list = advert_list;
    }

    public List<GoodGoodsListBean> getGood_goods_list() {
        return good_goods_list;
    }

    public void setGood_goods_list(List<GoodGoodsListBean> good_goods_list) {
        this.good_goods_list = good_goods_list;
    }

    public List<RecommendGoodsListBean> getRecommend_goods_list() {
        return recommend_goods_list;
    }

    public void setRecommend_goods_list(List<RecommendGoodsListBean> recommend_goods_list) {
        this.recommend_goods_list = recommend_goods_list;
    }


}
