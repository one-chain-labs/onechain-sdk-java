package io.onechain.hchain.domain.req.transfer;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.Range;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lhb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("转账订单分页查询请求")
public class TransferOrderQueryPageReq extends TransferOrderQueryReq {

    @NotNull(message = "pageIndex must be not null")
    @Min(value = 1, message = "page index start with 1")
    private Integer pageIndex = 1;

    //    @ApiModelProperty("页容 区间[1, 1000]")
    @NotNull(message = "pageSize must be not null")
    @Range(min = 1, max = 1000, message = "pageSize [1, 1000]")
    private Integer pageSize = 10;


}

