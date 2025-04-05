package io.onechain.hchain.domain.resp.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * Author: Bin
 * Date: 2025/3/10 17:25
 */
@Data
@Accessors(chain = true)
@ApiModel("订单详情")
public class OtcOrderDetailResp {

    @ApiModelProperty("业务单号")
    private String orderNo;

    @ApiModelProperty("广告id")
    private String advertiseId;

    @ApiModelProperty("交易类型(相对于boss)，1-出售，2-购买")
    private Integer bossTradeType;

    @ApiModelProperty("交易类型(相对于用户)，1-购买，2-出售")
    private Integer userTradeType;

    @ApiModelProperty("状态,购买(WAIT_PAY-待支付,PAY_TIMEOUT-支付超时关闭,PAY_SUCCESS-支付成功,PAY_REFUND_SUCCESS-支付退款成功,RELEASE_CURRENCY-已放币),出售(INIT-初始化,INIT_FAIL-初始化失败,WAIT_PAY-待支付,PAY_TIMEOUT-支付超时关闭,PAYING-支付中,PAY_SUCCESS-支付成功,PAY_FAIL-支付失败,PAY_REFUND_SUCCESS-确认退币成功,PAYMENT_SUCCESS-确认付款成功)")
    private String status;

    @ApiModelProperty("显示状态, 购买(WAIT_PAY-未付款,WAIT_RECEIVE_CURRENCY-待收币,CANCELED-已取消,COMPLETED-已完成), 出售(WAIT_PAY-未付款,CANCELED-已取消,WAIT_RECEIVE_AMOUNT-待收款,COMPLETED-已完成)")
    private String showStatus;

    @ApiModelProperty("创建人did")
    private String creatorDid;

    @ApiModelProperty("创建人链地址")
    private String creatorChainAddress;

    @ApiModelProperty("平台链地址")
    private String platformChainAddress;

    @ApiModelProperty("订单币种id")
    private Long orderCurrencyId;

    @ApiModelProperty("订单币种编码")
    private String orderCurrencyCode;

    @ApiModelProperty("结算币种id")
    private Long settleCurrencyId;

    @ApiModelProperty("结算币种编码")
    private String settleCurrencyCode;

    @ApiModelProperty("交易价格(用户app详情-单价)")
    private BigDecimal price;

    @ApiModelProperty("交易数量(订单金额，用户app详情-金额)")
    private BigDecimal tradeNumber;

    @ApiModelProperty("结算金额(boss购买订单详情-收款金额，用户app详情-金额)")
    private BigDecimal settleAmount;

    @ApiModelProperty("用户收款人(boss购买订单详情-收款人)")
    private String uReceiveUserName;

    @ApiModelProperty("用户收款账号(boss购买订单详情-收款账号)")
    private String uReceiveAccount;

    @ApiModelProperty("用户收款二维码(boss购买订单详情-收款二维码)")
    private String uReceiveQrCode;

    @ApiModelProperty("支付过期时间")
    private LocalDateTime payExpiredTime;

    @ApiModelProperty("平台收款人(用户购买订单的付款信息-收款人)")
    private String pReceiveUserName;

    @ApiModelProperty("平台收款账号(用户购买订单的付款信息-汇旺ID)")
    private String pReceiveAccount;

    @ApiModelProperty("平台收款二维码(用户购买订单的付款信息-汇旺收款码)")
    private String pReceiveQrCode;

    @ApiModelProperty("支付渠道 hone-汇旺(boss购买订单详情-收款方式)")
    private String payChannel;

    @ApiModelProperty("hash")
    private String hash;

    @ApiModelProperty("tg联系客服")
    private String relationTg;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("完成时间")
    private LocalDateTime completeTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

}
