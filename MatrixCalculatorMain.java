import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.util.Scanner;

/**
 * Defines the driver class prompting user input of a matrix in the form
 * [1 2 3; 1 2 3; 1 2 3] and offering choices of conversion into REF or
 * RREF form, inversion, or computing the determinant of the matrix.
 *
 * @author JZ-FSDev
 * @since 17.0.1
 * @version 0.0.1
 */
public class MatrixCalculatorMain {

    public static void main(String[] args) {
        String input;
        input = JOptionPane.showInputDialog("Enter a square matrix to invert in the form [1 2 3; 1 2 3; 1 2 3]:");
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        String[] token = input.split(";");
        for (String s : token) {
            s.trim();
        }

        String[] nums = new String[token.length * token.length];

        Scanner scan;
        int count = 0;
        for (int i = 0; i < token.length; i++) {
            scan = new Scanner(token[i]);
            while (scan.hasNext()) {
                nums[count++] = scan.next();
            }
        }

        Matrix matrix = new Matrix(token.length, token.length, nums);

        String[] choices = { "Inverse", "Determinant", "REF", "RREF" };
        input = (String) JOptionPane.showInputDialog(null, "Choose an operation:", "Matrix Calculator",
                JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        JFrame parent = new JFrame();
        switch (input) {
            case "Inverse":
                Matrix inverse = MatrixAlgorithms.invert(matrix);
                if (!matrix.hasRowZeros() && !matrix.hasColZeros()) {
                    JOptionPane.showMessageDialog(parent, inverse);
                } else {
                    JOptionPane.showMessageDialog(parent, "No inverse exists");
                }
                break;
            case "Determinant":
                JOptionPane.showMessageDialog(parent, MatrixAlgorithms.determinant(matrix));
                break;
            case "REF":
                input = JOptionPane
                        .showInputDialog("Enter the constants column in the form: 1 2 3");
                input = input.trim();
                token = input.split(" ");
                MatrixCell[] constantCol = new MatrixCell[token.length];
                for (int i = 0; i < constantCol.length; i++) {
                    constantCol[i] = new MatrixCell(Integer.parseInt(token[i]), 1);
                }
                MatrixAlgorithms.convertToRefFormWithConstants(matrix, constantCol);
                String s1 = "Constant values from top to bottom: ";
                for (int i = 0; i < constantCol.length; i++) {
                    s1 += constantCol[i] + "  ";
                }

                JOptionPane.showMessageDialog(parent, matrix + "\n" + s1);
                break;
            case "RREF":
                input = JOptionPane
                        .showInputDialog("Enter the constants column in the form: 1 2 3");
                input = input.trim();
                token = input.split(" ");

                MatrixCell[] constantCol2 = new MatrixCell[token.length];
                for (int i = 0; i < constantCol2.length; i++) {
                    constantCol2[i] = new MatrixCell(Integer.parseInt(token[i]), 1);
                }

                MatrixAlgorithms.convertToRefFormWithConstants(matrix, constantCol2);
                MatrixAlgorithms.convertToRrefFormWithConstants(matrix, constantCol2);
                String s2 = "Constant values from top to bottom: ";
                for (int i = 0; i < constantCol2.length; i++) {
                    s2 += constantCol2[i] + "  ";
                }

                JOptionPane.showMessageDialog(parent, matrix + "\n" + s2);
                break;
        }
        System.exit(0);
    }
}
