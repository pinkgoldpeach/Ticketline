#!/usr/bin/env bash
#echo "Welcome to the funny build tool"

echo "Deleting Database file..."
rm database/db/*
echo "Database file deleted!"

echo "Starting Maven Build Chain..."
echo
echo

echo "------------------------------"
mvn clean install javadoc:aggregate > buildLog.txt
#tail -f buildLog.txt
echo "------------------------------"
echo "Build ended"

echo
echo
echo
echo

echo "Errors and warnings found in log:"
echo "---------------------------------"

grep -i "error" buildLog.txt
grep -i "warning" buildLog.txt
