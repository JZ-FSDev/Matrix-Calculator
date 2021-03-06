/**
 * Defines matrix algorithms used to solve or convert matrices.
 *
 * @author JZ-FSDev
 * @since 17.0.1
 * @version 0.0.1
 */
public abstract class MatrixAlgorithms {
    
    /**
     * Multiplies the specified row of this matrix with the appropriate
     * scalar to convert the the leading non-zero cell to the number 1.
     * Multiplies the speficied other matrix's row with the same scalar
     * used in the conversion of the leading non-zero cell to the number 1
     * in the original matrix. Used for the inversion algorithm where
     * the other matrix is the identity matrix from the beginning of inversion.
     * 
     * 
     * @param row The row to convert to a leading 1.
     * @param matrix The matrix to have a row converted to a leading 1.
     * @param other The other matrix to have the same operation repeated on the same
     *              specified row.
     */
    private static void changeLeadNonZeroToOneSimult(int row, Matrix matrix, Matrix other){
        int leadindColIndex = matrix.leadingNonZeroIndex(row);
        if(leadindColIndex == Integer.MAX_VALUE){
            leadindColIndex = matrix.getMatrix().length - 1;
        }
        int leadNumeratorScalar = matrix.getMatrix()[row][leadindColIndex].getNumerator();
        int leadDenominatorScalar = matrix.getMatrix()[row][leadindColIndex].getDenominator();
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

    private static void changeLeadNonZeroToOneWithConstants(int row, Matrix matrix, MatrixCell[] constantCol){
        int leadindColIndex = matrix.leadingNonZeroIndex(row);
        if(leadindColIndex == Integer.MAX_VALUE){
            leadindColIndex = matrix.getMatrix().length - 1;
        }
        int leadNumeratorScalar = matrix.getMatrix()[row][leadindColIndex].getNumerator();
        int leadDenominatorScalar = matrix.getMatrix()[row][leadindColIndex].getDenominator();
        int numerator, denominator, otherNumerator, otherDenominator;
        for(int i = 0; i < matrix.getMatrix().length; i++){
            numerator = matrix.getMatrix()[row][i].getNumerator();
            denominator = matrix.getMatrix()[row][i].getDenominator();
            if(numerator != 0){
                matrix.getMatrix()[row][i].setNumerator(numerator * leadDenominatorScalar);
                matrix.getMatrix()[row][i].setDenominator(denominator * leadNumeratorScalar);
                matrix.getMatrix()[row][i].simplify();
            }
        }
        otherNumerator = constantCol[row].getNumerator();
        otherDenominator = constantCol[row].getDenominator();
        if(otherNumerator != 0){
            constantCol[row].setNumerator(otherNumerator * leadDenominatorScalar);
            constantCol[row].setDenominator(otherDenominator * leadNumeratorScalar);
            constantCol[row].simplify();
        }
    }

    /**
     * Converts the specified matrix to reduced row echelon form.
     * Repeats the operations done on the matrix to the other specified matrix.
     * Used for the inversion algorithm where the other matrix is the identity 
     * matrix from the beginning of inversion.
     * 
     * @param matrix The matrix to be converted to reduced row echelon form.
     * @param other The other matrix to have the same operations repeated when
     *              converting the main matrix to reduced row echelon form.
     */
    private static void convertToRrefFormSimult(Matrix matrix, Matrix other){
        int numeratorScalar, denominatorScalar;
        for(int i = matrix.getMatrix().length - 1; i > 0; i--){
            for(int j = i - 1; j >= 0; j--){
                int topRow = matrix.trailingNonZeroIndex(i);
                int lowerRow = matrix.trailingNonZeroIndex(j);
                if(topRow == lowerRow){
                    numeratorScalar = Math.abs(matrix.getMatrix()[j][lowerRow].getNumerator());
                    denominatorScalar = matrix.getMatrix()[j][lowerRow].getDenominator();
                    if(matrix.getMatrix()[j][lowerRow].getNumerator() < 0){
                        matrix.rowOperation(j, i, numeratorScalar, denominatorScalar);
                        other.rowOperation(j, i, numeratorScalar, denominatorScalar);
                    }else{
                        matrix.rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                        other.rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                    }
                }
                printBothMatricesInversion(matrix, other);
            }
        }
    }

    public static void convertToRrefFormWithConstants(Matrix matrix, MatrixCell[] constantCol){
        int numeratorScalar, denominatorScalar;
        int operatingColIndex;
        for(int i = matrix.getMatrix().length - 1; i > 0; i--){
            operatingColIndex = matrix.leadingNonZeroIndex(i);
            if(operatingColIndex != Integer.MAX_VALUE){
                for(int j = i - 1; j >= 0; j--){
                    numeratorScalar = Math.abs(matrix.getMatrix()[j][operatingColIndex].getNumerator());
                    denominatorScalar = matrix.getMatrix()[j][operatingColIndex].getDenominator();
                    if(matrix.getMatrix()[j][operatingColIndex].getNumerator() < 0){
                        constantCol[j].addScalarMultipleCell(constantCol[i], numeratorScalar, denominatorScalar);
                        matrix.rowOperation(j, i, numeratorScalar, denominatorScalar);
                    }else{
                        constantCol[j].addScalarMultipleCell(constantCol[i], -1 * numeratorScalar, denominatorScalar);
                        matrix.rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                    }
                    System.out.println(matrix);
                    String s = "";
                    for(int k = 0; k < constantCol.length; k++){
                        s += constantCol[k] + " ";
                    }
                    System.out.println(s);
                }
            }

        }
    }

    /**
     * Converts the specified matrix to row echelon form.
     * Repeats the operations done on the matrix to the other specified matrix.
     * Used for the inversion algorithm where the other matrix is the identity 
     * matrix from the beginning of inversion.
     * 
     * @param matrix The matrix to be converted to reduced row echelon form.
     * @param other The other matrix to have the same operations repeated when
     *              converting the main matrix to reduced row echelon form.
     */
    private static void convertToRefFormSimult(Matrix matrix, Matrix other){
        int numeratorScalar, denominatorScalar;
        for(int i = 0; i < matrix.getMatrix().length; i++){
            int topRow = matrix.leadingNonZeroIndex(i);
            printBothMatricesInversion(matrix, other);
            sortByLeadingNonZeroSimult(matrix, other);
            if(topRow != Integer.MAX_VALUE){
                changeLeadNonZeroToOneSimult(i, matrix, other);
                for(int j = i + 1; j < matrix.getMatrix()[i].length; j++){
                    int lowerRow = matrix.leadingNonZeroIndex(j);
                    if(topRow == lowerRow && lowerRow != Integer.MAX_VALUE){
                        numeratorScalar = Math.abs(matrix.getMatrix()[j][lowerRow].getNumerator());
                        denominatorScalar = matrix.getMatrix()[j][lowerRow].getDenominator();
                        if(matrix.getMatrix()[j][lowerRow].getNumerator() < 0){
                            matrix.rowOperation(j, i, numeratorScalar, denominatorScalar);
                            other.rowOperation(j, i, numeratorScalar, denominatorScalar);
                        }else{
                            matrix.rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                            other.rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                        }
                    }
                }
            }
        }
    }

    public static void convertToRefFormWithConstants(Matrix matrix, MatrixCell[] constantCol){
        int numeratorScalar, denominatorScalar;
        for(int i = 0; i < matrix.getMatrix().length; i++){
            int operatingColIndex = matrix.leadingNonZeroIndex(i);
            sortByLeadingNonZeroWithConstants(matrix, constantCol);
            if(operatingColIndex != Integer.MAX_VALUE){
                changeLeadNonZeroToOneWithConstants(i, matrix, constantCol);
                for(int j = i + 1; j < matrix.getMatrix()[i].length; j++){
                    int receivingColIndex = matrix.leadingNonZeroIndex(j);
                    if(operatingColIndex == receivingColIndex && receivingColIndex != Integer.MAX_VALUE){
                        numeratorScalar = Math.abs(matrix.getMatrix()[j][receivingColIndex].getNumerator());
                        denominatorScalar = matrix.getMatrix()[j][receivingColIndex].getDenominator();
                        if(matrix.getMatrix()[j][receivingColIndex].getNumerator() < 0){
                            constantCol[j].addScalarMultipleCell(constantCol[i], numeratorScalar, denominatorScalar);
                            matrix.rowOperation(j, i, numeratorScalar, denominatorScalar);
                        }else{
                            constantCol[j].addScalarMultipleCell(constantCol[i], -1 * numeratorScalar, denominatorScalar);
                            matrix.rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                        }
                    }
                }
            }
        }
    }

    /**
     * Sorts the specified matrix by left most leading non-zero rows at the top of the
     * matrix with decreasing left leading non-zero rows under.  Full zero rows will be
     * arranged to the bottom of the matrix.  Any operations used to sort the matrix
     * will be repeated on the other matrix.  Used for the inversion algorithm where 
     * the other matrix is the identity matrix from the beginning of inversion.
     * 
     * @param matrix The matrix to be converted to be sorted.
     * @param other The other matrix to have the same operations repeated when
     *              sorting the main matrix.
     */
    private static void sortByLeadingNonZeroSimult(Matrix matrix, Matrix other){
        int[] leadingNonZeroPos = new int[matrix.getMatrix().length];
        for(int i = 0; i < matrix.getMatrix().length; i++){
            leadingNonZeroPos[i] = matrix.leadingNonZeroIndex(i);
            if(leadingNonZeroPos[i] == Integer.MAX_VALUE){
                leadingNonZeroPos[i] = matrix.getMatrix().length;
            }
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

    private static void sortByLeadingNonZeroWithConstants(Matrix matrix, MatrixCell[] constantCol){
        int[] leadingNonZeroPos = new int[matrix.getMatrix().length];
        for(int i = 0; i < matrix.getMatrix().length; i++){
            leadingNonZeroPos[i] = matrix.leadingNonZeroIndex(i);
            if(leadingNonZeroPos[i] == Integer.MAX_VALUE){
                leadingNonZeroPos[i] = matrix.getMatrix().length;
            }
        }
        int temp;
        int pos = 0;
        for(int j = 0; j < matrix.getMatrix().length; j++){
            for(int k = pos; k < matrix.getMatrix().length; k++){
                if(leadingNonZeroPos[k] == j){
                    temp = leadingNonZeroPos[pos];
                    leadingNonZeroPos[pos] = leadingNonZeroPos[k];
                    leadingNonZeroPos[k] = temp;
                    MatrixCell swap = constantCol[pos];
                    constantCol[pos] = constantCol[k];
                    constantCol[k] = swap;
                    matrix.swapRow(pos++, k);
                }
            }
        }
    }

    /**
     * Prints both the specified main matrix and matrix that is to resemble the inverted
     * matrix from the inversion algorithm for debugging purposes.
     * 
     * @param original The main matrix to conver to the identity matrix.
     * @param toInvert The matrix to resemble the inverted matrix after the inversion algorithm.
     */
    private static void printBothMatricesInversion(Matrix original, Matrix toInvert){
        System.out.println("Original Matrix:");
        System.out.println(original);
        System.out.println("Inversion Matrix:");
        System.out.println(toInvert);
    }

    public static String matrixAndConstantColToString(Matrix matrix, MatrixCell[] constantCol){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < matrix.getMatrix().length; i++){
            for(int j = 0; j < matrix.getMatrix()[i].length; j++){
                stringBuilder.append(matrix.getMatrix()[i][j] + "        ");
            }
            stringBuilder.append("|        " + constantCol[i] + "\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Performs the inversion algorithm on the specified matrix.  Will halt the algorithm if
     * a column or row of zeros is introduced in the matrix, signifiying that the determinant
     * of the matrix of zero and hence, not invertible.
     * 
     * @param matrix The matrix to be inverted.
     * @return The inverted matrix.
     */
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

    /**
     * Returns the determinant of the specified matrix.
     * 
     * @param matrix The matrix to have its determiannt computed.
     * @return The determinant of the specified matrix.
     */
    public static String determinant(Matrix matrix){
        matrix.convertToRefForm();
        return matrix.mainDiagonalProduct();
    }
}
