package io.onechain.hchain.domain.req.common;

import lombok.Data;


/**
 * 请求基类
 * @author Lhb
 * @version v0.0.1 2024-01-20 17:11:09
 */
@Data
public class HeaderRequest {


    private String tokenId;

    private String accessToken;

}
