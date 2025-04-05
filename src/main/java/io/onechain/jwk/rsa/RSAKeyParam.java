package io.onechain.jwk.rsa;

import lombok.Data;

/**
 * @author chiyu
 * @date 2024/12/2 15:01
 */
@Data
public class RSAKeyParam {

    private String kid;
    private String privateKey;
    private String publicKey;
}
