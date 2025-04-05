package io.onechain.hchain.domain.resp.did;

import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lhb
 */
@Data
public class UserWalletResp {

    @ApiModelProperty("DID")
    private String did;

    @ApiModelProperty("登录用户编号")
    private String userNo;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("链")
    private String chain;

    @ApiModelProperty("账户编号")
    private String account;

    @ApiModelProperty("账户名称")
    private String accountName;

    @ApiModelProperty("钱包类型")
    private String walletType;

    @ApiModelProperty("别称")
    private String aliasName;
}
