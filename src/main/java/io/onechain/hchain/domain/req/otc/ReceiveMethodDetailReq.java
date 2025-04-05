package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: Bin
 * Date: 2025/3/11 10:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "收款方式-详情查询")
public class ReceiveMethodDetailReq extends BaseUserReq {
}
