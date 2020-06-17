## Appium Assignment - Kindle availability test
Using appium to automate testing of a product availablity on Amazon Shopping application.

### Prerequisites
This assignment requires following softwares to be installed on the system

- Java JDK 8+
- Android SK Tools 
- Appium Server

### Setting up environment

1. After installing above softwares, setup environment variables `JAVA_HOME` and `ANDROID_HOME`. Confirm by using below commands that these produce some output. Also, make sure `PATH` is properly configured.

Windows
```console
C:\> echo %JAVA_HOME%
C:\Program Files\Java\jdk1.8.0_211

C:\> echo %ANDROID_HOME%
C:\Program Files\android-sdk

```
Linux or MacOS
```console
$ echo $JAVA_HOME
/usr/local/bin/java

$ echo $ANDROID_HOME
/usr/lib/android-sdk
```
2. Create and launch an AVD

Go to AVD manager in Android Studio. Create a AVD with image targetting `x86-64` and platform version `10.0+`.  
Using other platform won't work because attached Amazon application targets specifically `x86-64`. Download the image if necessary. Launch the AVD when configured. First time it can take a few minutes.

3. Download and extract Assignment.zip somwhere. 

4. Launch appium server from Appium Desktop or from command depending upon how it's installed

5. Find `global.properties` and update `deviceName`, `platformVersion` to match AVD created in step `2`. Also, update `appiumServer` if running appium on different port

```properties
deviceName=Pixel_3_API_30
platformVersion=11.0
appiumServer=http://127.0.0.1:4723
```

6. Go to the root of extract directory and run below command to begin tests


Windows
```console
C:\> mvnw.cmd test
```

Linux or MacOS

```console
$ ./mvnw test
```


[![Terminal Output](https://img.youtube.com/vi/NDtPZc1wx_M/2.jpg)](https://www.youtube.com/watch?v=NDtPZc1wx_M)

