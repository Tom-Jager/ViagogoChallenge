# ViagogoChallenge

Instruction for building and running code
===========================================

Navigate to ViagogoChallenge/out/artifacts/ViagogoChallenge_jar/ViagogoChallenge.jar from the git directory. Here you can download the exectutable jar file.
Please e-mail me at tjager22@gmail.com if there are any problems

Assumptions
===========

I have assumed that each event will have a maximum of 10 tickets available but this is easily scalable. I have also assumed that the idetifier for each event is procedurally generated, but could implement a naming system if needed.

If multpile events were needed at the same location, I could simply update the Venue class to contain a list of events. I would create an appropriate icon for multiple event locations. Now the method for finding the closest events would also add an event counter to the Pair class which would be used to ensure that we know how many events are at the closer venues.

Finally, in a larger world size I would implement the map data as a graph with each vertex being a Venue. The edges would be weighted and visually a real world map. I would use breadth first seacrh algorithm to find the nearest venues to the chosen node until the 5 nearest had been found.

