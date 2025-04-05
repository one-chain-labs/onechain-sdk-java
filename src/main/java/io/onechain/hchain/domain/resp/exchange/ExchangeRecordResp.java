//
//

package io.onechain.hchain.domain.resp.exchange;

import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExchangeRecordResp {
    @ApiModelProperty("主键Id")
    private Long id;
    @ApiModelProperty("业务单号")
    private String orderId;
    @ApiModelProperty("用户DID")
    private String did;
    @ApiModelProperty("交易地址")
    private String sender;
    @ApiModelProperty("承兑币种名称")
    private String coinXSymbol;
    @ApiModelProperty("承兑币种")
    private String coinX;
    @ApiModelProperty("承兑金额")
    private BigDecimal coinXAmount;
    @ApiModelProperty("兑付币种名称")
    private String coinYSymbol;
    @ApiModelProperty("兑付币种")
    private String coinY;
    @ApiModelProperty("兑付金额")
    private BigDecimal coinYAmount;
    @ApiModelProperty("汇率")
    private String rate;
    @ApiModelProperty("交易时间")
    private LocalDateTime tradeTime;
    @ApiModelProperty("交易")
    private String hash;
    @ApiModelProperty("交易状态")
    private String exchangeState;


}
