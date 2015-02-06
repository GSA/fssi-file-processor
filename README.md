#FSSI File Processor

Traditional Data warehouses and Data Integration platforms provide a great way to manage structured data. But what happens when your data is semi-structured? or unstructured? This application aims to "Assist" with turning semi-structured and unstructured data into structured data.

This J2SE Application, which is used by the FSSI Data Management team, pre-processes Source files for validation and data ingest into the FSSI Data Management Data Warehouse.

##Overview
The FSSI File Processor at its core can accept information from a multitude of different file types, process that data as standard java objects, and then export that data in any format requested (Pretty cool, right?).

The FSSI File Processor has 4 main objects that server as inputs to the process; 

1. [**Providers**](/working/providers/): Providers tell you how a file should be processed. All Schemas, Source Files, and Data Mappings must be represented by a provider.
2. [**Source Files**](/working/srcfiles/): These are the files that are provided to you by the provider.
3. [**Schemas**](/working/schemas/): are the building blocks for file validation. Regardless of whether you are provided a .csv, .xlsx, .txt, etc... data should be validated based upon its "Schema." Schemas are not required, but are important to the FSSI Data Management team for validating data quality and consistancy.

##Requirements
The FSSI File processor itself is run on files. It uses the above objects to run itself. At a minimum you must have [Providers](../working/providers/) and [Source Files](../working/srcfiles/). 


##Configuration
The FSSI File Processor is configured in 2 main ways:

1. Global configuration via the ##config.properties file
2. Local configuration via ##Providers files.


##Running the FSSI File Processor
TBD

##Point of Contact
If you have any questions on this repository, please feel free to contact [strategicsourcing@gsa.gov](mailto:strategicsourcing@gsa.gov).



<a href="https://scan.coverity.com/projects/4177">
  <img alt="Coverity Scan Build Status"
       src="https://scan.coverity.com/projects/4177/badge.svg"/>
</a>