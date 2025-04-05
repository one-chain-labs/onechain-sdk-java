package io.onechain.hchain.domain.req.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-13 16:41:59
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("验证收款方信息请求")
@Data
public class VerificationReq extends BaseUserReq {

    @ApiModelProperty("接收方信息-是收件人的信息")
    private Map<String, String> creditPartyIdentifier;

    @ApiModelProperty("受益人信息-是收件卡的信息")
    private Map<String, String> beneficiary;
}
