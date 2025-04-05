package io.onechain.hchain.domain.resp.common;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-04 15:03:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "分页结果")
public class PageResult<T> {

    @ApiModelProperty("分页数据")
    private List<T> rows;

    @ApiModelProperty("总页数")
    private long totalNum;

    @ApiModelProperty("页大小")
    private int pageSize;

    @ApiModelProperty("当前页")
    private int pageIndex;
}
