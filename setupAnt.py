#!/usr/bin/python
import subprocess

print "Setting upp ant build files..."
subprocess.call(["android update project -p ./"],shell=True)
subprocess.call(["android update test-project -m ../ -p tests"],shell=True)

#print "Building main project..."
#subprocess.call(["ant clean debug"], shell=True)

