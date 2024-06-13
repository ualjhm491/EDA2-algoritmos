//PRIM CON PQ
public Result primPQ(){
  		String source = this.origin;
		if(source == null || !this.graph.containsKey(source)) return null;
		
		Result result = new Result(source);
		HashMap<String, String> parents = new HashMap<>();
		TreeMap<Double, HashSet<String>> key = new TreeMap<>();
		HashSet<String> mstSet = new HashSet<>();
		
		key.put(0.0, new HashSet<>());
		key.get(0.0).add(source);
		parents.put(source, null);
		
		for (int i = 0; i < graph.size(); i++) {
			String u = minKeyPQ(key, mstSet);
			mstSet.add(u);
			for (String v : getNeighbors(u)) {
				if(mstSet.contains(v)) continue;
				Double w = getWeight(u, v);
				if(w == null) continue;
				String parent = parents.get(v);
				Double wold = getWeight(parent, v);
				if(wold == null || w < wold) {
					if(wold != null) {
						key.get(wold).remove(v);
						if(key.get(wold).size() == 0) {
							key.remove(wold);
						}
					}
					HashSet<String> h = key.get(w);
					if(h == null) {
						key.put(w, h = new HashSet<>());
					}
					h.add(v);
					parents.put(v, u);
				}
			}
		}
		
		for (Entry<String, String> it : parents.entrySet()) {
			if(it.getValue() == null) continue;
			result.addEdge(it.getKey(), it.getValue(), getWeight(it.getKey(), it.getValue()));
		}
		return result;
  	}
  	
  	private String minKeyPQ(TreeMap<Double, HashSet<String>> keys, HashSet<String> mstSet) {
  		do {
  			Entry<Double, HashSet<String>> p = keys.firstEntry();
  			for (String v : p.getValue()) {
  				if(!mstSet.contains(v)) {
  					p.getValue().remove(v);
  					return v;
  				}
			}
  			
  			keys.remove(p.getKey());
  			
  		}while(!keys.isEmpty());
  		return null;
	}

//PRIM 
  public static void prim(int graph[][]) {
    	int V = graph.length;
    	int parent[] = new int[V];
      int key[] = new int[V];
      Boolean mstSet[] = new Boolean[V];
      for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
       }
      key[0] = 0;
      parent[0] = -1;
      for (int count = 0; count < V ; count++) {
        int u = minKey(key, mstSet);
        mstSet[u] = true;
        for (int v = 0; v < V; v++)
                if (graph[u][v] != 0 && mstSet[v] == false
                    && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }
        printMST(parent, graph);
    }

 public static int minKey(int key[], Boolean mstSet[]) {
        int min = Integer.MAX_VALUE, min_index = -1; 
        for (int v = 0; v < key.length; v++) {
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        }
        return min_index;
    }

public static void printMST(int parent[], int graph[][]) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < parent.length; i++)
            System.out.println(parent[i] + " - " + i + "\t"
                               + graph[i][parent[i]]);
    }


//KRUSKAL
static int INF = Integer.MAX_VALUE;
	
public static void kruskalMST(int graph[][]) {
		int V = graph.length;
	  int mincost = 0;
	  int[] parent = new int[V];
	  for (int i = 0; i < V; i++)
	        parent[i] = i;
	 
	    int edge_count = 0;
	    while (edge_count < V - 1) {
	        int min = INF, a = -1, b = -1;
	        for (int i = 0; i < V; i++) {
	            for (int j = 0; j < V; j++) {
	                if (find(parent, i) != find(parent, j) && graph[i][j] < min) {
	                    min = graph[i][j];
	                    a = i;
	                    b = j;
	                }
	            }
	        }
	        union1(parent, a, b);
	        System.out.printf("Edge %d:(%d, %d) cost:%d \n", edge_count++, a, b, min);
	        mincost += min;
	    }
	    System.out.printf("\n Minimum cost = %d \n", mincost);
	}

static int find(int[] parent, int i) {
	    while (parent[i] != i)
	        i = parent[i];
	    return i;
}

static void union1(int[] parent, int i, int j) {
	    int a = find(parent, i);
	    int b = find(parent, j);
	    parent[a] = b;
	}
