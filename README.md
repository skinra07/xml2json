# xml2json 

<br/> This is java application that convert xml file to json file. It uses mapping json file to intelligently make decisions about which translation (if any) to provide to a given field. This will include processes to change field names, field values and field types.

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

** NOTE: ** Please see the mapping.txt under docs folder that describe mapping instructions.


