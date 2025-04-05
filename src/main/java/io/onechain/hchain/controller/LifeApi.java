package io.onechain.hchain.controller;


import io.onechain.hchain.domain.req.life.CreatePhoneChargesReq;
import io.onechain.hchain.domain.req.life.PayPhoneChargesReq;
import io.onechain.hchain.domain.req.life.QueryPhoneChargesOrderListReq;
import io.onechain.hchain.domain.resp.common.CommonResp;
import io.onechain.hchain.domain.resp.common.CreateOrderResp;
import io.onechain.hchain.domain.resp.common.PageResult;
import io.onechain.hchain.domain.resp.life.PayPhoneChargesResp;
import io.onechain.hchain.domain.resp.life.PhoneChargesDetailResp;

import java.util.concurrent.CompletableFuture;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-07 10:03:37
 */
public interface LifeApi {

    /**
     * 创建话费充值单
     */
    CompletableFuture<CommonResp<CreateOrderResp>> createPhoneCharges(CreatePhoneChargesReq req);

    /**
     * 支付话费充值单
     */
    CompletableFuture<CommonResp<PayPhoneChargesResp>> payPhoneCharges(PayPhoneChargesReq req);

    /**
     * 查询话费充值单分页
     */
    CompletableFuture<CommonResp<PageResult<PhoneChargesDetailResp>>> queryPhoneChargesOrderList(
            QueryPhoneChargesOrderListReq req);
}
