call runcrud
if "%ERRORLEVEL%" == "0" goto runbrowser
echo.
echo RUNCRUD calling error
goto fail

:runbrowser
start http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo.
echo Cannot run the browser.
goto fail

:fail
echo.
echo There were errors. Breaking work.

:end
echo.
echo Work is finished.