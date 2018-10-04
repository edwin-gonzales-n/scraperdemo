#!/usr/bin/env bash
X=5
echo This is a test from Intellij
echo Please enter your first name
read USER_INPUT
echo "I am going to create a file for you with your name and an extension of sh"
touch ${USER_INPUT}_file.sh
echo "file ${USER_INPUT}_file.sh created in directory $PWD"
echo "A quote is \", backslach is \\. backtick is \`."
echo "A few spaces are     ;dollar is \$. \$X is ${X}."


