package io.onechain.hchain.domain.resp.transfer;

import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lhb
 */
@Data
public class GasTxBuilderResponse {

    @ApiModelProperty("hash")
    private String hash;

    @ApiModelProperty("待签名交易")
    private String rawTransaction;

    @ApiModelProperty("交易过期时间")
    private Long expiration;

    @ApiModelProperty("手续费赞助地址")
    private String sponsor;

    @ApiModelProperty("reservationId")
    private String reservationId;
}