*** Settings ***
Library    RemoteSikuliLibrary
Library    Process
Library    DateTime

*** Keywords ***
Log results and kill process
	Stop Remote Server
	Terminate All Processes
	
Start test server
    ${process}    Start Process    java    -cp    ${maven.runtime.classpath}    org.robotframework.remoteserver.RemoteServer    --library    com.github.hi_fi.remotesikulilibrary.RemoteSikuliLibrary:/    --port    62022
	Set Test Variable    ${testServer}    ${process}
	
Start test application
    ${process}    Start Process    java    -cp    ${maven.test.classpath}    com.github.hi_fi.testapp.TestSwingApp
	Set Test Variable    ${testApp}    ${process}