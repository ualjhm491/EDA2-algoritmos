public class RoadNetwork<Vertex> implements Graph<Vertex>, Iterable<Vertex> {
	protected boolean directed;
	protected TreeMap<Vertex, TreeMap<Vertex, Double>> adjacencyMap;
	protected TreeMap<Vertex, Boolean> visited;
	protected ArrayList<Vertex> result;
	protected ArrayList<ArrayList<Vertex>> resultSimplePaths;
	double shortestDistanceCircuit;
	protected ArrayList<Vertex> shortestCircuit;
		
	// Simple Paths with 'visited' TreeMap PROFESORES
	public ArrayList<ArrayList<Vertex>> simplePaths(Vertex source, Vertex destination) {
		result = new ArrayList<Vertex>();
		resultSimplePaths = new ArrayList<ArrayList<Vertex>>();
		visited = new TreeMap<Vertex, Boolean>();
		for (Vertex v : this.adjacencyMap.keySet())
			visited.put(v, false);
		simplePathsAux(source, destination);
		return resultSimplePaths;
	}

	// Simple Paths with 'visited' TreeMap
	private void simplePathsAux(Vertex current, Vertex destination) {
		result.add(current);
		visited.put(current, true);
		if (current.equals(destination)) {
			ArrayList<Vertex> resultAux = new ArrayList<Vertex>();
			for (int i = 0; i < result.size(); i++) {
				resultAux.add(result.get(i));
			}
			resultSimplePaths.add(resultAux);
		}

		TreeMap<Vertex, Double> neighborMap = adjacencyMap.get(current);
		for (Map.Entry<Vertex, Double> entry : neighborMap.entrySet()) {
			Vertex to = entry.getKey();
			if (!visited.get(to))
				simplePathsAux(to, destination);
		}
		result.remove(result.size() - 1);
		visited.put(current, false);
	}
/**
	 * Recursivamente explora todos los caminos simples entre un vértice de origen y un vértice de destino en un grafo.
	 * Este método es llamado internamente para construir caminos completando un camino cada vez.
	 * 
	 * @param graph El grafo sobre el cual se están buscando los caminos.
	 * @param vertices La lista de vértices que forman el camino actual.
	 * @param result Un objeto Resultado que se usa para almacenar el mejor camino encontrado.
	 * @param current El vértice actual en el camino.
	 * @param destination El vértice de destino del camino.
	 * @param origin El vértice de origen inicial para el camino.
	 */
	private void simplePaths(Network<String> graph, ArrayList<String> vertices, Resultado result, String current, String destination, String origin) {
		vertices.add(current);
		if (current.equals(destination)) {
			if (vertices.size() == graph.numberOfVertices()) {
				Double w = graph.getWeight(vertices.get(vertices.size() - 1), origin);
				if (w != null) {
					Resultado r = new Resultado(origin);
					for (int i = 0; i < vertices.size() - 1; i++) {
						String from = vertices.get(i);
						String to = vertices.get(i + 1);
						r.addEdge(from, to, graph.getWeight(from, to));
					}
					String from = vertices.get(vertices.size() - 1);
					r.addEdge(from, origin, graph.getWeight(from, origin));
					if (result.getTotal() == 0 || r.getTotal() < result.getTotal()) {
						result.clear();
						result.copy(r);
					}
				}
			}
			vertices.remove(vertices.size() - 1);
			return;
		}
		for (String to : graph.getNeighbors(current)) {
			if (vertices.contains(to)) continue;
			simplePaths(graph, vertices, result, to, destination, origin);
		}
		vertices.remove(vertices.size() - 1);
	}

/**
	 * Ejecuta una búsqueda voraz para encontrar una ruta aproximada más corta para el problema del viajante de comercio (TSP).
	 *
	 * @param net El grafo sobre el cual se busca la ruta.
	 * @param source El vértice inicial desde el cual comenzar la ruta.
	 * @return Un objeto Resultado que contiene la ruta aproximada más corta.
	 */
	public static Resultado tspGreedy(Network<String> net, String source) {
		Resultado result = new Resultado(source);
		HashSet<String> visited = new HashSet<>();
		String current = source;
		visited.add(current);

		while (visited.size() < net.numberOfVertices()) {
			String next = closestNeighbor(net, visited, current);
			if (next == null) {
				break; // no hay solución, salir del bucle
			}
			Double weight = net.getWeight(current, next);
			result.addEdge(current, next, weight);
			visited.add(next);
			current = next;
		}

		if (visited.size() == net.numberOfVertices()) {
			Double weight = net.getWeight(current, source);
			result.addEdge(current, source, weight);
		}
		return result;
	}
	// Function to find the minimum cost path for all the paths
    public static void greedyTSP(int[][] graph, int source) {
        int sum = 0;
        int counter = 0;
        int j = 0, i = 0;
        int min = Integer.MAX_VALUE;
        List<Integer> visitedRouteList = new ArrayList<>();
        visitedRouteList.add(source);
        int[] route = new int[graph.length];
        while (i < graph.length && j < graph[i].length) {
             if (counter >= graph[i].length - 1) {
                break;
            }
            if ((j != i) && !(visitedRouteList.contains(j))) {
                if (graph[i][j] < min) {
                    min = graph[i][j];
                    route[counter] = j + 1;
                }
            }
            j++;
            if (j == graph[i].length) {
                sum += min;
                min = Integer.MAX_VALUE;
                visitedRouteList.add(route[counter] - 1);
                j = 0;
                i = route[counter] - 1;
                counter++;
            }
        }

        i = route[counter - 1] - 1;

        if (graph[i][source]>-1) {
        	min = graph[i][source];
        	sum += min;
        	visitedRouteList.add(source);
        }
        
        for (Integer node : visitedRouteList) {
        	System.out.print(node + " -> ");
        }
        System.out.println();
        System.out.println("Minimum Cost is : " + sum);
    }

/*
Para el método TSPGreedy en Java:
¿Cómo selecciona este algoritmo el siguiente nodo a visitar durante la
construcción del camino?
El algoritmo selecciona el siguiente nodo a visitar comparando los costos 
de las aristas que conectan el nodo actual con todos los nodos no visitados.
Utiliza una estrategia voraz para elegir la arista de menor costo en cada paso.

¿Qué estrategias podrían implementarse para mejorar la eficiencia de búsqueda 
de la ruta mínima en este algoritmo?
Se podrían implementar estrategias como el uso de una estructura de datos más eficiente
para determinar rápidamente el nodo con el menor costo disponible, como por ejemplo,
una cola de prioridad (min-heap).
También se podría explorar la posibilidad de utilizar técnicas de poda para evitar
explorar caminos que ya son claramente peores que la solución actual.

¿Cómo se manejan los casos donde no todos los nodos del grafo están directamente 
conectados entre sí?
En el código proporcionado, se maneja mediante la comprobación de si hay un camino válido
entre dos nodos antes de considerar esa arista en el cálculo de la ruta mínima. 
Esto se ve reflejado en la condición if (graph[i][j] < min) donde se asegura que solo 
se consideren aristas válidas (con peso no negativo).

Para el método tspGreedy en Java usando una clase Network:
¿Cuál es la diferencia clave entre este método y el TSPGreedy en términos de estructuras de
datos utilizadas?
Este método utiliza una estructura más abstracta (Network) para representar el grafo, lo cual
permite una mayor flexibilidad y abstracción en la manipulación de los vértices y aristas.
Utiliza una lista de adyacencia implícita en Network, lo cual puede facilitar la manipulación
y el acceso a los vecinos de cada vértice.

¿Cómo podríamos adaptar este método para trabajar con pesos negativos en las aristas del grafo?
Para trabajar con pesos negativos, se debería considerar un algoritmo diferente, ya que los 
métodos voraces como este no garantizan resultados correctos en presencia de pesos negativos.
Se podría considerar el uso de algoritmos como Bellman-Ford o Floyd-Warshall, que pueden manejar
aristas con pesos negativos.

¿Qué criterios podríamos ajustar para modificar la heurística voraz utilizada en este método 
y obtener diferentes soluciones?
Se podrían ajustar los criterios de selección del vecino más cercano (closestNeighbor) 
para considerar diferentes métricas de proximidad, como el peso de la arista o la distancia
geográfica en un contexto aplicado.

Para el método simplePaths en Java:
¿Cómo se garantiza que este algoritmo explore todos los caminos simples entre un origen y 
un destino en un grafo?
Se garantiza mediante un enfoque recursivo que explora cada posible camino desde el nodo 
origen hasta el nodo destino.El método verifica en cada paso si se ha alcanzado el nodo 
destino y, en ese caso, agrega el camino actual a la lista de caminos simples encontrados.

¿Qué modificaciones podríamos hacer para limitar la profundidad de búsqueda en este algoritmo
y obtener caminos simples más cortos?
Se podría agregar un parámetro adicional al método recursivo para limitar la profundidad 
máxima de búsqueda.También se podría implementar una verificación de la longitud actual 
del camino para detener la exploración si ya se supera la longitud de un camino encontrado 
previamente.

¿Cómo podríamos adaptar este método para encontrar todos los caminos simples entre todos los
pares de vértices en lugar de solo entre un par de origen y destino?
Se podría modificar el método para que recorra todos los pares de vértices en el grafo 
y aplique el algoritmo simplePaths para cada par de origen y destino.
Esto requeriría una estructura de control adicional para iterar sobre todos los 
vértices como origen y destino.

Para el método simplePathsAux en Java usando una clase RoadNetwork:
¿Cuál es la ventaja de usar una estructura de datos como TreeMap para representar la red de
carreteras en este método?
TreeMap proporciona una estructura ordenada que facilita la iteración ordenada sobre los 
vértices y el acceso eficiente a los vecinos de cada vértice. Esto es útil para garantizar 
que los caminos se consideren en un orden sistemático y para evitar la repetición de 
caminos debido al orden de exploración.

¿Qué estrategias podrían aplicarse para evitar ciclos infinitos en la exploración de caminos
simples en este algoritmo?
El marcado de vértices como visitados (visited.put(current, true)) y la actualización 
posterior (visited.put(current, false)) ayuda a evitar que se explore el mismo camino varias
veces. Se podrían implementar verificaciones adicionales para detectar y evitar bucles 
infinitos, como limitar la profundidad de búsqueda o utilizar técnicas de detección de ciclos.

¿Cómo podríamos modificar este método para encontrar el camino simple más corto entre dos 
vértices en lugar de todos los caminos simples?
Se podría adaptar el método para que mantenga un seguimiento del camino más corto encontrado
hasta el momento y lo actualice si se encuentra un camino más corto durante la exploración.
Esto requeriría una comparación continua de la longitud del camino actual con el 
camino más corto conocido y la actualización correspondiente de la solución si se 
encuentra una import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class Resultado {
    private String source;
    private double totalWeight;
    private String path;

    public Resultado(String source) {
        this.source = source;
        this.totalWeight = 0.0;
        this.path = source;
    }

    public void addEdge(String from, String to, Double weight) {
        totalWeight += weight;
        path += " -> " + to;
    }

    @Override
    public String toString() {
        return "Path: " + path + "\nTotal weight: " + totalWeight;
    }
}

public class Network<T> {
    private Map<T, TreeMap<T, Double>> adjMap;

    public Network() {
        this.adjMap = new TreeMap<>();
    }

    public void addVertex(T vertex) {
        adjMap.putIfAbsent(vertex, new TreeMap<>());
    }

    public void addEdge(T from, T to, double weight) {
        adjMap.get(from).put(to, weight);
        adjMap.get(to).put(from, weight); // assuming undirected graph
    }

    public Double getWeight(T from, T to) {
        return adjMap.get(from).get(to);
    }

    public int numberOfVertices() {
        return adjMap.size();
    }

    public TreeMap<T, Double> getNeighbors(T vertex) {
        return adjMap.get(vertex);
    }
}

public class TSPGreedy {
    public static Resultado tspGreedy(Network<String> net, String source) {
        Resultado result = new Resultado(source);
        HashSet<String> visited = new HashSet<>();
        String current = source;
        visited.add(current);

        while (visited.size() < net.numberOfVertices()) {
            String next = closestNeighbor(net, visited, current);
            if (next == null) {
                break; // No solution, exit the loop
            }
            Double weight = net.getWeight(current, next);
            result.addEdge(current, next, weight);
            visited.add(next);
            current = next;
        }

        if (visited.size() == net.numberOfVertices()) {
            Double weight = net.getWeight(current, source);
            result.addEdge(current, source, weight);
        }
        return result;
    }

    private static String closestNeighbor(Network<String> net, HashSet<String> visited, String current) {
        Double minWeight = Double.MAX_VALUE;
        String closest = null;
        for (Map.Entry<String, Double> neighbor : net.getNeighbors(current).entrySet()) {
            if (!visited.contains(neighbor.getKey()) && neighbor.getValue() < minWeight) {
                minWeight = neighbor.getValue();
                closest = neighbor.getKey();
            }
        }
        return closest;
    }

    public static void main(String[] args) {
        Network<String> net = new Network<>();
        net.addVertex("A");
        net.addVertex("B");
        net.addVertex("C");
        net.addVertex("D");

        net.addEdge("A", "B", 10);
        net.addEdge("A", "C", 15);
        net.addEdge("A", "D", 20);
        net.addEdge("B", "C", 35);
        net.addEdge("B", "D", 25);
        net.addEdge("



