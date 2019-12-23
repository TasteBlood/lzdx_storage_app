package com.cloudcreativity.storage.utils;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    String TEST_HOST = "http://192.168.31.196:8087/";
//    String TEST_HOST = "http://store-service-landa.lz-cc.com/";
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
    @POST("/order/selectAppOrder")
    @FormUrlEncoded
    Observable<String> getBoughtOrders(@Field("page") int page,
                                         @Field("size") int size,
                                         @Field("state") int state,
                                         @Field("mainState") int mainState,
                                         @Field("surveyState") int surveyState,
                                         @Field("checkState") int checkState,
                                         @Field("storeId") int storeId);

    //查询采购单商品
    @POST("/needgoods/searchGoods")
    @FormUrlEncoded
    Observable<String> getBuyGoods(@Field("orderId") int orderId,
                                   @Field("page") int page,
                                   @Field("size") int size);

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
                                      @Field("number") double number,
                                      @Field("position") String position,
                                      @Field("address") String address,
                                      @Field("newPrice") Integer newPrice,
                                      @Field("wayState") int wayState,
                                      @Field("productDate") String productDate,
                                      @Field("period") int  period);

    //查询全部的供应商
    @POST("/provider/getProviderList")
    @FormUrlEncoded
    Observable<String> getProviders(@Field("page") int page,
                                    @Field("size") int size,
                                    @Field("shopName") String shopName);

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


    // 库管登录 可能会返回多个账户 因为库管对应多仓库
    @POST("/surveyAccount/accountLogin")
    @FormUrlEncoded
    Observable<String> adminLogin(@Field("accountName") String account,
                                  @Field("accountPwd") String pwd,
                                  @Field("state") int state);

    //其他角色登录
    @POST("/surveyAccount/accountLogin")
    @FormUrlEncoded
    Observable<String> login(@Field("accountName") String account,
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
                                     @Field("goodsName") String key,
                                     @Field("categoryOneId")Integer categoryOneId,
                                     @Field("categoryTwoId")Integer categoryTwoId);

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
    /**
     * @author : xuxiwu
     * created: 2019/8/2 16:22
     * desc: state  =  1 待审批  state = 2 待出库 state = 3 已出库
     */
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
                                     @Field("price") int price,
                                     @Field("uses") String uses);

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
                                   @Field("pageNum") int page,
                                   @Field("pageSize") int size,
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

    /**
    * @author : xuxiwu
    * created : 2019/8/2 16:22
    * desc : 出库申请
    */
    @POST("/app/storeApply/addApply")
    @FormUrlEncoded
    Observable<String> applyOut(@Field("accountId") int accountId,
                                @Field("storeId") int storeId,
                                @Field("remark") String remark,
                                @Field("institutionId") int institutionId,
                                @Field("applyGoodsDomain") String goodsDomains);

    /**
    * @author : xuxiwu
    * created: 2019/8/2 16:22
    * desc: 出库审批
    */
    @POST("/app/storeApply/editApply")
    @FormUrlEncoded
    Observable<String> checkOut(@Field("id") int orderId,
                                @Field("state") int state,
                                @Field("remark") String remark,
                                @Field("applyAccountId") int applyAccountId);

    /**
    * @author : xuxiwu
    * created: 2019/8/2 16:22
    * desc:上传文件
    */
    @POST("/contract/upload")
    @Multipart
    Observable<String> uploadFile(@Part MultipartBody.Part file);

    /**
    * @author : xuxiwu
    * created: 2019/8/2 16:22
    * desc: 采购单上传合同
    */
    @POST("/app/enter/updateOrderFile")
    @FormUrlEncoded
    Observable<String> uploadContract(@Field("orderId") int orderId,
                                      @Field("fileUrl") String fileUrl);

    /**
    * @author : xuxiwu
    * created: 2019/8/2 16:22
    * desc:查询审批单
    */
    @POST("/app/funds/queryApproverAttach")
    @FormUrlEncoded
    // flag ==1 未通过  0 通过
    Observable<String> getApproveList(@Field("accountId") int accountId,
                                      @Field("flag") int flag,
                                      @Field("page") int page,
                                      @Field("size") int size);

    /**
    * @author : xuxiwu
    * created: 2019/8/2 16:22
    * desc:查询报销单详情
    */
    @POST("/app/funds/getOrderInfoById")
    @FormUrlEncoded
    Observable<String> getApproveDetail(@Field("orderAttachId") int orderAttachId);

    @POST("/app/funds/personInChargeTwoHandle")
    @FormUrlEncoded
    Observable<String> handState2(@Field("state") int state,
                                  @Field("orderAttachId") int orderAttachId,
                                  @Field("reason") String reason);

    @POST("/app/funds/personInChargeThreeHandle")
    @FormUrlEncoded
    Observable<String> handState3(@Field("state") int state,
                                  @Field("orderAttachId") int orderAttachId,
                                  @Field("reason") String reason);

    @POST("/app/funds/personInChargeFourHandle")
    @FormUrlEncoded
    Observable<String> handState4(@Field("state") int state,
                                  @Field("orderAttachId") int orderAttachId,
                                  @Field("reason") String reason);

    @POST("/app/funds/personInChargeFiveHandle")
    @FormUrlEncoded
    Observable<String> handState5(@Field("state") int state,
                                  @Field("orderAttachId") int orderAttachId,
                                  @Field("reason") String reason);

}
