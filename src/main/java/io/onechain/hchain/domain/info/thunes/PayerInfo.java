package io.onechain.hchain.domain.info.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lhb
 */
@Data
@ApiModel("付款方信息")
public class PayerInfo {
    // 银行id
    @ApiModelProperty("付款方id")
    private Integer id;

    @ApiModelProperty("付款方名字")
    private String name;

    @ApiModelProperty("精度")
    private Integer precision;

    @ApiModelProperty("货币的最小面额 比如0.01 而有些货币的最小面额是100")
    private String increment;

    @ApiModelProperty("货币币种")
    private String currency;

    @ApiModelProperty("国家/地区代码")
    private String countryIsoCode;

    @ApiModelProperty("服务信息")
    private ServiceInfo service;

    @ApiModelProperty("交易类型信息")
    private TransactionTypesInfo transactionTypes;

    @Data
    @ApiModel("在某个交易类型下的必填字段，即，告诉使用者某个渠道付款方式需要的必填字段")
    public static class TransactionTypeInfo {

        @ApiModelProperty("收款人的必填字段，由于收款人会有多个账号，因此收款人和收款账号是两个字段")
        private List<List<String>> creditPartyIdentifiersAccepted;

        @ApiModelProperty("收款账号的必填字段")
        private List<List<String>> requiredReceivingEntityFields;

        @ApiModelProperty("付款账号的必填字段")
        private List<List<String>> requiredSendingEntityFields;
    }

    @Data
    @ApiModel("各个渠道的付款必填字段")
    public static class TransactionTypesInfo {
        private TransactionTypeInfo c2c;
        private TransactionTypeInfo c2b;
        private TransactionTypeInfo b2c;
        private TransactionTypeInfo b2b;
    }

    @Data
    @ApiModel("报价单服务信息")
    public static class ServiceInfo {

        @ApiModelProperty("服务id")
        private Integer id;

        @ApiModelProperty("服务名字")
        private String name;
    }
}