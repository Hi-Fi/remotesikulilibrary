# Remote Sikuli Library for Robot Framework
Introduction
------------
Robot framework library to utilize Sikuli's UI testing capabilities either remotely or locally.

* More information about this library can be found in the
  [Keyword Documentation](http://search.maven.org/remotecontent?filepath=com/github/hi-fi/remotesikulilibrary/0.0.4/remotesikulilibrary-0.0.4.html).
* For keyword completion in RIDE you can download this
  [Library Specs](http://search.maven.org/remotecontent?filepath=com/github/hi-fi/remotesikulilibrary/0.0.4/remotesikulilibrary-0.0.4.xml)
  and place it in your PYTHONPATH.

Usage
-----
If you are using the [robotframework-maven-plugin](http://robotframework.org/MavenPlugin/) you can
use this library by adding the following dependency to 
your pom.xml:

    <dependency>
        <groupId>com.github.hi-fi</groupId>
        <artifactId>remotesikulilibrary</artifactId>
        <version>0.0.4</version>
    </dependency>
    
With Gradle, library can be use by importing it as a dependency in build.gradle. Because of Sikuli's own pom-file, dependencies have to be introduces following way:

    switch(System.getProperty('os.name').toLowerCase().split()[0]) {
        case 'windows':
            compile group: 'com.sikulix', name: 'sikulixlibswin', version: '1.1.0'
            break
        case 'linux':
            compile group: 'com.sikulix', name: 'sikulixlibslux', version: '1.1.0'
            break
        case 'mac':
            compile group: 'com.sikulix', name: 'sikulixlibsmac', version: '1.1.0'
            break
        default:
            throw new Exception('Unknown OS')
    }
    runtime('com.github.hi-fi:remotesikulilibrary:0.0.4') {
        exclude module: '${sikulix.libs}'
    }
    
Library import in Robot tests can be done with:

|                    |                                 |
| ----------------   | ------------------------------- | 
| *** Settings ***   |                                 |                 
| Library            | RemoteSikuliLibrary             |   
   
Example
-------
Usage examples can be found at [Tests-folder](/src/test/robotframework/acceptance).

Demo can be run by cloning this repository and running acceptance tests with Maven:

    mvn verify
    
Demo uses own simple Swing application, which is included in tests.

Notes
-----

Debug-level logging is quite exhaustive at remote calls, because base64 encoded images are written to logs, too. 
