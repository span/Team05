#!/usr/bin/python
import subprocess
subprocess.call(["ant emma debug install test"],shell=True)
subprocess.call(["rm coverage.ec"],shell=True)
subprocess.call(["adb pull /data/data/se.team05/coverage.ec"],shell=True)
subprocess.call(["say finished testing"],shell=True)