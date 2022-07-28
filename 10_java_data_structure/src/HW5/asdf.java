package HW5;

import java.util.Arrays;

public class asdf {
   static int i = 0;
   public static void mnemonicsAdd(String[] mnemonics, String s) {
      mnemonics[i] = s;
      i++;
   }

   static String[] KEYS = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };

   public static String[] letterCombinations(String digits) {
      int size = 1;
      String[] temp = digits.split("");
      for (int i = 0; i < digits.length(); i++) {
         if(KEYS[Integer.parseInt(temp[i])].equals("")) continue;
         size *= KEYS[Integer.parseInt(temp[i])].length();
      }
      String[] mnemonics = new String[size];
      combination("", digits, 0, mnemonics);
      return mnemonics;
   }
   // recursive function
   public static void combination(String prefix, String digits, int idx, String[] mnemonics) {
      if (idx >= digits.length()) {
         mnemonicsAdd(mnemonics, prefix);
         return;
      }
      String[] temp = digits.split("");
      String letters = KEYS[Integer.parseInt(temp[idx])];
      String[] temp2 = letters.split("");
      for (int i = 0; i < letters.length(); i++) {
         combination(prefix + temp2[i], digits, idx + 1, mnemonics);
      }
   }

   public static void main(String[] args) {
	   final String[] numCodes = {"", "", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"};
	   String[] temp2 = numCodes[1].split("");
	   System.out.println((temp2.length));
	   if(1%5!=0) System.out.println(true);
   }
}