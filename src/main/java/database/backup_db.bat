@echo off
setlocal

:: Configuration settings
set DB_HOST=localhost
set DB_USER=root
set DB_PASS=root
set DB_NAME=transactions_database

set BACKUP_PATH=C:\Projects\FamilyBudgetManagementSystem\src\main\java\database\backup
set TIMESTAMP=%date:~10,4%%date:~4,2%%date:~7,2%-%time:~0,2%%time:~3,2%
set BACKUP_FILE=%BACKUP_PATH%\%DB_NAME%_%TIMESTAMP%.sql

:: MySQL dump command
set MYSQLDUMP="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqldump.exe"

echo.
echo Backing up %DB_NAME% to %BACKUP_FILE%
echo.

:: Create backup directory if not exist
if not exist %BACKUP_PATH% (
    mkdir %BACKUP_PATH%
)

:: Run mysqldump
%MYSQLDUMP% -h %DB_HOST% -u %DB_USER% -p%DB_PASS% %DB_NAME% > %BACKUP_FILE%

echo.
echo Backup finished
echo.

endlocal