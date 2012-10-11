#!/usr/bin/env python
# updates the copyright information for all .cs files
# usage: call recursiveTraversal, with the following parameters
# parent directory, old copyright text content, new copyright text content
import os
import sys
def updateSource(filename, oldCopyright, newCopyright):
    utfStream = chr(0xef)+chr(0xbb)+chr(0xbf)
    fileData = file(filename,"r+").read()
    isUTF = False
    if (fileData.startswith(utfStream)):
        isUTF = True
        fileData = fileData[3:]
    if (oldCopyright != None):
        if (fileData.startswith(oldCopyright)):
            fileData = fileData[len(oldCopyright):]
    if not (fileData.startswith(newCopyright)):
        print "updating " + filename
        fileData = newCopyright + fileData
        if (isUTF):
            file(filename,"w").write(utfStream + fileData)
        else:
            file(filename,"w").write(fileData)

def recursiveTraversal(dir, oldCopyright, newCopyright):
    directories = os.listdir(dir)
    print "listing " + dir
    for directory in directories:
        directoryPath = os.path.join(dir,directory)
        if (os.path.isdir(directoryPath)):
            recursiveTraversal(directoryPath, oldCopyright, newCopyright)
        else:
            if (directoryPath.endswith(".xml")):
                updateSource(directoryPath, oldCopyright, newCopyright)


oldCopyright = file("old.txt","r+").read()
newCopyright = file("new.txt","r+").read()
recursiveTraversal(sys.argv[1], oldCopyright, newCopyright)
exit()