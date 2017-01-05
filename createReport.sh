#!/usr/bin/env bash
echo "Creating Reports with Git Inspector..."
gitinspector --file-types=java,fxml,sql,properties --since 05/02/2016 --format=html -r -m -l -T -w > report.html
echo "Report created and stored in report.html"