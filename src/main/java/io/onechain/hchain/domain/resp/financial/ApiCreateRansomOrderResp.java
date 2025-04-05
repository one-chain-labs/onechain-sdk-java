package io.onechain.hchain.domain.resp.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Author: Bin
 * Date: 2025/3/14 14:59
 */
@ApiModel("创建赎回订单响应")
@Data
@Accessors(chain = true)
public class ApiCreateRansomOrderResp {

    @ApiModelProperty("赎回业务订单号")
    private String orderNo;

    @ApiModelProperty("待签名交易数据")
    private String rawTransaction;

    @ApiModelProperty("订单签名过期时间")
    private LocalDateTime expirations;
}
