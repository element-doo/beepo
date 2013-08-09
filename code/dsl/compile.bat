@echo off
setlocal

java -jar ^
	"%~dp0dsl-clc.jar" 0.7.12 ^
	--project-ini-path="~/.config/beepo/project.ini" ^
	--dsl-path="%~dp0model" ^
	--namespace=hr.element.beepo ^
	--output-path="%~dp0out" ^
  --language=scala
	%*
