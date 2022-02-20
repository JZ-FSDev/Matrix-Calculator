/**
 * Defines an abstract class containing common math operations that
 * can be used publicly.
 *
 * @author JZ-FSDev
 * @since 17.0.1
 * @version 0.0.1
 */
public abstract class MathOperations {

    /**
     * Returns the greatest common divisor of two specified integers.
     * 
     * @param a The first integer to compute the greatest common divisor from.
     * @param b The second integer to compute the greatest common divisor from.
     * @return The greatest common divisor of two specified integers.
     */
    public static int gcd(int a, int b){
        int gcd = 1;
        for(int i = 1; i <= Math.abs(Math.min(a, b)); i++){
            if(a % i == 0 && b % i == 0){
                gcd = i;
            }
        }
        return gcd;
    }

    /**
     * Returns the lowest common divisor of two specified integers.
     * 
     * @param a The first integer to compute the lowest common divisor from.
     * @param b The second integer to compute the lowest common divisor from.
     * @return The lowest common divisor of two specified integers.
     */
    public static int lcd(int a, int b){
        int lcd = 0;
        for(int i = Math.max(a,b); i <= a*b && lcd == 0; i++){
            if(i % a == 0 && i % b == 0){
                lcd = i;
            }   
        }
        return lcd;
    }
}

