package io.onechain.hchain.domain.info.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lhb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("来源信息")
public class SourceInfo extends AmountInfo {

    @ApiModelProperty("国家/地区代码")
    private String countryIsoCode;
}