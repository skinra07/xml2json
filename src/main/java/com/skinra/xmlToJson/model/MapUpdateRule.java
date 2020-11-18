package com.skinra.xmlToJson.model;

import com.skinra.xmlToJson.model.types.ModeType;
import com.skinra.xmlToJson.model.types.ModifyAttributeType;

import java.util.List;

public class MapUpdateRule
{
    private String path;
    private ModeType mode;
    private ModifyAttributeType modifyType;
    private List<ActionFieldRule> actions;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ModeType getMode() {
        return mode;
    }

    public void setMode(ModeType mode) {
        this.mode = mode;
    }

    public ModifyAttributeType getModifyType() {
        return modifyType;
    }

    public void setModifyType(ModifyAttributeType modifyType) {
        this.modifyType = modifyType;
    }

    public List<ActionFieldRule> getActions() {
        return actions;
    }

    public void setActions(List<ActionFieldRule> actions) {
        this.actions = actions;
    }
}
