:begin


call mvn -B -U -e install --file "super-parent/pom.xml" ^
 -DcreateChecksum=true ^
 -Dmaven.wagon.http.ssl.insecure=true ^
 -Dmaven.wagon.http.ssl.allowall=true ^
 -Dmaven.wagon.http.ssl.ignore.validity.dates=true

 call mvn -B -U -e install --file "version-management-bouncy-castle/pom.xml" ^
 -DcreateChecksum=true ^
 -Dmaven.wagon.http.ssl.insecure=true ^
 -Dmaven.wagon.http.ssl.allowall=true ^
 -Dmaven.wagon.http.ssl.ignore.validity.dates=true

 call mvn -B -U -e install --file "version-management-groovy/pom.xml" ^
 -DcreateChecksum=true ^
 -Dmaven.wagon.http.ssl.insecure=true ^
 -Dmaven.wagon.http.ssl.allowall=true ^
 -Dmaven.wagon.http.ssl.ignore.validity.dates=true

 call mvn -B -U -e install --file "version-management-tomcat/pom.xml" ^
 -DcreateChecksum=true ^
 -Dmaven.wagon.http.ssl.insecure=true ^
 -Dmaven.wagon.http.ssl.allowall=true ^
 -Dmaven.wagon.http.ssl.ignore.validity.dates=true

 call mvn -B -U -e install --file "parent/pom.xml" ^
 -DcreateChecksum=true ^
 -Dmaven.wagon.http.ssl.insecure=true ^
 -Dmaven.wagon.http.ssl.allowall=true ^
 -Dmaven.wagon.http.ssl.ignore.validity.dates=true


 call mvn -B -U -e install --file "pom.xml" ^
 -DcreateChecksum=true ^
 -Dmaven.wagon.http.ssl.insecure=true ^
 -Dmaven.wagon.http.ssl.allowall=true ^
 -Dmaven.wagon.http.ssl.ignore.validity.dates=true


pause
cls
goto :begin
