package se.intensity.core.sources;

import java.util.Random;
public class Numbers {
        
        private static final Random RANDOM = new Random();
        
        public static int randomInt( int size ) {
                return RANDOM.nextInt( size );
        }
}
