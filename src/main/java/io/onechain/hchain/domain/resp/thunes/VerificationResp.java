package io.onechain.hchain.domain.resp.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-13 16:46:45
 */
@ApiModel("收方信息验证结果")
@Data
public class VerificationResp {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("账户状态")
    private String accountStatus;
}
