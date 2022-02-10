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
}
