package Crypto;

import java.io.*;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

public class Huffman {
    private String pathToOriginalFile;
    private String pathToHuffmanFile;

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
            HuffmanNode huffman_tree = this.buildTree(freq_map);
            HashMap<Character, String> huffman_map = this.getHuffmanMap(huffman_tree, new HashMap<>(), new StringBuilder());
            //escrever no novo arquivo
            File huffman_file = new File(this.pathToHuffmanFile);
            if (huffman_file.exists()){
                huffman_file.delete();
            }
            huffman_file.createNewFile();
            writeNewFile(cripto_file, huffman_file, huffman_map);
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
            System.out.println(freq_map.toString());
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

    private HashMap<Character, String> getHuffmanMap(HuffmanNode node, HashMap<Character, String> huffman_map, StringBuilder prefix) {
        if (node != null) {
            if (node.left == null && node.right == null) {
                huffman_map.put(node.data, prefix.toString());

            } else {
                prefix.append('0');
                huffman_map = getHuffmanMap(node.left, huffman_map, prefix);
                prefix.deleteCharAt(prefix.length() - 1);

                prefix.append('1');
                huffman_map = getHuffmanMap(node.right, huffman_map, prefix);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }

        return huffman_map;

    }

    private void writeNewFile (File criptoFile, File huffmanFile, HashMap<Character, String> huffmanMap){
        try {
            FileReader fr = new FileReader(criptoFile);
            FileWriter fw = new FileWriter(huffmanFile, true);
            int i;
            while((i=fr.read()) != -1){
                System.out.println(huffmanMap.get((char) i));
                fw.append(huffmanMap.get((char) i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
