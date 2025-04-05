package io.onechain.hchain.domain.req.exchange;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BasePageReq;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Lhb
 * @version v0.0.1 2025-03-10 14:55:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("分页查询兑汇订单")
public class QueryExchangePageReq extends BasePageReq {

    @ApiModelProperty("承兑币种")
    private String coinX;

    @ApiModelProperty("兑付币种")
    private String coinY;

    @ApiModelProperty("交易状态")
    private String exchangeState;

    @NotNull(message = "开始时间不能为空")
    @ApiModelProperty(value = "开始时间", required = true)
    private Long startTradeTime;

    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty(value = "结束时间", required = true)
    private Long endTradeTime;
}
