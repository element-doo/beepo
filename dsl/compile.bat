@echo off
setlocal

set GENERATED=%~dp0src\generated

if exist "%GENERATED%\java" (
	rmdir /S /Q "%GENERATED%\java" > NUL
)

java -jar ^
	"%~dp0dsl-clc.jar" 0.7.11 ^
	--project-ini-path="%GENERATED%\resources\project.ini" ^
	--dsl-path="%~dp0dsl" ^
	--namespace=model ^
	--output-path="%GENERATED%" ^
	%*

