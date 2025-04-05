package io.onechain.hchain.domain.resp.did;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lhb
 */
@Data
@ApiModel("用户token信息")
public class UserTokenProfile implements Serializable {

    @ApiModelProperty("过期时间")
    private Long expireTime;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("渠道用户编号")
    private String channelUserNo;

    @ApiModelProperty("用户编号")
    private String userNo;

    @ApiModelProperty("访问token")
    private String accessToken;

    @ApiModelProperty("提供方 hc")
    private String provider;

    @ApiModelProperty("DID")
    private String did;
}