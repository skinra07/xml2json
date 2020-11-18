package com.skinra.xmlToJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skinra.xmlToJson.util.Helper;

public class ConvertXmlToJson
{
    private String inputXmlFileName;
    private String mappingFileName;

    private String inputXmlData;
    private String jsonStringData;

    public ConvertXmlToJson(String mappingFile, String inputFileName)
    {
        this.mappingFileName = mappingFile;
        this.inputXmlFileName = inputFileName;
    }

    public String getInputXmlData() {
        return inputXmlData;
    }

    public void setInputXmlData(String inputXmlData) {
        this.inputXmlData = inputXmlData;
    }

    public String getJsonStringData() {
        return jsonStringData;
    }

    public void setJsonStringData(String jsonStringData)
    {
        this.jsonStringData = jsonStringData;
    }

    // This function convert xml to modified json file
    public void xmlToJson() throws Exception
    {
        inputXmlData = Helper.readFile(inputXmlFileName);

        // Using org.json library to convert xml to json string
        org.json.JSONObject jsonObject = org.json.XML.toJSONObject(inputXmlData);
        String jsonString = jsonObject.toString();

        // Using Jackson 2 API, map json string to ObjectNode
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObjectNode = objectMapper.readValue(jsonString,ObjectNode.class);

        UpdateJson updateJson = new UpdateJson(jsonObjectNode, mappingFileName);

        // This function modify json file based on mapping file
        updateJson.updateIt();

        jsonStringData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObjectNode);
    }
}
