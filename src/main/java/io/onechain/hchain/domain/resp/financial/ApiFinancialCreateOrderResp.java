package io.onechain.hchain.domain.resp.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Author: Bin
 * Date: 2025/3/14 10:54
 */
@Data
@Accessors(chain = true)
@ApiModel("创建订单响应")
public class ApiFinancialCreateOrderResp {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("交易hash")
    private String hash;

    @ApiModelProperty("待签名交易数据")
    private String rawTransaction;

    @ApiModelProperty("订单签名过期时间")
    private LocalDateTime expirations;

}
