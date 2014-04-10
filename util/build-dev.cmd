pushd ..
call mvn -P prod clean verify
dir target\*.war

DEL E:\raju\PCN\openshift\pcn\webapps\*.war
XCOPY target\*.war E:\raju\PCN\openshift\pcn\webapps /R

cd E:\raju\PCN\openshift\pcn


for /F "usebackq tokens=1,2 delims==" %%i in (`wmic os get LocalDateTime /VALUE 2^>NUL`) do if '.%%i.'=='.LocalDateTime.' set ldt=%%j
set ldt=%ldt:~0,4%-%ldt:~4,2%-%ldt:~6,2% %ldt:~8,2%:%ldt:~10,2%:%ldt:~12,6%

set date = %mydate%_%mytime%
git commit -am "Root.war deployed [%ldt%] "
git push
rhc tail pcn