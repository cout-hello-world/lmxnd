#!/bin/bash
FILE=XBJL-1.1.1
set -x
wget https://github.com/digidotcom/XBeeJavaLibrary/releases/download/v1.1.1/${FILE}.zip -O ${FILE}.zip
unzip ${FILE}.zip
cp XBJL-1.1.1/xbjlib-1.1.1.jar XBJL-1.1.1/extra-libs/*.jar ./
rm -r ${FILE}/ ${FILE}.zip
