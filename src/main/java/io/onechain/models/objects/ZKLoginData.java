package io.onechain.models.objects;

import java.util.List;

// 主类，对应整个 JSON 对象
public class ZKLoginData {
    private ProofPoints proofPoints;
    private IssBase64Details issBase64Details;
    private String headerBase64;

    public ProofPoints getProofPoints() {
        return proofPoints;
    }

    public void setProofPoints(ProofPoints proofPoints) {
        this.proofPoints = proofPoints;
    }

    public IssBase64Details getIssBase64Details() {
        return issBase64Details;
    }

    public void setIssBase64Details(IssBase64Details issBase64Details) {
        this.issBase64Details = issBase64Details;
    }

    public String getHeaderBase64() {
        return headerBase64;
    }

    public void setHeaderBase64(String headerBase64) {
        this.headerBase64 = headerBase64;
    }

}

