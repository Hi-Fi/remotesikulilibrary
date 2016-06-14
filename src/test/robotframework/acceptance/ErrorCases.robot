*** Settings ***
Resource    common.robot

*** Test Cases ***
Connection to non-existent server should fail
    Enable Debugging
    ${port}    Start Remote Server
    Stop Remote Server
    Run Keyword And Expect Error    *    Initialize Connection    http://127.0.0.1:${port}/
	
Local click to non-existent text/image should provide error
	Run Keyword And Expect Error    *    Click Item    non-existent text and image
	
Remote click to non-existent text/image should provide error
    [Teardown]    Log results and kill process
	Disable Debugging
    Start test server
    Initialize Connection    http://127.0.0.1:62022/
	Run Keyword And Expect Error    *    Click Item    non-existent text and image