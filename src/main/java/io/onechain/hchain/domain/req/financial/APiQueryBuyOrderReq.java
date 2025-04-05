package io.onechain.hchain.domain.req.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.TypeBaseUserReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: Bin
 * Date: 2025/3/14 14:31
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("查询用户理财持仓中列表")
@Data
public class APiQueryBuyOrderReq extends TypeBaseUserReq {

    @ApiModelProperty(value = "分页码,第一次传空字符串,下一页根据返回值传", required = true)
    private String nextCursor;
}
