package io.onechain.hchain.domain.resp.did;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lhb
 */
@Data
@ApiModel("手机号授权响应")
public class AuthenticateUserResponse {


    @ApiModelProperty("认证编码")
    private String code;
}
