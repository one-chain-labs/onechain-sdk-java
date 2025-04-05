package io.onechain.hchain.domain.resp.wallet;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author killer at 2024/12/27 15:54
 */
@ApiModel("币种VO")
@Data
public class CurrencyInfo {

    @ApiModelProperty("币种分类 1:法币 2 数字货币")
    private Integer currencyType;

    @ApiModelProperty("币种代码")
    private String currency;

    @ApiModelProperty("币种名称")
    private String name;

    @ApiModelProperty("币种图标")
    private String pic;

    @ApiModelProperty("USD汇率")
    private BigDecimal exchangeRate;

    @ApiModelProperty("显示精度")
    private Integer displayDecimals;

    @ApiModelProperty("计算精度")
    private Integer calculateDecimals;

    @ApiModelProperty("币种符号")
    private String symbol;

    @ApiModelProperty("币种地址")
    private String coinType;
}
