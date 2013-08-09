#!/bin/sh

dir="$( dirname "$(readlink -f "$0")" )"
out="$dir/out"

rm -rf "$out"

java \
  -jar "$dir/dsl-clc.jar" 0.7.12 \
  --project-ini-path="~/.config/beepo/project.ini" \
  --dsl-path="$dir/model" \
  --namespace=hr.element.beepo \
  --output-path="$out" \
  --language=scala \
	"$@"

