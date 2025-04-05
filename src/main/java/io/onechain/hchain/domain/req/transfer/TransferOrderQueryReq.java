package io.onechain.hchain.domain.req.transfer;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author lhb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("查询订单请求")
public class TransferOrderQueryReq extends BaseReq {

    @ApiModelProperty("交易hash")
    private String hash;

    @ApiModelProperty("接收方地址")
    private String toAddress;

    @ApiModelProperty("转账币种")
    private String currency;

    @ApiModelProperty("状态列表:" +
            "UN_PAY:待支付" +
            "RUNNING:转账中" +
            "SUCCESS:转账成功" +
            "FAIL:转账失败" +
            "CANCEL:取消" +
            "TIMEOUT:超时")
    private List<String> statusList;

    @ApiModelProperty("开始时间 yyyy-MM-dd")
    private Long beginTime;

    @ApiModelProperty("结束时间 yyyy-MM-dd")
    private Long endTime;

    @ApiModelProperty("完成开始时间 yyyy-MM-dd")
    private Long completeBeginTime;

    @ApiModelProperty("完成结束时间 yyyy-MM-dd")
    private Long completeEndTime;

}

