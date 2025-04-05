package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseReq;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Author: Bin
 * Date: 2025/3/11 10:56
 * @author lhb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("图片上传请求对象")
public class UploadFileReq extends BaseReq {

    @ApiModelProperty("图片base64")
    @NotEmpty
    private String base64;
}
