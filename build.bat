:begin

call mvn -B -U -e install --file "super-parent/pom.xml"
call mvn -B -U -e install --file "version-management-bouncy-castle/pom.xml"
call mvn -B -U -e install --file "version-management-bouncy-castle/pom.xml"
call mvn -B -U -e install --file "version-management-tomcat/pom.xml"
call mvn -B -U -e install --file "parent/pom.xml"
call mvn -B -U -e install --file "dependencies/pom.xml"
call mvn -B -U -e install --file "pom.xml"

pause
cls
goto :begin
