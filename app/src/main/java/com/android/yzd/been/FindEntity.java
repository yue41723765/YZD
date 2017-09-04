package com.android.yzd.been;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class FindEntity {


    /**
     * not_read : 0
     * goods_list : [{"goods_id":"10","goods_name":"美涂士强力乳胶漆 墙","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png"},{"goods_id":"9","goods_name":"美涂士强力乳胶漆 墙","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png"},{"goods_id":"8","goods_name":"美涂士强力乳胶漆 墙","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png"}]
     * integral_list : [{"i_g_id":"3","goods_name":"简约时尚衣架","goods_logo":"http://yzd.txunda.com/Uploads/IntegralGoods/2016-12-07/5847f83941e3c.png","goods_brief":"使用多种空间，无需打孔自由伸缩，可360度旋转，加粗碳钢30KG承重 衣服宝宝五件随心挂","need_integral":"10"},{"i_g_id":"4","goods_name":"简约时尚衣架","goods_logo":"http://yzd.txunda.com/Uploads/IntegralGoods/2016-12-07/5847f83941e3c.png","goods_brief":"使用多种空间，无需打孔自由伸缩，可360度旋转，加粗碳钢30KG承重 衣服宝宝五件随心挂","need_integral":"10"},{"i_g_id":"5","goods_name":"简约时尚衣架","goods_logo":"http://yzd.txunda.com/Uploads/IntegralGoods/2016-12-07/5847f83941e3c.png","goods_brief":"使用多种空间，无需打孔自由伸缩，可360度旋转，加粗碳钢30KG承重 衣服宝宝五件随心挂","need_integral":"10"}]
     */

    private String not_read;
    private List<GoodsListBean> goods_list;
    private List<IntegralListBean> integral_list;

    public String getNot_read() {
        return not_read;
    }

    public void setNot_read(String not_read) {
        this.not_read = not_read;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public List<IntegralListBean> getIntegral_list() {
        return integral_list;
    }

    public void setIntegral_list(List<IntegralListBean> integral_list) {
        this.integral_list = integral_list;
    }
}
