import java.io.*;
import java.util.*;
public class TextSteganography {
    // Encode and hide message
    public static void encode(String coverFile, String outputFile, String secretMessage)
            throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(coverFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        StringBuilder binary = new StringBuilder();
        // Convert message to binary
        for (char c : secretMessage.toCharArray()) {
            binary.append(String.format("%8s",
                    Integer.toBinaryString(c)).replace(' ', '0'));
        }
        String line;
        int index = 0;
        while ((line = br.readLine()) != null) {

            // Add invisible spaces based on binary bits
            if (index < binary.length()) {

                if (binary.charAt(index) == '1') {
                    line += " "; // one space
                } else {
                    line += "  "; // two spaces
                }
                index++;
            }
            bw.write(line);
            bw.newLine();
        }
        br.close();
        bw.close();
        System.out.println("Message Hidden Successfully.");
    }
    // Decode hidden message
    public static void decode(String encodedFile, int messageLength)
            throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(encodedFile));
        StringBuilder binary = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.endsWith("  ")) {
                binary.append("0");
            } else if (line.endsWith(" ")) {
                binary.append("1");
            }
        }
        br.close();
        StringBuilder message = new StringBuilder();
        // Convert binary to text
        for (int i = 0; i < messageLength * 8; i += 8) {
            String byteStr = binary.substring(i, i + 8);
            int charCode = Integer.parseInt(byteStr, 2);
            message.append((char) charCode);
        }
        System.out.println("Hidden Message: " + message);
    }
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            // Input cover file and secret message
            System.out.print("Enter Secret Message: ");
            String secret = sc.nextLine();
            // Encode message
            encode("cover.txt", "encoded.txt", secret);
            // Decode message
            decode("encoded.txt", secret.length());
            sc.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}