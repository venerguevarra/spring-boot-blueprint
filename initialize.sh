# Author: Vener Guevarra
#!/bin/bash

read -p "Project Name: " projectName
read -p "Package Name: " packageName
read -p "Author  Name: " authorName

companyName="`echo $packageName | cut -d '.' -f 2`"
systemId="`echo $packageName | cut -d '.' -f 3`"

echo "project name: $projectName"
echo "package     : com.$companyName.$systemId"

rm -rf ../$projectName

mkdir -p ../$projectName/
cp -r ./src ../$projectName/
cp ./pom.xml ../$projectName/
cp ./README.md ../$projectName/

mv ../$projectName/src/main/java/com/starter/service ../$projectName/src/main/java/com/starter/$systemId
mv ../$projectName/src/main/java/com/starter ../$projectName/src/main/java/com/$companyName
mv ../$projectName/src/test/java/com/starter/service ../$projectName/src/test/java/com/starter/$systemId
mv ../$projectName/src/test/java/com/starter ../$projectName/src/test/java/com/$companyName

sed -i '' -e "s/{projectName}/$projectName/" "../$projectName/README.md"
sed -i '' -e "s/{authorName}/$authorName/" "../$projectName/README.md"
sed -i '' -e "s/starterGroupId/com.$companyName/" "../$projectName/pom.xml"
sed -i '' -e "s/starterArtifactId/$projectName/" "../$projectName/pom.xml"
sed -i '' -e "s/applicationClass/com.$companyName.$systemId.Application/" "../$projectName/pom.xml"

sed -i '' -e "s/{projectName}/$projectName/" "../$projectName/src/main/resources/application.yml"
sed -i '' -e "s/artifactPoolName/$projectName/" "../$projectName/src/main/resources/application.yml"
sed -i '' -e "s/{projectName}/$projectName/" "../$projectName/src/main/resources/logback.xml"

find "../$projectName/src/main/java/com/$companyName/$systemId" -type f -exec sed -i '' -e "1s/package com.starter.service;/package com.$companyName.$systemId;/" {} \;

find "../$projectName/src/main/java/com/$companyName/$systemId" -type f -exec sed -i '' -e "1s/package com.starter.service;/package com.$companyName.$systemId;/" {} \;
find "../$projectName/src/main/java/com/$companyName/$systemId/exception" -type f -exec sed -i '' -e "1s/package com.starter.service.exception;/package com.$companyName.$systemId.exception;/" {} \;
find "../$projectName/src/main/java/com/$companyName/$systemId/filter" -type f -exec sed -i '' -e "1s/package com.starter.service.filter;/package com.$companyName.$systemId.filter;/" {} \;
find "../$projectName/src/main/java/com/$companyName/$systemId/http" -type f -exec sed -i '' -e "1s/package com.starter.service.http;/package com.$companyName.$systemId.http;/" {} \;
find "../$projectName/src/main/java/com/$companyName/$systemId/model" -type f -exec sed -i '' -e "1s/package com.starter.service.model;/package com.$companyName.$systemId.model;/" {} \;
find "../$projectName/src/main/java/com/$companyName/$systemId/repository" -type f -exec sed -i '' -e "1s/package com.starter.service.repository;/package com.$companyName.$systemId.repository;/" {} \;
find "../$projectName/src/main/java/com/$companyName/$systemId/resource" -type f -exec sed -i '' -e "1s/package com.starter.service.resource;/package com.$companyName.$systemId.resource;/" {} \;

find "../$projectName/src/main/java/com/$companyName/$systemId" -type f -exec sed -i '' -e "s/com.starter.service/com.$companyName.$systemId/" {} \;
find "../$projectName/src/main/java/com/$companyName/$systemId/exception" -type f -exec sed -i '' -e "s/com.starter.service/com.$companyName.$systemId/" {} \;
find "../$projectName/src/main/java/com/$companyName/$systemId/filter" -type f -exec sed -i '' -e "s/com.starter.service/com.$companyName.$systemId/" {} \;
find "../$projectName/src/main/java/com/$companyName/$systemId/http" -type f -exec sed -i '' -e "s/com.starter.service/com.$companyName.$systemId/" {} \;
find "../$projectName/src/main/java/com/$companyName/$systemId/model" -type f -exec sed -i '' -e "s/com.starter.service/com.$companyName.$systemId/" {} \;
find "../$projectName/src/main/java/com/$companyName/$systemId/repository" -type f -exec sed -i '' -e "s/com.starter.service/com.$companyName.$systemId/" {} \;
find "../$projectName/src/main/java/com/$companyName/$systemId/resource" -type f -exec sed -i '' -e "s/com.starter.service/com.$companyName.$systemId/" {} \;

find "../$projectName/src/test/java/com" -type f -exec sed -i '' -e "s/com.starter.service/com.$companyName.$systemId/" {} \;

retval=$?
if [ $retval -eq 0 ]; then
    echo "$projectName project successfully created."
else 
    echo "Failed to setup $projectName project. No project directory created."
    rm -rf ../$projectName
fi