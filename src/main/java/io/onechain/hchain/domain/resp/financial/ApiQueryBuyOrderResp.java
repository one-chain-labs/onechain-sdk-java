package io.onechain.hchain.domain.resp.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-04 11:33:29
 */
@Data
@ApiModel("查询用户购买订单信息")
public class ApiQueryBuyOrderResp {

    @ApiModelProperty(value = "分页码,第一次传空字符串,下一页根据返回值传")
    private String nextCursor;

    @ApiModelProperty(value = "购买订单信息")
    private List<ApiUserBuyOrderResp> orderInfoList;
}
