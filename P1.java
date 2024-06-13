package AlgoritmosEDA_II;

public class algoritmos_EDA_2 {
	
//PRACTICA 01 
	public static void torneoRecursivoCaso1(int[][] tabla, int n) {
		if (n == 2) {
			tabla[1][1] = 2;
			tabla[2][1] = 1;
		} else {
			torneoRecursivoCaso1(tabla, n / 2);
			completarTabla(tabla, n);
		}
	}
	public static void completarTabla(int[][] tabla, int n) {
		// Cuadrante inferior izqdo
		int medio = n / 2;
		for (int i = medio + 1; i <= n; i++) {
			for (int j = 1; j <= medio; j++) {
				tabla[i][j] = tabla[i - medio][j] + medio;
			}
		}
		for (int i = 1; i <= medio; i++) {
			for (int j = medio; j < n; j++) {
				int posOponente = (i + j - 1) % medio + medio + 1;
				tabla[i][j] = posOponente;
				tabla[posOponente][j] = i;
			}
		}
	}
	
	private static void torneoRecursivoCaso3(int[][] tabla, int inf, int sup) {
		if (sup == inf + 1) {
			tabla[0][inf] = sup + 1;
			tabla[0][sup] = inf + 1;
		} else {
			int medio = (inf + sup) / 2;
			torneoRecursivoCaso3(tabla, inf, medio);
			torneoRecursivoCaso3(tabla, medio + 1, sup);
			completarTablaCaso3(tabla, inf, medio, medio + 1);
			completarTablaCaso3(tabla, medio + 1, sup, inf);
		}
	}
	private static void completarTablaCaso3(int[][] tabla, int inf, int sup, int eqInic) {
		int diaInf = sup - inf;
		int diaSup = diaInf * 2;
		for (int j = diaInf, k = 0; j <= diaSup; j++, k++) {
			tabla[j][inf] = eqInic + k + 1;
		}

		for (int i = inf + 1; i <= sup; i++) {
			tabla[diaInf][i] = tabla[diaSup][i - 1];
			for (int j = diaInf + 1; j <= diaSup; j++) {
				tabla[j][i] = tabla[j - 1][i - 1];
			}
		}
	}

	// Pseudocodigo Seleccion DyV
	El problema de la selección (Divide y Vencerás) ⇒ Algoritmo
		seleccion(int[n] T; ini, fin, s: int) tipo int
			si ini == fin
				retorna T[ini]
			sino
				w = reorganiza(T, ini, fin) // w es el pivote
				k = w - ini + 1 // offset del primer elemento
				si (s <= k)
					retorna seleccion(T, ini, w, s)
				sino
					retorna seleccion(T, w+1, fin, s-k)
				finsi
			finsi
		finseleccion


		reorganiza(int[n] T; ini, fin: int) tipo int
			x = T[ini]; i = ini-1; j = fin+1
			mientras true
				repetir
					j = j - 1
				hasta T[j] <= x
				repetir
					i = i + 1
				hasta T[i] >= x
				si (i < j)
					intercambiar(T, i, j)
				sino
					retorna j
				finsi
			finmientras
		finreorganiza


}
