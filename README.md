# NanoRPC4J

A Java Client library for interacting with rai_node 10.
Latest version: 0.0.5-SNAPSHOT

## Usage

* Make sure you have a running rai_node 10 available
* Enabled RPC: Set *rpc_enable* to true in your config.json
* Some functions require that *enable_control* is also set to true

### JAR

Download the latest jar file and add it to your build path:
<https://oss.sonatype.org/content/repositories/snapshots/com/nanoxic/NanoRPC4J/0.0.5-SNAPSHOT/>

### Maven

No releases yet. To use a snapshot add following repository to your pom.xml
```
<repository>
	<id>oss-sonatype</id>
	<name>oss-sonatype</name>
	<url>https://oss.sonatype.org/content/repositories/snapshots/</url>
	<snapshots>
		<enabled>true</enabled>
	</snapshots>
</repository>
```
Then add the dependency itself:
```
<dependency>
	<groupId>com.nanoxic</groupId>
	<artifactId>NanoRPC4J</artifactId>
	<version>0.0.5-SNAPSHOT</version>
</dependency>
```

## Examples

Some usage examples can be found here: <https://github.com/nanoxic/NanoRPC4J-examples>

## Documentation

Javadocs available: <http://blog.nanoxic.com/NanoRPC4J/javadoc/>

## Contribute

* We accept patches!!!
* Report bugs and request features on GitHub
* Donate: xrb_351knwi97pb5fd4q3ibj5pox1m6ganm6tciz3nhkoz1y34oqb4ge5i6hjs4u
