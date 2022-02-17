public abstract class MathOperations {

    public static int gcd(int a, int b){
        int gcd = 1;
        for(int i = 1; i <= Math.abs(Math.min(a, b)); i++){
            if(a % i == 0 && b % i == 0){
                gcd = i;
            }
        }
        return gcd;
    }

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
