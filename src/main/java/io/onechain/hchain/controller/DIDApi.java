package io.onechain.hchain.controller;


import io.onechain.hchain.annotations.ApiOperation;
import io.onechain.hchain.domain.req.common.HeaderRequest;
import io.onechain.hchain.domain.req.did.*;
import io.onechain.hchain.domain.resp.common.CommonResp;
import io.onechain.hchain.domain.resp.did.*;
import io.onechain.models.ZKLoginDataResponse;
import io.onechain.models.objects.ZKLoginData;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-06 14:20:35
 */
public interface DIDApi {

    CompletableFuture<CommonResp<String>> sendCode(SmsCodeSendReq smsCodeSendRequest);

    CompletableFuture<CommonResp<AuthenticateUserResponse>> sms(SmsAuthenticateRequest request);

    CompletableFuture<CommonResp<AuthorizeTokenProfileResponse>> getToken(AuthorizeTokenProfileRequest request);


    CompletableFuture<CommonResp<UserTokenProfile>> getTokenUserProfile();

    CompletableFuture<CommonResp<AuthorizeTokenProfileResponse>> refreshJwtToken(RefreshJwtTokenReq req);

    CompletableFuture<CommonResp<ZKLoginData>> getZkProofs(ZkProofsReq req);


}
