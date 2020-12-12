# The Pawn Game - U.S. Chess Center #
## By Tara Bobinac ##

**Credits**: This application uses Open Source components. You can find the source code of this open source project along with license information below. I acknowledge and am grateful to developer https://github.com/amir650 for their contributions to open source. The merging of different parts of the code and especially the GUI are all thanks to them.

**Project**: BlackWidow-Chess https://github.com/amir650/BlackWidow-Chess

**License (LGPL-2.1)**: https://github.com/git/git/blob/master/LGPL-2.1

The Pawn Game is a Java application that allows two students on different computers to play the U.S. Chess Center's Pawn Game.

**1. To download the game**: 

Press the file labeled "PawnGame.jar" and press "Download" in the upper righthand corner. Your computer will say that this type of file can harm your device because it can't read its contents, but press "Keep" to download it anyway; it will not harm your computer. The game should now be in your Downloads folder.


**2. Downloading Java**

  If you do not already have Java installed on your computer, visit this link: https://www.oracle.com/java/technologies/javase-jdk15-downloads.html
   
   - If you have a **Mac/Apple computer**: Press the download link across from "macOS Installer".
  
   - If you have a **Windows computer**: Press the download link across from "Windows x64 Installer".
   
   - If you have a **Linux OS**: Press the download link across from "Linux x64 Debian Package". 
  
  **If for any reason the later steps do not work and you have a Linux OS, download the link across from "Linux x64 RPM Package" or "Linux ARM 64 RPM Package".**
   
   Java should now be in your Downloads folder.
   
   Go to your downloads folder and double click the file that starts with "jdk-15.0.1...". Follow the prompts on the screen. You now have Java installed on your computer!
   
   
**3. Opening your network for connection**
   
  Go to https://github.com/kaklakariada/portmapper. Scroll down to where it says "Download Now". Your computer will say that this type of file can harm your device because it can't read its contents, but press "Keep" to download it anyway; it will not harm your computer. The program should now be in your downloads folder.
   
   - If you have a **Mac/Apple computer**: Make sure the file labeled "portmapper-2.2.1.jar" is still in your Downloads folder. Keep it there! Go to your Applications (the rocket button at the bottom of the screen) and type "Terminal" into the search bar. It looks like a black box with a white arrow and line. 

   When you open it, type in:
   
      cd Downloads
   
   and hit enter. Then type in:
   
      java -jar portmapper-2.2.1.jar
   
   and press enter.
      
  - If you have a **Windows computer**: Go into your Downloads folder and double click the program titled "portmapper-2.2.1.jar".
      
  - If you have a **Linux OS**: Make sure the file labeled "portmapper-2.2.1.jar" is still in your Downloads folder. Keep it there! Go to your Applications (on the bottom left of your screen) and type "Terminal" into the search bar. It looks like a black box with a white arrow and line. 
  
   When you open it, type in:
   
      cd Downloads
   
   and hit enter. Then type in:
   
      java -jar portmapper-2.2.1.jar
   
   and press enter.
      
  With the program now open, under the bottom left section titled "Router" press "Connect". After a few seconds, the button should say "Disconnect". Leave it like that.
   
  Under the section that says **Port mapping presets** press "Create". For **Description type** in "Pawn Game". Do not type anything into **Remote host**. **Interal Client** can remain as is. Press **Add range**. The protocol should be TCP and for **External ports** type in 3000 on the left side and 3001 on the right side. If the button labeled "External ports are equal to internal ports" is not checked, check it. The same numbers should automatically pop up under **Internal ports**. Press "Add" and then press "Save". 
   
   "Pawn Game" should be listed under **Port mapping presets**. Press "Pawn Game" and then press "Use". Two **Port mappings** should show up under the top section. Click the one that has **External port** and **Internal port** labeled as 3001. Then press "Remove" and then press "Update". 
   
   You can now close the program. 
   
   If you ever want to remove this network connection, open the program, press "Connect", press the "Pawn Game" under **Port mappings** and press "Remove" then "Update" and close the program. Remember that if you do this and you want to play the Pawn Game again, you will have to follow all of the parts of step 3 again except for the downloading.
   
   **If for any reason any of these steps do not work, visit the link at the beginning of step 3 and scroll down until you find your problem. There you will find various solutions to the issue you are facing.**
   
   
**4. Time to play the game!**
   
  - If you have a **Mac/Apple computer**: Make sure the file labeled "PawnGame.jar" is still in your Downloads folder. Keep it there! Go to your Applications (the rocket button at the bottom of the screen) and type "Terminal" into the search bar. It looks like a black box with a white arrow and line. 
  
   When you open it, type in:
   
      cd Downloads
   
   and hit enter. Then type in:
   
      java -jar PawnGame.jar
   
   and press enter. Your game should be working!
   
  - If you have a **Windows computer**: Find the folder where you have the file PawnGame.jar (it should be in your Downloads folder unless you moved it). Double click it. Your game should be working!
   
  - If you have a **Linux OS**: Make sure the file labeled "PawnGame.jar" is still in your Downloads folder. Keep it there! Go to your Applications (bottom left of your screen) and type "Terminal" into the search bar. It looks like a black box with a white arrow and line. 
  
   When you open it, type in:
   
      cd Downloads
   
   and hit enter. Then type in: 
      
      java -jar PawnGame.jar
      
   and press enter. Your game should be working!
   
   
   Although all known bugs have been addressed, if the game ever becomes unresponsive and you cannot close it:
   
   If the initial connection is unsuccessful, it will take 40 seconds for the program to let you click it again. Otherwise:
   
   - If you have a **Mac/Apple computer**: Press the red "X" at the top left corner of the terminal in which you typed "java -jar PawnGame.jar". This will stop the program.
   
   - If you have a **Windows computer**: Hold "Control + Alt + Delete" at the same time. Your keyboard may vary. If this does not work, try "Control + Shift + Escape". Select "Task Manager". Select the unresponsive app. Tap "End Task". This will stop the program.
   
   - If you have a **Linux OS**: Press the "X" that should be in the top right corner of the terminal in which you typed "java -jar PawnGame.jar". This will stop the program.
  
   
   **When you want to play again, all you need to do is repeat step 4. Have fun playing the Pawn Game!**
