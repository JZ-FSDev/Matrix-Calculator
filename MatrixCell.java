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
        int lcd = MathOperations.lcd(denominator, otherDenominator);
        numerator *= lcd/Math.abs(denominator);
        denominator *= lcd/Math.abs(denominator);
        otherNumerator *= lcd/Math.abs(otherDenominator);
        otherDenominator *= lcd/Math.abs(otherDenominator);
        numerator += otherNumerator;
        simplify();
    }

    public void simplify(){
        int temp = numerator;
        numerator /= MathOperations.gcd(temp, denominator);
        denominator /= MathOperations.gcd(temp, denominator);
        if(numerator < 0 && denominator < 0){
            numerator *= -1;
            denominator *= -1;
        }
        if(numerator > 0 && denominator < 0){
            numerator *= -1;
            denominator *= -1;
        }
        if(numerator == 0){
            denominator = 1;
        }
    } 


    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        if(denominator == 1){
            stringBuilder.append(numerator);
        }else{
            stringBuilder.append(numerator);
            stringBuilder.append("/");
            stringBuilder.append(denominator);
        }
        return stringBuilder.toString();
    }
}
