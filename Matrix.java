import java.util.Scanner;

/**
 * Defines a matrix of matrix cells.
 *
 * @author JZ-FSDev
 * @since 17.0.1
 * @version 0.0.1
 */
public class Matrix {
    
    private MatrixCell[][] matrix;

    /**
     * Creates a matrix of matrix cells of specified width, length, and
     * rational numbers in the form of a String array.
     * 
     * @param width The width in matrix cells of the matrix.
     * @param length The length in matrix cells of the matrix.
     * @param nums The rational numbers for each of the matrix cells where each index
     *             of the String array has rational or whole number(s) separated by spaces.
     */
    public Matrix(int width, int length, String[] nums){
        matrix = new MatrixCell[width][length];
        int count = 0;
        Scanner scan;
        String[] temp;
        for(int i = 0; i < matrix.length; i++){
            for(int k = 0; k < matrix[i].length; k++){
                scan = new Scanner(nums[count]);
                temp = nums[count++].split("/");
                if(temp.length == 2){
                    matrix[i][k] = new MatrixCell(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
                }else{
                    matrix[i][k] = new MatrixCell(Integer.parseInt(temp[0]), 1);
                }
                scan.close();
            }
        }
    }

    /**
     * Creates a matrix based on the specified 2D matrix cell array.
     *  
     * @param matrix The matrix cell 2D array used to create the matrix.
     */
    public Matrix(MatrixCell[][] matrix){
        this.matrix = matrix;
    }

    /**
     * Returns a 2D matrix cell array representing the identity matrix of specified size n.
     * 
     * @param n The size (length and width) of the identity matrix cell 2D array.
     * @return A 2D matrix cell array representing the identity matrix of specified size n.
     */
    public static MatrixCell[][] identityMatrix(int n){
        MatrixCell[][] toReturn = new MatrixCell[n][n];
        for(int i = 0; i < toReturn.length; i++){
            for(int k = 0; k < toReturn.length; k++){
                if(i == k){
                    toReturn[i][k] = new MatrixCell(1, 1);
                }else{
                    toReturn[i][k] = new MatrixCell(0, 1);
                }
            }
        }
        return toReturn;
    }

    /**
     * Returns the main diagonal product of this matrix as a simplified lowest form rational
     * number as a String.
     * 
     * @return The main diagonal product of this matrix as a simplified lowest form rational
     *         number as a String.
     */
    public String mainDiagonalProduct(){
        int productNumerator = 1;
        int productDenominator = 1;
        for(int i = 0; i < matrix.length; i++){
            productNumerator *= matrix[i][i].getNumerator();
            productDenominator *= matrix[i][i].getDenominator();
        }
        MatrixCell temp = new MatrixCell(productNumerator, productDenominator);
        temp.simplify();
        return temp.toString();
    }

    /**
     * Converts this matrix to reduced row echelon form.
     */
    public void convertToRrefForm(){
        int numeratorScalar, denominatorScalar;
        for(int i = matrix.length - 1; i > 0; i--){
            for(int j = i - 1; j >= 0; j--){
                if(trailingNonZeroIndex(j) == trailingNonZeroIndex(i)){
                    numeratorScalar = Math.abs(matrix[j][trailingNonZeroIndex(j)].getNumerator());
                    denominatorScalar = matrix[j][trailingNonZeroIndex(j)].getDenominator();
                    if(matrix[j][trailingNonZeroIndex(j)].getNumerator() < 0){
                        rowOperation(j, i, numeratorScalar, denominatorScalar);
                    }else{
                        rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                    }
                }
            }
        }
    }

    /**
     * Converts this matrix to row echelon form.
     */
    public void convertToRefForm(){
        int numeratorScalar, denominatorScalar;
        for(int i = 0; i < matrix.length; i++){
            int topRow = leadingNonZeroIndex(i);
            System.out.println(this);
            sortByLeadingNonZero();
            if(topRow != -1){
                changeLeadNonZeroToOne(i);
                for(int j = i + 1; j < matrix[i].length; j++){
                    int lowerRow = leadingNonZeroIndex(j);
                    if(leadingNonZeroIndex(j) == leadingNonZeroIndex(i)){
                        numeratorScalar = Math.abs(matrix[j][lowerRow].getNumerator());
                        denominatorScalar = matrix[j][lowerRow].getDenominator();
                        if(matrix[j][lowerRow].getNumerator() < 0){
                            rowOperation(j, i, numeratorScalar, denominatorScalar);
                        }else{
                            rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                        }
                    }
                }
            }
        }
    }

    /**
     * Multiplies the specified row of this matrix with the appropriate
     * scalar to convert the the leading non-zero cell to the number 1.
     * 
     * @param row The row to convert to a leading 1.
     */
    public void changeLeadNonZeroToOne(int row){
        int leadindColIndex = leadingNonZeroIndex(row);
        if(leadindColIndex == -1){
            leadindColIndex = getMatrix().length - 1;
        }
        int leadNumeratorScalar = matrix[row][leadindColIndex].getNumerator();
        int leadDenominatorScalar = matrix[row][leadindColIndex].getDenominator();
        int numerator, denominator;
        for(int i = 0; i < matrix.length; i++){
            numerator = matrix[row][i].getNumerator();
            denominator = matrix[row][i].getDenominator();
            if(numerator != 0){
                matrix[row][i].setNumerator(numerator * leadDenominatorScalar);
                matrix[row][i].setDenominator(denominator * leadNumeratorScalar);
                matrix[row][i].simplify();
            }
        }
    }

    /**
     * Sorts this matrix by left most leading non-zero rows at the top of the matrix
     * with decreasing left leading non-zero rows under.  Full zero rows will be
     * arranged to the bottom of the matrix.
     */
    public void sortByLeadingNonZero(){
        int[] leadingNonZeroPos = new int[matrix.length];
        for(int i = 0; i < matrix.length; i++){
            leadingNonZeroPos[i] = leadingNonZeroIndex(i);
        }
        int temp;
        int pos = 0;
        for(int j = 0; j < matrix.length; j++){
            for(int k = pos; k < matrix.length; k++){
                if(leadingNonZeroPos[k] == j){
                    temp = leadingNonZeroPos[pos];
                    leadingNonZeroPos[pos] = leadingNonZeroPos[k];
                    leadingNonZeroPos[k] = temp;
                    swapRow(pos++, k);
                }
            }
        }
    }

    /**
     * Return the index of the first matrix cell of this matrix from the left
     * of the specified row that is a non-zero cell or -1 if the entire row is zeros.
     * 
     * @param row The row to return the index of the first matrix cell from the left that is non-zero.
     * @return The index of the first matrix cell of this matrix from the left of the specified row that
     *         is a non-zero cell or -1 if the entire row is zeros.
     */
    public int leadingNonZeroIndex(int row){
        int index = -1;
        for(int i = 0; i < matrix.length && index == -1; i++){
            if(matrix[row][i].getNumerator() != 0){
                index = i;
            }
        }
        return index;
    }

    /**
     * Return the index of the first matrix cell of this matrix from the right
     * of the specified row that is a non-zero cell.
     * 
     * @param row The row to return the index of the first matrix cell from the right that is non-zero.
     * @return The index of the first matrix cell of this matrix from the right of the specified row that
     *         is a non-zero cell.
     */
    public int trailingNonZeroIndex(int row){
        int index = 0;
        for(int i = matrix.length - 1; i >= 0 && index == 0; i--){
            if(matrix[row][i].getNumerator() != 0){
                index = i;
            }
        }
        return index;
    }

    /**
     * Swaps the specified two rows of this matrix.
     * 
     * @param row1 The row to swap with the other specified row.
     * @param row2 The row to swap with the other specified row.
     */
    public void swapRow(int row1, int row2){
        MatrixCell swap;
        for(int i = 0; i < matrix.length; i++){
            swap = matrix[row1][i];
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = swap;
        }
    }

    /**
     * Performs a row operation by multiplying the specified numerator and denominator scalar
     * to the specified row2 and adds it to row 1.
     * 
     * @param row1 The row to be added the scalar mutiplied row2. 
     * @param row2 The row to be multiplied by the numerator and denominator scalars.
     * @param numeratorScalar The scalar to multiply the numerator of row2.
     * @param denominatorScalar The scalar to multiply the denominator or row2.
     */
    public void rowOperation(int row1, int row2, int numeratorScalar, int denominatorScalar){
        for(int i = 0; i < matrix.length; i++){
            matrix[row1][i].addScalarMultipleCell(matrix[row2][i], numeratorScalar, denominatorScalar);
        }
    }

    /**
     * Returns true if the specified rows are scalar multiples of one another, false otherwise.
     * 
     * @param row1 The first row to compare with the other row for scalar multiplicity.
     * @param row2 The second row to compare with the other row for scalar multiplicity.
     * @return True if the specified rows are scalar multiples of one another, false otherwise.
     */
    public boolean isRowScalarMultiple(int row1, int row2){
        boolean rowScalarMultiple = true;
        boolean zeroColumn;
        double row1Value, row2Value;
        double scalarMultiple = 0;
        for(int i = 0; i < matrix[0].length && rowScalarMultiple; i++){
            row1Value = matrix[row1][i].decimalValue();
            row2Value = matrix[row2][i].decimalValue();
            if((row1Value == 0 && row2Value != 0) || (row1Value != 0 && row2Value == 0)){
                rowScalarMultiple = false;
            }else{
                zeroColumn = row1Value == 0 && row1Value == 0;
                if(!zeroColumn && scalarMultiple == 0){
                    scalarMultiple = row1Value/row2Value;
                }
                if(!zeroColumn && row1Value / row2Value != scalarMultiple){
                    rowScalarMultiple = false;
                }
            }
        }
        return rowScalarMultiple;
    }

    /**
     * Returns true if this matrix is in identity form, false otherwise.
     * 
     * @return True if this matrix is in identity form, false otherwise.
     */
    public boolean isIdentityForm(){
        boolean identityForm = true;
        for(int i = 0; i < matrix.length && identityForm; i ++){
            for(int k = 0; k < matrix[i].length && identityForm; k++){
                if(i == k && matrix[i][k].decimalValue() != 1){
                    identityForm = false;
                }else if(i != k && matrix[i][k].decimalValue() != 0){
                    identityForm = false;
                }
            }
        }
        return identityForm;
    }

    /**
     * Returns true if this matrix has a row of zeros, false otherwise.
     * 
     * @return True if this matrix has a row of zeros, false otherwise.
     */
    public boolean hasRowZeros(){
        boolean zeros;
        boolean rowZeros = false;
        for(int i = 0; i < matrix[0].length && !rowZeros; i++){
            zeros = true;
            for(int k = 0; k < matrix.length && zeros; k++){
                if(matrix[i][k].getNumerator() != 0){
                    zeros = false;
                }
            }
            if(zeros){
                rowZeros = true;
            }
        }
        return rowZeros;
    }

    /**
     * Returns true if this matrix has a column of zeros, false otherwise.
     * 
     * @return True if this matrix has a column of zeros, false otherwise.
     */
    public boolean hasColZeros(){
        boolean zeros;
        boolean colZeros = false;
        for(int i = 0; i < matrix[0].length && !colZeros; i++){
            zeros = true;
            for(int k = 0; k < matrix.length && zeros; k++){
                if(matrix[k][i].getNumerator() != 0){
                    zeros = false;
                }
            }
            if(zeros){
                colZeros = true;
            }
        }
        return colZeros;
    }

    /**
     * Returns this matrix's 2D matrix cell array.
     * 
     * @return This matrix's 2D matrix cell array.
     */
    public MatrixCell[][] getMatrix(){
        return matrix;
    }

    /**
     * Returns this matrix as a String.
     * 
     * @return The String representation of this matrix.
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                stringBuilder.append(matrix[i][j]);
                stringBuilder.append("        ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
