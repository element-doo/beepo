@echo off
setlocal

set DIR=%~dp0
set OUT=%DIR%../scala/model-generated/src/main

java -jar "%DIR%dsl-clc.jar" 0.7.12 ^
	--project-ini-path="~/.config/beepo/project.ini" ^
	--dsl-path="%~dp0model" ^
	--namespace=hr.element.beepo ^
	--output-path="%OUT%" ^
  --language=scala ^
	%*
