public class Buque {
	static int n; /** La cantidad de contenedores. */
	static int[] p;	/** Los pesos de los contenedores. */
	static int P;	/** La capacidad máxima del buque. */
	static int voa;	/** El valor óptimo actual de la carga (valor óptimo alcanzado). */
	static int pact;	/** La carga actual del buque (peso acumulado actual). */
	static int[] s;	/** La solución actual (estado de los elementos). */
	static int[] soa;	/** La mejor solución encontrada (estado óptimo de los elementos). */
	int pesoRestante = calcularPesoRestante();	/** The peso restante. */
  
	public Buque(int[] pesos, int capacidad) {
		n = pesos.length;
        p = pesos;
        P = capacidad;
        voa = 0;
        pact = 0;
		s = new int[n];
		soa = new int[n];    
    }
	public static int[] getP() {
		return p;
	}
	public static void setP(int[] p) {
		Buque.p = p;
	}
	public  void setP(int p) {
		P = p;
	}

	public Solucion greedy() {
		greedy1();
		return new Solucion(voa, soa, p);
	}
	private static int greedy1() {
		Arrays.sort(p); // Orden -> O(n logn)
		for (int i = 0; i < p.length; i++) { // Orden -> O(n)
			if (pact + p[i] <= P) {
				soa[i] = 1;
				pact += p[i];
				continue;
			}
			break;
		}
		voa = pact;
		return pact;
	}

  // BACKTRACKING RECURSIVO
public Solucion backTrakingRec03() {  
		backTrakingRec03(0, pesoRestante);
		return new Solucion(voa, soa, p);
	}
private static void backTrakingRec03(int nivel, int pesoRestante) {
		if (pact + pesoRestante <= voa)
			return;
		if (nivel > n - 1) {
			if (pact > voa)
				voa = pact;
			System.arraycopy(s, 0, soa, 0, n); // Utilizamos el System.arrayCopy para
			return;
		}
		if (pact + p[nivel] <= P) {
			pact += p[nivel];
			s[nivel] = 1;
			backTrakingRec03(nivel + 1, pesoRestante - p[nivel]);
			pact -= p[nivel];
			s[nivel] = 0;
		}
		backTrakingRec03(nivel + 1, pesoRestante - p[nivel]);
	}

  // MOCHILA 0 / 0.5 / 1
 private static void backTrakingRec03(int nivel, int pesoRestante) {
        if (pact + pesoRestante <= voa) {
            return;
        }
        if (nivel > n - 1) {
            if (pact > voa) {
                voa = pact;
                System.arraycopy(s, 0, soa, 0, n);
            }
            return;
        }
        if (pact + p[nivel] <= P) {
            // Incluye el objeto completo
            pact += p[nivel];
            s[nivel] = 1;
            backTrakingRec03(nivel + 1, pesoRestante - p[nivel]);
            pact -= p[nivel];
            s[nivel] = 0;
        }

        // Incluye la mitad del objeto si la capacidad lo permite
        if (pact + p[nivel] / 2.0 <= P) {
            pact += p[nivel] / 2.0;
            s[nivel] = 0.5;
            backTrakingRec03(nivel + 1, pesoRestante - p[nivel] / 2.0);
            pact -= p[nivel] / 2.0;
            s[nivel] = 0;
        }

        // No incluye el objeto
        backTrakingRec03(nivel + 1, pesoRestante - p[nivel]);
    }
}

  // BACKTRACKING ITERATIVO
  public Solucion backTrakingIterative() {
		voa = -1;
		int pesoRestante = 0;
		for (int i = 0; i < n; i++) {
			pesoRestante += p[i];
			s[i] = -1;
		}
		backTrakingIterative(0, pesoRestante);
		return  new Solucion(voa, soa, p);

	}
	private static void backTrakingIterative(int nivel, int pesoRestante) {
		do {
			s[nivel]++;
			if (s[nivel] == 1) {
				pact += p[nivel];
			} else {
				pesoRestante -= p[nivel];
			}
			if (nivel == n - 1 && pact <= P && pact > voa) {
				voa = pact;
				System.arraycopy(s, 0, soa, 0, n);
			}
			if (nivel < n - 1 && pact <= P && pact + pesoRestante > voa) {
				nivel++;
			} else {
				while (nivel > -1 && s[nivel] == 1) {
					pact -= p[nivel];
					s[nivel] = -1;
					pesoRestante += p[nivel];
					nivel--;
				}
			}
		} while (nivel > -1);
	}
