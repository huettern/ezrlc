# pro2 [![Build Status](https://travis-ci.org/noah95/pro2.svg?branch=master)](https://travis-ci.org/noah95/pro2)

## Build
```
mvn -f java/pro2/pom.xml clean compile assembly:single
cp java/pro2/target/*.jar .
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