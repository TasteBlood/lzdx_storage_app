package com.cloudcreativity.storage.utils;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 整个程序的网络接口配置
 */
public interface APIService {
    /**
     * 网络请求的配置
     */
    long timeOut = 10;//网络超时
    /**
     * 整体的接口配置
     */
    String TEST_HOST = "http://store-service-landa.lz-cc.com/";
    String ONLINE_HOST = "http://store-service-landa.lz-cc.com/";
    String HOST_APP = AppConfig.DEBUG ? TEST_HOST : ONLINE_HOST;

    @GET("app/version/find")
    Observable<String> getLastVersion();

    //查询分类
    @GET("/category/categoryList")
    Observable<String> getCategory();

    //查询采购列表
    @POST("/order/selectOrder")
    @FormUrlEncoded
    Observable<String> getBuyOrders(@Field("page") int page,
                                      @Field("size") int size,
                                      @Field("surveyState") int surveyState,
                                      @Field("state") int state,
                                      @Field("storeId") int storeId);

    //查询入库单
    @POST("/order/selectOrder")
    @FormUrlEncoded
    Observable<String> getBoughtOrders(@Field("page") int page,
                                         @Field("size") int size,
                                         @Field("state") int state,
                                         @Field("mainState") int mainState,
                                         @Field("surveyState") int surveyState,
                                         @Field("storeId") int storeId);

    //查询采购单商品
    @POST("/needgoods/searchGoods")
    @FormUrlEncoded
    Observable<String> getBuyGoods(@Field("orderId") int orderId);

    //查询待入库商品
    /**
    * @author : xuxiwu
    * created: 2019/8/2 16:22
    * desc:
     * @param orderId 订单Id
     * @param state = 2
    */
    @POST("/surveyGoods/selectAllSurvey")
    @FormUrlEncoded
    Observable<String> getWaitEnterGoods(@Field("orderId") int orderId,
                                             @Field("state") int state);

    /**
    * @author : xuxiwu
    * created: 2019/8/2 16:22
    * desc:添加入库
    */
    @POST("/app/enter/addRecord")
    @FormUrlEncoded
    Observable<String> enterStore(@Field("institutionId") int institutionId,
                                      @Field("orderId") int orderId,
                                      @Field("needGoodsId") int needGoodsId,
                                      @Field("goodsId") int goodsId,
                                      @Field("providerId") int providerId,
                                      @Field("storeId") int storeId,
                                      @Field("specsId") int specsId,
                                      @Field("unitId") int unitId,
                                      @Field("price") int price,
                                      @Field("number") float number,
                                      @Field("position") String position,
                                      @Field("address") String address,
                                      @Field("newPrice") Integer newPrice,
                                      @Field("wayState") int wayState);

    //查询全部的供应商
    @POST("/provider/getProviderList")
    @FormUrlEncoded
    Observable<String> getProviders(@Field("page") int page,
                                      @Field("size") int size);

    //添加普通采购采价记录
    @POST("/app/surveyGoods/addSurvey")
    @FormUrlEncoded
    Observable<String> addBuyPriceRecord(@Field("needGoodsId") int gid,
                                             @Field("providerId") int providerId,
                                             @Field("price") int price,
                                             @Field("address") String address,
                                             @Field("orderId") int orderId);

    //获取采价列表
    @POST("/surveyGoods/selectAllSurvey")
    @FormUrlEncoded
    Observable<String> getPriceList(@Field("needGoodsId") int gid);


    // 库管登录
    @POST("/surveyAccount/accountLogin")
    @FormUrlEncoded
    Observable<String> adminLogin(@Field("accountName") String account,
                                  @Field("accountPwd") String pwd,
                                  @Field("state") int state);

    //添加供应商
    @POST("/app/provider/add")
    @FormUrlEncoded
    Observable<String> addProvider(@Field("shopName") String shopName,
                                       @Field("realName") String realName,
                                       @Field("address") String address,
                                       @Field("phone") String mobile);

    //获取仓库下的商品
    @POST("/admin/goodsInfo")
    @FormUrlEncoded
    Observable<String> getStoreGoods(@Field("page") int page,
                                         @Field("size") int size,
                                         @Field("storeId") int storeId,
                                         @Field("goodsName") String key);

    //入库记录
    @POST("/enter/recordList")
    @FormUrlEncoded
    Observable<String> getEnterRecords(@Field("goodsId") int goodsId,
                                            @Field("page") int page,
                                            @Field("size") int size);

    //出库记录
    @POST("/out/recordList")
    @FormUrlEncoded
    Observable<String> getOutRecords(@Field("goodsId") int goodsId,
                                        @Field("page") int page,
                                        @Field("size") int size);

    //查询出库单
    @POST("/storeApply/selectApplyAll")
    @FormUrlEncoded
    Observable<String> getOutList(@Field("state") int state,
                                    @Field("accountId") int accountId,
                                    @Field("storeId") int storeId);

    //查询出库单商品
    @POST("/applyGoods/selectGoodsAll")
    @FormUrlEncoded
    Observable<String> getOutListGoods(@Field("applyId") int applyId);

    //出库
    @POST("/app/out/add")
    @FormUrlEncoded
    Observable<String> outRecord(@Field("number") float number,
                                     @Field("person") String person,
                                     @Field("address") String address,
                                     @Field("specsId") int specsId,
                                     @Field("unitId") int unitId,
                                     @Field("goodsId") int goodsId,
                                     @Field("storeId") int storeId,
                                     @Field("accountId") int accountId,
                                     @Field("articleId") int articleId,
                                     @Field("applyId") int applyId,
                                     @Field("price") int price);

    //查询原始商品
    /**
    * @author : xuxiwu
    * created: 2019/8/2 16:22
    * desc:
     * @param state ==2 餐饮商品
    */
    @POST("/goodsCategory/selectGoodsInfo")
    @FormUrlEncoded
    Observable<String> getFullGoods(@Field("categoryOneId") int categoryOneId,
                                   @Field("categoryTwoId") int categoryTwoId,
                                   @Field("goodsName") String goodsName,
                                   @Field("page") int page,
                                   @Field("size") int size,
                                   @Field("state") int state);

    //餐饮采价
    @POST("/app/surveyInfo/addSurveyInfo")
    @FormUrlEncoded
    Observable<String> addRestaurantPrice(@Field("goodsId") int goodsId,
                                              @Field("specsId") int specsId,
                                              @Field("providerId") int providerId,
                                              @Field("price") int price,
                                              @Field("remarks") String remarks,
                                              @Field("address") String address);

    //查询餐饮采价商品
    @POST("/needgoods/searchAllGoods")
    @FormUrlEncoded
    Observable<String> getRestaurantGoods(@Field("orderId") int orderId);

    /**
    * @author : xuxiwu
    * created: 2019/8/2 16:22
     * *@param state 1 盘亏 2 盘盈
     * @param  articleId 物品记录id
     * @param goodsId 物品id
    * desc:
    */
    @POST("/app/inventory/addGoodsLoos")
    @FormUrlEncoded
    Observable<String> doJudge(@Field("accountId") int accountId,
                                   @Field("price") int price,
                                    @Field("articleId") int articleId,
                                    @Field("position") String position,
                                    @Field("lRemarks") String remarks,
                                    @Field("state") int state,
                                    @Field("unitId") int unitId,
                                    @Field("providerId") int providerId,
                                    @Field("specsId") int specsId,
                                    @Field("number") float number,
                                    @Field("goodsId") int goodsId,
                                    @Field("storeId") int storeId);

}
