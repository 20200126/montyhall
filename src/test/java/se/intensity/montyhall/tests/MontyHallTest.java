package se.intensity.montyhall.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import se.intensity.montyhall.sources.MontyHall;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Test for Monty Hall game including simulations
 */

public class MontyHallTest {
        
        public MontyHallTest() {
        }
        
        @BeforeClass public static void beforeTests() {}
        @Before      public        void beforeEach () {}
        @After       public        void afterEach  () {}
        @AfterClass  public static void afterTests () {}
        
        @Test
        public void newGameTest() {
                MontyHall game = newGame();
        }
        
        @Test
        public void gameStartTest() {
                gameStart();
        }
        
        @Test
        public void selectDoorTest() {
                MontyHall.HostsTurn hostsTurn = selectDoor();
        
                Assert.assertNotNull( hostsTurn );
        }
        
        @Test
        public void hostDiscardsTest() {
                MontyHall.PlayerLast playerLast = hostDiscard();
                
                Assert.assertNotNull( playerLast );
        }
        
        @Test
        public void playerLastKeepTest() {
                playerLast( 1d / 3, MontyHall.PlayerLast::keepDoor );
        }
        
        @Test
        public void playerLastSwapTest() {
                playerLast( 2d / 3, MontyHall.PlayerLast::swapDoor );
        }
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        
        private MontyHall newGame() {
                return new MontyHall( "Volvo XC90" );
        }
        private MontyHall.PlayerStarts gameStart() {
                MontyHall game = newGame();
                
                MontyHall.PlayerStarts playerStarts = game.start();
        
                Assert.assertNotNull( playerStarts );
                
                return playerStarts;
        }
        
        private MontyHall.HostsTurn selectDoor() {
                MontyHall.PlayerStarts playerStarts = gameStart();
                
                return playerStarts.selectDoorRandomly();
        }
        
        private MontyHall.PlayerLast hostDiscard() {
                MontyHall.HostsTurn hostsTurn = selectDoor();
                
                return hostsTurn.discardDoor();
        }
        
        private void playerLast( double expected, Function<MontyHall.PlayerLast, MontyHall.GameResults> lambda) {
                AtomicInteger won = new AtomicInteger();
                
                int simulations = 9999;
                
                IntStream.range( 0, simulations ).parallel().forEach( i -> {
                        
                        MontyHall.PlayerLast playerLast = hostDiscard();
                        
                        MontyHall.GameResults results = lambda.apply(playerLast );
                        
                        if ( results.playerWon() ) {
                                won.incrementAndGet();
                        }
                });
                
                // With 10% error margin allowed for the simulation results
                double plusminus = 0.1;
                
                Assert.assertEquals( simulations * expected, won.get(), simulations * plusminus );
        }
        
}
