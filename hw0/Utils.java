
public class Utils {
	public static void main(String[] args) {
		int [] test1 = {-6, 2, 4};
		int [] test2 = {8, 2, -1, -1, 15};
		int [] test3 = {5, 1, 0, 3, 6};

		System.out.println(max(test1));
		System.out.println(max(test2));
		System.out.println(max(test3));
		System.out.println(threeSum(test1));
		System.out.println(threeSum(test2));
		System.out.println(threeSum(test3));
		System.out.println(threeSumDistinct(test1));
		System.out.println(threeSumDistinct(test2));
		System.out.println(threeSumDistinct(test3));

	}

	static int max(int[] a) {
		int maxVal = a[0];
		for (int i = 1; i < a.length; i++) {
			if (a[i] > maxVal) {
				maxVal = a[i];
			}
		}
		return maxVal;
	}
	static boolean threeSum(int[] a) {
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a.length; j++) {
				for(int k = 0; k < a.length; k++) {
					if (a[i] + a[j] + a[k] == 0) {
						return true;
					}

				}
			}
		}
		return false;
	}
	static boolean threeSumDistinct(int a[]) {
		for(int i = 0; i < a.length; i++) {
			for(int j = i + 1; j < a.length; j++) {
				for(int k = j + 1; k < a.length; k++) {
					if (a[i] + a[j] + a[k] == 0) {
						return true;
					}

				}
			}
		}
		return false;
	}
}