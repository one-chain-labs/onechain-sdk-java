package io.onechain.hchain.domain.req.did;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-31 11:00:10
 */

@ApiModel("查询钱包请求")
@Data
public class QueryWalletReq {

    @ApiModelProperty("用户did，和钱包地址任意传一个")
    private String did;

    @ApiModelProperty("用户钱包地址，和did任意传一个")
    private String address;
}
