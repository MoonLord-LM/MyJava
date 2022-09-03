:begin

call mvn -B -U -e install -DcreateChecksum=true --file "super-parent/pom.xml"
call mvn -B -U -e install -DcreateChecksum=true --file "version-management-bouncy-castle/pom.xml"
call mvn -B -U -e install -DcreateChecksum=true --file "version-management-groovy/pom.xml"
call mvn -B -U -e install -DcreateChecksum=true --file "version-management-tomcat/pom.xml"
call mvn -B -U -e install -DcreateChecksum=true --file "parent/pom.xml"
call mvn -B -U -e install -DcreateChecksum=true --file "pom.xml"

pause
cls
goto :begin
