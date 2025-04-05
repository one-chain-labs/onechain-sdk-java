package io.onechain.hchain.domain.resp.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Author: Bin
 * Date: 2025/3/11 10:54
 */
@Data
@ApiModel(value = "收款方式-详情查询")
public class ReceiveMethodDetailResp {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户did")
    private String did;

    @ApiModelProperty(value = "汇旺收款人")
    private String userName;

    @ApiModelProperty(value = "汇旺收款账号")
    private String honeAccount;

    @ApiModelProperty(value = "汇旺收款二维码")
    private String honeQrCode;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}
