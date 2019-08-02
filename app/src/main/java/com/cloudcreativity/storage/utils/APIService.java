package com.cloudcreativity.storage.utils;

import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.entity.BuyGoods;
import com.cloudcreativity.storage.entity.BuyOrder;
import com.cloudcreativity.storage.entity.Category;
import com.cloudcreativity.storage.entity.PriceGoods;
import com.cloudcreativity.storage.entity.Provider;

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
    String TEST_HOST = "http://192.168.31.160:8080/";
    String ONLINE_HOST = "http://192.168.31.160:8080/";
    String HOST_APP = AppConfig.DEBUG ? TEST_HOST : ONLINE_HOST;

    //查询分类
    @GET("/category/categoryList")
    Observable<Category> getCategory();

    //查询采购列表
    @POST("/order/selectOrder")
    @FormUrlEncoded
    Observable<BuyOrder> getBuyOrders(@Field("page") int page,
                                      @Field("size") int size);

    //查询采购单商品
    @POST("/needgoods/searchGoods")
    @FormUrlEncoded
    Observable<BuyGoods> getBuyGoods(@Field("orderId") int orderId);

    //查询全部的供应商
    @POST("/provider/getProviderList")
    @FormUrlEncoded
    Observable<Provider> getProviders(@Field("page") int page,
                                      @Field("size") int size);

    //添加普通采购采价记录
    @POST("/surveyGoods/addSurvey")
    @FormUrlEncoded
    Observable<BaseResult> addBuyPriceRecord(@Field("needGoodsId") int gid,
                                             @Field("providerId") int providerId,
                                             @Field("price") int price,
                                             @Field("address") String address,
                                             @Field("orderId") int orderId);

    //获取采价列表
    @POST("/surveyGoods/selectAllSurvey")
    @FormUrlEncoded
    Observable<PriceGoods> getPriceList(@Field("needGoodsId") int gid);

    // 采价员登录
    @POST("/surveyAccount/accountLogin")
    @FormUrlEncoded
    Observable<BaseResult> priceLogin(@Field("accountName") String account,
                                      @Field("accountPwd") String pwd);

    // 餐饮采价员登录
    @POST("/surveyAccount/accountLogin")
    @FormUrlEncoded
    Observable<BaseResult> restaurantLogin(@Field("accountName") String account,
                                           @Field("accountPwd") String pwd);

    // 库管登录
    @POST("/surveyAccount/accountLogin")
    @FormUrlEncoded
    Observable<BaseResult> adminLogin(@Field("accountName") String account,
                                      @Field("accountPwd") String pwd);

    //添加供应商
    @POST("/provider/add")
    @FormUrlEncoded
    Observable<BaseResult> addProvider(@Field("shopName") String shopName,
                                       @Field("realName") String realName,
                                       @Field("address") String address,
                                       @Field("phone") String mobile);
}
