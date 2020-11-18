package com.skinra.xmlToJson;

import com.skinra.xmlToJson.util.Helper;
import org.apache.commons.cli.CommandLine;

public class xml2Json
{
    public static void main(String[] args)
    {
	    try
        {
            // using common apache CLI to parse command line arguments
            CommandLine cmds = Helper.parseCommands(args);

            if ( cmds == null )
            {
                System.exit(1);
            }

            String mappingFile = Helper.getMappingFile(cmds);

            String inputXmlFile = Helper.getInputFile(cmds);

            System.out.println("-----------------INPUT----------------");
            Helper.printXml(inputXmlFile);

            ConvertXmlToJson convertXmlToJson = new ConvertXmlToJson(mappingFile, inputXmlFile);

            convertXmlToJson.xmlToJson();

            System.out.println("-----------------OUTPUT----------------");
            Helper.printJson(convertXmlToJson.getJsonStringData());

        }
	    catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}