#!/usr/bin/python
import subprocess
print "Cleaning old installations..."
subprocess.call(["adb uninstall se.team05"],shell=True)
subprocess.call(["adb uninstall se.team05.test"],shell=True)

print "\nRunning tests..."
subprocess.call(["ant emma debug install test"],shell=True)

print "\nRemoving old coverage file..."
subprocess.call(["rm coverage.ec"],shell=True)

print "\nPulling in new coverage file..."
subprocess.call(["adb pull /data/data/se.team05/coverage.ec"],shell=True)

print "\nFinished testing"
subprocess.call(["say finished testing"],shell=True)

print "\nUninstalling apps..."
subprocess.call(["adb uninstall se.team05"],shell=True)
subprocess.call(["adb uninstall se.team05.test"],shell=True)
