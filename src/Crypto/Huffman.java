package Crypto;

import java.io.*;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

public class Huffman {
    private String pathToOriginalFile;
    private String pathToHuffmanFile;
    private HashMap<Character, String> huffmanMap = new HashMap<>();
    private HuffmanNode huffmanTree;

    public Huffman(String pathToOriginalFile, String pathToHuffmanFile){
        this.pathToOriginalFile = pathToOriginalFile;
        this.pathToHuffmanFile = pathToHuffmanFile;
    }

    public void compressHuffman(){
        try {
            //montar ávore de huffman
            File cripto_file = new File(this.pathToOriginalFile);
            if (!cripto_file.exists()) {
                throw new FileNotFoundException();
            }
            HashMap<Character, Integer> freq_map = this.getCharFrequenciesMap(cripto_file);
            assert freq_map != null;
            System.out.println("O mapa de frequência é:\n" + freq_map.toString() + "\n\n");
            this.huffmanTree = this.buildTree(freq_map);
            this.getHuffmanMap(this.huffmanTree, new StringBuilder());
            //escrever no novo arquivo
            File huffman_file = new File(this.pathToHuffmanFile);
            if (huffman_file.exists()){
                huffman_file.delete();
            }
            huffman_file.createNewFile();
            writeNewFile(cripto_file, huffman_file, this.huffmanMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<Character, Integer> getCharFrequenciesMap(File criptoFile){
        try {
            FileReader fr = new FileReader(criptoFile);
            int i;
            // Creating an empty HashMap
            HashMap<Character, Integer> freq_map = new HashMap<Character, Integer>();
            while((i=fr.read()) != -1){
                int oldValue = freq_map.getOrDefault((char) i, 0);
                freq_map.put((char) i, oldValue + 1);
            }
            return freq_map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HuffmanNode buildTree(HashMap<Character, Integer> freqMap){
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        Set<Character> keySet = freqMap.keySet();

        for (Character c : keySet) {
            HuffmanNode huffmanNode = new HuffmanNode();
            huffmanNode.data = c;
            huffmanNode.frequency = freqMap.get(c);
            huffmanNode.left = null;
            huffmanNode.right = null;
            priorityQueue.offer(huffmanNode);
        }

        assert priorityQueue.size() > 0;

        while (priorityQueue.size() > 1) {

            HuffmanNode x = priorityQueue.peek();
            priorityQueue.poll();

            HuffmanNode y = priorityQueue.peek();
            priorityQueue.poll();

            HuffmanNode sum = new HuffmanNode();

            sum.frequency = x.frequency + y.frequency;
            sum.data = '-';

            sum.left = x;

            sum.right = y;

            priorityQueue.offer(sum);
        }

        return priorityQueue.poll();
    }

    private void getHuffmanMap(HuffmanNode node, StringBuilder prefix) {
        if (node != null) {
            if (node.left == null && node.right == null) {
                this.huffmanMap.put(node.data, prefix.toString());

            } else {
                prefix.append('0');
                getHuffmanMap(node.left, prefix);
                prefix.deleteCharAt(prefix.length() - 1);

                prefix.append('1');
                getHuffmanMap(node.right, prefix);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }

    private void writeNewFile (File criptoFile, File huffmanFile, HashMap<Character, String> huffmanMap){
        try {
            FileReader fr = new FileReader(criptoFile);
            FileWriter fw = new FileWriter(huffmanFile, true);
            int i;
            while((i=fr.read()) != -1){
                fw.append(huffmanMap.get((char) i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String decompressHuffman(){
        try {
            StringBuilder stringBuilder = new StringBuilder();
            HuffmanNode temp = this.huffmanTree;
            File huffman_file = new File(this.pathToHuffmanFile);
            if (!huffman_file.exists()) {
                throw new FileNotFoundException();
            }
            FileReader fr = new FileReader(huffman_file);
            int i;
            while((i=fr.read()) != -1){
                int j = Integer.parseInt(String.valueOf((char) i));
                if (j == 0) {
                    temp = temp.left;
                    if (temp.left == null && temp.right == null) {
                        stringBuilder.append(temp.data);
                        temp = this.huffmanTree;
                    }
                }
                if (j == 1) {
                    temp = temp.right;
                    if (temp.left == null && temp.right == null) {
                        stringBuilder.append(temp.data);
                        temp = this.huffmanTree;
                    }
                }
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

class HuffmanNode implements Comparable<HuffmanNode> {
    int frequency;
    char data;
    HuffmanNode left, right;

    public int compareTo(HuffmanNode node) {
        return frequency - node.frequency;
    }
}
