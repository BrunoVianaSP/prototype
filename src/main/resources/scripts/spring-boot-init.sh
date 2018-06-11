#!/bin/bash

sudo yum update -y

sudo yum install -y mysql

# Removes current java version
yum list installed | grep java-1.* | awk '{print $1}' | xargs sudo yum remove -y

# Installs Java 8
sudo yum install -y java-1.8.0-openjdk.x86_6