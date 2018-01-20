package com.diyishuai.notebook.bean;

public class HbaseData {
    private byte[] family;
    private byte[] qualifier;
    private byte[] value;

    public HbaseData() {
    }

    public HbaseData(byte[] family, byte[] qualifier, byte[] value) {
        this.family = family;
        this.qualifier = qualifier;
        this.value = value;
    }

    public byte[] getFamily() {
        return family;
    }

    public void setFamily(byte[] family) {
        this.family = family;
    }

    public byte[] getQualifier() {
        return qualifier;
    }

    public void setQualifier(byte[] qualifier) {
        this.qualifier = qualifier;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}
