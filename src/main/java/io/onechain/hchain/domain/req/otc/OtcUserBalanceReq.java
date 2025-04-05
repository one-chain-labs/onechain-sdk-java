package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * Author: Bin
 * Date: 2025/3/11 10:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OtcUserBalanceReq extends BaseUserReq {

    @ApiModelProperty("币种编码")
    @NotEmpty
    private String currencyCode;
}
