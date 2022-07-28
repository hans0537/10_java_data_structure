package HW3;

public class HW3Test {
	
	public HW3Test() {
	}
	
	public void test() {
		HW3<String> hw3 = new HW3<String>();
		int totalFailures = 0;
		try {
			hw3.deleteFront();
			System.err.println("Failed 1"); // empty list shouldn't succeed in deletion
			totalFailures++;
		} catch(Exception e) {
			System.out.println("Success");
		}
		try {
			hw3.deleteRear();
			System.err.println("Failed 2"); // empty list shouldn't succeed in deletion
			totalFailures++;
		} catch(Exception e) {
			System.out.println("Success");
		}
		hw3.insertRear("One");
		hw3.insertFront("Two");
		hw3.insertRear("Three");
		hw3.insertRear("Four"); // Order so far: 2, 1, 3, 4
		
		if(!"Two".equals(hw3.getFront())) { System.err.println("Failed 3");totalFailures++;}
		if(!"Four".equals(hw3.getRear())) { System.err.println("Failed 4");totalFailures++;}
		if(!"Two".equals(hw3.deleteFront())){ System.err.println("Failed 5");totalFailures++;}
		if(!"One".equals(hw3.getFront())) { System.err.println("Failed 6");totalFailures++;}
		if(!"Four".equals(hw3.getRear())) { System.err.println("Failed 7");totalFailures++;}
		if(hw3.size() != 3) {System.err.println("Failed 8"); totalFailures++;} // Order so far: 1, 3, 4
		if(!"Four".equals(hw3.deleteRear())) { System.err.println("Failed 9");totalFailures++;}
		if( hw3.size() != 2) {System.err.println( "Failed 10"); totalFailures++;} // Order so far: 1, 3
		hw3.deleteRear(); // Order so far: 1
		if(!"One".equals(hw3.deleteFront())) { System.err.println("Failed 11");totalFailures++;}
		//if(hw3.size() != 0) {System.err.println("Failed 12");totalFailures++;}
		
		try {
			for(int i = 0; i < 1000; i++) {
				hw3.insertRear("" + i);
			}
			if(hw3.size() != 1000) throw new Exception();
			//System.out.println("Success adding 1000 items");
		} catch(Exception e) {
			System.err.println("Failed 12");
			totalFailures++;
		}
		//System.out.println("Total number of failures: " + totalFailures + "/12");
		System.out.println("Final score: " + (int)(100 * totalFailures / 12.0));
	}
	
	public static void main(String[] args) {
		HW3Test ht = new HW3Test();
		ht.test();
	}
	
}