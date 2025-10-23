@echo off
set JAVA_HOME=C:\Users\Usuario\AppData\Local\Programs\Eclipse Adoptium\jdk-21.0.4.7-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

javac -cp ".;lib/mysql-connector-j-9.4.0.jar" src\view\*.java src\model\*.java src\controller\*.java src\dao\*.java src\service\*.java
java -cp ".;lib/mysql-connector-j-9.4.0.jar;src" view.TelaLogin
pause
