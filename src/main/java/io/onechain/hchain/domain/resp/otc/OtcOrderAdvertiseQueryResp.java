package io.onechain.hchain.domain.resp.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * Author: Bin
 * Date: 2025/3/10 17:08
 */
@Data
@Accessors(chain = true)
@ApiModel("用户订单-查询广告列表响应")
public class OtcOrderAdvertiseQueryResp {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("广告id")
    private String advertiseId;

    @ApiModelProperty("广告类型，对于boss(1-出售，2-购买),对于用户(1-购买，2-出售)")
    private Integer advertiseType;

    @ApiModelProperty("状态，0-禁用，1-启用")
    private Integer status;

    @ApiModelProperty("订单币种id")
    private Long orderCurrencyId;

    @ApiModelProperty("订单币种编码")
    private String orderCurrencyCode;

    @ApiModelProperty("结算币种id")
    private Long settleCurrencyId;

    @ApiModelProperty("结算币种编码")
    private String settleCurrencyCode;

    @ApiModelProperty("交易价格")
    private BigDecimal price;

    @ApiModelProperty("订单最小限额(限制结算币种,页面显示放在结算币种下面)")
    private BigDecimal orderMinAmount;

    @ApiModelProperty("订单最大限额(限制结算币种,页面显示放在结算币种下面)")
    private BigDecimal orderMaxAmount;

    @ApiModelProperty("支付过期时间，单位：分钟")
    private Integer payExpiredTime;

    @ApiModelProperty("支付方式：hone-汇旺")
    private String payChannel;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
