@echo off
if "%1"=="" (
    echo Usage: validate.bat <file1> <file2> ...
    exit /b 1
)
java -jar target/validator-sudoku-1.0.jar %1