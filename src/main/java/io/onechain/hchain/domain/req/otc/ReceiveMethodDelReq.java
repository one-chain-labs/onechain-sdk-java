package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: Bin
 * Date: 2025/3/11 10:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "收款方式-删除收款方式")
public class ReceiveMethodDelReq extends BaseUserReq {
}
