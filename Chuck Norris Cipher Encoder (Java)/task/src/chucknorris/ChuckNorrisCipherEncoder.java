package chucknorris;

import java.util.Objects;
import java.util.Scanner;

public class ChuckNorrisCipherEncoder {

    private static final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true){
            System.out.println("Please input operation (encode/decode/exit):");
            String operationType = scanner.nextLine();

            switch (operationType){
                case "encode":
                    System.out.println("Input string:");
                    String input = scanner.nextLine();
                    String binary = to7BitBinary(input);
                    String encoded = encodeChuckNorris(binary);
                    System.out.println("Encoded string:");
                    System.out.println(encoded);
                    break;
                case "decode":
                    System.out.println("Input encoded string:");
                    String encodedInput = scanner.nextLine();

                    if (!isValidEncodedString(encodedInput)) {
                        System.out.println("Encoded string is not valid.");
                        break;
                    }

                    String binaryDecoded = decodeChuckNorris(encodedInput);
                    String decoded = sevenBitBinaryToString(binaryDecoded);
                    System.out.println("Decoded string:");
                    System.out.println(decoded);
                    break;
                case "exit":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.printf("There is no '%s' operation\n", operationType);

            }
        }



    }


    private String to7BitBinary(String input) {
        StringBuilder binaryBuilder = new StringBuilder();
        for (char c : input.toCharArray()) {
            String bin = Integer.toBinaryString(c);
            String padded = String.format("%7s", bin).replace(' ', '0');
            binaryBuilder.append(padded);
        }
        return binaryBuilder.toString();
    }


    private String encodeChuckNorris(String binary) {
        StringBuilder result = new StringBuilder();

        int i = 0;
        while (i < binary.length()) {
            char currentBit = binary.charAt(i);
            int count = 0;

            // Zähle gleichbleibende Bits
            while (i < binary.length() && binary.charAt(i) == currentBit) {
                count++;
                i++;
            }


            if (currentBit == '1') {
                result.append("0 ");
            } else {
                result.append("00 ");
            }


            result.append("0".repeat(count));


            if (i < binary.length()) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    private String decodeChuckNorris(String binary){

        String[] binaryValues = binary.split("\\s+");
        StringBuilder decodeBuilder = new StringBuilder();

        for(int i = 0; i < binaryValues.length; i+=2){
            String valueType = binaryValues[i];
            int valueAmount = binaryValues[i + 1].length();
            char value;
            if(Objects.equals(valueType, "00")){
                value = '0';
            }else if(Objects.equals(valueType, "0")){
                value = '1';
            }else{
                return "Not a valid String";
            }

            decodeBuilder.append(String.valueOf(value).repeat(valueAmount));
        }
        return decodeBuilder.toString();


    }

    private String sevenBitBinaryToString(String binary){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < binary.length(); i += 7){
            String segment = binary.substring(i, i + 7);
            int charCode = Integer.parseInt(segment, 2);
            result.append((char) charCode);
        }
        return result.toString();
    }

    private static boolean isValidEncodedString(String encoded) {
        String[] parts = encoded.trim().split("\\s+");

        if (!encoded.matches("[0 ]+")) return false;

        if (parts.length % 2 != 0) return false;

        for (int i = 0; i < parts.length; i += 2) {
            if (!parts[i].equals("0") && !parts[i].equals("00")) return false;
        }

        int totalBits = 0;
        for (int i = 1; i < parts.length; i += 2) {
            totalBits += parts[i].length();
        }

        return totalBits % 7 == 0;
    }

}
