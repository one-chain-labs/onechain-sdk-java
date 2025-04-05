package io.onechain.hchain.domain.req.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-12 17:08:36
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("创建thunes提现单请求")
@Data
public class CreateOrderReq extends BaseUserReq {

    @ApiModelProperty("提现订单号，调用方自行生成")
    @NotBlank(message = "提现订单号不能为空")
    private String orderId;

    @ApiModelProperty("调用链参数")
    @NotNull(message = "调用链参数不能为空")
    private ChainParam chainParam;

    @ApiModelProperty("调用thunes参数")
    @NotNull(message = "调用thunes参数不能为空")
    private ThunesParam thunesParam;

    @ApiModel("提现链上参数")
    @Data
    public static class ChainParam {

        @ApiModelProperty(value = "用户钱包地址", required = true)
        @NotBlank(message = "用户钱包地址不能为空")
        private String senderAddress;

        @ApiModelProperty(value = "提现币种", required = true)
        @NotBlank(message = "提现币种")
        private String coinName;

        @ApiModelProperty(value = "链上交易金额", required = true)
        @NotBlank(message = "链上交易金额不能为空")
        private String amount;
    }

    @ApiModel("thunes参数")
    @Data
    public static class ThunesParam {

        @ApiModelProperty("报价单id")
        @NotBlank(message = "报价单id不能为空")
        private String externalId;

        @ApiModelProperty("付款人信息")
        @NotEmpty(message = "付款人信息不能为空")
        private Map<String, String> from;

        @ApiModelProperty("收款人账号信息")
        @NotEmpty(message = "收款人账号信息不能为空")
        private Map<String, String> creditPartyIdentifier;

        @ApiModelProperty("收款人信息")
        @NotEmpty(message = "收款人信息不能为空")
        private Map<String, String> to;

        @ApiModelProperty("付款目的")
        private String purposeOfRemittance;
    }
}

