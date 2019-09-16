package ui;

import Crypto.Cesar;
import Crypto.Huffman;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String pathToOriginalFile = "./mensagem.txt";
        String pathToCriptoFile = "./cripto.txt";
        String pathToCompressedFile = "./compressed.txt";

        System.out.println("Entre com o shift da criptografia de C\u00e9sar:\n");
        Scanner sc = new Scanner(System.in);
        int cesarShift = sc.nextInt();
        Cesar cf = new Cesar(pathToOriginalFile, pathToCriptoFile, cesarShift);
        cf.CesarCripto();
        Huffman hf = new Huffman(pathToCriptoFile, pathToCompressedFile);
        hf.compressHuffman();
    }
}
