package io.onechain.hchain.domain.resp.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.info.thunes.UIInfo;
import lombok.Data;

import java.util.List;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-20 15:57:15
 */
@Data
@ApiModel("提现供应商需要填写UI")
public class PayerUIResp {

    @ApiModelProperty
    private List<UIInfo> uis;
}
