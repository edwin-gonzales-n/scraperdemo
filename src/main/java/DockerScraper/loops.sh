#!/usr/bin/env sh
USER_INPUT=something

while [ "$USER_INPUT" != "bye" ]
do
    echo "Please type something"
    echo "In order to get out type the word \"bye\""
    read USER_INPUT
    echo "You typed: $USER_INPUT"
    if [ "$USER_INPUT" = "bye" ]; then
        echo "bye bye!"
    fi
done