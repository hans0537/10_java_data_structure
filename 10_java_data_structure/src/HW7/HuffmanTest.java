package HW7;

import java.io.*;

public class HuffmanTest {
	public HuffmanTest(Huffman h, String filePath) throws MyException {
		BufferedReader br = null;
		String full = "";
		try {
			br = new BufferedReader(new FileReader(filePath));
			String line;
			while((line = br.readLine()) != null) 
				full += "\n" + line; 
			
			br.close();
		} catch(Exception e) {
			System.err.println("File error: " + e.getMessage());
			System.exit(-1);
		}
		String code = h.encode(full);
		String rec = h.decode(code);
		if(!full.equals(rec) || code.length() >= full.length() * 16) throw new MyException();
	}
	
	public static void main(String[] args) {
		String[] paths = {"test1.txt", "test2.txt", "test3.txt", "test4.txt"};
		double score = 60.0, denom = score / paths.length;
		int strike = 0;
		for(String p : paths) {
			try {
				new HuffmanTest(new Huffman(), p);
			} catch(MyException e) {
				score -= denom;
			}
			catch(Exception e) {
				strike++;
				score /= 2; // 50% off
				if(strike == 2) {
					System.err.println("Strike two. No credits.");
					score = 0;
				}
			}
		}
		System.out.println("Final correctness score: " + Math.max(0, score));
	}
	
	class MyException extends Exception {
		public MyException() {}
	}
}
