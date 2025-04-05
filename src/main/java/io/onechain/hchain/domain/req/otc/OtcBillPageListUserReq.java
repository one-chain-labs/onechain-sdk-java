package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Author: Bin
 * Date: 2025/3/10 17:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("otc订单-账单请求")
public class OtcBillPageListUserReq extends BasePageReq {

    @ApiModelProperty(value = "创建开始时间")
    private LocalDateTime createTimeStart;

    @ApiModelProperty(value = "创建结束时间")
    private LocalDateTime createTimeEnd;
}
