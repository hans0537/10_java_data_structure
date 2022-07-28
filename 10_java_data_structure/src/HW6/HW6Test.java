package HW6;


public class HW6Test {

	HW6 hw; 
	final double EPSILON = 1e-5; 
			
	public HW6Test() {
		hw = new HW6();
		String[] exps = {"(5-2)/5/8/10", "821*0-1*(5-6)", "((((4-5)/(5-3)+1)*6-1)/5+1)*1", "1+1+1+1+1*2", 
				"(2-1)*(4-3)*(5-2)*(6-4)/2", "(100-10-20-30)/2+2*1*2", "12345", "(10*20-100-50)/2-15"};
		double[] ans = {0.0075, 1.0, 1.4, 6.0, 3.0, 24.0, 12345.0, 10.0};
		String[] ans2 = {"52-5/8/10/", "8210*156-*-", "45-53-/1+6*1-5/1+1*", "11+1+1+12*+", "21-43-*52-*64-*2/",
				"10010-20-30-2/21*2*+", "12345", "1020*100-50-2/15-"};
		double score = 80, denom = score / (exps.length * 3);
		int wrongs = 0;
		for(int i = 0; i < exps.length; i++) {
			try {
				hw.buildTree(exps[i]);
				if(Math.abs(hw.eval() - ans[i]) > EPSILON) wrongs++; //score -= denom;
				String infix = hw.toString().replaceAll("\\s", "");
				if(!infix.equals(exps[i])) wrongs++; //score -= denom;
				
				String postfix = hw.toPostfixString().replaceAll("\\s", "");
				if(!postfix.equals(ans2[i])) wrongs++; //score -= denom;
				
			} catch(Exception e) {
				System.err.println("\tException at sample " + i);
				wrongs++;
			}
		}
		score -= (wrongs * denom);
		System.out.println("Score: " + Math.max(0, ((int)(score * 10))) / 10.0 + "/80");
	}
	
	public static void main(String[] args) {
		HW6Test h = new HW6Test();
	}

}
