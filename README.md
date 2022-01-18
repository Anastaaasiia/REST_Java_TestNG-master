# task1
Please, pull the code or download it like .zip archive 
To run a tests, please use MAVEN (I've been using version 3.6.1) or you can run them from NetBeans/Eclipse/IntelliJ IDEA
To run from Command Line, please use command: mvn test -PGitHubScenarious
To run from NetBeans/Eclipse/IntelliJ IDEA, please right click on project -> Test (or Alt+F6 for NetBeans)
If you want to run just one test (not all of them in one time), please uncomment the line: //groups={"stop"} inside test scripts that you don't want to run.
But keep in mind that the order of tests execution is important. For example, You can't run scenario4 without run scenario1 firstly.
 
