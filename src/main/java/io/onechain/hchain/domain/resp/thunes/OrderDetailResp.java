package io.onechain.hchain.domain.resp.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.info.thunes.AmountInfo;
import io.onechain.hchain.domain.info.thunes.PayerInfo;
import io.onechain.hchain.domain.info.thunes.SourceInfo;
import lombok.Data;

import java.util.Map;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-13 10:42:49
 */
@Data
@ApiModel("订单详情")
public class OrderDetailResp {

    @ApiModelProperty("状态码")
    private String status;

    @ApiModelProperty("状态码说明")
    private String statusMessage;

    @ApiModelProperty("外部单号")
    private String orderNo;

    @ApiModelProperty("交易类型")
    private String transactionType;

    @ApiModelProperty("创建时间")
    private Long creationTime;

    @ApiModelProperty("完成时间")
    private Long completeTime;

    @ApiModelProperty("提现目的")
    private String purposeOfRemittance;

    @ApiModelProperty("接收方信息-是收件人的信息")
    private Map<String, String> creditPartyIdentifier;

    @ApiModelProperty("受益人信息-是收件卡的信息")
    private Map<String, String> beneficiary;

    @ApiModelProperty("来源信息")
    private SourceInfo source;

    @ApiModelProperty("目的信息")
    private AmountInfo destination;

    @ApiModelProperty("费用信息")
    private AmountInfo fee;

    @ApiModelProperty("付款人信息")
    private PayerInfo payer;

    @ApiModelProperty("外汇汇率 (数字使用 string 表示)")
    private String wholesaleFxRate;
}
