
import java.io.*;
import java.util.Scanner;

public class HuffmanEncoding {
    public static void main(String[] args) throws IOException {
        // Open the file with Scanner
            File FileReader = new File("input.txt");
            Scanner input = new Scanner(FileReader);

            // Read the first line as an integer
            int n = Integer.parseInt(input.nextLine().trim());
            char[] symbols = new char[n];
            double[] frequency = new double[n];

            // Read the remaining lines for symbols and frequencies
            for (int i = 0; i < n; i++) {
                
                String[] parts = input.nextLine().trim().split(" ");
                symbols[i] = parts[0].charAt(0);
                frequency[i] = Double.parseDouble(parts[1]);
            }

        // Measure runtime for Min-Heap implementation
        long startTime = System.nanoTime();
        TreeNode rootMinHeap = buildMinHeap(symbols, frequency);
        long minHeapTime = System.nanoTime() - startTime;

        // Measure runtime for Ordered Array implementation
        startTime = System.nanoTime();
        TreeNode rootOrderedArray = buildOrderedArray(symbols, frequency);
        long orderedArrayTime = System.nanoTime() - startTime;

        // Create Huffman Binary Codes for Min-Heap implementation
        char[] Symbols_Code = new char[n];
        String[] Codes = new String[n];
        int[] index = {0};
        buildBinaryCode(rootMinHeap, "", Symbols_Code, Codes, index);

        // Sort Huffman Codes Alphabetically
        bubbleSort(Symbols_Code, Codes, n);

        // Display Huffman Tree
        System.out.println("Huffman Encoding Tree for given characters:");
        displayTree(rootMinHeap, 0);

        // Display Huffman Codes
        System.out.println("\nCodes for input characters are:");
        for (int i = 0; i < n; i++) {
            System.out.println(Symbols_Code[i] + ": " + Codes[i]);
        }

        // Display runtime information
        Scanner input1 = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter the message to encode: ");
            String message = input1.nextLine().trim();
            String Message_Code = "";

            boolean valid = true;
            for (char c : message.toCharArray()) {
                boolean found = false;
                for (int i = 0; i < n; i++) {
                    if (Symbols_Code[i] == c) {
                        Message_Code += Codes[i];
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Error: Encountered disallowed character");
                    valid = false;
                    break;
                }
            }

            if (valid) {
                System.out.println("The Huffman code for this message: " + Message_Code);

                // Calculate and display compression percentage
                int originalSize = message.length() * 8;
                int compressedSize = Message_Code.length();
                double compressionPercentage = ((originalSize - compressedSize) / (double) originalSize) * 100;

                System.out.printf("Compression percentage for above message = %.2f%%%n", compressionPercentage);
                System.out.printf("Running time of Huffman's algorithm using Min-Heap as Min-Priority Queue = "+minHeapTime+" Nano seconds%n");
                System.out.printf("Running time of Huffman's algorithm using Ordered Array as Min-Priority Queue = "+orderedArrayTime+" Nano seconds%n");

                break;
            }
        }
    }
   

    private static TreeNode buildMinHeap(char[] symbols, double[] frequencies) {
        int n = symbols.length;
        MinHeap minHeap = new MinHeap(n);

        // Insert symbols into the MinHeap
        for (int i = 0; i < n; i++) {
            minHeap.insert(new TreeNode(symbols[i], frequencies[i]));
        }

        // Build Huffman Tree
        while (minHeap.getSize() > 1) {
            TreeNode left = minHeap.remove();
            TreeNode right = minHeap.remove();

            // Combine two nodes into a new parent node
            TreeNode parent = new TreeNode(' ', left.freq + right.freq);
            parent.left = left;
            parent.right = right;

            // Insert the new parent node back into the MinHeap
            minHeap.insert(parent);
        }

        return minHeap.remove(); // Root of the Huffman Tree
    }

    private static TreeNode buildOrderedArray(char[] symbols, double[] frequencies) {
        int n = symbols.length;
        OrderedArray orderedArray = new OrderedArray(n);

        for (int i = 0; i < n; i++) {
            orderedArray.insert(new TreeNode(symbols[i], frequencies[i]));
        }

        while (orderedArray.getSize() > 1) {
            TreeNode x = orderedArray.remove();
            TreeNode y = orderedArray.remove();

            TreeNode z = new TreeNode(' ', x.freq + y.freq);
            z.left = x;
            z.right = y;

            orderedArray.insert(z);
        }

        return orderedArray.remove();
    }
 private static void buildBinaryCode(TreeNode root, String code, char[] symbols, String[] codes, int[] index) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            symbols[index[0]] = root.symbol;
            codes[index[0]] = code;
            index[0]++;
        }
        buildBinaryCode(root.left, code + "0", symbols, codes, index);
        buildBinaryCode(root.right, code + "1", symbols, codes, index);
    }
    private static void bubbleSort(char[] symbols, String[] codes, int size) {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (symbols[j] > symbols[j + 1]) {
                    // Swap symbols
                    char tempSymbol = symbols[j];
                    symbols[j] = symbols[j + 1];
                    symbols[j + 1] = tempSymbol;

                    // Swap corresponding codes
                    String tempCode = codes[j];
                    codes[j] = codes[j + 1];
                    codes[j + 1] = tempCode;
                }
            }
        }
    }

   private static void displayTree(TreeNode root, int level) {
       
    if (root == null) return;

    // Display right subtree first (in reverse order for visualization)
    displayTree(root.right, level + 1);

    // Print the current node with indentation proportional to the level
    System.out.println(" ".repeat(level * 4) + (root.symbol != ' ' ? root.symbol + " (" + root.freq + ")" : "(" + root.freq + ")"));

    // Display left subtree
    displayTree(root.left, level + 1);
}

}
