package io.onechain.hchain.domain.req.common;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
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
@ApiModel(value = "分页请求")
public class BasePageReq extends BaseUserReq {

    @NotNull(message = "pageIndex must not be null")
    @Min(value = 1, message = "page index start with 1")
    @ApiModelProperty(value = "当前页", required = true)
    private Integer pageIndex = 1;

    @NotNull(message = "pageSize must not be null")
    @Range(min = 1, max = 1000, message = "pageSize [1, 1000]")
    @ApiModelProperty(value = "页大小", required = true)
    private Integer pageSize = 10;
}
