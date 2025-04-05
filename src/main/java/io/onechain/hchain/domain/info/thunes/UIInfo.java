package io.onechain.hchain.domain.info.thunes;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-20 15:41:21
 */
@Data
@ApiModel("前端标准UI对象")
public class UIInfo {

    @ApiModelProperty("控件类型")
    private String type;

    @ApiModelProperty("用于显示的标签")
    private String label;

    @ApiModelProperty("用于提交的字段名，map中的key")
    private String key;

    @ApiModelProperty("默认值,也是提交的值")
    private String value;

    @ApiModelProperty("输入框正则校验")
    private String inputReg;

    @ApiModelProperty("输入框校验错误提示")
    private String inputError;

    @ApiModelProperty("确认输入框正则校验")
    private String confirmReg;

    @ApiModelProperty("确认输入框校验错误提示")
    private String confirmError;

    @ApiModelProperty("是否显示")
    private boolean show;

    @ApiModelProperty("是否提交该字段")
    private boolean submitRequired;

    @ApiModelProperty("是否必填")
    private boolean required;

    @ApiModelProperty("输入框提示文字")
    private String hintText;

    @ApiModelProperty("输入框类型")
    private String inputType;

    @ApiModelProperty("是否异步校验")
    private boolean asyncValidate;

    @ApiModelProperty("是否大写")
    private boolean uppercase;

    @ApiModelProperty("下拉框，单选框，多选框的选项")
    private List<String> options;

    @ApiModelProperty("子表单, 递归，内容和父级一样")
    private List<UIInfo> children;


    @Data
    @ApiModel("前端下拉框，单选框，多选框的选项")
    public static class Option {

        @ApiModelProperty("显示的文本")
        private String label;

        @ApiModelProperty("实际的值")
        private String value;
    }
}
