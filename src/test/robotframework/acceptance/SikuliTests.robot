*** Settings ***
Library    RemoteSikuliLibrary
Library    Process
Library    DateTime

*** Test Cases ***
Capture screenshot locally
    Enable Debugging
    Capture Screenshot
     
Test image click locally
    Enable Debugging
    ${imageLocation}    Capture Screenshot
	Set Test Image Directory    .
	Click Item    ${imageLocation}
	Capture Screenshot
	
Local call should be made if server connection is closed
    Enable Debugging
    ${port}    Start Remote Server
    Initialize Connection    http://127.0.0.1:${port}/
    Stop Remote Server
    Capture Screenshot
	
Test remote screenshot capture to other remote server
    [Teardown]    Log results and kill process
    Start Process    java    -cp    ${maven.runtime.classpath}    org.robotframework.remoteserver.RemoteServer    --library    com.github.hi_fi.remotesikulilibrary.RemoteSikuliLibrary:/    --port    62022
    Enable Debugging
    Initialize Connection    http://127.0.0.1:62022/
	Enable Debugging
    Capture Screenshot
	
Test image click at other remote server
    [Teardown]    Log results and kill process
    Start Process    java    -cp    ${maven.runtime.classpath}    org.robotframework.remoteserver.RemoteServer    --library    com.github.hi_fi.remotesikulilibrary.RemoteSikuliLibrary:/    --port    62022
    Enable Debugging
    Initialize Connection    http://127.0.0.1:62022/
	Enable Debugging
    ${imageLocation}    Capture Screenshot
	Set Test Image Directory    .
	Click Item    ${imageLocation}
	Capture Screenshot
	
	
*** Keywords ***
Log results and kill process
	Terminate Process
	${result}    Get Process Result
	Log    ${result.stdout}
	Log    ${result.stderr}