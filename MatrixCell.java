public class MatrixCell {
    
    private int numerator;
    private int denominator;

    public MatrixCell(int numerator, int denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public void setNumerator(int n){
        this.numerator = n;
    }

    public void setDenominator(int n){
        this.denominator = n;
    }    

    public int getNumerator(){
        return this.numerator;
    }

    public int getDenominator(){
        return this.denominator;
    }

    public double numericalValue(){
        return numerator/denominator;
    }
    
    public void addScalarMultipleCell(MatrixCell other, int numeratorScalar, int denominatorScalar){
        int otherNumerator = numeratorScalar * other.getNumerator();
        int otherDenominator = denominatorScalar * other.getDenominator();
        int lcd = lcd(denominator, otherDenominator);

        numerator *= lcd/Math.abs(denominator);
        denominator *= lcd/Math.abs(denominator);

        otherNumerator *= lcd/Math.abs(otherDenominator);
        otherDenominator *= lcd/Math.abs(otherDenominator);

        numerator += otherNumerator;

        simplify();
    }
    
    public void simplify(){
        numerator /= gcd(numerator, denominator);
        denominator /= gcd(numerator, denominator);
    } 

    public int gcd(int a, int b){
        int gcd = 1;
        for(int i = 1; i <= Math.min(a, b); i++){
            if(a % i == 0 && b % i == 0){
                gcd = i;
            }
        }
        return gcd;
    }

    public int lcd(int a, int b){
        int lcd = 0;
        for(int i = Math.max(a,b); i <= a*b && lcd == 0; i++){
            if(i % a == 0 && i % b == 0){
                lcd = i;
            }   
        }
        return lcd;
    }    
}
