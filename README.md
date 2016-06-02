# pro2  [![Circle CI](https://circleci.com/gh/noah95/pro2/tree/master.svg?style=shield&amp;circle-token=1242b68593e38e62a5369f0a6cf95e30adc733d2)](https://circleci.com/gh/noah95/pro2/tree/master)

FHNW 2nd Semester project working repository

------------

## Build status

| Branch   | Status |
|----------|--------|
| **master**   | [![Circle CI](https://circleci.com/gh/noah95/pro2/tree/master.svg?style=shield&amp;circle-token=1242b68593e38e62a5369f0a6cf95e30adc733d2)](https://circleci.com/gh/noah95/pro2/tree/master)       |
| **solver**   | [![Circle CI](https://circleci.com/gh/noah95/pro2/tree/solver.svg?style=shield&amp;circle-token=1242b68593e38e62a5369f0a6cf95e30adc733d2)](https://circleci.com/gh/noah95/pro2/tree/master)       |
| **igassist** | [![Circle CI](https://circleci.com/gh/noah95/pro2/tree/igassist.svg?style=shield&amp;circle-token=1242b68593e38e62a5369f0a6cf95e30adc733d2)](https://circleci.com/gh/noah95/pro2/tree/master)       |

------------

## Build

### Build using ant
```
cd java/
ant
cp pro2/target/archive-tmp/pro2-.jar .
```

### Build javadoc
```
cd java/
ant -buildfile pro2/javadoc.xml
```
To push javadoc to github pages, follow the following guide: https://vaadin.com/blog/-/blogs/host-your-javadoc-s-online-in-github

### using maven (deprecated)
```
mvn -f java/pro2/pom.xml clean compile assembly:single
cp java/pro2/target/*.jar .
```

## Setup build environment on ubuntu
Install maven compiler
```
sudo apt-get install maven
```
Install oracle jdk 8
```
sudo apt-get install python-software-properties
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
```
Get Repository
```
git clone https://github.com/noah95/pro2.git
cd pro2
```
Build it!
```
mvn -f java/pro2/pom.xm
```
Run it
```
java -jar pro2-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```


## Installing maven for eclipse

Help -> Install new Software -> Work with..
URL: http://download.eclipse.org/technology/m2e/releases/
Press enter
Select all and install.

## Install jFreeChart in Eclipse

Using JCommon 1.0.23 and JFreeChart 1.0.19 

1. Download [JCommon](https://sourceforge.net/projects/jfreechart/files/) and [JFreeChart](https://sourceforge.net/projects/jfreechart/files/)
2. Unzip both in any diretory
3. Run `ant javadoc` in both `jcommon-1.0.23/and` and `jfreechart-1.0.19/ant` to generate javadocs
4. In Eclipse, select Project Preferences, then choose the Java ->
Build Path -> Libraries
5. Add JARs.. and select the .jar file from the lib directory within the project
6. Expand the library entrty, double click Source attachement, and add the external folder src from the extracted archive
7. Repeat for JFreeChart

## Installing ant and maven
### On Mac
`brew install ant`
`brew install maven`

## Installing Windowbuilder
use this link:
help > install new software > work with
http://download.eclipse.org/windowbuilder/WB/release/R201506241200-1/4.5/