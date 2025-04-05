package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: Bin
 * Date: 2025/3/10 17:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户订单-查询广告列表")
public class OtcOrderAdvertiseQueryReq extends BaseUserReq {
}
