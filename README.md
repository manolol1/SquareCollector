# SquareCollector

**SquareCollector is a little arcade game made with libGDX. The goal of the game is to collect as many falling squares as possible.**

## Running Instructions

### Java Executable (any Desktop OS, JVM required)

For this method, a Java Runtime needs to be installed.

1. Make sure that a Java Runtime is installed. There are lots of guides on the internet for this.
2. Download the latest .jar file from the release tab.
3. Run the .jar file with Java. Sometimes, this can be done by double-clicking the file.
   If that doesn't work, you probably need to execute it by running `java -jar SquareCollector_Version.jar` in a terminal.


### APK File (Android)

1. Download the latest .apk file from the release tab. This file must be placed somewhere on your Android device. I recommend the Downloads folder.
2. Install the apk using a file browser. **Google Play Protect might block the installation. You need to tap on "more details" and then on "install anyway".**

### Native Linux Executable

Due to an issue with some native code, the Linux executable requires a Java Runtime aswell. At that point, you could probably just use the jar file.
I will most likely look into other ways of packaging the game as native applications soon.

If you still want to use the native image:

1. Make sure that a Java Runtime is installed. There are lots of guides on the internet for this.
2. Download the latest native_linux file from the release tab.
3. You most likely need to add executing permissions to the file. On most distributions this can be done by running `sudo chmod +x Exact_File_Name`.
4. Run the file by double clicking it. If that doesn't work, you can also run it in a terminal with the following command: `./Exact_File_Name`
