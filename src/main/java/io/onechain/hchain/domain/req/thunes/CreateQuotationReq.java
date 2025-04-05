package io.onechain.hchain.domain.req.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Lhb
 * @version v0.0.1 2025-03-12 15:55:41
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("创建thunes报价单")
@Data
public class CreateQuotationReq extends BaseUserReq {

    @ApiModelProperty(value = "报价单id，调用方自行生成", required = true)
    @NotBlank(message = "报价单id不能为空")
    private String quotationOrderId;

    @ApiModelProperty(value = "银行和渠道的ID", required = true)
    @NotNull(message = "提供商id不能为空")
    private Integer payerId;

    @ApiModelProperty(value = "报价方式 SOURCE_AMOUNT DESTINATION_AMOUNT 两个值二选一即可", required = true)
    @NotBlank(message = "报价模式不能为空")
    private String mode;

    @ApiModelProperty(value = "提现交易类型 C2C C2B B2C B2B", required = true)
    @NotBlank(message = "交易类型不能为空")
    private String transactionType;

    @ApiModelProperty(value = "当 mode=SOURCE_AMOUNT 时，表示能付出的美元数量，根据美元推算目标币种数量，当 mode=DESTINATION_AMOUNT 表示需要收到的目标币种数量，根据目标数量能反推出美元数量", required = true)
    @NotBlank(message = "金额不能为空")
    private String amount;

    @ApiModelProperty(value = "目标币种", required = true)
    @NotBlank(message = "提现币种不能为空")
    private String destinationCurrency;
}
