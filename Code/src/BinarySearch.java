
public class BinarySearch {
	
	/*
	 * returns the index where the target is located or
	 * negative one if its not found
	 */
	public static int iBinarySearch (int[ ] A, int target) {
		int l = 0;
		int r = A.length - 1;
		
		while (l<=r) {
			
			int m = (l + r)/2;
			
			if(target==A[m]) {
				return m;
			}
			else {
				if(target < A[m]) {
					r = m-1;
				} else {
					l = m+1;
				}
			}
			
		}
		return -1; //target not found
	}
	
	public static int recBinarySearch(int [ ] A, int target, int l, int r) {
		if(l>r) {
			return -1;
		}
		int m = (l+r)/2;
		if(target == A[m]) {
			return m;
		} else if(target < A[m]){
			//target in left portion of the array
			return recBinarySearch(A,  target,  l,  m-1);
		}else  {
			return recBinarySearch(A,  target, m+1, r);
		}
	}
	
	public static void main(String[] args) {
		int [] array = {3, 10, 20, 53, 7, 99};
		
		System.out.println(iBinarySearch(array, 3));
		System.out.println(iBinarySearch(array, 75));
		System.out.println(recBinarySearch(array, 3, 0, array.length-1));
		System.out.println(recBinarySearch(array, 75, 0, array.length-1));
	}
}
