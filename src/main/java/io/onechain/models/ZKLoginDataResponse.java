package io.onechain.models;

import io.onechain.models.objects.ZKLoginData;
import lombok.Data;

// 主类，对应整个 JSON 对象
@Data
public class ZKLoginDataResponse {

    private ZKLoginData zkLoginData;

    private String error;

}

