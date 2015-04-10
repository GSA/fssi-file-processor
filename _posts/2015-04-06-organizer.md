---
layout: post
title:  "Organizer Guide"
---

One of the niftier features of the FSSI File Processor is its organizer. The organizer is a loosly coupled component that is only executed when a schema is also available. It was developed to help organizations standardize data formats to a schema. There are currently two organizer routines, that are defined in the config.properties file:

1. Exploder
2. Imploder

##Exploder
The exploder takes a source file and explodes it to include all data elements of a schema. It takes any fields identified in the schema that were not found in the source file and it adds it to the data set. Lets look at the below scenario.

In this scenario, we assume that the provider has provided a schema with more fields than have actually been provided by the source file. In this case, the Exploder will actually add that field and to the exported data file.

First, lets look at the following source file:

| transId  | productNm  | partNo  |
| -------- | ---------- | ------- |
| 012345   | Red Pen    | 045675  |
| 098742   | Green Pen  | 045676  |
| 623i45   | Orange Pen | 045677  |
| 023451   | Red Pen    | 045675  |


This source file contains 3 fields; transId, productNm, and partNo.


Next, lets look at the Schema for this source File

{% highlight XML %}
<schema>
	<name>example Schema</name>
 	<fields>
 		<field>
 			<name>TRANSID</name>
		</field>
 		<field>
 			<name>ORDERDT</name>
		</field>
 		<field>
 			<name>PRODUCTNM</name>
		</field>	
 		<field>
 			<name>PARTNO</name>
		</field>				
 	</fields>
</schema>    
{% endhighlight %} 	

The schema actually identifies 4 fields; transId, orderDt, ProductNm, and partNo. The Exploder will actually add the missing orderDt field to the file. Based upon the above scenario, this would be the output file format:


| TRANSID  | ORDERDT    | PRODUCTNM  | PARTNO  |
| -------- | ---------- | ---------- | ------- |
| 012345   |            | Red Pen    | 045675  |
| 098742   |            | Green Pen  | 045676  |
| 623i45   |            | Orange Pen | 045677  |
| 023451   |            | Red Pen    | 045675  |


###Why you should use the Exploder
The exploder is a great way to test and debug schemas. As your building the schema, you can run the FSSI File Processor in exploder mode and it will show you which fields mapped, and which didn't.

**NOTE: The Exploder will first organize files by fields defined in the schema, and then will add the additional fields afterwards in the order that they were found.**

##Imploder
The imploder takes a source file and implodes it to match the schema. It will add fields that are in the schema but not in the source file as well as remove fields that are not. Confused? lets look at the following scenario.

Here is our source file:

| TRANSID  | ORDERDT     | PRODUCTNM  | PARTNO  | SYSFLAG |
| -------- | ----------- | ---------- | ------- | ------- |
| 012345   |  2014-01-02 | Red Pen    | 045675  | 1       |
| 098742   |  2014-01-02 | Green Pen  | 045676  | 0       |
| 623i45   |  2014-01-02 | Orange Pen | 045677  | 1       |
| 023451   |  2014-01-02 | Red Pen    | 045675  | 0       |


The source file above consists of some system fields that we really don't care about. Their is no reason to have them in our schema, and it is possible other provides may not have that field either.

Next, lets look at the Schema for this source File

{% highlight XML %}
<schema>
	<name>example Schema</name>
 	<fields>
 		<field>
 			<name>TRANSID</name>
		</field>
 		<field>
 			<name>ORDERDT</name>
		</field>
 		<field>
 			<name>PRODUCTNM</name>
		</field>	
 		<field>
 			<name>PARTNO</name>
		</field>
 		<field>
 			<name>AGE</name>
		</field>							
 	</fields>
</schema>    
{% endhighlight %} 	


Now when we look at the schema, we not only do not see the SYSFLAG field, but we also see a new field, AGE, that was not in the source file. The Imploder will remove the SYSFLAG field, as well as add the AGE field that was not in the file.

Here is what our exported file would look like.


| TRANSID  | ORDERDT     | PRODUCTNM  | PARTNO  | AGE     |
| -------- | ----------- | ---------- | ------- | ------- |
| 012345   |  2014-01-02 | Red Pen    | 045675  |         |
| 098742   |  2014-01-02 | Green Pen  | 045676  |         |
| 623i45   |  2014-01-02 | Orange Pen | 045677  |         |
| 023451   |  2014-01-02 | Red Pen    | 045675  |         |



###Why you should use the Imploder
The imploder is designed to create a consistant, uniformed format. Regardless of what order and what fields were provided by a provider, the imploder will map it to the schema consistantly. This is extremely powerful when leveraging other data integration tools that require consistant fields and formats.



##Field Organization
Both the Imploder and Exploder will re-organize the file based upon the schema. The organization is based upon the order of the elements in the schema. If you want to have the export file organized alphabetically, you need only move the fields around in the schema.

Example source File

| TRANSID  | ORDERDT     | PRODUCTNM  | PARTNO  | AGE     |
| -------- | ----------- | ---------- | ------- | ------- |
| 012345   |  2014-01-02 | Red Pen    | 045675  |         |
| 098742   |  2014-01-02 | Green Pen  | 045676  |         |
| 623i45   |  2014-01-02 | Orange Pen | 045677  |         |
| 023451   |  2014-01-02 | Red Pen    | 045675  |         |



Example Schema
{% highlight XML %}
<schema>
	<name>example Schema</name>
 	<fields>
 		<field>
 			<name>PARTNO</name>
		</field>
 		<field>
 			<name>AGE</name>
		</field>
 		<field>
 			<name>PRODUCTNM</name>
		</field>	
 		<field>
 			<name>TRANSID</name>
		</field>
 		<field>
 			<name>ORDERDT</name>
		</field>							
 	</fields>
</schema>    
{% endhighlight %} 	



This would yield the following source File:

| PARTNO  | AGE  | PRODUCTNM  | TRANSID | ORDERDT    |
| ------- | ---- | ---------- | ------- | ---------- |
| 045675  |      | Red Pen    | 012345  | 2014-01-02 |
| 045676  |      | Green Pen  | 098742  | 2014-01-02 |
| 045677  |      | Orange Pen | 623i45  | 2014-01-02 |
| 045675  |      | Red Pen    | 023451  | 2014-01-02 |



