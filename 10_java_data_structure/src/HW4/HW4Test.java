package HW4;

import java.util.*;

public class HW4Test {
	Hashtable<String, String> tblCorr, tblErr;
	
	public HW4Test() {
		tblCorr = new Hashtable<String, String>();
		tblErr = new Hashtable<String, String>();
		
		tblCorr.put("x-y+z/1-7*y", "xy-z1/+7y*-");
		tblCorr.put("a-a-a-a-a*b*b*b*b", "aa-a-a-ab*b*b*b*-");
		tblCorr.put("b*b*b*b*b*a-a-a-a-a", "bb*b*b*b*a*a-a-a-a-");
		tblCorr.put("a/b+a*b-a/b+a*b", "ab/ab*+ab/-ab*+");
		tblCorr.put("1+2+3+4+5+6+7+8/a/b/c/d/e/f", "12+3+4+5+6+7+8a/b/c/d/e/f/+");
		//tblCorr.put("c*7/2+2/1-2", "c7*2/21/+2-");
		tblCorr.put("", "");
		
		tblErr.put("a-a-a-a-a*b*b*b*b*", "");
		tblErr.put("", "a+b-c");
		tblErr.put("a-b+c-d+-1", "");
		//tblErr.put("", "ab/+");
		tblErr.put("ab-c+d", "");
		//tblErr.put("", "aab/");
	}
	
	public int grade() {
		return Math.max(gradeHelper(true) + gradeHelper(false), 0);
	}
	
	private String removeSpaces(String s) {
		String tmp = "";
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != ' ') tmp += s.charAt(i);
		}
		return tmp;
	}
	
	private void err(String s) {
		System.err.println("\t" + s);
	}
	
	private int gradeHelper(boolean isCorr) {
		int denom = 40 / (tblCorr.size() + tblErr.size());
		int grade = tblCorr.size() * 40 / (tblCorr.size() + tblErr.size());
		if(!isCorr)
			grade = tblErr.size() * 40 / (tblCorr.size() + tblErr.size());
		//System.out.println(grade);
		//int grade = isCorr ? tblCorr.size() : tblErr.size();
		HW4 hw = new HW4();
		Iterator<String> it = tblCorr.keySet().iterator();
		Hashtable<String, String> tbl = tblCorr;
		if(!isCorr) {
			it = tblErr.keySet().iterator();
			tbl = tblErr;
		}
		while(it.hasNext()) {
			String infix = it.next(), postfix = tbl.get(infix);
			try {
				if(isCorr) { // Testing correct pairs
					String ans = this.removeSpaces(hw.infix2Postfix(infix)),
							ans2 = this.removeSpaces(hw.postfix2Infix(postfix));
					if(!ans.equals(postfix)) { grade -= denom; err(infix);}
					if(!ans2.equals(infix)) { grade -= denom; err(postfix);}
				} else { // Testing incorrect pairs
					if(postfix.length() == 0) { // If the infix contains error
						String ans = this.removeSpaces(hw.infix2Postfix(infix));
						if(!ans.equals("")) { grade -= denom; err(infix); }
					} else { // If the postfix contains error
						String ans = this.removeSpaces(hw.postfix2Infix(postfix));
						ans = this.removeSpaces(hw.postfix2Infix(postfix));
						if(!ans.equals("")) { grade -= denom; err(postfix); }
					}
				}
			} catch(Exception e) {
				System.err.println("Infix: " + infix + "\tPostfix: " + postfix);
				grade -= 20;
			}
		}
		return grade;
	}
	
	public static void main(String[] args) {
		HW4Test h = new HW4Test();
		System.out.println("Grading for correctness");
		System.out.println("Correctness grade: " + h.grade() + "/40");
	}
	
	
}
