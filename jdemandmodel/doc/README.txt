JDemandModel
----------------

A JAVA application to simulate the electricity demand of a dwelling.

Running the program without options will simply produce an output file (CSV) which can be found in the folder data/output with simulation data for one day (10-minute-intervals). To show the available options start the application like this:
	
	java -jar jdm-v0.1.jar --help

Some examples and JUnit tests are included in the project folder, as well as a java documentation.


Change Log v0.1:
----------------
- added Apache Commons CLI
- added a lighting model
- added a TimeConverter class to convert the output data to different time interval lengths
- added power cycle interface for custom power cycles of washing machine and washer-dryer
- introduced an own Random class RandomUtil to use a seed later


TODOs:
----------------

- loading a seed sometimes causes exceptions
- ProcessBuilder GNU R Plot -> show Swing (Demo)
- option to force overwriting, else don't
- option to set the separator of the output file
- use Apache Commons Logger
- correct WashingCycle error (has no effect)
