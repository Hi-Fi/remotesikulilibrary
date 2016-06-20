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
	
Local input of text 
	Wait Until Screen Contains    buttons.png
	Input Text    Test text    empty_text_field.png
	Wait Until Screen Contains    filled_text_field.png

Local typing of special keys 
	[Template]    Local typing of special keys
	a    CTRL
	HOME    SHIFT

Local input and replace of text 
	Wait Until Screen Contains    buttons.png
	Input Text    Test text    empty_text_field.png
	Wait Until Screen Contains    filled_text_field.png	
	Replace Text In Field    ${EMPTY}    filled_text_field.png
	Wait Until Screen Contains    empty_text_field.png
	
Local call should be made if server connection is closed
    ${port}    Start Remote Server
    Initialize Connection    http://127.0.0.1:${port}/
    Stop Remote Server
    Capture Screenshot

****Keyword****
Local typing of special keys    
    [Arguments]    ${keys}    @{modifiers}
    [Teardown]    Log results and kill process
    Start test application
	Wait Until Screen Contains    buttons.png
	Input Text    Test text    empty_text_field.png
	Wait Until Screen Contains    filled_text_field.png
	Type Keys    ${keys}    @{modifiers}
	Wait Until Screen Contains    filled_and_selected_text_field.png    0.9
