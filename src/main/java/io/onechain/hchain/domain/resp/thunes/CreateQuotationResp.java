package io.onechain.hchain.domain.resp.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.info.thunes.AmountInfo;
import io.onechain.hchain.domain.info.thunes.PayerInfo;
import io.onechain.hchain.domain.info.thunes.SourceInfo;
import lombok.Data;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-12 16:03:20
 */
@ApiModel("创建thunes报价单响应")
@Data
public class CreateQuotationResp {

    @ApiModelProperty("三方返回的报价ID")
    private Long id;

    @ApiModelProperty("我方生成的外部报价ID")
    private String externalId;

    @ApiModelProperty("付方信息")
    private PayerInfo payer;

    @ApiModelProperty("报价方式")
    private String mode;

    @ApiModelProperty("交易类型")
    private String transactionType;

    @ApiModelProperty("来源信息")
    private SourceInfo source;

    @ApiModelProperty("目的信息")
    private AmountInfo destination;

    @ApiModelProperty("发送金额信息")
    private AmountInfo sentAmount;

    @ApiModelProperty("外汇汇率 (数字使用 string 表示)")
    private String wholesaleFxRate;

    @ApiModelProperty("费用信息")
    private AmountInfo fee;

    @ApiModelProperty("创建时间")
    private Long creationDate;

    @ApiModelProperty("过期时间")
    private Long expirationDate;

}
