package io.onechain.hchain.domain.req.did;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Lhb
 * @version v0.0.1 2025-03-31 14:31:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("刷新Token请求")
public class RefreshJwtTokenReq extends BaseReq {

    @NotBlank
    @ApiModelProperty(value = "随机字符串", required = true)
    private String nonce;
}
