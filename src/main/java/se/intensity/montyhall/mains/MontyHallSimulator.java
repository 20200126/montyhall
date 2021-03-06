package se.intensity.montyhall.mains;

import se.intensity.montyhall.sources.MontyHall;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
public class MontyHallSimulator {
        
        private final int simulations;
        
        // Atomic because we run several simulations concurrently
        private AtomicInteger
                keepWins = new AtomicInteger(),
                swapWins = new AtomicInteger()
        ;
        
        private long elapsed;
        
        public MontyHallSimulator( int simulations ) {
                this.simulations = simulations;
        }
        
        /**
         * Many games
         */
        public void simulateGames() {
                long start = System.nanoTime();
                
                IntStream.range( 0, simulations ).parallel().forEach( i -> {
                        simulateGame();
                });
                
                this.elapsed = System.nanoTime() - start;
        }
        
        /**
         * One game
         */
        private void simulateGame() {
                // New game created
                MontyHall game = new MontyHall( "Volvo XC90" );
        
                // We declare the game started which returns an object for next available actions
                MontyHall.PlayerStarts playerStarts = game.start();
        
                // Can only select a door randomly 
                MontyHall.HostsTurn hostsTurn = playerStarts.selectDoorRandomly();
        
                // Now host can only discard a door
                MontyHall.PlayerLast playerLast = hostsTurn.discardDoor();
        
                // First we attempt with keep and record result 
                keepDoor(playerLast);
        
                // Now we attempt with swap and record result
                swapDoor(playerLast);
        }
        
        private void keepDoor( MontyHall.PlayerLast playerLast ) {
                MontyHall.GameResults results = playerLast.keepDoor();
                if ( results.playerWon() ) {
                        keepWins.incrementAndGet();
                }
        }
        
        private void swapDoor( MontyHall.PlayerLast playerLast ) {
                MontyHall.GameResults results = playerLast.swapDoor();
                if ( results.playerWon() ) {
                        swapWins.incrementAndGet();
                }
        }
        
        public void presentResults() {
                System.out.println( "Simulation took " + TimeUnit.NANOSECONDS.toMillis( elapsed ) + " ms." );
                System.out.println( "For " + simulations + " simulations, player won by keeping  " + keepWins.get() + " times." );
                System.out.println( "For " + simulations + " simulations, player won by swapping " + swapWins.get() + " times." );
        }
        
        public static void main( String[] args ) {
                // If called from editor, we use these defaults
                if ( args.length == 0 ) {
                        args = new String[] { "1000000" };
                }
                
                int simulations = Integer.parseInt( args[0] );
        
                {
                        MontyHallSimulator simulator = new MontyHallSimulator( simulations );
        
                        simulator.simulateGames();
        
                        simulator.presentResults();
                }
        
                // To make sure mvn exec exits properly
                System.exit( 0 );
        }
        
}
