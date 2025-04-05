package io.onechain.hchain.controller;


import io.onechain.hchain.annotations.ApiOperation;
import io.onechain.hchain.domain.req.common.OrderIdReq;
import io.onechain.hchain.domain.req.transfer.*;
import io.onechain.hchain.domain.resp.common.CommonResp;
import io.onechain.hchain.domain.resp.common.CreateOrderResp;
import io.onechain.hchain.domain.resp.common.PageResult;
import io.onechain.hchain.domain.resp.transfer.*;
import jakarta.validation.Valid;

import java.util.concurrent.CompletableFuture;

/**
 * 转账接口
 *
 * @author Lhb
 * @version v0.0.1 2025-03-06 14:07:16
 */
public interface TransferApi {

    /**
     * 创建转账订单
     */
    CompletableFuture<CommonResp<CreateOrderResp>> createOrder(TransferOrderReq req);

    /**
     * 转账-执行交易
     */
    CompletableFuture<CommonResp<TransferOrderTxResp>> sendTx(TransferOrderTxReq req);

    /**
     * 分页-查询订单
     */
    CompletableFuture<CommonResp<PageResult<TransferOrderResp>>> pageList(TransferOrderQueryPageReq req);

    /**
     * 查询订单
     */
    CompletableFuture<CommonResp<TransferOrderResp>> queryOrder(TransferOrderQueryReq req);


    /**
     * 构建代付交易
     */
    CompletableFuture<CommonResp<GasTxBuilderResponse>> buildSponsorTransaction(BuildSponsorTransactionRequest req);

    /**
     * 发送代付交易
     */
    CompletableFuture<CommonResp<ProxyPayTxResp>> doProxyPayTx(ProxyPayTxRequest request);

}
