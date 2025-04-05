package io.onechain.hchain.domain.req.did;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author lhb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("发送短信验证码请求")
public class SmsCodeSendReq extends BaseReq {

    @NotNull(message = "时间戳不能为空")
    @ApiModelProperty(value = "时间戳,单位毫秒", required = true)
    private Long timestamp;

    @NotBlank(message = "商户签名不能为空")
    @ApiModelProperty(value = "商户签名", required = true)
    private String merchantSign;

    @NotBlank(message = "商户id不能为空")
    @ApiModelProperty(value = "商户id", required = true)
    private String merchantId;

    @ApiModelProperty(value = "手机号码", required = true)
    @NotBlank(message = "手机号码不能为空")
    private String mobile;

    @ApiModelProperty(value = "手机号前缀", required = true)
    @NotBlank(message = "手机号码前缀不能为空")
    private String mobilePrefix;
}
