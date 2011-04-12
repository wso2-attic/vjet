REM create this file as we need start.bat for reverse engineering for build scripts tool
REM set env variables. please use / instead of \
REM use set root.home=c:/yourDir to set root.home


REM when in your local box, just comment out below code; when move to serengeti, copy below code and set as an "Execute windows batch command".
REM pushd C:\ede-3.0.9
REM call C:\ede-3.0.9\env-java5-ibm.cmd
REM popd

@echo off
set TEST_HOME=C:\views\eric_liger\nexustools\com.ebay.tools\com.ebay.tools.vjet2\tests\com.ebay.tools.vjet2.core.test\pdebuild\vjetBuild

REM set ECLIPSE_HOME=C:/ede-3.0.9/eclipse
set component.name=vjet





ant -buildfile %TEST_HOME%\build.xml -Dwindowtester.install.location=c:/wintest

pause