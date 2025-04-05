package io.onechain.hchain.domain.req.exchange;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Lhb
 * @version v0.0.1 2025-03-04 09:53:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("创建兑汇订单")
public class CreateOrderReq extends BaseUserReq {

    @NotBlank(message = "汇兑交易对ID不能为空")
    @ApiModelProperty(value = "汇兑交易对ID", required = true)
    private String pairId;

    @NotNull(message = "兑换金额不能为空")
    @ApiModelProperty(value = "兑换金额", required = true)
    private String amount;

    @NotBlank(message = "基准币种不能为空,传完整币种信息不要传简称")
    @ApiModelProperty(value = "基准币种", required = true)
    private String coinX;

    @NotBlank(message = "计价币种不能为空,传完整币种信息不要传简称")
    @ApiModelProperty(value = "计价币种", required = true)
    private String coinY;

}
