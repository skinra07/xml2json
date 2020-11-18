package com.skinra.xmlToJson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skinra.xmlToJson.model.ActionFieldRule;
import com.skinra.xmlToJson.model.MapUpdateRule;
import com.skinra.xmlToJson.model.MappingRules;
import com.skinra.xmlToJson.model.MatchFieldRule;
import com.skinra.xmlToJson.model.types.ActivityType;
import com.skinra.xmlToJson.model.types.ModeType;
import com.skinra.xmlToJson.model.types.ModifyAttributeType;
import com.skinra.xmlToJson.util.Helper;

import java.util.List;

public class UpdateJson
{
    private MappingRules rulesObject;
    private ObjectNode objectJsonNode;
    private String mappingFileName;

    public UpdateJson(ObjectNode objectJsonNode, String mappingFileName)
    {
        this.objectJsonNode = objectJsonNode;
        this.mappingFileName = mappingFileName;
    }

    public void updateIt() throws Exception
    {
        convertMappingRulesToObject();

        for( MapUpdateRule updateRule : rulesObject.getUpdates() )
        {
            // Currently it only support update mode type. In future we can include 'add' and 'delete' mode type too
            if ( updateRule.getMode().equals(ModeType.update) )
            {
                processUpdateMode(updateRule);
            }
            else
            {
                throw new UnsupportedOperationException("Currently Only mode = update is supported");
            }
        }
    }

    // This function serialize mapping file to MappingRules object.
    private void convertMappingRulesToObject() throws Exception
    {
        String jsonMapRule = Helper.readFile(mappingFileName);
        ObjectMapper mapper = new ObjectMapper();
        rulesObject = mapper.readValue(jsonMapRule, MappingRules.class);
    }

    private void processUpdateMode(MapUpdateRule updateRule) throws Exception
    {
        // This return list of JsonNode with key and value. And value is NOT Object or Array.
        JsonNode modifyNodes = objectJsonNode.at(updateRule.getPath());

        if ( modifyNodes == null )
        {
            throw new Exception(" Unable to find Json node object for path: " + updateRule.getPath());
        }

        processActionField(updateRule, modifyNodes);
    }

    // This function modify Json field and value based on 'actions' in mapping rules
    private void processActionField(MapUpdateRule updateRule, JsonNode nameNodes)
    {
        List<ActionFieldRule> actionFields = updateRule.getActions();

        for( ActionFieldRule actionField : actionFields)
        {
            if ( actionField.getMatch() != null )
            {
                processMatchField(updateRule.getModifyType(), actionField, nameNodes);
            }
            else if (updateRule.getModifyType() == ModifyAttributeType.name)
            {
                modifyNameField(actionField, nameNodes);
            }
            else if ( updateRule.getModifyType() == ModifyAttributeType.value)
            {
                modifyValueField(actionField, nameNodes);
            }
        }
    }

    // Modify field name
    private void modifyNameField(ActionFieldRule action, JsonNode nameNodes)
    {
        if ( action.getIdx() == -1)
        {
            for( JsonNode nameNode : nameNodes )
            {
                ObjectNode object = (ObjectNode) nameNode;

                if ( action.getModifyTo() != null && !action.getModifyTo().isEmpty() )
                {
                    object.set(action.getModifyTo(), object.get(action.getFieldName()));

                    object.remove(action.getFieldName());
                }
            }
        }
        else
        {
            ObjectNode object = (ObjectNode) nameNodes.get(action.getIdx());

            if ( action.getModifyTo() != null && !action.getModifyTo().isEmpty() )
            {
                object.set(action.getModifyTo(), object.get(action.getFieldName()));

                object.remove(action.getFieldName());
            }
        }
    }

    // Modify field value
    private void modifyValueField(ActionFieldRule action, JsonNode jsonNodes)
    {
        if ( action.getIdx() == -1)
        {
            for( JsonNode jsonNode : jsonNodes )
            {
                MatchFieldProcess.updateJsonValue(jsonNode, action);
            }
        }
        else
        {
            JsonNode jsonObject = jsonNodes.get(action.getIdx());

            MatchFieldProcess.updateJsonValue(jsonObject, action);
        }
    }

    // Modify based on 'match' field
    private void processMatchField(ModifyAttributeType modifyType, ActionFieldRule actionFieldRule, JsonNode jsonNodes)
    {
        if ( modifyType == ModifyAttributeType.name )
        {
            throw new UnsupportedOperationException("At this time, match field is not supported for modifyType=name");
        }

        MatchFieldRule match = actionFieldRule.getMatch();

        // for mode type = 'value' with 'match' field criteria
        if ( modifyType == ModifyAttributeType.value )
        {
            if (match.getActivity() == ActivityType.replace)
            {
                MatchFieldProcess.ReplaceActivity(actionFieldRule, jsonNodes);
            }
            else if (match.getActivity() == ActivityType.stateAbbr)
            {
                MatchFieldProcess.stateAbbreviationActivity(actionFieldRule, jsonNodes);
            }
        }
        // for mod type = 'type' with 'match' field criteria
        else if ( modifyType == ModifyAttributeType.type )
        {
            if (match.getActivity() == ActivityType.convert)
            {
                MatchFieldProcess.ConvertActivity(actionFieldRule, jsonNodes);
            }
        }
    }
}
