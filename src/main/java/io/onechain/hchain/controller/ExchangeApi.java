package io.onechain.hchain.controller;


import io.onechain.hchain.domain.req.common.BasePageReq;
import io.onechain.hchain.domain.req.common.BaseReq;
import io.onechain.hchain.domain.req.common.PaymentOrderReq;
import io.onechain.hchain.domain.req.exchange.CreateOrderReq;
import io.onechain.hchain.domain.resp.common.CommonResp;
import io.onechain.hchain.domain.resp.common.CreateOrderResp;
import io.onechain.hchain.domain.resp.common.PageResult;
import io.onechain.hchain.domain.resp.exchange.CoinYInfoResp;
import io.onechain.hchain.domain.resp.exchange.CreateTradeResp;
import io.onechain.hchain.domain.resp.exchange.ExchangeRecordResp;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-04 16:08:59
 */
public interface ExchangeApi {



    /**
     * 查询兑汇信息
     * @param req
     * @return
     */
    CompletableFuture<CommonResp<Map<String, List<CoinYInfoResp>>>> queryExchangeInfo(BaseReq req);


    /**
     * 查询兑汇订单列表
     * @param req
     * @return
     */
    CompletableFuture<CommonResp<PageResult<ExchangeRecordResp>>> queryOrderList(BasePageReq req);


    /**
     * 创建兑汇订单
     * @param req
     * @return
     */
    CompletableFuture<CommonResp<CreateTradeResp>> createOrder(CreateOrderReq req);

    /**
     * 支付兑汇订单
     * @param req
     * @return
     */
    CompletableFuture<CommonResp<Boolean>> paymentOrder(PaymentOrderReq req);
}
