package se.intensity.montyhall.sources;

import se.intensity.core.sources.Numbers;

import java.util.ArrayList;

/**
 * An object oriented stateful version of Monty Hall 
 * @author Joseph S.
 */
public class MontyHall {
        
        // Represents the doors that are remaining and choosable by the host. 
        // The size of this list changes when the user selects a door, and when host discards a loosing door.
        private final ArrayList<Door> remaining;
        
        // This refers to the Door the player has currently selected
        private Door selected;
        
        /////////////////////////////////////////////////////////////////////
        
        public MontyHall( String prize ) {
                int doors = 3;
                
                this.remaining = new ArrayList<>(doors);
                
                // Create doors
                int index = -1; while ( ++index < doors ) {
                        this.remaining.add( new Door( index ) );
                }
                
                // Pick a random door and add a prize to it 
                getDoor( getRemainingRandomIndex() ).addPrize( prize );
        }
        
        private Door getDoor( int number ) {
                return this.remaining.get(number);
        }
        
        private int getRemainingRandomIndex() {
                return Numbers.randomInt( this.remaining.size() );
        }
        
        public PlayerStarts start() {
            return new PlayerStarts(); 
        }
        
        void selectDoorRandomly() {
                selectDoor( getRemainingRandomIndex() );
        }
        
        private void selectDoor( int index ) {
                this.selected = remaining.remove( index );
                
        }
        
        private void swapDoor() {
                Door last = remaining.remove( 0 );
                remaining.add(selected);
                selected = last;
        }
        
        private void discardDoor() {
                // There is only one winner door. Find first that is a looser door. 
                int index = -1; while ( ++index < remaining.size() ) {
                        if ( !getDoor(index).isPrized() ) break;  
                }
                
                // Now remove it
                remaining.remove(index);
        }
        
        /////////////////////////////////////////////////////////////////////
        
        /**
         * Delegating API that specifies next acceptable action after game.start()                  
         */        
        public class PlayerStarts {
                PlayerStarts(){}
        
                public HostsTurn selectDoorRandomly() {
                        MontyHall.this.selectDoorRandomly(); 
                        
                        return new HostsTurn();
                }
        }
        
        /////////////////////////////////////////////////////////////////////
        
        /**
         * Delegating API that specifies next acceptable action after player.selectDoor                  
         */
        public final class HostsTurn {
                HostsTurn(){}
        
                public PlayerLast discardDoor() {
                    MontyHall.this.discardDoor();
                    
                    return new PlayerLast();
                }
        }
        
        /////////////////////////////////////////////////////////////////////
        
        /**
         * Delegating API that specifies next acceptable action after host has discarded a looser                 
         */
        public final class PlayerLast {
                PlayerLast(){}
        
                public GameResults keepDoor() {
                        // Do nothing, keep things as is. Selected remains the same.
                        
                        return new GameResults();
                }
                
                public GameResults swapDoor() {
                        MontyHall.this.swapDoor();
                        
                        return new GameResults();
                }
                
        }
        
        /////////////////////////////////////////////////////////////////////
        
        public final class GameResults {
                GameResults(){}
                
                public boolean playerWon() {
                        return selected.isPrized();
                }
        
                /**
                 *  Never called
                 */
                public String playerPrize() {
                        return selected.getPrize();
                }
        }
        
}


