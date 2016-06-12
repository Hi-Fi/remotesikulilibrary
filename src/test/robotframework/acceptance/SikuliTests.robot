*** Settings ***
Library    RemoteSikuliLibrary
Library    Process
Library    String

*** Test Cases ***
Capture screenshot locally
    Enable Debugging
    Capture Screenshot
     
Local call should be made if server connection is closed
    Enable Debugging
    ${port}    Start Remote Server
    Initialize Connection    http://127.0.0.1:${port}/
    Stop Remote Server
    Capture Screenshot
	
Test remote screenshot capture to other remote server
    [Teardown]    Terminate Process
    Start Process    java    -cp    ${classpath}    org.robotframework.remoteserver.RemoteServer    --library    com.github.hifi.remotesikulilibrary.RemoteSikuliLibrary:/    --port    62022
    Enable Debugging
    Initialize Connection    http://127.0.0.1:62022/
	Enable Debugging
    Capture Screenshot