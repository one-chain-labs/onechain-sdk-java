package io.onechain.hchain.controller;

import io.onechain.hchain.annotations.ApiOperation;
import io.onechain.hchain.domain.req.did.QueryWalletReq;
import io.onechain.hchain.domain.req.transfer.TransferOrderQueryPageReq;
import io.onechain.hchain.domain.req.transfer.TransferOrderQueryReq;
import io.onechain.hchain.domain.req.wallet.QueryCurrencyReq;
import io.onechain.hchain.domain.resp.common.CommonResp;
import io.onechain.hchain.domain.resp.common.PageResult;
import io.onechain.hchain.domain.resp.did.UserWalletResp;
import io.onechain.hchain.domain.resp.transfer.TransferOrderResp;
import io.onechain.hchain.domain.resp.wallet.CurrencyChainResp;
import jakarta.validation.Valid;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-25 17:12:58
 */



public interface WalletApi {

    @ApiOperation(value = "获取链支持币种的信息及和USD的汇率")
    CompletableFuture<CommonResp<List<CurrencyChainResp>>> queryChainCurrencyForList(QueryCurrencyReq req);

    @ApiOperation(value = "查询钱包基础信息")
    CompletableFuture<CommonResp<List<UserWalletResp>>> queryUserWalletForList(QueryWalletReq req);



}
