public double camionDP3(int P, int[] p, double[] b) {
 int n = b.length;
 array = new double[P+1];
 for (int i = 1; i <= n; i++) {
     for (int j = P; j >= 0; j--) {
    	 if(p[i-1] <= j) {
		array[j] = Math.max(array[j], 
			array[j - p[i-1]]+b[i-1]);
    	 }
     }
  }
  return array[P];
}
public void test(int j, int c, int[] p, double[] b, int[] sol) {
   if(j > 0) {
      if(c < p[j-1]) {
	 test(j-1, c, p, b, sol);
      }else {
	  if(tabla[j-1][c-p[j-1]] + b[j-1] > tabla[j-1][c]) {
		test(j-1, c-p[j-1], p, b, sol);
		sol[j-1] = 1;
	  }else {
		test(j-1, c, p, b, sol);
	  }
      }
    }
}


//Manera Recursiva
public double camionRecursive(int n, int P, int[] p, double[] b) {
    if(n == 0 || P == 0) return 0;
	if(p[n-1] > P) {
	    return camionRecursive(n-1, P, p, b);
        }
	return Math.max(camionRecursive(n-1, P - p[n-1], p, b) + b[n-1],
			camionRecursive(n-1, P, p, b));
	}

public double camionRec(int n, int P, int p[], double b[]) {
  if(n == 0 || P == 0) return 0;
  if(tabla[n][P] != -1) {
     return tabla[n][P];
  }
  if(p[n-1] > P) {
     return tabla[n][P] = camionRec(n-1, P, p, b);
  }
  double b1 = camionRec(n-1, P - p[n-1],  p, b) + b[n-1];
  double b2 = camionRec(n-1, P, p, b);
  return tabla[n][P] = Math.max(b1, b2);
}

//RELLENAR LA MATRIZ DE -1
public double camionTable(int n, int P, int p[], double b[]) {
   tabla = new double[n+1][P+1];
   for (int i = 0; i < tabla.length; i++) {
      for (int j = 0; j < tabla[0].length; j++) {
	tabla[i][j] = -1;
      }
   }
   return camionRec(n, P, p, b);
}
