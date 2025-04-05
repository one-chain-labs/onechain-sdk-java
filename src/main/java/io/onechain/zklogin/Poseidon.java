package io.onechain.zklogin;

import java.math.BigInteger;
import java.util.*;

public class Poseidon {

    // 对应 JavaScript 中的 pow5 函数
    private static BigInteger pow5(BigInteger v) {
        BigInteger o = v.multiply(v);
        return v.multiply(o).multiply(o).mod(PoseidonConstants.F);
    }

    // 对应 JavaScript 中的 mix 函数
    private static List<BigInteger> mix(List<BigInteger> state, BigInteger[][] M) {
        List<BigInteger> out = new ArrayList<>();
        for (int x = 0; x < state.size(); x++) {
            BigInteger o = BigInteger.ZERO;
            for (int y = 0; y < state.size(); y++) {
                o = o.add(M[x][y].multiply(state.get(y)));
            }
            out.add(o.mod(PoseidonConstants.F));
        }
        return out;
    }

    public static BigInteger poseidon(BigInteger... inputs) {
        if (inputs.length == 0) {
            throw new IllegalArgumentException("poseidon-lite: Not enough inputs");
        }

        BigInteger[] c = PoseidonConstants.C_BIGINT_ARRAY[inputs.length - 1];
        BigInteger[][] m = PoseidonConstants.M_BIGINT_ARRAY[inputs.length - 1];

        int t = inputs.length + 1;
        int nRoundsF = PoseidonConstants.N_ROUNDS_F;
        int nRoundsP = PoseidonConstants.N_ROUNDS_P[t - 2];

        BigInteger[][] M = new BigInteger[t][t];

        // 假设文件中前 t*(nRoundsF + nRoundsP) 个元素是 C，后面的元素是 M
        int cLength = t * (nRoundsF + nRoundsP);
        List<BigInteger> C = new ArrayList<>(Arrays.asList(c).subList(0, cLength));
        for (int i = 0; i < t; i++) {
            System.arraycopy(m[i], 0, M[i], 0, t);
        }

        List<BigInteger> state = new ArrayList<>();
        state.add(BigInteger.ZERO);
        Collections.addAll(state, inputs);

        for (int x = 0; x < nRoundsF + nRoundsP; x++) {
            for (int y = 0; y < state.size(); y++) {
                state.set(y, state.get(y).add(C.get(x * t + y)));
                if (x < nRoundsF / 2 || x >= nRoundsF / 2 + nRoundsP) {
                    state.set(y, pow5(state.get(y)));
                } else if (y == 0) {
                    state.set(y, pow5(state.get(y)));
                }
            }
            state = mix(state, M);
        }

        return state.get(0);
    }


    // 读取文件中的常量

    public static void main(String[] args) {
        // 示例使用
        List<BigInteger> inputs = new ArrayList<>();
        inputs.add(BigInteger.ZERO);
        BigInteger result = poseidon(BigInteger.ZERO, BigInteger.ONE, new BigInteger("2"), BigInteger.valueOf(3));
        System.out.println("Poseidon result: " + result);
    }
}
