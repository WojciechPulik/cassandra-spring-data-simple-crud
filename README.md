"# cassandra-spring-data" 


## Technologies
Project is created with:

*  Java version: 1.8

*  Spring Boot version: 2.4.2 (with Spring Web MVC, Spring Data Cassandra)

*  Apache Cassandra version 3.11.9

## API
Provided endpoints:

*  POST /api/message - adds message to database

*  POST /api/send - prints send messages content of all messages with given 'magic_number' and removes them from database

*  GET /api/messages/{emailValue} - returns messages containing 'emailValue' 

## Setup
To run this project you must have Apache Cassandra and CQLSH installed locally.

Start your Cassandra database and CQLSH, then:


```
CREATE KEYSPACE “wpulik”

use wpulik;
 
CREATE TABLE message(
   id timeuuid PRIMARY KEY,
   email text,
   title text,
   content text,
   magic_number int
);
```

You need to create a custom index for 'email' field that has options with mode: CONTAINS along with analyzer_class to make case_sensitive effective.


```
CREATE CUSTOM INDEX idx_email ON wpulik.message (email) 
USING 'org.apache.cassandra.index.sasi.SASIIndex' 
WITH OPTIONS = {
	'mode': 'CONTAINS', 
	'analyzer_class': 'org.apache.cassandra.index.sasi.analyzer.NonTokenizingAnalyzer', 
	'case_sensitive': 'false'};
```
We also need to create custom index for 'magic_number':

```
CREATE CUSTOM INDEX fn_contains ON wpulik.message (magic_number) 
USING 'org.apache.cassandra.index.sasi.SASIIndex'
WITH OPTIONS = { 'mode': 'SPARSE' };
```
To run Spring Boot project go to the project folder:


```
mvn install

mvn spring-boot:run
```







