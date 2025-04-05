package io.onechain.hchain.domain.req.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.domain.req.common.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-21 09:56:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("thunes提现分页查询接口")
public class QueryPageReq extends BasePageReq {

    private Boolean showThunes;
}
