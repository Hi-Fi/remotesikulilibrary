*** Settings ***
Resource    common.robot
Suite Setup    Set Test Image Directory    .${/}src${/}test${/}resources${/}testImages    
Test Setup    Prepare Test Environment
Test Teardown    Log results and kill process

*** Test Cases ***
Test image wait remotely
	Wait Until Screen Contains    buttons.png

Test image click at other remote server
	Wait Until Screen Contains    buttons.png
	Click Item    ok_button.png
	Wait Until Screen Contains    ok_clicked.png
	
Remote input of text 
	Wait Until Screen Contains    buttons.png
	Input Text    Test text    empty_text_field.png
    Wait Until Screen Contains    filled_text_field.png
	
Test remote screenshot capture to other remote server
    Capture Screenshot
    
Remote typing of special keys 
	[Template]    Remote typing of special keys
	a    CTRL
	HOME    SHIFT

****Keyword****
Prepare test environment for Remote tests
    Start test server
    Initialize Connection    http://127.0.0.1:62022/
    Start test application

Remote typing of special keys    
    [Arguments]    ${keys}    @{modifiers}
    [Teardown]    Log results and kill process
    Start test application
	Wait Until Screen Contains    buttons.png
	Input Text    Test text    empty_text_field.png
	Wait Until Screen Contains    filled_text_field.png
	Type Keys    ${keys}    @{modifiers}
	Wait Until Screen Contains    filled_and_selected_text_field.png    0.9
