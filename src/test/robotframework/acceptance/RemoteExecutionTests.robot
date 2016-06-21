*** Settings ***
Resource    common.robot
Suite Setup    Set Test Image Directory    .${/}src${/}test${/}resources${/}testImages    
Test Setup    Prepare test environment for remote tests
Test Teardown    Log results and kill process
Force Tags    Remote

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
    
Test image clicks remotely
    [Template]   Click images
    Click Item    ok_button.png   ok_clicked.png
    Double Click Item    cancel_button.png    cancel_double_clicked.png
    Right Click Item    submit_button.png    submit_right_clicked.png
    
Remote typing of special keys 
	[Template]    Remote typing of special keys
	a    CTRL
	HOME    SHIFT

Remote controlling of application
    ${pid}    Open App    java -cp ${maven.test.classpath} com.github.hi_fi.testapp.TestSwingApp "First app"
    Wait Until Screen Contains    first_app_title.png    0.8
    Set Test Variable    ${pid}    ${pid}
    ${pid2}    Open App    java -cp ${maven.test.classpath} com.github.hi_fi.testapp.TestSwingApp "Focus test app"
    Wait Until Screen Contains    focus_test_app_title.png    0.8
    Set Test Variable    ${pid2}    ${pid2}
    Switch App    ${pid}
    Wait Until Screen Contains    first_app_title.png    0.8
    Switch App    ${pid2}
    Wait Until Screen Contains    focus_test_app_title.png    0.8
    Close App    ${pid2}
    Wait Until Screen Contains    first_app_title.png    0.8
    Close App    ${pid}
    
****Keyword****
Prepare test environment for remote tests
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
