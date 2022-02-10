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
    
}
