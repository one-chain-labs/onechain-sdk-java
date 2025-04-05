//
//

package io.onechain.hchain.domain.resp.exchange;


import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("交易对信息")
@Data
public class CoinYInfoResp {
    @ApiModelProperty("交易对Id")
    private String pairId;
    @ApiModelProperty("基准币种地址")
    private String coinXCoinType;
    @ApiModelProperty("基准币种简单名称")
    private String coinXName;
    @ApiModelProperty("计价币种简单名称")
    private String coinYName;
    @ApiModelProperty("计价币种地址")
    private String coinYCoinType;
    @ApiModelProperty("汇率")
    private String rate;
    @ApiModelProperty("X币种兑换最小金额")
    private String coinXSwapMin;
    @ApiModelProperty("X币种兑换最大金额")
    private String coinXSwapMax;
    @ApiModelProperty("Y币种兑换最小金额")
    private String coinYSwapMin;
    @ApiModelProperty("Y币种兑换最大金额")
    private String coinYSwapMax;
    @ApiModelProperty("是否被锁-false为启用  true为未启用")
    private Boolean lockStatus;

}
