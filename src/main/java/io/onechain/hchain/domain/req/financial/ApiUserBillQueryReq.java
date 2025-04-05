package io.onechain.hchain.domain.req.financial;

import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Author: Bin
 * Date: 2025/3/10 11:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiUserBillQueryReq extends BasePageReq {

    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;
}
