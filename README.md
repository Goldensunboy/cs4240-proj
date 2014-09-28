cs4240-proj
===========

MIPS frontend
Steps to setup ANTLR on Eclipse:

1) Take a look at ANTLR IDE download guide to understand why we are doing certain things we do below

2) Download http://www.antlr3.org/download/antlr-3.5.2-complete.jar

3) Download https://www.eclipse.org/downloads/packages/release/indigo/sr2

4) Open Eclipse, go to "Help>Install New Software...". 

5) Make sure below details area ONLY these two options are selected:
	"Show only the latest versions of available software"
	"Contact all updates sites during install to find required software"

6) Under "Work with:" pick "Indigo - http://download.eclipse.org/releases/indigo" (this will take a while)

7) In "type filter text" area type "GEF" and from the results select "Graphical Editing Framework GEF"

8) Then type "zest" and from the results select "Graphical Editing Framework Zest Visualization Toolkit"

9) The type "dynamic language" and from the results select "Dynamic Languages Toolkit Core Frameworks"

10) After selecting the required tools in steps 7 through 9, click on the black arrow with a small white cross on it from the "type filter text" bar (if you don't do that Eclipse will only download the tool you selected in step 9)

11) Click next, agree to terms and conditions, accepting everything if the files are not signed, restart etc ...

12) Go to "Help>Install New Software...", click on "Add...". Then let Name be "ANTLR IDE" and the Location be "http://antlrv3ide.sourceforge.net/updates". Click OK

13) Again make sure below details area ONLY these two options are selected:
	"Show only the latest versions of available software"
	"Contact all updates sites during install to find required software"

13) From "Work with:" pick "ANTLR IDE - http://antlrv3ide.sourceforge.net/updates" and select all of the tools that show up. Click next, accept, restart, etc...

14) Create a Java project, right click on the project 

