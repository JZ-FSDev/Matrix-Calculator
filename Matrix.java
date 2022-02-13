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
}
