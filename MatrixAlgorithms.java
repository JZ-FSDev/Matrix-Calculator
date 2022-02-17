public abstract class MatrixAlgorithms {
    
    private static void changeLeadNonZeroToOneSimult(int row, Matrix matrix, Matrix other){
        int leadNumeratorScalar = matrix.getMatrix()[row][matrix.leadingNonZeroIndex(row)].getNumerator();
        int leadDenominatorScalar = matrix.getMatrix()[row][matrix.leadingNonZeroIndex(row)].getDenominator();
        int numerator, denominator, otherNumerator, otherDenominator;
        for(int i = 0; i < matrix.getMatrix().length; i++){
            numerator = matrix.getMatrix()[row][i].getNumerator();
            denominator = matrix.getMatrix()[row][i].getDenominator();
            otherNumerator = other.getMatrix()[row][i].getNumerator();
            otherDenominator = other.getMatrix()[row][i].getDenominator();
            if(numerator != 0){
                matrix.getMatrix()[row][i].setNumerator(numerator * leadDenominatorScalar);
                matrix.getMatrix()[row][i].setDenominator(denominator * leadNumeratorScalar);
                matrix.getMatrix()[row][i].simplify();
            }
            if(otherNumerator != 0){
                other.getMatrix()[row][i].setNumerator(otherNumerator * leadDenominatorScalar);
                other.getMatrix()[row][i].setDenominator(otherDenominator * leadNumeratorScalar);
                other.getMatrix()[row][i].simplify();
            }
        }
    }

    private static void convertToRrefFormSimult(Matrix matrix, Matrix toInvert){
        int numeratorScalar, denominatorScalar;
        for(int i = matrix.getMatrix().length - 1; i > 0; i--){
            for(int j = i - 1; j >= 0; j--){
                if(matrix.trailingNonZeroIndex(j) == matrix.trailingNonZeroIndex(i)){
                    numeratorScalar = matrix.getMatrix()[j][matrix.trailingNonZeroIndex(j)].getNumerator();
                    denominatorScalar = matrix.getMatrix()[j][matrix.trailingNonZeroIndex(j)].getDenominator();
                    if(matrix.getMatrix()[j][matrix.trailingNonZeroIndex(j)].getNumerator() < 0){
                        matrix.rowOperation(j, i, numeratorScalar, denominatorScalar);
                        toInvert.rowOperation(j, i, numeratorScalar, denominatorScalar);
                    }else{
                        matrix.rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                        toInvert.rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                    }
                }
            }
        }
    }
}
