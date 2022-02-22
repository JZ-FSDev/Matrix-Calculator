import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.util.Scanner;

/**
 * Defines the driver class prompting user input of a matrix in the form [1 2 3; 1 2 3; 1 2 3]
 * and offering choices of conversion into REF or RREF form, inversion, or computing the
 * determinant of the matrix.
 *
 * @author JZ-FSDev
 * @since 17.0.1
 * @version 0.0.1
 */
public class MatrixCalculatorMain{

    public static void main(String[] args) {
        String input;
        input = JOptionPane.showInputDialog("Enter a squar matrix to invert in the form [1 2 3; 1 2 3; 1 2 3]:");
        Scanner scan;
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        String[] token = input.split(";");
        for(String s: token){
            s.trim();
        }

        String[] nums = new String[token.length * token.length];

        int count = 0;
        for(int i = 0; i < token.length; i++){
            scan = new Scanner(token[i]);
            while(scan.hasNext()){
               nums[count++] = scan.next();
            }
        }
        
        Matrix matrix = new Matrix(token.length, token.length, nums);

        String[] choices = {"Inverse", "Determinant", "REF", "RREF"};
        input = (String)JOptionPane.showInputDialog(null, "Choose an operation:", "Matrix Calculator", 
                JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        JFrame parent = new JFrame();        
        switch(input){
            case "Inverse":
                Matrix inverse = MatrixAlgorithms.invert(matrix);
                JOptionPane.showMessageDialog(parent, inverse);
                break;
            case "Determinant":
                JOptionPane.showMessageDialog(parent, MatrixAlgorithms.determinant(matrix));
                break;
            case "REF":
                matrix.convertToRefForm();
                JOptionPane.showMessageDialog(parent, matrix);
                break;
            case "RREF":
                matrix.convertToRefForm();
                matrix.convertToRrefForm();
                JOptionPane.showMessageDialog(parent, matrix);
                break;
        }
        System.exit(0);
    }
}
