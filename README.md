
![tag jdk17](https://img.shields.io/badge/tag-jdk17-orange.svg)
![technology Java](https://img.shields.io/badge/technology-Java-olive.svg)
![technology Maven](https://img.shields.io/badge/technology-Maven-green.svg)
![technology Selenium](https://img.shields.io/badge/technology-Selenium-green.svg)

This repository contains a web ui automation example project.
The tests were develop in Java with Maven using Selenium for the user interface validations.

The project use WebDriverManager, this is an open-source Java library that carries out the management of the drivers
required by Selenium WebDriver.
The pom.xml file located under src folder is an XML file that contains information about the project and configuration
details used by Maven to build the project.

Tests use the **Page object model** pattern. The advantage of the model is that it reduces code duplication and improves
test maintenance.
Under this model, for each web page in the application, there should be a corresponding Page Class. This Page class will
identify the WebElements of that web page and also contains page methods which perform operations on those WebElements.


**Prerequisites**

Java 17 installed

Intellij Idea + TestNG plugin

Google Chrome and Firefox browsers

Docker with : -selenium/node-chrome-debug
              -selenium/node-firefox-debug
              -selenium/hub

**Installation**

1-Clone repository

2-Open project and import maven dependencies. Webdrivermanager helps to

download executables automatically.

**Test Execution**

a) To run the tests select xml file under src > test > java > suite folder, right click and
then click on run.

b) To run using docker-compose : 

    - Open the terminal and go to automation-example folder
    - Run "docker-compose up -d" command to run the hub and the nodes
    - Select allTestsRemote.xml file under suite folder, right click and then click on run.
    - Note that parameter name="type" with value="remote" should be set instead of value="local"

c) To run tests using Maven from command line:

    - Open the terminal and go to automation-example folder
    - Run the following command:
    
        mvn verify -PallTests

    - This will execute all tests defined in allTests.xml using the maven-failsafe-plugin
    - Reports will be generated under /target/failsafe-reports/emailable-report.html

**Report**

After running the tests using mvn the failsafe-reports will be displayed under
/target/failsafe-reports/emailable-report.html
also there is another report provider by Extent-Report library under the reports' folder.


 