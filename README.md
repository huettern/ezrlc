# ezRLC  [![Circle CI](https://circleci.com/gh/noah95/pro2/tree/master.svg?style=shield&amp;circle-token=1242b68593e38e62a5369f0a6cf95e30adc733d2)](https://circleci.com/gh/noah95/pro2/tree/master)

FHNW 2nd Semester project for calculating equivalent circuits based upon touchstone impedanze measurements

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
java -jar pro2-.jar
```

### Build javadoc
```
cd java/
ant -buildfile pro2/javadoc.xml
```
To push javadoc to github pages, follow the following guide: https://vaadin.com/blog/-/blogs/host-your-javadoc-s-online-in-github

## Installing ant
### On Mac
`brew install ant`

## Installing Windowbuilder
use this link:
help > install new software > work with
http://download.eclipse.org/windowbuilder/WB/release/R201506241200-1/4.5/