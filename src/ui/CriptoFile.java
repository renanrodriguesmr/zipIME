package ui;

import com.sun.tools.jconsole.JConsoleContext;

import java.io.*;
import java.util.HashMap;

public class CriptoFile {
    private String pathToOriginalFile;
    private String pathToCriptoFile;
    int CesarShift;

    CriptoFile(String pathToOriginalFile, String pathToCriptoFile, int CesarShift){
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

    private char CesarCriptoChar(int c){
        if (c < 65 || (c > 90 && c < 97) || c > 122) {
            return (char) c;
        }

        if (c < 91){
            return (char) (((c-65)%25)+65 + this.CesarShift);
        }

        return (char) (((c-97)%25)+97 + this.CesarShift);
    }

    public void compressHuffman(){
        try {
            File cripto_file = new File(this.pathToCriptoFile);
            if (!cripto_file.exists()) {
                throw new FileNotFoundException();
            }
            HashMap<String, Integer> freq_map = this.getCharFrequenciesMap(cripto_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, Integer> getCharFrequenciesMap(File criptoFile){
        try {
            FileReader fr = new FileReader(criptoFile);
            int i;
            // Creating an empty HashMap
            HashMap<String, Integer> freq_map = new HashMap<String, Integer>();
            while((i=fr.read()) != -1){
                int oldValue = freq_map.getOrDefault(String.valueOf((char) i), 0);
                freq_map.put(String.valueOf((char) i), oldValue + 1);
            }
            System.out.println(freq_map.toString());
            return freq_map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class charFreq {
    private char key;
    private int freq;

    charFreq(char key, int freq){
        this.key = key;
        this.freq = freq;
    }


    public char getKey() {
        return key;
    }

    public int getFreq() {
        return freq;
    }
}
