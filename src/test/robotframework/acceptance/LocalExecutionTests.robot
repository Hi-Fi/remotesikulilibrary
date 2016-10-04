*** Settings ***
Resource    common.robot
Suite Setup    Set Test Image Directory    .${/}src${/}test${/}resources${/}testImages    
Test Setup    Start test application
Test Teardown    Log results and kill process
Force Tags    Local

*** Test Cases ***
Capture screenshot locally
    Capture Screenshot
	
Test image wait locally
	Wait Until Screen Contains    buttons.png
	
Test image clicks locally
    [Template]   Click images
    Click Item    ok_button.png   ok_clicked.png
    Double Click Item    cancel_button.png    cancel_double_clicked.png
    Right Click Item    submit_button.png    submit_right_clicked.png
	
Test image click offset
    Double Click Item    ok_button.png    0.7    100
    Wait Until Screen Contains    cancel_double_clicked.png    0.9
    Double Click Item    submit_button.png    0.7    -100
    Wait Until Screen Contains    cancel_double_clicked.png    0.9
    
Local input of text 
	Wait Until Screen Contains    buttons.png
	Input Text To Field    Test text    empty_text_field.png
	Wait Until Screen Contains    filled_text_field.png
	
Local check that screen doesn't contain item
	Wait Until Screen Contains    buttons.png
	Input Text To Field    Test text    empty_text_field.png
	Wait Until Screen Does Not Contain    empty_text_field.png    0.8

Local typing of special keys 
	[Template]    Local typing of special keys
	a    CTRL
	HOME    SHIFT

Local input and copy text to clipboard 
    ${fill text}    Set Variable    Test text
	Wait Until Screen Contains    buttons.png
	Input Text To Field    ${fill text}    empty_text_field.png	
	Wait Until Screen Contains    filled_text_field.png
	Input Text To Field    ${fill text}    filled_text_field.png	
	Type Keys    a    CTRL
	Type Keys    c    CTRL
	${text}    Get Clipboard Content
	Should Be Equal    ${text}    ${fill text}${fill text}

Local input and replace of text 
	Wait Until Screen Contains    buttons.png
	Input Text To Field    Test text    empty_text_field.png
	Wait Until Screen Contains    filled_text_field.png	
	Replace Text In Field    ${EMPTY}    filled_text_field.png
	Wait Until Screen Contains    empty_text_field.png
	
Local call should be made if server connection is closed
    ${port}    Start Remote Server
    Initialize Connection    http://127.0.0.1:${port}/
    Stop Remote Server
    Capture Screenshot
    
Local controlling of application
    [Setup]    NONE
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
    [Teardown]    Close opened apps

****Keyword****  
Local typing of special keys    
    [Arguments]    ${keys}    @{modifiers}
    [Teardown]    Log results and kill process
    Start test application
	Wait Until Screen Contains    buttons.png
	Input Text To Field    Test text    empty_text_field.png
	Wait Until Screen Contains    filled_text_field.png
	Type Keys    ${keys}    @{modifiers}
	Wait Until Screen Contains    filled_and_selected_text_field.png    0.9
