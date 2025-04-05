package io.onechain.hchain.domain.req.transfer;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author lhb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("构建转账请求")
public class TransferOrderReq extends BaseReq {

    @ApiModelProperty(value = "发送方地址", required = true)
    @NotBlank(message = "发送方地址不能为空")
    private String fromAddress;

    @ApiModelProperty("接收方地址")
    @NotBlank(message = "接收方地址不能为空")
    private String toAddress;

    @ApiModelProperty("转账币种")
    @NotBlank(message = "转账币种不能为空")
    private String currency;

    @ApiModelProperty("转账金额")
    @NotNull(message = "转账金额不能为空")
    private BigDecimal amount;

    @ApiModelProperty("备注")
    private String remark;

}
