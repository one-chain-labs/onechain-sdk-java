package io.onechain.hchain.domain.resp.wallet;

import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lhb
 */
@Data
public class CurrencyChainResp {

    @ApiModelProperty("链")
    private String chain;

    @ApiModelProperty("币种列表")
    private List<CurrencyInfo> currencyList;
}
