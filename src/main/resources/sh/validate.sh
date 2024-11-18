#!/bin/bash

if [ $# -lt 1 ]; then
    echo "Usage: ./validate.sh <file1> <file2> ..."
    exit 1
fi

# Run the Java program with the provided files
java -jar validator-sudoku-1.0.jar "$@"