package io.onechain.hchain.domain.resp.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * Author: Bin
 * Date: 2025/3/10 17:11
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户订单-创建订单响应")
public class OtcOrderCreateResp {

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "展示状态")
    private String showStatus;

    @ApiModelProperty(value = "待签名交易")
    private String rawTransaction;

    @ApiModelProperty(value = "支付过期时间")
    private LocalDateTime payExpiredTime;
}
