package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BasePageReq;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Author: Bin
 * Date: 2025/3/11 10:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OtcOrderPageListUserReq extends BasePageReq {

    @ApiModelProperty("进度类型，1-进行中，2-已完成")
    @NotNull
    private Integer processType;

    @ApiModelProperty(value = "用户交易类型,1-购买，2-出售")
    private Integer userTradeType;

    @ApiModelProperty("查询状态，1-未付款,2-已付款,3-已完成，4-已取消")
    private Integer queryStatus;

    @ApiModelProperty(value = "创建开始时间")
    private LocalDateTime createTimeStart;

    @ApiModelProperty(value = "创建结束时间")
    private LocalDateTime createTimeEnd;
}
