package io.onechain.hchain.domain.req.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-12 17:48:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("查询提现汇率请求")
public class CurrencyRateReq extends BaseReq {

    @ApiModelProperty("付方id")
    private Integer payerId;

    @ApiModelProperty("提现交易类型 C2C C2B B2C B2B，默认是C2C的")
    private String transactionType;

    @ApiModelProperty("源币种 默认是USD的，而且目前也只支持USD的")
    private String sourceCurrency;
}
