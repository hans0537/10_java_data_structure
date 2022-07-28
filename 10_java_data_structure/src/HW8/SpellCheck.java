package HW8;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * Name: Sungjoo Shin
 *
 */

public class SpellCheck {
	final String path = "dictionary.txt";

	ArrayList<String> dicWords = new ArrayList<String>();

	public SpellCheck() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null) {
				line = line.trim();
				// 'line' is a valid word
				dicWords.add(line);
			}
			br.close();
		} catch(Exception e) {
			System.err.println("File error: " + e.getMessage());
			System.exit(-1);
		}      
	}

	/*
	 * Split the input word into alphabet and add each alphabet to tempA(ArrayList).
	 * I remove a single character from any position using tempA. 
	 * And then create newWord with remaining values in tempA.
	 * Check the newWord is in dicWords and if there is, check overlap with a(input ArrayList).
	 * If not overlapping, add the newWord into a.
	 * Time complexity: O(N^3)
	 */
	public void removeCheck(String word, ArrayList<String> a) {
		String[] temp = word.split("");
		ArrayList<String> tempA = new ArrayList<String>();
		for (int i = 0; i < temp.length; i++) {
			tempA.add(temp[i]);
		}

		for (int i = 0; i < word.length(); i++) {
			String newWord = "";
			String s = tempA.remove(i);
			for (int j = 0; j < tempA.size(); j++) {
				newWord += tempA.get(j);
			}
			if(dicWords.contains(newWord)) {
				if(!a.contains(newWord)) {
					a.add(newWord);
				}
			}
			newWord = "";
			tempA.add(i, s);
		}
	}

	/*
	 * Split the input word into alphabet and add each alphabet to tempA(ArrayList).
	 * I created alphabets array.
	 * And then add alphabets of a single character into any position of tempA
	 * And then create newWord with tempA.
	 * Check the newWord is in dicWords and if there is, check overlap with a(input ArrayList)
	 * If not overlapping, add the newWord into a.
	 * Time complexity: O(N^4)
	 */
	public void additionCheck(String word, ArrayList<String> a) {
		String[] alphabets = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		String[] temp = word.split("");
		ArrayList<String> tempA = new ArrayList<String>();
		for (int i = 0; i < temp.length; i++) {
			tempA.add(temp[i]);
		}
		String newWord = "";

		int i = 0;
		while(i < word.length() + 1) {
			for (int j = 0; j < alphabets.length; j++) {
				tempA.add(i, alphabets[j]);
				for (int k = 0; k < tempA.size(); k++) {
					newWord += tempA.get(k);
				}
				if (dicWords.contains(newWord)) {
					if(!a.contains(newWord)) {
						a.add(newWord);
					}
				}
				newWord = "";
				tempA.remove(i);
			}
			i++;
		}
	}

	/*
	 * Similar with addition.
	 * Split the input word into alphabet and add each alphabet to tempA(ArrayList).
	 * I created alphabets array.
	 * And then replace of a single character with tempA
	 * And then create newWord with tempA.
	 * Check the newWord is in dicWords and if there is, check overlap with a(input ArrayList)
	 * If not overlapping, add the newWord into a.
	 * Time complexity: O(N^4)
	 */
	public void replaceCheck(String word, ArrayList<String> a) {
		String[] alphabets = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		String[] temp = word.split("");
		ArrayList<String> tempA = new ArrayList<String>();
		for (int i = 0; i < temp.length; i++) {
			tempA.add(temp[i]);
		}

		String newWord = "";
		int i = 0;
		while(i < word.length()) {
			for (int j = 0; j < alphabets.length; j++) {
				String s = tempA.get(i);
				tempA.set(i, alphabets[j]);
				for (int k = 0; k < tempA.size(); k++) {
					newWord += tempA.get(k);
				}
				if(dicWords.contains(newWord)) {
					if(!a.contains(newWord)) {
						a.add(newWord);
					}
				}
				newWord = "";
				tempA.set(i, s);
			}
			i++;
		}
	}

	/*
	 * Split the input word into alphabet and add each alphabet to tempA(ArrayList).
	 * And swap two adjacent characters using with tempA
	 * And then create newWord with tempA.
	 * Check the newWord is in dicWords and if there is, check overlap with a(input ArrayList)
	 * If not overlapping, add the newWord into a.
	 * Time complexity: O(N^3)
	 */
	public void swapCheck(String word, ArrayList<String> a) {
		String[] temp = word.split("");
		ArrayList<String> tempA = new ArrayList<String>();
		for (int i = 0; i < temp.length; i++) {
			tempA.add(temp[i]);
		}

		for (int i = 0; i < tempA.size() - 1; i++) {
			String newWord = "";
			String t = tempA.get(i);
			tempA.set(i, tempA.get(i + 1));
			tempA.set(i + 1, t);
			for (int j = 0; j < tempA.size(); j++) {
				newWord += tempA.get(j);
			}
			if(dicWords.contains(newWord)) {
				if(!a.contains(newWord)) {
					a.add(newWord);
				}
			}
			newWord = "";
			tempA.set(i + 1, tempA.get(i));
			tempA.set(i, t);
		}
	}

	/*
	 * The length of the return array should be the same as the number of words in 'sentence'.
	 * The i-th element of the return array is  the substitute candidate for the i-th word in the sentence.
	 * If the i-th word is a valid word (i.e., not a typo), then the array should be empty.
	 */

	/*
	 * First split the sentence and store each words into words(String array).
	 * 1. And start for loop with words array. => O(N) 
	 * 2. Check if each value of words array is in the dictionary(dicWords) => O(N^2) this is operating with No.1 time complexity.
	 * 3. If the word is in dictionary(not typo) just add null in res[i](ArrayList).
	 * 4. If the word is a typo, go to four modifications method.
	 * 5. I made four helper functions that implement modifications and the explanation is above each method.
	 * 6. removeCheck    => O(N^3) 
	 *    additionCheck  => O(N^4)
	 *    replaceCheck   => O(N^4)
	 *    swapCheck      => O(N^3)
	 * 7. Therefore the Time Complexity of spellCheck method is O(N^5)
	 */
	public ArrayList<String>[] spellCheck(String sentence) {

		String[] words = sentence.split(" ");
		ArrayList<String>[] res = new ArrayList[words.length];

		for (int i = 0; i < words.length; i++) {
			if (dicWords.contains(words[i].toLowerCase())) {
				res[i] = new ArrayList<String>();
				res[i].add(null);
			}else {
				res[i] = new ArrayList<String>();
				removeCheck(words[i], res[i]);
				additionCheck(words[i],res[i]);
				replaceCheck(words[i], res[i]);
				swapCheck(words[i], res[i]);
			}
		}
		return res;
	}

	public static void main(String[] args) {
		SpellCheck sc = new SpellCheck();
		String[] sentences = {"I ate an x", "paint the banel", "shee is a riend", "kangaru"};
		// Feel free to change the following printout routine
		for(String sent : sentences) {
			String[] words = sent.split("\\s");
			ArrayList<String>[] ret = sc.spellCheck(sent);
			if(ret == null) continue;
			String cand = "{";

			for (int i = 0; i < ret.length; i++) {
				if(i == ret.length - 1) {
					if(ret[i].isEmpty()) {
						cand += "null";
					}
					else if(ret[i].get(0) == null) {
						cand += "null";
					}else {
						cand += "[";
						for (int k = 0; k < ret[i].size(); k++) {
							if(k == ret[i].size() - 1) {
								cand += ret[i].get(k) + "]";
							}else {
								cand += ret[i].get(k) + ", ";
							}
						}
					}
				}else {
					if(ret[i].isEmpty()) {
						cand += "null";
					}else if(ret[i].get(0) == null) {
						cand += "null, ";
					}else {
						cand += "[";
						for (int k = 0; k < ret[i].size(); k++) {
							if(k == ret[i].size() - 1) {
								cand += ret[i].get(k) + "], ";
							}else {
								cand += ret[i].get(k) + ", ";
							}
						}
					}
				}   
			}
			cand += "}";
			System.out.print(cand);
			System.out.println();
		}
	}
}