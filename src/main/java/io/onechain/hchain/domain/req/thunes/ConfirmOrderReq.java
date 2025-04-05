package io.onechain.hchain.domain.req.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.OrderIdReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Lhb
 * @version v0.0.1 2025-03-13 09:04:45
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("确认提现交易请求")
public class ConfirmOrderReq extends OrderIdReq {

    @ApiModelProperty(value = "签名", required = true)
    @NotBlank(message = "签名不能为空")
    private String signature;
}
