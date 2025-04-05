package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Author: Bin
 * Date: 2025/3/11 10:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "收款方式-编辑收款方式")
public class ReceiveMethodEditReq extends BaseUserReq {

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty
    private String password;

    @ApiModelProperty(value = "汇旺收款人", required = true)
    @NotBlank
    private String userName;

    @ApiModelProperty(value = "汇旺收款账号", required = true)
    @NotBlank
    private String honeAccount;

    @ApiModelProperty(value = "汇旺收款二维码", required = true)
    @NotBlank
    private String honeQrCode;
}
