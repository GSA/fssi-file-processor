[![Build Status](https://travis-ci.org/GSA/fssi-file-processor.svg?branch=master)](https://travis-ci.org/GSA/fssi-file-processor)
<a href="https://scan.coverity.com/projects/4177">
  <img alt="Coverity Scan Build Status"
       src="https://scan.coverity.com/projects/4177/badge.svg"/>
</a>

#FSSI File Processor

Traditional Data warehouses and Data Integration platforms provide a great way to manage structured data. But what happens when your data is semi-structured? or unstructured? This application aims to "Assist" with turning semi-structured and unstructured data into structured data.

This J2SE Application, which is used by the FSSI Data Management team, pre-processes Source files for validation and data ingest into the FSSI Data Management Data Warehouse.

You can find more on the General Services Administration (GSA) Federal Strategic Sourcing Initiative (FSSI) by going here: [https://strategicsourcing.gov/](https://strategicsourcing.gov/)

##Overview
The FSSI File Processor at its core can accept information from a multitude of different file types, process that data as standard java objects, and then export that data in any format requested (Pretty cool, right?).

The FSSI File Processor has 4 main objects that server as inputs to the process; 

1. **Config File** (config.properties): The config file is used to set global configuration options like directories and logging modes.
2. **Providers**: Providers tell you how a file should be processed.
3. **Schemas**: are the building blocks for file validation. Regardless of whether you are provided a .csv, .xlsx, .txt, etc... data should be validated based upon its "Schema." Schemas are not required, but are important to the FSSI Data Management team for validating data quality and consistancy.
4. **Source Files**: These are the files that are provided to you by the provider.

There are 2 main outputs of the FSSI File Processor

1. **Staged Files**: The FSSI file processor ingests data as java objects, and then exports them in whatever format you wish. When using a schema, only files that have no Fatal errors are staged.
2. **Logs**: Who doesn't like a good run down of what happened? The logs provide detailed information on the file and is the primary method of viewing the results of schema validation.

##Requirements
The FSSI File processor itself is run on files that you provide it. It uses the above objects to run itself. At a minimum you must have a valid Config File, a working directory, and at least 1 source file.


##Configuration
The FSSI File Processor is configured in 2 main ways:

1. Global configuration via the **config.properties** file
2. Local configuration via **Providers** files.


##Running the FSSI File Processor
If using a production release, you may simply use the provided run.sh (Unix/Linux) or run.bat (Windows) file.

If you are directly downloading the current head you can use maven to package the code (mvn package) and then run the following command:

    java -Xms512m -Xmx1028m -jar ./target/fssi-fileprocessor-*-jar-with-dependencies.jar

##Point of Contact
If you have any questions on this repository, please feel free to contact [strategicsourcing@gsa.gov](mailto:strategicsourcing@gsa.gov).
