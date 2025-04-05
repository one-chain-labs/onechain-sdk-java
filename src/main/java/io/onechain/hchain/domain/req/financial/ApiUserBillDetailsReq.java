package io.onechain.hchain.domain.req.financial;

import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: Bin
 * Date: 2025/3/10 11:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiUserBillDetailsReq extends BaseUserReq {

    @ApiModelProperty(value = "账单ID",required = true)
    @NotBlank(message = "billId must be not blank")
    private String billId;
}
