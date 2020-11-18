package com.skinra.xmlToJson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skinra.xmlToJson.model.ActionFieldRule;
import com.skinra.xmlToJson.model.MatchFieldRule;
import com.skinra.xmlToJson.model.types.ConditionType;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatchFieldProcess
{

    // store 50 states with abbr for 'stateAbbr' activity
    private static final Map<String, String> stateAbbr = Stream.of(
            new AbstractMap.SimpleEntry<>("ALABAMA", "AL"),
            new AbstractMap.SimpleEntry<>("ALASKA", "AK"),
            new AbstractMap.SimpleEntry<>("ARKANSAS", "AR"),
            new AbstractMap.SimpleEntry<>("CALIFORNIA", "CA"),
            new AbstractMap.SimpleEntry<>("COLORADO", "CO"),
            new AbstractMap.SimpleEntry<>("CONNECTICUT", "CT"),
            new AbstractMap.SimpleEntry<>("DELAWARE", "DE"),
            new AbstractMap.SimpleEntry<>("GEORGIA", "GA"),
            new AbstractMap.SimpleEntry<>("HAWAII", "HI"),
            new AbstractMap.SimpleEntry<>("IDAHO", "ID"),
            new AbstractMap.SimpleEntry<>("ILLINOIS", "IL"),
            new AbstractMap.SimpleEntry<>("INDIANA", "IN"),
            new AbstractMap.SimpleEntry<>("KANSAS", "KS"),
            new AbstractMap.SimpleEntry<>("KENTUCKY", "KY"),
            new AbstractMap.SimpleEntry<>("LOUISIANA", "LA"),
            new AbstractMap.SimpleEntry<>("MAINE", "ME"),
            new AbstractMap.SimpleEntry<>("MARYLAND", "MD"),
            new AbstractMap.SimpleEntry<>("MICHIGAN", "MI"),
            new AbstractMap.SimpleEntry<>("MINNESOTA", "MN"),
            new AbstractMap.SimpleEntry<>("MISSISSIPPI", "MS"),
            new AbstractMap.SimpleEntry<>("MISSOURI", "MO"),
            new AbstractMap.SimpleEntry<>("MONTANA", "MT"),
            new AbstractMap.SimpleEntry<>("NEBRASKA", "NE"),
            new AbstractMap.SimpleEntry<>("NEVADA", "NV"),
            new AbstractMap.SimpleEntry<>("NEW HAMPSHIRE", "NH"),
            new AbstractMap.SimpleEntry<>("NEW JERSEY", "NJ"),
            new AbstractMap.SimpleEntry<>("MEW MEXICO", "NM"),
            new AbstractMap.SimpleEntry<>("NEW YORK", "NY"),
            new AbstractMap.SimpleEntry<>("NORTH CAROLINA", "NC"),
            new AbstractMap.SimpleEntry<>("NORTH  DAKOTA", "ND"),
            new AbstractMap.SimpleEntry<>("OHIO", "OH"),
            new AbstractMap.SimpleEntry<>("OKLAHOMA", "OK"),
            new AbstractMap.SimpleEntry<>("OREGON", "OR"),
            new AbstractMap.SimpleEntry<>("PENNSYLVANIA", "PA"),
            new AbstractMap.SimpleEntry<>("RHODE ISLAND", "RI"),
            new AbstractMap.SimpleEntry<>("SOUTH CAROLINA", "SD"),
            new AbstractMap.SimpleEntry<>("TENNESSEE", "TN"),
            new AbstractMap.SimpleEntry<>("TEXAS", "TX"),
            new AbstractMap.SimpleEntry<>("UTAH", "UT"),
            new AbstractMap.SimpleEntry<>("VERMONT", "VT"),
            new AbstractMap.SimpleEntry<>("VIRGINIA", "VA"),
            new AbstractMap.SimpleEntry<>("WASHINGTON", "WA"),
            new AbstractMap.SimpleEntry<>("WEST VIRGINIA", "WV"),
            new AbstractMap.SimpleEntry<>("WISCONSIN", "WI"),
            new AbstractMap.SimpleEntry<>("WYOMING", "WY"),
            new AbstractMap.SimpleEntry<>("GUAM", "GU"),
            new AbstractMap.SimpleEntry<>("PUERTO RICO", "PR"),
            new AbstractMap.SimpleEntry<>("VIRGIN ISLANDS", "VI")
            )
            .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

    public static void ReplaceActivity(ActionFieldRule action, JsonNode jsonNodes)
    {
        MatchFieldRule matchFieldRule = action.getMatch();

        if (ConditionType.equals == matchFieldRule.getCondition() )
        {
            // idx = -1 means apply updates for all JsonNodes in array
            if ( action.getIdx() == -1 )
            {
                for( JsonNode jsonNode : jsonNodes )
                {
                    updateJsonValueWithPattern(jsonNode, action);
                }
            }
            else
            {
                // Apply only to specific array[idx]
                JsonNode jsonObject = jsonNodes.get(action.getIdx());

                updateJsonValueWithPattern(jsonObject, action);
            }
        }
    }

    // update type
    public static void ConvertActivity(ActionFieldRule action, JsonNode jsonNodes)
    {
        if (action.getIdx() == -1)
        {
            for( JsonNode jsonNode : jsonNodes )
            {
                updateJsonValueWithType(jsonNode,action);
            }
        }
        else
        {
            JsonNode jsonObject = jsonNodes.get(action.getIdx());

            updateJsonValueWithType(jsonObject,action);
        }

    }

    // update json value
    public static void updateJsonValue(JsonNode jsonObject, ActionFieldRule action)
    {
        if ( jsonObject == null || jsonObject.isNull() )
        {
            return;
        }

        Iterator<String> fieldNames = jsonObject.fieldNames();

        while (fieldNames.hasNext())
        {
            String fieldName = fieldNames.next();

            if (fieldName.equals(action.getFieldName()))
            {
                if (action.getModifyTo() != null && !action.getModifyTo().isEmpty())
                {
                    ((ObjectNode) jsonObject).put(fieldName, action.getModifyTo());
                    break;
                }
            }
        }
    }

    public static void updateJsonValueWithPattern(JsonNode jsonObject, ActionFieldRule action)
    {
        if ( jsonObject == null || jsonObject.isNull() )
        {
            return;
        }

        Iterator<String> fieldNames = jsonObject.fieldNames();

        while(fieldNames.hasNext())
        {
            String fieldName = fieldNames.next();

            if ( fieldName.equals(action.getFieldName()) )
            {
                JsonNode fieldValue = jsonObject.get(fieldName);

                if ( fieldValue.asText().equals(action.getMatch().getPattern() ))
                {
                    if (action.getModifyTo() != null && !action.getModifyTo().isEmpty())
                    {
                        ((ObjectNode) jsonObject).put(fieldName, action.getModifyTo());
                        break;
                    }
                }
            }
        }
    }

    public static void updateJsonValueWithType(JsonNode jsonObject, ActionFieldRule action)
    {
        if ( jsonObject == null || jsonObject.isNull() )
        {
            return;
        }

        Iterator<String> fieldNames = jsonObject.fieldNames();

        while (fieldNames.hasNext())
        {
            String fieldName = fieldNames.next();

            if (fieldName.equals(action.getFieldName()))
            {
                // we can add more check for more types in future.
                if ( ConditionType.integerType.equals(action.getMatch().getCondition()) )
                {
                    ((ObjectNode) jsonObject).put(fieldName, jsonObject.get(fieldName).asInt());
                    break;
                }
                if ( ConditionType.doubleType.equals(action.getMatch().getCondition()) )
                {
                    ((ObjectNode) jsonObject).put(fieldName, jsonObject.get(fieldName).asDouble());
                    break;
                }
            }
        }
    }

    public static void updateJsonValueWithStateAbbr(JsonNode jsonObject, ActionFieldRule action)
    {
        if ( jsonObject == null || jsonObject.isNull() )
        {
            return;
        }

        Iterator<String> fieldNames = jsonObject.fieldNames();

        while (fieldNames.hasNext())
        {
            String fieldName = fieldNames.next();

            if (fieldName.equals(action.getFieldName()))
            {
                JsonNode fieldValue = jsonObject.get(fieldName);

                String valueStr = fieldValue.asText();

                String abbr = stateAbbr.get(valueStr.toUpperCase());

                if ( abbr != null )
                {
                    ((ObjectNode) jsonObject).put(fieldName, abbr);
                    break;
                }
            }
        }
    }

    // update value with stateAbbr activity
    public static void stateAbbreviationActivity(ActionFieldRule action, JsonNode jsonNodes)
    {
        if (action.getIdx() == -1)
        {
            for (JsonNode jsonNode : jsonNodes)
            {
                updateJsonValueWithStateAbbr(jsonNode, action);
            }
        }
        else
        {
            JsonNode jsonObject = jsonNodes.get(action.getIdx());

            updateJsonValueWithStateAbbr(jsonObject, action);
        }
    }
}
