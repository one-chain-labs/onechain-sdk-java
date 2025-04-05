package io.onechain.models.objects;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author chiyu
 * @date 2025/3/13 10:37
 */
@Data
@Builder
public class ZkServiceReqeust {

    private final String keyClaimName = "sub";

    private BigInteger maxEpoch;

    private String jwtRandomness;

    private String extendedEphemeralPublicKey;

    private String jwt;

    private String salt;

}
