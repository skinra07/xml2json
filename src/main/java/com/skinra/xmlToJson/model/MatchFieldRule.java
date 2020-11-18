package com.skinra.xmlToJson.model;


import com.skinra.xmlToJson.model.types.ActivityType;
import com.skinra.xmlToJson.model.types.ConditionType;

public class MatchFieldRule
{
    private ActivityType activity;
    private ConditionType condition;
    private String pattern;
    private Integer startPosition;
    private Integer length;

    public ActivityType getActivity() {
        return activity;
    }

    public void setActivity(ActivityType activity) {
        this.activity = activity;
    }

    public ConditionType getCondition() {
        return condition;
    }

    public void setCondition(ConditionType condition) {
        this.condition = condition;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Integer getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
