package io.onechain.models.objects;

public class IssBase64Details {
    private String value;
    private byte indexMod4;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte getIndexMod4() {
        return indexMod4;
    }

    public void setIndexMod4(byte indexMod4) {
        this.indexMod4 = indexMod4;
    }
}