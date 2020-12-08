# The Pawn Game #
## U.S. Chess Center ##
## By Tara Bobinac ##

The Pawn Game is a Java application that allows two students on different computers to play the U.S. Chess Center's Pawn Game.


**1. To download the game**: 

Press the file labeled "PawnGame.jar" and press download in the upper righthand corner. Your computer will say that this type of file can harm your device because it can't read its contents, but press "Keep" to download it anyway; it will not harm your computer. The game should now be in your Downloads folder.



**2. If you do not already have Java installed on your computer, visit this link: https://www.oracle.com/java/technologies/javase-jdk15-downloads.html**
   
      If you have a Mac/Apple computer:
      Press the download link across from "macOS Installer". It should be labeled "jdk-15.0.1_osx-x64_bin.dmg".
  
      If you have a Windows computer:
      Press the download link across from "Windows x64 Installer". It should be labeled "jdk-15.0.1_windows-x64_bin.exe".
   
      If you have a Linux OS:
      Press the download link across from "Linux x64 Debian Package". It should be labeled "jdk-15.0.1_linux-x64_bin.deb". If for any reason this doesn't work, download the link across from "Linux x64 RPM Package" or "Linux ARM 64 RPM Package".
   
   Java should now be in your Downloads folder.
   
   Go to your downloads folder and double click the file that starts with "jdk-15.0.1...". Follow the prompts on the screen. You now have Java installed on your computer!
   
   
   
   **3. Opening your network for connection**
   
   Go to https://www.noip.com/support/knowledgebase/general-port-forwarding-guide/ and scroll down until you find the brand of your router. Take a look at the username and password. Then go to http://192.168.1.1/ and type in the the username and password that were associated with your router. If that doesn't work, ask a parent if they changed the settings. Sometimes, the username is your wifi/network connection and your password is either blank or is the password to your wifi connection.
   
   a) Once you log in, look under the Advanced settings for LAN. Press it! Somewhere in the LAN settings, there should be a button labeled DHCP. Under that, there should be a section that asks for a MAC address and an IP address.
   
   Usually, there will be a dropdown menu under MAC address that has the name of your device. If you press it, it will automatically fill everything in. If not...
   
      For Mac/Apple computers and Windows computers, find your MAC address with this link: 
      https://faq.its.fsu.edu/network/device-registration/how-do-i-find-mac-address-my-computer
   
      For Linux OS users: 
      go to your Applications and type in Terminal. It looks like a black box with a white arrow and line. When you open it, type in "ifconfig -a". Press enter. To the right of the section labeled eth0, the number after HWaddr is your MAC address. For example:
   
      eth0      Link encap:Ethernet HWaddr #00:08:C7:1B:8C:02#
            inet addr:192.168.111.20  Bcast:192.168.111.255  Mask:255.255.255.0
          
   The local IP address should now automatically fill in. If not...
          
      If you have a Mac/Apple computer: 
      Click the ‘Apple’ logo in your Mac’s menu bar. Select "System Preferences…" Select "Network." In the left-hand menu, you should see a few options such as Wi-Fi and Ethernet. Select the interface you’re currently using to connect to the Internet (it’s the one with the green dot next to it). Your local IP address will be displayed on the right, as part of the "Connected" section.
   
      If you have a Windows computer:
      Go to the Windows start menu and open up Command Prompt (you can find this by typing in CMD). Type in “ipconfig” and hit Enter. Look for the line that reads “IPv4 Address.” The number across from that text is your local IP address.
   
      If you have Linux OS:
      Go to your Applications and type Terminal into the search bar. It looks like a black box with a white arrow and line. Type in "hostname -I" without the quotations marks and press enter. The number is your loca IP address.
         
   b) Now look for a button labeled WAN under the Advanced settings. Somewhere in the WAN settings, usually towards the bottom, there should be a button labeled Virtual Server or Port Forwarding. Most likely towards the bottom of that page, you will find a section labeled something like "Port Forwarding Lists". In that section, where it says "Name", "Title", or "Service Name" type in "Pawn Game". In sections that ask for "Port Range", "Local Port", or anything including the word port, type in "3000". For protocol, pick "BOTH". For the local IP address, there should again be a drop down menu for you to pick the name of your device. If not...
   
      If you have a Mac/Apple computer: 
      Click the ‘Apple’ logo in your Mac’s menu bar. Select ‘System Preferences…’ Select ‘Network.’ In the left-hand menu, you should see a few options such as Wi-Fi and Ethernet. Select the interface you’re currently using to connect to the Internet (it’s the one with the green dot next to it). Your local IP address will be displayed on the right, as part of the ‘Connected’ section.
   
      If you have a Windows computer:
      Go to the Windows start menu and open up Command Prompt (you can find this by typing in CMD). Type in “ipconfig” and hit Enter. Look for the line that reads “IPv4 Address.” The number across from that text is your local IP address.
   
      If you have Linux OS:
      Go to your Applications and type Terminal into the search bar. It looks like a black box with a white arrow and line. Type in "hostname -I" without the quotations marks and press enter. The number is your loca IP address.
   
   Now that all sections of the Port Forwarding Lists are filled out, press any button that is labeled "Add", "+", or something similar and press the Confirm or Apply button at the bottom.
   
   **4. Time to play the game!**
   
      If you have a Mac/Apple computer: Make sure the file labeled PawnGame.jar is still in your Downloads folder. Keep it there! Go to your Applications (the rocket button at the bottom of the screen) and type Terminal into the search bar. It looks like a black box with a white arrow and line. When you open it, type in "cd Downloads" without the quotations marks and hit enter. Then type in "java -jar PawnGame.jar" without the quotations marks and press enter. Your game should be working!
   
      If you have a Windows computer: Find the folder where you have the file PawnGame.jar (it should be in your Downloads folder unless you moved it). Double click it. Your game should be working!
   
      If you have a Linux computer: Make sure the file labeled PawnGame.jar is still in your Downloads folder. Keep it there! Go to your Applications (the rocket button at the bottom of the screen) and type Terminal into the search bar. It looks like a black box with a white arrow and line. When you open it, type in "cd Downloads" without the quotations marks and hit enter. Then type in "java -jar PawnGame.jar" without the quotations marks and press enter. Your game should be working!
   
   When you want to play again, all you need to do is repeat step 4! Have fun playing the Pawn Game!
