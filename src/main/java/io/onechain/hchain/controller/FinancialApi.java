package io.onechain.hchain.controller;


import io.onechain.hchain.domain.req.common.*;
import io.onechain.hchain.domain.req.financial.*;
import io.onechain.hchain.domain.resp.common.CommonResp;
import io.onechain.hchain.domain.resp.common.PageResult;

import io.onechain.hchain.domain.resp.financial.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-03 17:53:48
 */
public interface FinancialApi {

    /**
     * 用户持仓金额和已提取收益总金额
     */
    CompletableFuture<CommonResp<List<ApiSumBuyAndIncomeAmountResp>>> userBuyAndIncomeTotal(TypeBaseUserReq req);

    /**
     * 查询理财产品列表
     */
    CompletableFuture<CommonResp<List<ApiProductResp>>> getProductList(TypeBaseUserReq req);


    /**
     * 用户理财持仓中列表
     */
    CompletableFuture<CommonResp<ApiQueryBuyOrderResp>> queryBuyOrderList(APiQueryBuyOrderReq req);


    /**
     * 历史订单-用户所有已赎回的订单
     */
    CompletableFuture<CommonResp<PageResult<ApiRansomOrderResp>>> queryRansomOrderList(TypeBasePageReq req);

    /**
     * 订单详情
     */
    CompletableFuture<CommonResp<ApiOrderDetailsResp>> queryOrderDetails(OrderNoReq req);

    /**
     * 创建理财订单
     */
    CompletableFuture<CommonResp<ApiFinancialCreateOrderResp>> createOrder(ApiCreateOrderReq req);

    /**
     * 支付订单
     */
    CompletableFuture<CommonResp<Boolean>> paymentOrder(ApiPaymentOrderReq req);

    /**
     * 创建单个待领取收益订单
     */
    CompletableFuture<CommonResp<ApiWithdrawIncomeOrderResp>> createWithdrawIncomeOrder(OrderNoReq req);

    /**
     * 创建一键领取的收益订单
     */
    CompletableFuture<CommonResp<ApiWithdrawIncomeOrderResp>> batchCreateWithdrawIncomeOrder(TypeBaseUserReq req);

    /**
     * 确认领取收益
     */
    CompletableFuture<CommonResp<Boolean>> receiveIncome(ApiReceiveIncomeReq req);

    /**
     * 创建赎回订单
     */
    CompletableFuture<CommonResp<ApiCreateRansomOrderResp>> createRansomOrder(OrderNoReq req);

    /**
     * 确认赎回订单
     * /ransom/confirmOrder
     */
    CompletableFuture<CommonResp<Boolean>> ransomOrderConfirm(ApiRansomOrderConfirmReq req);

    /**
     * 查询用户理财账单
     * /bill
     */
    CompletableFuture<CommonResp<PageResult<ApiUserOrderBillResp>>> billList(ApiUserBillQueryReq req);

    /**
     * 用户理财账单详情
     * /bill/detail
     */
    CompletableFuture<CommonResp<ApiUserOrderBillDetailsResp>> billDetail(ApiUserBillDetailsReq req);
}
