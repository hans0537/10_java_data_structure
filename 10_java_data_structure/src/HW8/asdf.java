package HW8;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * Name: Junhyuk Lee
 *
 */
public class asdf {
    final String path = "dictionary.txt";
    ArrayList<String> dictionary = new ArrayList<String>();
    public asdf() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine()) != null) {
                line = line.trim();
                line = line.toLowerCase();
                // 'line' is a valid word
                dictionary.add(line);
            }
            br.close();
        } catch(Exception e) {
            System.err.println("File error: " + e.getMessage());
            System.exit(-1);
        }
    }
    /*
     * The length of the return array should be the same as the number of words in 'sentence'.
     * The i-th element of the return array is the substitute candidate for the i-th word in the sentence.
     * If the i-th word is a valid word (i.e., not a typo), then the array should be empty.
     */
    public ArrayList<String>[] spellCheck(String sentence) {

        sentence = sentence.toLowerCase();
        String[] words = sentence.split(" ");
        ArrayList<String>[] suggestions = new ArrayList[words.length];

        for(int i = 0; i < suggestions.length; i++){
            suggestions[i] = new ArrayList<String>();
        }

        for(int i = 0; i < words.length; i++){

            String word = words[i];
            if(!dictionary.contains(word)) {
                for (int j = 0; j < word.length(); j++) { // removal
                    String checkWord = "";
                    for (int k = 0; k < word.length(); k++) {
                        if (j != k) {
                            checkWord += word.charAt(k) + "";
                        }
                    }
                    if (dictionary.contains(checkWord)) {
                        suggestions[i].add(checkWord);
                    }
                }

                for (int j = 0; j <= word.length(); j++) { // addition
                    for (int k = 97; k <= 122; k++) {
                        char c = (char) k;
                        String head = word.substring(0, j);
                        String tail = word.substring(j);
                        String checkWord = head + c + tail;
                        if (dictionary.contains(checkWord)) {
                            suggestions[i].add(checkWord);
                        }
                    }
                }

                for (int j = 0; j < word.length(); j++) { // replace
                    for (int k = 97; k <= 122; k++) {
                        char c = (char) k;
                        if (word.charAt(j) != c) {
                            String head = word.substring(0, j);
                            String tail = word.substring(j + 1);
                            String checkWord = head + c + tail;
                            if (dictionary.contains(checkWord)) {
                                suggestions[i].add(checkWord);
                            }
                        }
                    }
                }

                for (int j = 1; j < word.length(); j++) { // swap
                    String head = word.substring(0, j - 1);
                    String str1 = word.substring(j - 1, j);
                    String str2 = word.substring(j, j + 1);
                    String tail = word.substring(j + 1);
                    String checkWord = head + str2 + str1 + tail;
                    if (dictionary.contains(checkWord)) {
                        suggestions[i].add(checkWord);
                    }
                }
            }
        }
        return suggestions;
    }

    public static void main(String[] args) {
        asdf sc = new asdf();
        String[] sentences = {"I ate an x", "paint the banel", "shee is a riend", "kangaru"};
        //"I ate an x", "paint the banel"
        // Feel free to change the following printout routine
        for (String sent : sentences) {
            String[] words = sent.split("\\s");
            ArrayList<String>[] ret = sc.spellCheck(sent);
            if(ret == null) continue;
            String cand = "";
            for(int i = 0; i < ret.length; i++) {
                if(ret[i] == null) {
                    System.out.print(words[i] + " ");
                    continue;
                }
                Iterator<String> it = ret[i].iterator();
                while (it.hasNext()) {
                    cand += (it.next());
                    if (it.hasNext()) {
                        cand += ",";
                    }
                }
                System.out.print("(" + cand + ") ");
                cand = "";
            }
            System.out.println();
        }
    }
}
