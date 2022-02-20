/**
 * Defines a rational number matrix cell.
 *
 * @author JZ-FSDev
 * @since 17.0.1
 * @version 0.0.1
 */
public class MatrixCell {
    
    private int numerator;
    private int denominator;

    /**
     * Creates a matrix cell of specified numerator and denominator.
     * 
     * @param numerator The numerator of this matrix cell.
     * @param denominator The denominator of this matrix cell.
     */
    public MatrixCell(int numerator, int denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Sets the numerator of this matrix cell to the speficied integer.
     * 
     * @param n The number to set this matrix cell's numerator to.
     */
    public void setNumerator(int n){
        this.numerator = n;
    }

    /**
     * Sets the denominator of this matrix cell to the speficied integer.
     * 
     * @param nThe number to set this matrix cell's denominator to.
     */
    public void setDenominator(int n){
        this.denominator = n;
    }    

    /**
     * Returns this matrix cell's numerator.
     * 
     * @return This matrix cell's numerator.
     */
    public int getNumerator(){
        return this.numerator;
    }

    /**
     * Returns this matrix cell's denominator.
     * 
     * @return This matrix cell's denominator.
     */
    public int getDenominator(){
        return this.denominator;
    }

    /**
     * Returns the decimal value of this matrix cell.
     * 
     * @return The decimal value of this matrix cell.
     */
    public double decimalValue(){
        return numerator/denominator;
    }

    /**
     * Adds a scalar multiple of another matrix cell to this matrix cell 
     * by a factor of the specified numerator and denominator scalar.
     * Simplifies the fraction of this cell to the lowest form after the addition.
     * 
     * @param other The other matrix cell to be multiplied by the numerator 
     *              and denominator scalar before being added to this matrix cell.
     * @param numeratorScalar The scalar to be multiplied to the other matrix cell's numerator.
     * @param denominatorScalar The scalar to be multiplied to the other matrix cell's denominator.
     */
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

    /**
     * Simiplies the fraction of this cell to the lowest form.
     */
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

    /**
     * Returns the String representation of the fraction of this cell.
     * 
     * @return The String representation of the fraction of this cell.
     */
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
