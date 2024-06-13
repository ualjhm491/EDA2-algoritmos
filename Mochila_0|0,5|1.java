import java.util.ArrayList;
import java.util.List;

class Objeto {
    double peso;
    double beneficio;

    Objeto(double peso, double beneficio) {
        this.peso = peso;
        this.beneficio = beneficio;
    }

    double getPeso() {
        return peso;
    }

    double getBeneficio() {
        return beneficio;
    }

    double getRelacionBeneficioPeso() {
        return beneficio / peso;
    }
}

public class Mochila {
    private List<Objeto> objetos;
    private double capacidad;

    public Mochila(List<Objeto> objetos, double capacidad) {
        this.objetos = objetos;
        this.capacidad = capacidad;
    }

    public double[] greedyKnapsack() {
        int n = objetos.size();
        double pesoFinal = 0;
        boolean[] usado = new boolean[n];
        double[] solucion = new double[n];

        for (int k = 0; k < n; k++) {
            double maxRelacion = 0;
            int item = -1;

          for (int i = 0; i < n; i++) {
                if (usado[i]) continue;
                double rel = objetos.get(i).getRelacionBeneficioPeso();
                if (rel > maxRelacion) {
                    maxRelacion = rel;
                    item = i;
                }
            }

          if (item == -1) return solucion;
            usado[item] = true;
            if (pesoFinal + objetos.get(item).getPeso() <= capacidad) {
                solucion[item] = 1;
                pesoFinal += objetos.get(item).getPeso();
            } 
            else if (pesoFinal + objetos.get(item).getPeso() / 2 <= capacidad) {
                solucion[item] = 0.5;
                pesoFinal += objetos.get(item).getPeso() / 2;
            } else {
                break;
            }
        }
        return solucion;
    }

  // Problema Mochila 0/0.5/1 con PD:
  public double camionDP3(int P, int[] p, double[] b) {
        int n = b.length;
        double[] array = new double[P + 1];
        double[] arrayMitad = new double[P + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = P; j >= 0; j--) {
                if (p[i - 1] <= j) {
                    array[j] = Math.max(array[j], array[j - p[i - 1]] + b[i - 1]);
                }
                if (p[i - 1] / 2.0 <= j) {
                    arrayMitad[j] = Math.max(arrayMitad[j], array[j - (int)(p[i - 1] / 2)] + b[i - 1] / 2);
                }
            }
        }
        for (int j = 0; j <= P; j++) {
            array[j] = Math.max(array[j], arrayMitad[j]);
        }
        return array[P];
    }

  // Método Backtracking adaptado para mochila 0/0.5/1
    public Solucion backTrakingRec03() {
        backTrakingRec03(0, pesoRestante);
        return new Solucion(voa, soa, p);
    }

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
            pact += p[nivel];
            s[nivel] = 1;
            backTrakingRec03(nivel + 1, pesoRestante - p[nivel]);
            pact -= p[nivel];
            s[nivel] = 0;
        }

        if (pact + p[nivel] / 2.0 <= P) {
            pact += p[nivel] / 2.0;
            s[nivel] = 0.5;
            backTrakingRec03(nivel + 1, pesoRestante - p[nivel] / 2.0);
            pact -= p[nivel] / 2.0;
            s[nivel] = 0;
        }
        backTrakingRec03(nivel + 1, pesoRestante - p[nivel]);
    }
