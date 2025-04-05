package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * Author: Bin
 * Date: 2025/3/11 10:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OtcOrderDetailReq extends BaseUserReq {

    @ApiModelProperty(value = "订单号", required = true)
    @NotEmpty
    private String orderNo;
}
