---
layout: post
title:  "Validator Guide"
---

Schemas are the centerpoint of the FSSI File Processor. They contain all of the business rules for the validator organizer functions which are the meat and potatoes of the whole system.

At runtime, Schemas are loaded into the system. Then, when a source file is being processed, if a schema is associated (via Provider object), it is personalized and then binded to the source file object. This allows the FSSI File Processor to be more flexible in 

leveraging the principles of [CSV Lint](http://csvlint.io) and based upon the [JSON Table Schema RFC](http://dataprotocols.org/json-table-schema/), The FSSI File Processor Schemas are written in [XML](http://en.wikipedia.org/wiki/XML).


##Features
Here are some of the things that set our schemas and validation process apart from other tools and services:

1. **Multiple Error Levels**: Constraints and Types can be configured to fail as a Warning, Error, or Fatal. This helps teams better define which issues are imporant. 
2. **Effective Date Based Validations**: As business rules change for providers over time, your schemas can too. Constraints can be configured with effective dates that will actually tailor the schema at runtime to only enforce validations relevant to that file.
3. **Alias' and Field Name Mappings**: Sometimes, field names change over time. In other scenarios, different providers call the same field 2 different things. With Alias', you can actually create a data dictionary that will map fields to the correct field.

##Basic Structure of a Schema


	<schema>
     	<fields>
     		<field 1>
     			<constraints>
     				<constraint 1></constraint 1>
     				<constraint n></constraint n>
 				</constraints>
 			</field 1>
     		<field 2></field 2>
     		<field 3></field 3>
     		<field n></field n>
     		etc...
 		</fields>
 	</schema>     			   			


As you can see, there is a parent->child relationship between the schemas, fields, and constraints objects. There is no limit as to the number of fields or constraints.


##Schema Attributes
---
The schema consists of the following top level elements:

###Name (**Required**)

Schemas are linked to source files by their name, which is documented in the provider file. Schema names should be unique. If 2 schemas have the same name, the FSSI-File-Processor will ignore the second. Name is a **required** field.

	<name>example_schema</name>

###Provider

In prior versions of the FSSI File Processor, providers and schemas were loosly coupled. After some thought, it made sense to not depend upon this field to physically map a schema to a provider. This is still, however, a valid field that could be useful for documentation purposes.

	<provider>example_provider</provider>


###Version
This is a user defined field. The system does not currently leverage it. This is still, however, a valid field that could be useful for documentation purposes.

	<version>1.0</version>

###Title
This is a user defined field. The system does not currently leverage it. This is still, however, a valid field that could be useful for documentation purposes.

	<title>1.0</title>


###Fields
Fields defines all of the field objects within the schema. Please see "the Schema Data Elements" section for more information. Fields are **required**.

	<fields>
		<field>
			<name>field 1</name>
		</field>
		<field>
			<name>field 2</name>		
		</field>
	</fields>



##Field Attributes
---

			<description>Each order should be identified by a unique order identification number</description>
			<constraints>
				<required level="error">true</required>
				<maxLength level="error">30</maxLength>
			</constraints>
			<alias>Ord_Num</alias>


###Name - **Required**
This is the standard or dictionary name for the field. Used for mapping to the source file as well as outputting to a staged file. Though the system does not enforce strict naming standards, it behooves the user to develop a consistant naming convention amongst fields. This is a **Required** field.

	<name>PRODUCT_DESCRIPTION</name>



###Title
This is the informal or long name for the field. Used for mapping to the source file as well as outputting to a staged file. The system does not currently leverage this field. This is still, however, a valid field that could be useful for documentation purposes.

	<title>Product Description</title>



###Description
This is a user defined field. The system does not currently leverage this field. This is still, however, a valid field that could be useful for documentation purposes.

	<description>Description of the product as provided by the supplier.</description>


###Type
A field’s type attribute is a string indicating the data type of this field.

The Type field is **Optional**. If a type is not defined, then the type is defaulted to **any** (AKA **string**). Additionally, if type is not defined, format ignored.

The system recognizes the following data types.


####Any
Default format when another is not applied.

	<type>any</type>


####String
A string (of arbitrary length). Example: "Hello World"

	<type>string</type>

####Number
A number including floating point numbers. Example: 123456.99

	<type>number</type>

####Integer
An integer. No floating point numbers. If floating points are provided, this will fail validation. Example: 123456.

	<type>integer</type>


####Date
a date. This MUST be in ISO6801 format "YYYY-MM-DD" or, if not, a format field must be provided describing the structure. Date validation is strict and format should be based upon [Joda Time Format](http://joda-time.sourceforge.net/apidocs/org/joda/time/format/DateTimeFormat.html). Example: 2014-02-28

**NOTE: There is currently an ancillary type validation in place that will fail records that are + or - 50 years from system date. This was designed to compensate for ussues**

	<type>date</type>



##Example Schemas
Example schemas can be found in the [samplefiles/schema folder](https://github.com/GSA/fssi-file-processor/tree/master/samplefiles/schemas) in Github. 



##XSD
When creating your schema, we highly recommend performing XSD Schema Validation (which can be easily done through a website like [http://www.xmlvalidation.com/](http://www.xmlvalidation.com/)). The current version of the FSSI File Processor XSD can be found here: [https://github.com/GSA/fssi-file-processor/blob/master/src/main/resources/schema.xsd](https://github.com/GSA/fssi-file-processor/blob/master/src/main/resources/schema.xsd).

This will allow you to quickly root out issues with the schema.


