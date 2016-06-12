*** Settings ***
Library    RemoteSikuliLibrary

*** Test Cases ***
Connection to non-existent server should fail
    Enable Debugging
    ${port}    Start Remote Server
    Stop Remote Server
    Run Keyword And Expect Error    *    Initialize Connection    http://127.0.0.1:${port}/