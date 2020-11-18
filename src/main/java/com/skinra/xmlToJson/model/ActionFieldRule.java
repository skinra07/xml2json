package com.skinra.xmlToJson.model;

public class ActionFieldRule
{
    private String fieldName;
    private Integer idx;
    private MatchFieldRule match;
    private String modifyTo;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public MatchFieldRule getMatch() {
        return match;
    }

    public void setMatch(MatchFieldRule match) {
        this.match = match;
    }

    public String getModifyTo() {
        return modifyTo;
    }

    public void setModifyTo(String modifyTo) {
        this.modifyTo = modifyTo;
    }
}
