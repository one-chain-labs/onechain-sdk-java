package io.onechain.hchain.domain.resp.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-12 17:52:04
 */
@Data
@ApiModel("提现汇率响应")
public class CurrencyRateResp {

    @ApiModelProperty("目标币种")
    private String destinationCurrency;

    @ApiModelProperty("交易类型")
    private String transactionType;

    @ApiModelProperty("兑换原始币种")
    private String sourceCurrency;

    @ApiModelProperty("汇率列表")
    private List<Rate> rates;


    @ApiModel("汇率信息")
    @Data
    private static class Rate {

        @ApiModelProperty("最小付款数量，和货币面值有关")
        private String sourceAmountMin;

        @ApiModelProperty("最大付款数量，不需要考虑，因为通常不会超过最大数量")
        private String sourceAmountMax;

        @ApiModelProperty("外汇汇率 (数字使用 string 表示)")
        private String wholesaleFxRate;
    }
}
