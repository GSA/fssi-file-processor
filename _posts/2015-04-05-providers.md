---
layout: post
title:  "Providers Guide"
---

Providers are the FSSI File Processors mechanism for routing different types of files. It is end user defined/created files that provides instructions as to how to identify a file and what schema it maps to. Here is an example of a provider record:


| PROVIDER_NAME | PROVIDER_IDENTIFIER | PROVIDER_EMAIL  | FILE_OUTPUT_TYPE | SCHEMA |
| ------------- | ------------------- | --------------- | ---------------- | ------ | 
| CMLC          | UID1                | example@gsa.gov | CSV              | CMLC_1 | 
| CMLC          | UID2                | example@gsa.gov | CSV              | CMLC_2 | 
| CMLC          | UID3                | example@gsa.gov | CSV              | CMLC_2 | 



**NOTE: The FSSI File Processor currently requires source files to be formatted so that elements of a file name are separated by an underscore (_). This means that the FSSI File Processor cannot currently handle providers identifiers with an underscore.**


##Provider File Format
Providers currently must be stored in "xlsx" documents under a specified "providers" directory. End users have the freedom to define how they build their providers files. You can have multiple provider files, and multiple records in each provider file. The system will loop through all of the files and ingest them.

##Provider Data Elements
Each provider consists of the following data elements:

###Provider Name - Required
The provider name is a required element, but it really serves as a reference point to group provider file types together. There are situations where the same provider may actually provide you several file types and formats. We use this for reporting and data management purposes, to better track which files have been submitted by which vendors.


###Provider Identifier - *Required / Unique
The provider identifier is the unique pattern in the file name that the FSSI File Processor will use to identify the file and map it to the correct provider record. It must be unique and any duplicates will be deleted at runetime. In the above example, the FSSI File Processor would be looking for files with "UID1", "UID2", and "UID3" in the file name. 


###Provider Email
The provider email is not required. It is used in the log output and can be leveraged however needed. The FSSI Data Management team scans the logs after the FSSI File Processor process runs and sends the log to the provider email provided in the provider file.


###File Output Type
The File output type determines how the FSSI File Processor exports the file. It currently accepts the following file types:

*"CSV" - Comma Separated File (.xsv)
*"XLS" - Older Microsoft Excel Format (.xls)
*"XLSX" - Newer Microsoft Excel Format (.xlsx)


###Schema
The Schema element is where you put what schema you want to organize and validate the file. It should match the schema name element in the schema file.


##Sample Providers File
A sample providers file can be found [Here](https://github.com/GSA/fssi-file-processor/blob/master/samplefiles/providers/providers.xlsx?raw=true).



