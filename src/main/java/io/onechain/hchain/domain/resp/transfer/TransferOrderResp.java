package io.onechain.hchain.domain.resp.transfer;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lhb
 */
@Data
@ApiModel("转账订单信息")
public class TransferOrderResp {

    @ApiModelProperty("交易哈希")
    private String hash;

    @ApiModelProperty("转出用户DID")
    private String did;

    @ApiModelProperty("转出用户昵称")
    private String nickName;

    @ApiModelProperty("转出账户")
    private String address;

    @ApiModelProperty("转出HCname")
    private String addressName;

    @ApiModelProperty("所属商户id")
    private String merchantId;

    @ApiModelProperty("所属商户名字")
    private String merchantName;

    @ApiModelProperty("转账类型")
    private String transferMethod;

    @ApiModelProperty("接收用户DID")
    private String toDid;

    @ApiModelProperty("接收用户昵称")
    private String toNickName;

    @ApiModelProperty("接收账户")
    private String toAddress;

    @ApiModelProperty("接收HCname")
    private String toAddressName;

    @ApiModelProperty("接受地址所属商户id")
    private String toMerchantId;

    @ApiModelProperty("接受地址所属商户名字")
    private String toMerchantName;

    @ApiModelProperty("币种")
    private String currency;

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("发起时间")
    private Long createTime;

    @ApiModelProperty("完成时间")
    private Long completeTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("转出用户名->根据转账方式显示")
    private String sender;

    @ApiModelProperty("接收用户名->根据转账方式显示")
    private String receiver;
}
