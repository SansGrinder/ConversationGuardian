Hello~ Didn't expect anyone to read this tbh. Like who'd visit my GitHub anyways XD

tldr; this is a random project created for fun (sorta), and it is not very optimal (like, ofc you'd use the popular encryptions instead of mine). However, you're more than welcome to try this out! 

### What is this project?
This is the source code of Conversation Guardian.app, which will **NOT** be published online on 1225.12.25. 
This app aims to protect your *important* conversations so others cannot get sufficient info by peeking at your chat history. 

#### What is required to run your code?
*(This would only be part of your concern if you're trying to use my source code, since the .jar and the .app doesn't require you to download those libraries apart from JDK and JRE)*
* Java JDK (23+)
* JRE (Java Runtime Environment)
* The following files if you want to use my code: 
  * ```javax.mail.jar```
  * ```javax.mail-api-1.6.2.jar```
  * ```javax.activation_1.2.2.v20221203-1659.jar```
  * ```pdfbox-app-3.0.3.jar```

#### This app has 2 main features: 
1. **Encrypting/Decrypting**: String texts (*most* characters you can see on your keyboard, don't support Chinese or other languages), so that they look obfuscated. This isn't *too* easy to break through. 
2. **Password-locking a PDF**: I know this is boring and many apps/websites can do it, but seriously, who could refuse such a function when developing an app for fun that could potentially be adapted by serious people?

#### Advantages/Disadvantages of the application:  

* Advantages: 
  * This app allows you to quickly encrypt texts, allowing you to **secure your conversation** ***(or to troll a friend)***. 
  * This app *(in theory)* works on **both Windows and Mac**. (I could package it into .exe and .app, but it's not like anyone apart from those who already are will actually ever use it)
  * This app is ***completely free*** and provides you the *.jar* *.app* and/or source code. 
  * This app *automatically* sends me, the developer, your error logs *(if you have internet connection)* ***via my own email***. 
  * Since you can see this page, the app is also *open-sourced*, meaning it is safe from viruses (check it yourself if you don't believe me). 
* Disadvantages: 
  * On Windows, running the .jar file will show a different GUI, and button orders will be inverted. Sorry, Windows users, but I can't really fix this. 
  * This app requires JDK 23.0.1 or higher. I have no idea why, but you need it. Yes, even if you are running the .jar on Windows. 
  * For assessment purposes, this app will need access to: 
    * Your local computer name. (eg. example.name)
    * Your downloads folder (the program assumes and requires you to have one)
    * (Optional) Internet Connection (For emailing me any errors you got)
    * The errors that occurred as you run the program. (So I can fix it if too many people are experiencing the same issue)
  * ```JFileChooser``` for PDF selecting is unstable. Sorry for the inconvenience, but I can't find a solution yet. 
  * For Mac Users, this *.app* has an *"Unidentified Developer"* and *.jar* might be considered as *"malware"*. However, I promise you it is not malware. 
    * Solution: System Preferences - Privacy and Security - ("Conversation Guardian.app might be malware blah blah blah") Open Anyway - Open Anyway
  * This project is for fun, meaning chances are that I might stop updating this project. 
  * ~~The developer for this project is a weirdo who loves ```NullPointerException(s)```~~.

#### How does this application encrypt my input?
Since my program is open-sourced anyway, I might as well tell you. 
1. Your input is split into an array of characters. (```char[]```)
2. Every character in that array will be replaced with a new character, generated using the current time (```System.currentTimeMillis()```) and my dictionary array. 
3. No. 2 generates a new String, putting the current time in front. 
4. The newly generated String is again split into an array of characters, and is again changed into new characters, but this time using the sum of its ASCII value this time. 
5. No. 4 also generates a new String, and I again put the new index I have used in front. 
6. Done! If you're wondering why your encrypted text always starts with a number, that's why. 

#### How about decryption? 
Well, the decryption process is pretty much the same, but inverted. I'd rather not repeat it - after all, the index is processed the same way. 

#### How did you password-lock my PDFs then?
As mentioned above, this application uses ```pdfbox-app-3.0.3.jar```. This library allows me to password-protect your PDF. 

#### You mentioned you get an email whenever an error occurs. How does that work?
First of all, ```javax.mail.jar``` did the job. I registered a [163 mail](https://mail.163.com) email and made that email send my *soon-abandoned* school email address an email with your error message and log. Feel free to impersonate the bot email, but really - why not just create one yourself?
Thanks to that design, not only will I stop receiving spam emails (as my school email is deleted), your personal information (eg, email address and password) will NEVER be leaked, as it is my alt email that's been sending out messages - not yours. 

#### Anything you want to say? 
Well, huge thanks to Mr. Pete and everyone else who has helped me develop this program. Special thanks to [Kimi AI](https://kimi.moonshot.cn) for idea brainstorming and technical support.  
If you want to try this program, thank you for trusting an untrustworthy student developer. I hope you find this project useful (or most likely, funny asf). 
