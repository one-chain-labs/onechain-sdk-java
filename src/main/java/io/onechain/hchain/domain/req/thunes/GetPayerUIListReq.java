package io.onechain.hchain.domain.req.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Lhb
 * @version v0.0.1 2025-03-20 15:31:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("获取供应商动态UI请求")
public class GetPayerUIListReq extends BaseReq {

    @ApiModelProperty(value = "供应商id", required = true)
    @NotNull(message = "供应商id不能为空")
    private Integer payerId;

    @ApiModelProperty(value = "交易类型", required = true)
    @NotBlank(message = "交易类型不能为空")
    private String transactionType;

    @ApiModelProperty(value = "是否显示简单的结构，当前端使用时填false，当只是观察结构时填true")
    private Boolean showSimple;
}
