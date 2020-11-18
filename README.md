xml2json: This is java application that convert xml file to json file. It uses mapping json file to intelligently make decisions about which translation (if any) to provide to a given field. This will include processes to change field names, field values and field types.

Library used: 
    commons-cli: command line parsing
    org.json: convert xml to json
    jackson json: modify json

Build/Run:
    I used intellij IDE in linux to build this application. I used intellij IDE to create executable xml2json.jar in 'out'. In order to run this application the following command is used:

Once you clone this xml2json repository, execute the following command:

java -jar xml2json.jar -i <input xml file> -m <mapping json file>

So if you execute:

java -jar out/xml2json.jar -i data/input.xml -m data/mapping.json


Result:
-----------------INPUT----------------
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<patients>
    <patient>
        <id>1234</id>
        <gender>m</gender>
        <name>John Smith</name>
        <state>Michigan</state>
        <dateOfBirth>03/04/1962</dateOfBirth>
    </patient>
    <patient>
        <id>5678</id>
        <gender>f</gender>
        <name>Jane Smith</name>
        <state>Ohio</state>
        <dateOfBirth>08/24/1971</dateOfBirth>
    </patient>
</patients>

-----------------OUTPUT----------------
{
  "patients" : {
    "patient" : [ {
      "name" : "John Smith",
      "state" : "MI",
      "patientid" : 1234,
      "age" : 55,
      "sex" : "male"
    }, {
      "name" : "Jane Smith",
      "state" : "OH",
      "patientid" : 5678,
      "age" : 45,
      "sex" : "female"
    } ]
  }
}

