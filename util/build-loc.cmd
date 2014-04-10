pushd ..
call mvn -P loc clean verify
dir target\*.war
popd
