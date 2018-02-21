#!/bin/sh
branch=`git status | grep 'On branch'| awk '{print $3 " "}'` 
echo $branch
git add .
if [ -n "$1" ];then
git commit -m $1
else
date=`date`
git commit -m "$date"
fi
git push origin $branch
