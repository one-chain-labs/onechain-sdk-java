package io.onechain.hchain.domain.req.did;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;


/**
 * @author lhb
 */
@Data
@ToString
@ApiModel("获取zk证明请求")
@Builder
public class ZkProofsReq extends BaseReq {

    @ApiModelProperty("最大epoch")
    @NotNull(message = "maxEpoch不能为空")
    private Long maxEpoch;

    @ApiModelProperty("jwt随机数")
    @NotBlank(message = "jwt随机数不能为空")
    private String jwtRandomness;

    @ApiModelProperty("临时公钥")
    @NotBlank(message = "临时公钥不能为空")
    private String extendedEphemeralPublicKey;

    @ApiModelProperty("jwt-token 信息")
    @NotBlank(message = "jwt-token不能为空")
    private String jwt;

    @ApiModelProperty("盐值")
    @NotBlank(message = "盐值不能为空")
    private String salt;

    @ApiModelProperty("jwt-sub信息,用户编号")
    @NotBlank(message = "jwt-sub信息")
    private String keyClaimName;
}
