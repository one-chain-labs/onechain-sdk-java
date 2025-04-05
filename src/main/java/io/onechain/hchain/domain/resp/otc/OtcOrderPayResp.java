package io.onechain.hchain.domain.resp.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Author: Bin
 * Date: 2025/3/10 17:14
 */

@Data
@Accessors(chain = true)
@ApiModel(value = "用户订单-支付订单响应")
public class OtcOrderPayResp {

    @ApiModelProperty(value = "展示状态")
    private String showStatus;
}
