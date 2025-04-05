package io.onechain.models.objects;

import java.util.List;

public class ProofPoints {
    private List<String> a;
    private List<List<String>> b;
    private List<String> c;

    public List<String> getA() {
        return a;
    }

    public void setA(List<String> a) {
        this.a = a;
    }

    public List<List<String>> getB() {
        return b;
    }

    public void setB(List<List<String>> b) {
        this.b = b;
    }

    public List<String> getC() {
        return c;
    }

    public void setC(List<String> c) {
        this.c = c;
    }
}
