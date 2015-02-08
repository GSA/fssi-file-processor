#! /bin/bash

export PATH="/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/apache-maven/apache-maven-3.2.5/bin:/usr/local/coverity/cov-analysis-macosx-7.6.0/bin"

mvn clean
cov-build --dir cov-int mvn -DskipTests=true compile

tar czvf fssiFileProcessor.tgz cov-int

curl --form token=H5Pdt8p-P2Tm46ep2tdnqg \
  --form email=davidlarrimore@gmail.com \
  --form file=@./fssiFileProcessor.tgz \
  --form version="9.0.1" \
  --form description="" \
  https://scan.coverity.com/builds?project=GSA%2Ffssi-file-processor
  
rm fssiFileProcessor.tgz
rm -R cov-int

mvn clean

