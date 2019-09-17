package Crypto;

import com.sun.tools.jconsole.JConsoleContext;

import java.io.*;
import java.util.HashMap;

public class Cesar {
    private String pathToOriginalFile;
    private String pathToCriptoFile;
    int CesarShift;

    public Cesar(String pathToOriginalFile, String pathToCriptoFile, int CesarShift){
        this.pathToOriginalFile = pathToOriginalFile;
        this.pathToCriptoFile = pathToCriptoFile;
        this.CesarShift = CesarShift;
    }

    public void CesarCripto(){
        try {
            // Abre o arquivo e verifica se existe.
            File message_file = new File(this.pathToOriginalFile);
            if (!message_file.exists()){
                throw new FileNotFoundException();
            }
            FileReader fr = new FileReader(message_file);

            // Abre o arquivo e verifica se existe. Caso exista, cria um novo.
            File cripto_file = new File(this.pathToCriptoFile);
            if (cripto_file.exists()){
                cripto_file.delete();
            }
            cripto_file.createNewFile();
            BufferedWriter fw = new BufferedWriter(new FileWriter(cripto_file, true));

            //Lê o arquivo original e escreve no novo arquivo;
            int i;
            while((i=fr.read()) != -1){
                fw.append(this.CesarCriptoChar(i));
            }

            //fecha os arquivos
            fw.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String CesarReverse (String message) {
        char[] messageArray = message.toCharArray();
        for (int i = 0; i < messageArray.length; i++){
            messageArray[i] = this.CesarCriptoCharReverse(messageArray[i]);
        }
        return new String(messageArray);
    }

    private char CesarCriptoChar(int c){
        // Retorna o caractere cifrado
        if (c < 65 || (c > 90 && c < 97) || c > 122) {
            return (char) c;
        }

        if (c < 91){
            return (char) (((c-65)%25)+65 + this.CesarShift);
        }

        return (char) (((c-97)%25)+97 + this.CesarShift);
    }

    private char CesarCriptoCharReverse(int c){
        // Retorna o caractere cifrado
        if (c < 65 || (c > 90 && c < 97) || c > 122) {
            return (char) c;
        }

        if (c < 91){
            return (char) (((c-65)%25)+65 - this.CesarShift);
        }

        return (char) (((c-97)%25)+97 - this.CesarShift);
    }
}