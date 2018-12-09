@echo off 

echo ---Deploying site 

rename d:\home\site\repository\sampleExam\target\*.war ROOT.war
copy d:\home\site\repository\sampleExam\target\*.war %DEPLOYMENT_TARGET%\webapps\

