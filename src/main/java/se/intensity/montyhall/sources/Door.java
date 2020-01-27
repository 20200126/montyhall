package se.intensity.montyhall.sources;

/**
 * Represents a Door with potentially hidden prize 
 * 
 * @author Joseph S.
 */
public final class Door {
        
        public final int index;
        
        private String prize;
        
        public Door( int index ) {
                this.index = index;
        }
        public void addPrize(String prize) {
                this.prize = prize; 
        }
        public String getPrize() {
                return prize;
        }
        public boolean isPrized() {
                return getPrize() != null;
        }
}
