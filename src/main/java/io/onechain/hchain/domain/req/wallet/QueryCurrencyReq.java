package io.onechain.hchain.domain.req.wallet;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author killer at 2024/12/27 16:14
 */
@ApiModel("币种列表请求")
@Data
@Builder
public class QueryCurrencyReq {

    @ApiModelProperty("最后更新时间")
    private LocalDateTime lastUpdateTime;

    @ApiModelProperty("币种")
    private String currency;

    @ApiModelProperty("是否查询链信息,默认false")
    private Boolean isLoadChain = false;
}
