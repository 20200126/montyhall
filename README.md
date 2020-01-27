# Monty Hall Game + Tests + Simulations

Tested with: JDK 11 + Maven 3.6.1

### Structure 

![Structure](https://i.imgur.com/gzyntK3.png) 

### Tests runs and passes

We've created unit tests that go all the way up to simulation level and checks the simulated results are as statistically expected within an error margin of 10%.

![](https://i.imgur.com/LTiPmNR.png)

### Java main class. MontyHallSimulator.java

To run the java main class, it can simply be done from terminal:

`mvn clean install exec:java -Dexec.mainClass="se.intensity.montyhall.mains.MontyHallSimulator" -Dexec.args="1000000"`

after having run 

`mvn clean install`

at least once prior.

![](https://i.imgur.com/ZWsv3lK.png)

