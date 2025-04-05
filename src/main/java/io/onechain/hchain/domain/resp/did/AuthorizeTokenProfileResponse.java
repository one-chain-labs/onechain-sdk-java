package io.onechain.hchain.domain.resp.did;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lhb
 */
@Data
@ApiModel("用户token信息")
public class AuthorizeTokenProfileResponse {

    @ApiModelProperty("token信息")
    private AccessTokenProfile accessTokenProfile;

    @ApiModelProperty("访问Token KEY=ACCESS_TOKEN")
    private String accessToken;

    @ApiModelProperty("JWT-Token")
    private String jwtToken;

    @ApiModelProperty("是否设置支付密码")
    private boolean settingPayPassword;

    @ApiModelProperty("头像")
    private String avatarUrl;

    @ApiModelProperty("名称")
    private String nickname;

    @ApiModelProperty("Did")
    private String did;

    @ApiModelProperty("用户盐值")
    private String salt;

    private boolean anonymous = false;


    @Data
    public static class AccessTokenProfile {
        @ApiModelProperty("签发者")
        private String iss;

        @ApiModelProperty("授权方")
        private String azp;

        @ApiModelProperty("接收方")
        private String aud;

        @ApiModelProperty("主题")
        private String sub;

        @ApiModelProperty("随机值")
        private String nonce;

        @ApiModelProperty("Token 的生效时间")
        private Long nbf;

        @ApiModelProperty("Token 签发时间")
        private Long iat;

        @ApiModelProperty("Token 过期时间")
        private Long exp;

        @ApiModelProperty("Token 唯一标识")
        private String jti;
    }


}
