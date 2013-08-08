#!/bin/sh

dir="$( dirname "$(readlink -f "$0")" )"

java \
	-jar "$dir/dsl-clc.jar" 0.7.12-SNAPSHOT \
	--project-ini-path="~/.config/beepo/project.ini" \
	--dsl-path="$dir/model" \
	--output-path="$dir/out" \
	--language=scala \
	"$@"

