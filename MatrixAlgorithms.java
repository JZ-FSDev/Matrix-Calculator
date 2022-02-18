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
                    numeratorScalar = Math.abs(matrix.getMatrix()[j][matrix.trailingNonZeroIndex(j)].getNumerator());
                    denominatorScalar = matrix.getMatrix()[j][matrix.trailingNonZeroIndex(j)].getDenominator();
                    if(matrix.getMatrix()[j][matrix.trailingNonZeroIndex(j)].getNumerator() < 0){
                        matrix.rowOperation(j, i, numeratorScalar, denominatorScalar);
                        toInvert.rowOperation(j, i, numeratorScalar, denominatorScalar);
                    }else{
                        matrix.rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                        toInvert.rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                    }
                }
                printBothMatricesInversion(matrix, toInvert);
            }
        }
    }

    private static void convertToRefFormSimult(Matrix matrix, Matrix toInvert){
        int numeratorScalar, denominatorScalar;
        for(int i = 0; i < matrix.getMatrix().length; i++){
            printBothMatricesInversion(matrix, toInvert);
            sortByLeadingNonZeroSimult(matrix, toInvert);
            changeLeadNonZeroToOneSimult(i, matrix, toInvert);
            for(int j = i + 1; j < matrix.getMatrix()[i].length; j++){
                // sortByLeadingNonZeroSimult(matrix, toInvert);
                if(matrix.leadingNonZeroIndex(j) == matrix.leadingNonZeroIndex(i)){
                    numeratorScalar = Math.abs(matrix.getMatrix()[j][matrix.leadingNonZeroIndex(j)].getNumerator());
                    denominatorScalar = matrix.getMatrix()[j][matrix.leadingNonZeroIndex(j)].getDenominator();
                    if(matrix.getMatrix()[j][matrix.leadingNonZeroIndex(j)].getNumerator() < 0){
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

    private static void sortByLeadingNonZeroSimult(Matrix matrix, Matrix other){
        int[] leadingNonZeroPos = new int[matrix.getMatrix().length];
        for(int i = 0; i < matrix.getMatrix().length; i++){
            leadingNonZeroPos[i] = matrix.leadingNonZeroIndex(i);
        }
        int temp;
        int pos = 0;
        for(int j = 0; j < matrix.getMatrix().length; j++){
            for(int k = pos; k < matrix.getMatrix().length; k++){
                if(leadingNonZeroPos[k] == j){
                    temp = leadingNonZeroPos[pos];
                    leadingNonZeroPos[pos] = leadingNonZeroPos[k];
                    leadingNonZeroPos[k] = temp;
                    other.swapRow(pos, k);
                    matrix.swapRow(pos++, k);
                }
            }
        }
    }

    private static void printBothMatricesInversion(Matrix original, Matrix toInvert){
        System.out.println(original);
        System.out.println("toInvert Matrix:");
        System.out.println(toInvert);
    }


    public static Matrix invert(Matrix matrix){
        Matrix toInvert = new Matrix(Matrix.identityMatrix(matrix.getMatrix().length));
        if(matrix.hasColZeros()){
            System.out.println("Since the matrix has a column of zeros, the determinant of the matrix is zero and thus the inverse does not exist");
        }else{
            convertToRefFormSimult(matrix, toInvert);
            if(matrix.hasRowZeros()){
                System.out.println("Since the matrix has a row of zeros, the determinant of the matrix is zero and thus the inverse does not exist");
            }else{
                convertToRrefFormSimult(matrix, toInvert);
            }
        }
        return toInvert;
    }

    public static String determinant(Matrix matrix){
        matrix.convertToRefForm();
        return matrix.mainDiagonalProduct();
    }
}
