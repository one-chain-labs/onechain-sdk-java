package io.onechain.hchain.domain.req.did;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author lhb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("获取授权token请求")
public class AuthorizeTokenProfileRequest extends BaseReq {

    @NotBlank(message = "授权码不能为空")
    @ApiModelProperty(value = "授权码", required = true)
    private String code;

    @NotBlank(message = "随机字符串不能为空")
    @ApiModelProperty(value = "随机字符串", required = true)
    private String nonce;

}
