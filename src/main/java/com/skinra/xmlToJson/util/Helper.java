package com.skinra.xmlToJson.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class Helper
{

    public static String readFile(String fileName) throws IOException
    {
        StringBuffer sb = new StringBuffer();
        try (InputStream fis = new FileInputStream(fileName);
             InputStreamReader isReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader bufReader = new BufferedReader(isReader) )
        {
            bufReader.lines().forEach(line -> sb.append(line));
        }
        return sb.toString();
    }

    public static void printXml(String xmlFileName) throws Exception
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document docXml = db.parse(new FileInputStream(new File(xmlFileName)));

        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(docXml), new StreamResult(out));
        System.out.println(out.toString());
    }

    public static void printJson(String jsonString)
    {
        System.out.println(jsonString);
    }

    public static CommandLine parseCommands(String[] args)
    {
        Options options = new Options();

        options.addRequiredOption("i", "input", true, "input xml file name");
        options.addRequiredOption("m", "mapping", true, "mapping json file name");

        CommandLine cmd = null;

        try
        {
            CommandLineParser parser = new DefaultParser();
            cmd = parser.parse(options, args);
        }
        catch (ParseException e)
        {
            System.err.println("Command line Argument ERROR: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("xml2json", options, true);
        }

        return cmd;
    }

    public static String getInputFile(CommandLine cmd)
    {
        if ( cmd.hasOption('i') )
        {
            return cmd.getOptionValue('i');
        }

        return null;
    }

    public static String getMappingFile(CommandLine cmd)
    {
        if ( cmd.hasOption('m') )
        {
            return cmd.getOptionValue('m');
        }

        return null;
    }

}
