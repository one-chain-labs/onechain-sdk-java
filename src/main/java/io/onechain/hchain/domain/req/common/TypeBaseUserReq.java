package io.onechain.hchain.domain.req.common;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: Bin
 * Date: 2025/3/14 11:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("兼容部分项目")
public class TypeBaseUserReq extends BaseUserReq {

//    @ApiModelProperty(hidden = true)
//    private String walletType;
//
//    public String getWalletType() {
//        return super.getMerchantId();
//    }
}
