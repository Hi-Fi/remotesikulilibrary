*** Settings ***
Library    RemoteSikuliLibrary
Library    Process
Library    DateTime

*** Keywords ***
Log results and kill process
	Stop Remote Server
	Terminate All Processes
	
Start test server
    ${process}    Start Process    java    -cp    ${maven.runtime.classpath}    org.robotframework.remoteserver.RemoteServer    --library    RemoteSikuliLibrary:/    --port    62022
	Set Test Variable    ${testServer}    ${process}
	
Start test application
    ${process}    Start Process    java    -cp    ${maven.test.classpath}    com.github.hi_fi.testapp.TestSwingApp
	Set Test Variable    ${testApp}    ${process}
	Sleep    5s
	Use Focused App As Region
	
Click images
    [Arguments]    ${Keyword to test}    ${button to click}    ${expected outcome}
    Run Keyword    ${Keyword to test}    ${button to click}
	Wait Until Screen Contains    ${expected outcome}    0.9
	
Close opened apps
    Close App    ${pid}
    Close App    ${pid2}