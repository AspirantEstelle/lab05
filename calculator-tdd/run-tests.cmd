@echo off
cd /d "%~dp0"
title Lab05 - Python unittest
echo Command: python -m unittest -v test_FourBasicOpt
echo.
if exist "%LOCALAPPDATA%\Programs\Python\Python314\python.exe" (
    "%LOCALAPPDATA%\Programs\Python\Python314\python.exe" -m unittest -v test_FourBasicOpt
) else (
    py -m unittest -v test_FourBasicOpt
)
echo.
echo Capture this console with all 8 tests and the final OK visible.
pause
