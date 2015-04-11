---
layout: post
title:  "Project Setup"
---

When running the FSSI File Processor, we tend to treat it like a project. It has inputs, constraints, and outputs. Knowing this, helps us make sure we have all of the pieces in place.

##Required Inputs
The FSSI File processor **Requires** the following inputs into the process:


1. **Provider Files** - Files that contain the mappings to the source files.
2. **Source Files** - The files that are to be processed.
3. **Folder Structure** - The FSSI File processor requires certain folders to exist. This can be augmented using the config.properties file, but the FSSI File processor defaults to the following directory structure:
	* /{Project Rooot Directory}/working/providers
	* /{Project Rooot Directory}/working/schemas
	* /{Project Rooot Directory}/working/srcfiles
	* /{Project Rooot Directory}/working/logs
	 */{Project Rooot Directory}/working/staged


##Optional Inputs
The following files are **Optional** to run the FSSI File Processor, but will most likely be required for whatever your trying to accomplish with the FSSI File Processor


1. **Config.Properties** - Text file that sets global variables as well as folder locations.
2. **Schemas** - XML files that act as a data dictionary is used for both Organizer and Validator routines.


##Outputs
Each source file that is processed has 2 output files.

1. **Staged File** - The staged file is the organized output file organized and in the format designated by the provider record.
2. **Log** - Each file has an associated log file that is produced in the logs directory.






