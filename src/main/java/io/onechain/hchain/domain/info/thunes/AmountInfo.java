package io.onechain.hchain.domain.info.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lhb
 */
@Data
@ApiModel("金额信息")
public  class AmountInfo {

    @ApiModelProperty("最终银行卡的到账金额 (使用 string 表示)")
    private String amount;

    @ApiModelProperty("到账的货币币种")
    private String currency;
}