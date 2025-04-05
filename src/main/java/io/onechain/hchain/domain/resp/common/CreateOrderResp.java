package io.onechain.hchain.domain.resp.common;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-04 09:52:51
 */
@Data
@ApiModel("创建订单相应")
public class CreateOrderResp {

    @ApiModelProperty("交易hash")
    private String hash;

    @ApiModelProperty("待签名交易数据")
    private String rawTransaction;

    @ApiModelProperty("订单签名过期时间")
    private LocalDateTime expirations;
}
