import java.util.Scanner;

public class Matrix {
    
    private MatrixCell[][] matrix;

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
            }
        } 
    }

    public Matrix(MatrixCell[][] matrix){
        this.matrix = matrix;
    }

    public void printBothMatricesInversion(Matrix original, Matrix toInvert){
        System.out.println(original);
        System.out.println("toInvert Matrix:");
        System.out.println(toInvert);
    }

    public Matrix invert(){
        Matrix toInvert = new Matrix(identityMatrix(matrix.length));
        if(hasColZeros()){
            System.out.println("Since the matrix has a column of zeros, the determinant of the matrix is zero and thus the inverse does not exist");
        }else{
            printBothMatricesInversion(this, toInvert);
            convertToRefFormSimult(toInvert);
            if(hasRowZeros()){
                System.out.println("Since the matrix has a row of zeros, the determinant of the matrix is zero and thus the inverse does not exist");
            }else{
                convertToRrefForm(toInvert);
            }
        }
        return toInvert;
    }

    public void determinant(){
        convertToRefForm();
        mainDiagonalProduct();
    }

    public void mainDiagonalProduct(){
        int productNumerator = 1;
        int productDenominator = 1;
        for(int i = 0; i < matrix.length; i++){
            productNumerator *= matrix[i][i].getNumerator();
            productDenominator *= matrix[i][i].getDenominator();
        }

        System.out.println("" + productNumerator + "/" + productDenominator);
    }


    public void convertToRrefForm(Matrix toInvert){
        int numeratorScalar, denominatorScalar;
        for(int i = matrix.length - 1; i > 0; i--){
            for(int j = i - 1; j >= 0; j--){
                if(trailingNonZeroIndex(j) == trailingNonZeroIndex(i)){
                    numeratorScalar = matrix[j][trailingNonZeroIndex(j)].getNumerator();
                    denominatorScalar = matrix[j][trailingNonZeroIndex(j)].getDenominator();
                    if(matrix[j][trailingNonZeroIndex(j)].getNumerator() < 0){
                        rowOperation(j, i, numeratorScalar, denominatorScalar);
                        toInvert.rowOperation(j, i, numeratorScalar, denominatorScalar);
                    }else{
                        rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                        toInvert.rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                    }
                }
                printBothMatricesInversion(this, toInvert);
            }
        }
    }

    public void convertToRefFormSimult(Matrix toInvert){
        int numeratorScalar, denominatorScalar;
        for(int i = 0; i < matrix.length; i++){
            sortByLeadingNonZeroSimult(toInvert);
            changeLeadNonZeroToOneSimult(i, toInvert);
            printBothMatricesInversion(this, toInvert);
            for(int j = i + 1; j < matrix[i].length; j++){
                sortByLeadingNonZeroSimult(toInvert);
                if(leadingNonZeroIndex(j) == leadingNonZeroIndex(i)){
                    numeratorScalar = matrix[j][leadingNonZeroIndex(j)].getNumerator();
                    denominatorScalar = matrix[j][leadingNonZeroIndex(j)].getDenominator();
                    if(matrix[j][leadingNonZeroIndex(j)].getNumerator() < 0){
                        rowOperation(j, i, numeratorScalar, denominatorScalar);
                        toInvert.rowOperation(j, i, numeratorScalar, denominatorScalar);
                    }else{
                        rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                        toInvert.rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                    }
                }
                printBothMatricesInversion(this, toInvert);
            }
        }
    }

    public void convertToRefForm(){
        int numeratorScalar, denominatorScalar;
        for(int i = 0; i < matrix.length; i++){
            sortByLeadingNonZero();
            changeLeadNonZeroToOne(i);
            System.out.println(this);
            for(int j = i + 1; j < matrix[i].length; j++){
                sortByLeadingNonZero();
                if(leadingNonZeroIndex(j) == leadingNonZeroIndex(i)){
                    numeratorScalar = matrix[j][leadingNonZeroIndex(j)].getNumerator();
                    denominatorScalar = matrix[j][leadingNonZeroIndex(j)].getDenominator();
                    if(matrix[j][leadingNonZeroIndex(j)].getNumerator() < 0){
                        rowOperation(j, i, numeratorScalar, denominatorScalar);
                    }else{
                        rowOperation(j, i, -1 * numeratorScalar, denominatorScalar);
                    }
                }
                System.out.println(this);
            }
        }
    }


    public void changeLeadNonZeroToOne(int row){
        int leadNumeratorScalar = matrix[row][leadingNonZeroIndex(row)].getNumerator();
        int leadDenominatorScalar = matrix[row][leadingNonZeroIndex(row)].getDenominator();
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

    public void changeLeadNonZeroToOneSimult(int row, Matrix other){
        int leadNumeratorScalar = matrix[row][leadingNonZeroIndex(row)].getNumerator();
        int leadDenominatorScalar = matrix[row][leadingNonZeroIndex(row)].getDenominator();
        int numerator, denominator, otherNumerator, otherDenominator;
        for(int i = 0; i < matrix.length; i++){
            numerator = matrix[row][i].getNumerator();
            denominator = matrix[row][i].getDenominator();
            otherNumerator = other.matrix[row][i].getNumerator();
            otherDenominator = other.matrix[row][i].getDenominator();
            if(numerator != 0){
                matrix[row][i].setNumerator(numerator * leadDenominatorScalar);
                matrix[row][i].setDenominator(denominator * leadNumeratorScalar);
                matrix[row][i].simplify();
            }
            if(otherNumerator != 0){
                other.matrix[row][i].setNumerator(otherNumerator * leadDenominatorScalar);
                other.matrix[row][i].setDenominator(otherDenominator * leadNumeratorScalar);
                other.matrix[row][i].simplify();
            }
        }
    }

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

    public void sortByLeadingNonZeroSimult(Matrix other){
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
                    other.swapRow(pos, k);
                    swapRow(pos++, k);
                }
            }
        }
    }

    public int leadingNonZeroIndex(int row){
        int index = matrix.length-1;
        for(int i = 0; i < matrix.length && index == matrix.length-1; i++){
            if(matrix[row][i].getNumerator() != 0){
                index = i;
            }
        }
        return index;
    }

    public int trailingNonZeroIndex(int row){
        int index = 0;
        for(int i = matrix.length - 1; i >= 0 && index == 0; i--){
            if(matrix[row][i].getNumerator() != 0){
                index = i;
            }
        }
        return index;
    }

    public void swapRow(int row1, int row2){
        MatrixCell swap;
        for(int i = 0; i < matrix.length; i++){
            swap = matrix[row1][i];
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = swap;
        }
    }

    public void rowOperation(int row1, int row2, int numeratorScalar, int denominatorScalar){
        for(int i = 0; i < matrix.length; i++){
            matrix[row1][i].addScalarMultipleCell(matrix[row2][i], numeratorScalar, denominatorScalar);
        }
    }

    public MatrixCell[][] identityMatrix(int n){
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

    public boolean rowScalarMultiple(int row1, int row2){
        boolean rowScalarMultiple = true;
        boolean zeroColumn;
        double row1Value, row2Value;
        double scalarMultiple = 0;
        for(int i = 0; i < matrix[0].length && rowScalarMultiple; i++){
            row1Value = matrix[row1][i].numericalValue();
            row2Value = matrix[row2][i].numericalValue();
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

    public boolean identityForm(){
        boolean identityForm = true;
        for(int i = 0; i < matrix.length && identityForm; i ++){
            for(int k = 0; k < matrix[i].length && identityForm; k++){
                if(i == k && matrix[i][k].numericalValue() != 1){
                    identityForm = false;
                }else if(i != k && matrix[i][k].numericalValue() != 0){
                    identityForm = false;
                }
            }
        }
        return identityForm;
    }

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

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                stringBuilder.append(matrix[i][j]);
                stringBuilder.append("\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
