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
    
    public void addScalarMultipleCell(MatrixCell other, int scalar){
        int otherNumerator = numerator + scalar * other.getNumerator();
        int otherDenominator = other.getDenominator();
        int gcd = gcd(otherNumerator, otherDenominator);
        if(gcd > 1){
            otherNumerator /= gcd;
            otherDenominator /= gcd;
        }
        numerator += otherNumerator;
        if(numeratorDenominatorGcd() > 1){
            numerator /= gcd;
            numerator /= gcd;
        }       
    }    
}
