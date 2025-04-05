package io.onechain.hchain.domain.resp.transfer;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lhb
 */
@Data
@ApiModel("转账方式响应")
public class TransferMethodResp {

    @ApiModelProperty("did")
    private String did;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("钱包")
    public List<Wallet> walletList;

    @Data
    public static class Wallet{
        @ApiModelProperty("钱包地址")
        private String address;

        @ApiModelProperty("别称")
        private String name;

        @ApiModelProperty("收款方")
        private String walletType;

        @ApiModelProperty("收款方")
        private String walletTypeName;
    }
}
