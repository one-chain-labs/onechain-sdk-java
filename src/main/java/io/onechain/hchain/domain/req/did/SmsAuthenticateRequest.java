package io.onechain.hchain.domain.req.did;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 手机号授权请求
 *
 * @author lhb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("手机号授权请求")
public class SmsAuthenticateRequest extends BaseReq {

    @ApiModelProperty(value = "号码前缀", required = true)
    @NotBlank(message = "手机号码前缀不能为空")
    private String mobilePrefix;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号码不能为空")
    private String mobile;

    @ApiModelProperty(value = "手机验证码", required = true)
    @NotBlank(message = "手机验证码不能为空")
    private String smsCode;

    @ApiModelProperty(value = "发送验证码成功后返回的code", required = true)
    @NotBlank(message = "发送验证码成功后返回的code不能为空")
    private String code;
}
