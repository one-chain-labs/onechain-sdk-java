//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.onechain.hchain.domain.resp.exchange;

import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateTradeResp {
    @ApiModelProperty("reservationId 提交签名时带上")
    private Long reservationId;
    @ApiModelProperty("交易hash 提交签名时带上")
    private String hash;
    @ApiModelProperty("待签名交易字符串 提交签名时带上")
    private String txBytes;
    @ApiModelProperty("业务单号 提交签名时带上")
    private String orderId;
    @ApiModelProperty("用户DID")
    private String did;
    @ApiModelProperty("交易地址")
    private String sender;
    @ApiModelProperty("承兑币种")
    private String coinX;
    @ApiModelProperty("承兑金额")
    private BigDecimal coinXAmount;
    @ApiModelProperty("兑付币种")
    private String coinY;
    @ApiModelProperty("兑付金额")
    private BigDecimal coinYAmount;
    @ApiModelProperty("汇率")
    private String rate;
    @ApiModelProperty("交易时间")
    private LocalDateTime tradeTime;
    @ApiModelProperty("交易状态")
    private String exchangeState;


}
