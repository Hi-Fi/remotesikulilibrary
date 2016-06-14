*** Settings ***
Library    RemoteSikuliLibrary
Library    Process

*** Test Cases ***
Connection to non-existent server should fail
	Log    ${maven.runtime.classpath}
	Log    ${maven.test.classpath}
	Start Process    java    -cp    ${maven.test.classpath}    com.github.hi_fi.testapp.TestSwingApp
	Sleep    20s
	Log results and kill process
    Enable Debugging
    ${port}    Start Remote Server
    Stop Remote Server
    Run Keyword And Expect Error    *    Initialize Connection    http://127.0.0.1:${port}/
	
Local click to non-existent text/image should provide error
	Run Keyword And Expect Error    *    Click Item    non-existent text and image
	
Remote click to non-existent text/image should provide error
    [Teardown]    Log results and kill process
	Disable Debugging
    Start Process    java    -cp    ${maven.runtime.classpath}    org.robotframework.remoteserver.RemoteServer    --library    com.github.hi_fi.remotesikulilibrary.RemoteSikuliLibrary:/    --port    62022
    Initialize Connection    http://127.0.0.1:62022/
	Run Keyword And Expect Error    *    Click Item    non-existent text and image
	
	
*** Keywords ***
Log results and kill process
	Stop Remote Server
	Terminate Process
	${result}    Get Process Result
	Log    ${result.stdout}
	Log    ${result.stderr}