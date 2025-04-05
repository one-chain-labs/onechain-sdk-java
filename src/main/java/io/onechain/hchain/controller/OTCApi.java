package io.onechain.hchain.controller;


import io.onechain.hchain.domain.req.common.BaseReq;
import io.onechain.hchain.domain.req.otc.*;
import io.onechain.hchain.domain.resp.common.CommonResp;
import io.onechain.hchain.domain.resp.common.PageResult;
import io.onechain.hchain.domain.resp.otc.*;
import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-06 14:20:35
 */

public interface OTCApi {

    /**
     * /purchase/ads/list
     * 购买广告查询
     */
    CompletableFuture<CommonResp<List<OtcOrderAdvertiseQueryResp>>> purchaseAdsList(OtcOrderAdvertiseQueryReq req);

    /**
     * 购买订单创建
     * /purchase/order/create
     */
    CompletableFuture<CommonResp<OtcOrderCreateResp>> purchaseOrderCreate(OtcOrderCreateReq req);

    /**
     * 购买订单支付
     * /purchase/order/pay
     */
    CompletableFuture<CommonResp<OtcOrderPayResp>> purchaseOrderPay(OtcOrderPayBuyReq req);

    /**
     * 售卖广告查询
     * /sale/ads/list
     */
    CompletableFuture<CommonResp<List<OtcOrderAdvertiseQueryResp>>> saleAdsList(OtcOrderAdvertiseQueryReq req);

    /**
     * 创建订单
     * /sale/order/create
     */
    CompletableFuture<CommonResp<OtcOrderCreateResp>> saleOrderCreate(OtcOrderCreateReq req);

    /**
     * 支付
     * /sale/order/pay
     */
    CompletableFuture<CommonResp<OtcOrderPayResp>> saleOrderPay(OtcOrderPaySellReq req);

    /**
     * 账单查询
     * /bills/list
     */
    CompletableFuture<CommonResp<PageResult<OtcOrderDetailResp>>> billsList(OtcBillPageListUserReq req);

    /**
     * 用户余额查询
     * /users/balance
     */
    CompletableFuture<CommonResp<BigDecimal>> usersBalance(OtcUserBalanceReq req);

    /**
     * 查询订单
     * /order/query
     */
    CompletableFuture<CommonResp<PageResult<OtcOrderDetailResp>>> orderQuery(OtcOrderPageListUserReq req);

    /**
     * 查询订单详情
     * /order/detail
     */
    CompletableFuture<CommonResp<OtcOrderDetailResp>> orderDetail(OtcOrderDetailReq req);

    /**
     * 计算订单金额
     * /order/total
     */
    CompletableFuture<CommonResp<CalculateAmountResp>> calculateAmount(OtcCalculateAmountReq req);

    /**
     * 查询收款方式
     * /payment/method/query
     */
    CompletableFuture<CommonResp<ReceiveMethodDetailResp>> paymentMethodQuery(ReceiveMethodDetailReq req);

    /**
     * 编辑收款方式
     * /payment/method/edit
     */
    CompletableFuture<CommonResp<Boolean>> paymentMethodEdit(ReceiveMethodEditReq req);

    /**
     * 删除收款方式
     * /payment/method/del
     */
    CompletableFuture<CommonResp<Boolean>> paymentMethodDel(ReceiveMethodDelReq req);

    /**
     * 上传base64
     * /payment/method/upload/base64
     */
    CompletableFuture<CommonResp<String>> paymentMethodUploadBase64(UploadFileReq req);

    /**
     * 上传文件
     * /payment/method/upload/file
     */
    CompletableFuture<CommonResp<String>> uploadFile(BaseReq request, File file);
}
