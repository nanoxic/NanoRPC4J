# NanoRPC4J

A Java Client library for interacting with rai_node 10.
Latest version: 0.2.2-SNAPSHOT

## Usage

* Make sure you have a running rai_node 10 available
* Enabled RPC: Set *rpc_enable* to true in your config.json
* Some functions require that *enable_control* is also set to true

### Release
##### JAR
Download the latest release jar and add it to your build path:
<https://oss.sonatype.org/content/groups/public/com/nanoxic/NanoRPC4J/0.2/>
##### Maven
Add the dependency:
```
<dependency>
	<groupId>com.nanoxic</groupId>
	<artifactId>NanoRPC4J</artifactId>
	<version>0.2</version>
</dependency>
```
### Snapshot
##### JAR
Download the latest snapshot jar and add it to your build path:
<https://oss.sonatype.org/content/repositories/snapshots/com/nanoxic/NanoRPC4J/0.2.2-SNAPSHOT/>
##### Maven
To use a snapshot add following repository to your pom.xml
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
	<version>0.2.2-SNAPSHOT</version>
</dependency>
```
## Examples
Some usage examples can be found here: <https://github.com/nanoxic/NanoRPC4J-examples>
## Documentation
Javadocs available: <http://blog.nanoxic.com/NanoRPC4J/0.2.2-SNAPSHOT/>
## Build
```
git clone https://github.com/nanoxic/NanoRPC4J.git
cd NanoRPC4J/
```
Either run: 
```
mvn clean install -DskipTests
```
Or:
* Rename application.properties.example to application.properties in src/test/resources
* Add the hostname and port form your node
* Add a public key, address, Wallet ID and password.
```
mvn clean install
```
## Contribute
* We accept patches!!!
* Report bugs and request features on GitHub
* Donate: xrb_351knwi97pb5fd4q3ibj5pox1m6ganm6tciz3nhkoz1y34oqb4ge5i6hjs4u

