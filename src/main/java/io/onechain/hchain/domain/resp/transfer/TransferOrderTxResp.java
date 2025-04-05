package io.onechain.hchain.domain.resp.transfer;

import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lhb
 */
@Data
public class TransferOrderTxResp {

    @ApiModelProperty("订单状态 " +
            "UN_PAY:待支付," +
            " RUNNING:转账中, " +
            "SUCCESS:转账成功, " +
            "FAIL:转账失败, " +
            "CANCEL:取消, " +
            "TIMEOUT:超时")
    public String status;

    @ApiModelProperty("交易单号")
    private String hash;


}
