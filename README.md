cs4240-proj
===========

MIPS frontend
Steps to setup ANTLR on Eclipse:

1) Take a look at ANTLR IDE download guide to understand why we are doing certain things we do below

2) Download http://www.antlr3.org/download/antlr-3.5.2-complete.jar and make a folder for it called "antlr" (IMPORTANT).

3) Download https://www.eclipse.org/downloads/packages/release/indigo/sr2

4) Open Eclipse, go to "Help>Install New Software...". 

5) Make sure below "Details" area ONLY these two options are selected (Otherwise you won't see the needed tools):
	"Show only the latest versions of available software"
	"Contact all updates sites during install to find required software"

6) Under "Work with:" pick "Indigo - http://download.eclipse.org/releases/indigo" (this may take a while)

7) In "type filter text" area type "GEF" and from the results select "Graphical Editing Framework GEF"

8) Then type "zest" and from the results select "Graphical Editing Framework Zest Visualization Toolkit"

9) The type "dynamic language" and from the results select "Dynamic Languages Toolkit Core Frameworks"

10) Somethig very stupid!!! After selecting the required tools in steps 7 through 9, click on the black arrow with a small white cross on it from the "type filter text" bar (if you don't do that Eclipse will only download the tool you selected in step 9)

11) Click next, agree to terms and conditions, accepting everything if the files are not signed, restart, blah, blah, etc ...

12) Go to "Help>Install New Software...", click on "Add...". Then let Name be "ANTLR IDE" and the Location be "http://antlrv3ide.sourceforge.net/updates". Click OK

13) Make sure below the "Details" the following options are selected:
	"Show only the latest versions of available software"
	"Group items by category" (Notice we select this one this time around)
	"Contact all updates sites during install to find required software"

13) From "Work with:" pick "ANTLR IDE - http://antlrv3ide.sourceforge.net/updates" and select all of the tools that show up. Click next, accept security warning, restart, etc...

14) Create a Java project called "cs4240-proj", right click on the project and click on "Configure>Convert to ANTLR Project..."

15) Go to "Window>Preferences" under "ANTLR" make sure "Mark generated resources as derived" is checked

16) Then under "Builder" below "Install Packeges" section click on "Add..." and add the jar file from Step 2. Click OK

17) (We are still under ANTLR) IMPORTANT: Go to "Code Generator" and select "Project relative folder" and type in "antlr-generated", also select "Append java package to output folder". Click OK

18) Clone the project somewhere else (maybe in your Download folder even, just need some stuff out of the repo, and then we'll delete it, so don't worry about where you put it) "git clone git@github.com:Goldensunboy/cs4240-proj.git"

19) Move the ".git" folder, "src" folder, ".gitignore" file, and "README.md" file from the cloned repo to the project we made in step 14. So just paste these files under "cs4240-proj". This is very hacky, I know. But so far I couldn't come up with a better way (we can discuss this later)

20) Go to the project you created in Step 14. Do a git status to make sure you are not missing anything, (git status should tell you everything is up to date, otherwise you have done something wrong)

21) Refresh your project in Eclipse and BOOM! ANTLR will work and it will generate "antlr-generated" folder for you 

22) Right click on "antlr-generated" then click on "Build Path>Use as Source Folder". You'll see some red marks. 

23) To get rid of that red warning add the .jar file from Step 2 to the build path: drag and drop the .jar file under the "cs4240-proj" project then refresh your Eclipse project. You'll see the .jar file shows up. Right click on it "Build Path>Add to Build Path" and you'll see the errors are gone (At the moment our Tiger.g contains some errors so still after adding the .jar file TigerParser.java will have the red mark)
