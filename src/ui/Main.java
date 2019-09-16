package ui;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String pathToOriginalFile = "./mensagem.txt";
        String pathToCriptoFile = "./cripto.txt";

        System.out.println("Entre com o shift da criptografia de C\u00e9sar:\n");
        Scanner sc = new Scanner(System.in);
        int cesarShift = sc.nextInt();
        CriptoFile cf = new CriptoFile(pathToOriginalFile, pathToCriptoFile, cesarShift);
        cf.CesarCripto();
        cf.compressHuffman();
    }
}
