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
        int index = matrix.length;
        for(int i = 0; i < matrix.length && index == matrix.length; i++){
            if(matrix[row][i].getNumerator() != 0){
                index = i;
            }
        }
        return index;
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
    
    public void changeLeadNonZeroToOneSimult(int row, Matrix other){
        System.out.println("changeLeadNonZeroToOneSimult");
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
}
