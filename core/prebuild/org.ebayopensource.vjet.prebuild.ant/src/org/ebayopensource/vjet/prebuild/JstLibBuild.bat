
@echo off

if "%1" == "" GOTO ERROR
if "%2" == "" GOTO ERROR
if "%3" == "" GOTO ERROR
if "%4" == "" GOTO ERROR

set jardir=%1
set srcdir=%2
set outputdir=%3
set libname=%4
set classpath=%jardir%\DsfBase.jar;%jardir%\DsfPrebuild.jar;%jardir%\org.eclipse.jdt.core_3.2.3.v_686_R32x.jar;%jardir%\VRhinoLib.jar;%jardir%\org.eclipse.equinox.common_3.2.0.v20060603.jar;%jardir%\org.eclipse.core.runtime_3.2.0.v20060603.jar;%jardir%\org.eclipse.osgi_3.2.2.R32x_v20070118.jar;%jardir%\org.eclipse.core.resources_3.2.2.R32x_v20061218.jar;%jardir%\org.eclipse.core.jobs_3.2.0.v20060603.jar;%jardir%\org.eclipse.core.contenttype_3.2.0.v20060603.jar;%jardir%\org.eclipse.equinox.preferences_3.2.1.R32x_v20060717.jar;%jardir%\JsNativeResource.jar;%jardir%\VjoJavaLib.jar

@echo Classpath jar dir: %jardir%

@echo Src dir: %srcdir%

@echo Jst lib output dir: %outputdir%

@echo Jar lib name: %libName%

@echo Classpath: %classpath%

java -cp .;%classpath% com.ebay.dsf.jstlib.build.JstBuildLibCmd %srcdir% %outputdir% %libname%

GOTO END

:ERROR
@echo Usage: JstLibBuild {ClassPathJarDir} {SrcDir} {OutputDir} {LibName}
@echo {ClassPathJarDir} - Directory of the jar files to be included in the classpath
@echo {SrcDir} - Directory of Java and JS source files to be built into the library
@echo {OutputDir} - Output directory of the Jst library
@echo {LibName} - File name of the Jst library


:END
