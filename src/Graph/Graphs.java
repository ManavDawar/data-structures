package Graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Graphs {
	
	static class Edge {
		int n;
		int w;

		Edge(int n, int w) {
			this.n = n;
			this.w = w;
		}
		
	}

	public static ArrayList<ArrayList<Edge>> graph = new ArrayList<>();

	public static ArrayList<ArrayList<Edge>> floyd = new ArrayList<>();

	public static ArrayList<ArrayList<Edge>> topo = new ArrayList<>();

	public static ArrayList<ArrayList<Edge>> bell = new ArrayList<>();

	static void addEdge(ArrayList<ArrayList<Edge>> graph, int v1, int v2, int w) {
		
		graph.get(v1).add(new Edge(v2, w));
		graph.get(v2).add(new Edge(v1, w));
		
	}

	static void addEdgeSingle(ArrayList<ArrayList<Edge>> graph, int v1, int v2, int w) {

		graph.get(v1).add(new Edge(v2, w));

	}

	
//	static void removeEdge(ArrayList<ArrayList<Edge>> graph, int v1, int v2, int w) {
//
//		graph.get(v1).remove();
//		graph.get(v2).remove(v1);
//
//	}

	static void display(ArrayList<ArrayList<Edge>> graph) {
		System.out.println("-----------------------------------------");

		for (int v = 0; v < graph.size(); v++) {

			System.out.print(v + " -> ");

			for (int n = 0; n < graph.get(v).size(); n++) {
				Edge ne = graph.get(v).get(n);
				System.out.print("[" + ne.n + "@" + ne.w + "] ");
			}
			System.out.println();

		}

		System.out.println("-----------------------------------------");
	}

	static boolean hasPath(ArrayList<ArrayList<Edge>> graph, int src, int des, boolean[] visited) {
		if (src == des) {
			return true;
		}

		visited[src] = true;

		for (int i = 0; i < graph.get(src).size(); i++) {
			Edge nbr = graph.get(src).get(i);

			if (visited[nbr.n] == false) {

				if (hasPath(graph, nbr.n, des, visited) == true) {
					return true;
				}

			}
		}

		return false;

	}

	static void printPath(ArrayList<ArrayList<Edge>> graph, int src, int des, boolean[] visited, String psf, int wt) {
		if (src == des) {
			System.out.println(psf + " @ " + wt);
			return;
		}

		visited[src] = true;

		for (int i = 0; i < graph.get(src).size(); i++) {

			Edge nbr = graph.get(src).get(i);

			if (visited[nbr.n] == false) {
				printPath(graph, nbr.n, des, visited, psf + nbr.n + " ", wt + nbr.w);

			}
		}

		visited[src] = false;

	}

	private static String sp;
	private static String lp;
	private static String cp;
	private static String fp;
	private static int spw;
	private static int lpw;
	private static int cpw;
	private static int fpw;

	public static void multisolver(ArrayList<ArrayList<Edge>> graph, boolean[] visited, int s, int d, int cf, int ff) {
		spw = Integer.MAX_VALUE;
		lpw = Integer.MIN_VALUE;
		cpw = Integer.MAX_VALUE;
		fpw = Integer.MIN_VALUE;

		sp = "";
		lp = "";
		cp = "";
		fp = "";

		multisolver1(graph, s, d, visited, cf, ff, s + "", 0);
		System.out.println("Shortest = " + sp + "@" + spw);
		System.out.println("Longest = " + lp + "@" + lpw);
		System.out.println("Ceil = " + cp + "@" + cpw);
		System.out.println("Floor = " + fp + "@" + fpw);

	}

	public static void multisolver1(ArrayList<ArrayList<Edge>> graph, int s, int d, boolean[] visited, int cf, int ff,
			String psf, int wsf) {

		if (s == d) {

			System.out.println(psf + "@" + wsf);
			if (wsf < spw) {
				sp = psf;
				spw = wsf;
			}

			if (wsf > lpw) {
				lp = psf;
				lpw = wsf;
			}

			if (wsf > cf && wsf < cpw) {
				cp = psf;
				cpw = wsf;
			}

			if (wsf < ff && wsf > fpw) {
				fp = psf;
				fpw = wsf;
			}

		}

		visited[s] = true;

		for (int n = 0; n < graph.get(s).size(); n++) {
			Edge ne = graph.get(s).get(n);
			if (visited[ne.n] == false) {
				multisolver1(graph, ne.n, d, visited, cf, ff, psf + ne.n, wsf + ne.w);
			}
		}
		visited[s] = false;

	}

	public static class pair implements Comparable<pair> {
		int data;
		String path;

		@Override
		public int compareTo(pair arg0) {
			// TODO Auto-generated method stub
			return this.data - arg0.data;
		}

	}

	public static PriorityQueue<pair> pq;

	public static void kthLargest(ArrayList<ArrayList<Edge>> graph, boolean[] visited, int s, int d, int k) {
		pq = new PriorityQueue<>();
		multisolver2(graph, visited, s, d, k, s + "", 0);
		while (pq.size() > 0) {
			pair p = pq.remove();
			System.out.println(p.data + " @ " + p.path);
		}
	}

	private static void multisolver2(ArrayList<ArrayList<Edge>> graph2, boolean[] visited, int s, int d, int k,
			String psf, int wsf) {

		if (s == d) {
			if (pq.size() < k) {
				pair p = new pair();
				p.data = wsf;
				p.path = psf;
				pq.add(p);
			} else if (pq.peek().data < wsf) {
				pair p = pq.remove();
				p.data = wsf;
				p.path = psf;
				pq.add(p);
			}
		}

		visited[s] = true;

		for (int n = 0; n < graph.get(s).size(); n++) {
			Edge ne = graph.get(s).get(n);
			if (visited[ne.n] == false) {
				multisolver2(graph2, visited, ne.n, d, k, psf + ne.n, wsf + ne.w);
			}
		}
		visited[s] = false;

	}

	static class THelper {
		int v;
		String psf;
		int dsf;

		public THelper(int v, String psf, int dsf) {
			this.v = v;
			this.psf = psf;
			this.dsf = dsf;
		}
	}

	public static boolean bfs(ArrayList<ArrayList<Edge>> graph, int src, int des) {

		LinkedList<THelper> queue = new LinkedList<>();
		boolean[] visited = new boolean[graph.size()];

		queue.add(new THelper(src, "" + src, 0));

		while (queue.size() > 0) {

			// grmwc

			THelper rem = queue.removeFirst();

			System.out.println(rem.v + " via " + rem.psf + " @ " + rem.dsf);

			if (visited[rem.v] == true) {
				continue;
			} else {
				visited[rem.v] = true;
			}

			if (rem.v == des) {
				return true;
			}

			for (int n = 0; n < graph.get(rem.v).size(); n++) {
				Edge ne = graph.get(rem.v).get(n);

				if (visited[ne.n] == false) {
					THelper nbr = new THelper(ne.n, rem.psf + ne.n, rem.dsf + ne.w);
					queue.addLast(nbr);
				}
			}
		}

		return false;

	}

	static class Helper {
		int i;
		int j;
		int t;

		public Helper(int i, int j, int t) {
			this.i = i;
			this.j = j;
			this.t = t;
		}

		public String toString() {
			return "the block @" + i + "," + j + "burnt at @" + t;
		}
	}

	static void fire(int[][] matrix) {

		LinkedList<Helper> list = new LinkedList<>();

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 2) {
					Helper helper = new Helper(i, j, 0);
					list.add(helper);
				}
			}
		}

		while (list.size() != 0) {
			Helper help = list.removeFirst();
			System.out.println(help);

			int i = help.i, j = help.j;

			if (i + 1 < matrix.length && matrix[i + 1][j] == 0) {
				matrix[i + 1][j] = 2;
				Helper h = new Helper(i + 1, j, help.t + 1);
				list.add(h);
			}

			if (i - 1 >= 0 && matrix[i - 1][j] == 0) {
				matrix[i - 1][j] = 2;
				Helper h = new Helper(i - 1, j, help.t + 1);
				list.add(h);
			}

			if (j + 1 < matrix[0].length && matrix[i][j + 1] == 0) {
				matrix[i][j + 1] = 2;
				Helper h = new Helper(i, j + 1, help.t + 1);
				list.add(h);
			}

			if (j - 1 >= 0 && matrix[i][j - 1] == 0) {
				matrix[i][j - 1] = 2;
				Helper h = new Helper(i, j - 1, help.t + 1);
				list.add(h);
			}

		}

	}

	static ArrayList<String> gcc(ArrayList<ArrayList<Edge>> graph) {

		ArrayList<String> list = new ArrayList<>();

		boolean[] visited = new boolean[graph.size()];

		for (int v = 0; v < graph.size(); v++) {
			if (visited[v] == false) {
				String comp = getconnectedcomponent(graph, visited, v);
				list.add(comp);
			}
		}
		return list;
	}

	private static String getconnectedcomponent(ArrayList<ArrayList<Edge>> graph, boolean[] visited, int v) {

		String comp = "";

		LinkedList<Integer> queue = new LinkedList<>();
		queue.add(v);

		while (queue.size() > 0) {
			int rem = queue.removeFirst();

			if (visited[rem] == true) {
				continue;
			} else {
				visited[rem] = true;
			}

			comp += rem;

			for (int n = 0; n < graph.get(rem).size(); n++) {
				Edge ne = graph.get(rem).get(n);
				if (visited[ne.n] == false) {
					queue.add(ne.n);
				}
			}

		}

		return comp;
	}

	static ArrayList<Integer> gccint(ArrayList<ArrayList<Edge>> graph) {

		ArrayList<Integer> list = new ArrayList<>();

		boolean[] visited = new boolean[graph.size()];

		for (int v = 0; v < graph.size(); v++) {
			if (visited[v] == false) {
				Integer comp = getconnectedcomponentint(graph, visited, v);
				list.add(comp);
			}
		}
		return list;
	}

	private static Integer getconnectedcomponentint(ArrayList<ArrayList<Edge>> graph, boolean[] visited, int v) {

		Integer comp = 0;

		LinkedList<Integer> queue = new LinkedList<>();
		queue.add(v);

		while (queue.size() > 0) {
			int rem = queue.removeFirst();

			if (visited[rem] == true) {
				continue;
			} else {
				visited[rem] = true;
			}

			comp++;

			for (int n = 0; n < graph.get(rem).size(); n++) {
				Edge ne = graph.get(rem).get(n);
				if (visited[ne.n] == false) {
					queue.add(ne.n);
				}
			}

		}

		return comp;
	}

	public static void astro(int[] arr1, int[] arr2, int n) {

		ArrayList<ArrayList<Edge>> graph1 = new ArrayList<>();
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph1.add(new ArrayList<>());
		}

		for (int i = 0; i < arr1.length; i++) {
			addEdge(graph1, arr1[i], arr2[i], 1);
		}
		list = gccint(graph1);
		int ans = 0;
		System.out.println(list);

		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				ans += list.get(i) * list.get(j);
			}
		}
		System.out.println(ans);

	}

	public static int noofisland(int[][] arr) {
		int count = 0;

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				if (arr[i][j] == 1) {
					count++;
					funcall(arr, i, j);
				}
			}
		}

		return count;
	}

	private static void funcall(int[][] arr, int i, int j) {

		if (i >= arr.length || i < 0 || j >= arr.length || j < 0 || arr[i][j] != 1) {
			return;
		}
		arr[i][j] = 0;
		funcall(arr, i + 1, j);
		funcall(arr, i - 1, j);
		funcall(arr, i, j + 1);
		funcall(arr, i, j - 1);

	}

	static class THelper1 {
		int v;
		String psf;
		int dsf;

		public THelper1(int v, String psf, int dsf) {
			this.v = v;
			this.psf = psf;
			this.dsf = dsf;
		}
	}

	public static void vdismin(ArrayList<ArrayList<Edge>> graph, int src) {
		Integer Fvertex = bfsfordiameter(graph, 0);
		Integer Lvertex = bfsfordiameter(graph, Fvertex);
		System.out.println(Fvertex + " " + Lvertex);
		boolean[] visited = new boolean[graph.size()];
		printPath(graph, Fvertex, Lvertex, visited, "", 0);
	}

	public static int bfsfordiameter(ArrayList<ArrayList<Edge>> graph, int src) {

		LinkedList<THelper1> queue = new LinkedList<>();
		boolean[] visited = new boolean[graph.size()];

		queue.add(new THelper1(src, "" + src, 0));
		int vertex = 0;

		while (queue.size() > 0) {

			// grmwc

			THelper1 rem = queue.removeFirst();
			vertex = rem.v;
//			System.out.println(rem.v + " via " + rem.psf + " @ " + rem.dsf);

			if (visited[rem.v] == true) {
				continue;
			} else {
				visited[rem.v] = true;
			}

			for (int n = 0; n < graph.get(rem.v).size(); n++) {
				Edge ne = graph.get(rem.v).get(n);

				if (visited[ne.n] == false) {
					THelper1 nbr = new THelper1(ne.n, rem.psf + ne.n, rem.dsf + ne.w);
					queue.addLast(nbr);
				}
			}
		}

		return vertex;

	}

	public static class bihelper {
		int v;
		Integer level;

		public bihelper(int v, int state) {
			this.v = v;
			this.level = state;
		}
	}

	public static boolean isbipartite(ArrayList<ArrayList<Edge>> graph) {

		LinkedList<bihelper> queue = new LinkedList<>();

		bihelper src = new bihelper(0, 0);

		queue.add(src);
		int[] visited = new int[graph.size()];
		Arrays.fill(visited, -1);

		while (queue.size() > 0) {
			bihelper rem = queue.removeFirst();

			if (visited[rem.v] == -1) {
				visited[rem.v] = rem.level % 2;
			} else if (visited[rem.v] == rem.level % 2) {
				continue;
			} else {
				return false;
			}

			for (int n = 0; n < graph.get(rem.v).size(); n++) {
				Edge ne = graph.get(rem.v).get(n);

				if (visited[ne.n] == -1) {
					bihelper nbr = new bihelper(ne.n, rem.level + 1);
					queue.addLast(nbr);
				}
			}
		}
		return true;

	}

//	static void  firestorm(ArrayList<ArrayList<Integer>> matrix) {
//		LinkedList<Helper> queue=new LinkedList<>();
//		
//		for (int i = 0; i < matrix.size(); i++) {
//			for (int j = 0; j < matrix.get(0).size(); j++) {
//				if (matrix.get(i).get(j) == 2) {
//					Helper helper = new Helper(i, j, 0);
//					queue.add(helper);
//				}
//			}
//		}
//		
//		
//
//		while (queue.size() > 0) {
//			Helper rem = queue.removeFirst();
//
//			if(matrix.get(rem.i).get(rem.j)>0) {
//				continue;
//			}else {
//				matrix.get(rem.i).set(rem.j, rem.t);
//			}
//			System.out.println(rem.i+","+rem.j);
//			if (i + 1 < matrix.length && matrix[i + 1][j] == 0) {
//				matrix[i + 1][j] = 2;
//				Helper h = new Helper(i + 1, j, help.t + 1);
//				list.add(h);
//			}
//
//			if (i - 1 >= 0 && matrix[i - 1][j] == 0) {
//				matrix[i - 1][j] = 2;
//				Helper h = new Helper(i - 1, j, help.t + 1);
//				list.add(h);
//			}
//
//			if (j + 1 < matrix[0].length && matrix[i][j + 1] == 0) {
//				matrix[i][j + 1] = 2;
//				Helper h = new Helper(i, j + 1, help.t + 1);
//				list.add(h);
//			}
//
//			if (j - 1 >= 0 && matrix[i][j - 1] == 0) {
//				matrix[i][j - 1] = 2;
//				Helper h = new Helper(i, j - 1, help.t + 1);
//				list.add(h);
//			}
//
//		}
//
//		
//	}

//	=================================================Diff Approach===============================================
//	 public static class Node {
//         int pathLength;
//         String path;
//     }
//
//     public static void kLargest(ArrayList<ArrayList<Edge>> graph , int src , int dest,boolean[] visited,int pathLength,String path,ArrayList<Node> myList){
//        if(src==dest){
//            Node temp = new Node();
//            temp.pathLength=pathLength;
//            temp.path=path;
//            myList.add(temp);
//            return ;
//        }
//        if(visited[src])
//         return;
//        
//        visited[src]=true;
//        ArrayList<Edge> list = graph.get(src);
//
//        for(int i=0;i<list.size();++i){
//            Edge edge = list.get(i);
//            kLargest(graph, edge.n, dest,visited , pathLength +edge.w , path+"=>"+edge.n,myList);
//        }
//        visited[src]=false;
//    }
//
//
//    public static void kLargest(int k , int floor , int ceil){
//        ArrayList<Node> list =new ArrayList<Node>();
//        kLargest(graph,0,6,new boolean[graph.size()],0,"0",list);
//
//        Comparator<Node> cmp = new Comparator<Node>(){
//            public int compare(Node n1 , Node n2){
//                return n1.pathLength-n2.pathLength;
//            }
//        };
//
//        Collections.sort(list,cmp);
//
//        for(Node node:list){
//            System.out.println("length:"+node.pathLength + " path:"+node.path);
//        }
//
//        System.out.println("Kth-Largest: path: "+ list.get(k-1).path+ " length="+list.get(k-1).pathLength);
//
//        int myceil=0,myFloor=0;
//        String myceilPath="null",myfloorPath="null";
//
//        for(Node node:list){
//            if(node.pathLength>floor)
//                break;
//               
//                myFloor=node.pathLength;
//                myfloorPath=node.path;
//            }
//            System.out.println();
//            System.out.println("FLOOR: path: "+ myfloorPath+ " length="+myFloor);
//                System.out.println();
//  
//            for(int i=list.size()-1;i>=0;--i){
//                Node node = list.get(i);
//                if(node.pathLength<ceil)
//                    break;
//                   
//                    myceil=node.pathLength;
//                    myceilPath=node.path;
//            }
//                System.out.println("CEIL: path: "+ myceilPath+ " length="+myceil);
//
//    }

//========================================29 may 2019=======================================
	// ----------------------------------------------------------------------------------------------------------
	// Ques 1 :Dijstra: Single sorce all
	static class DijkstraHelper implements Comparable<DijkstraHelper> {
		int v;
		int dsf;
		String psf;

		public DijkstraHelper(int v, String psf, int dsf) {
			this.v = v;
			this.psf = psf;
			this.dsf = dsf;
		}

		@Override
		public int compareTo(DijkstraHelper arg0) {
			return this.dsf - arg0.dsf;
		}

		public String toString() {
			return v + " via " + psf + " @" + dsf;
		}
	}

	public static void Dijkstra(ArrayList<ArrayList<Edge>> graph, int src, boolean[] visited) {

		PriorityQueue<DijkstraHelper> queue = new PriorityQueue<>();

		queue.add(new DijkstraHelper(src, src + "", 0));
		while (queue.size() > 0) {
			DijkstraHelper rem = queue.peek();
			queue.remove();

			if (visited[rem.v] == true) {
				continue;
			} else {
				visited[rem.v] = true;
			}

			System.out.println("Path " + rem.v + " via " + rem.psf + " @ " + rem.dsf);

			for (int n = 0; n < graph.get(rem.v).size(); n++) {
				Edge ne = graph.get(rem.v).get(n);

				if (visited[ne.n] == false) {
					queue.add(new DijkstraHelper(ne.n, rem.psf + ne.n, rem.dsf + ne.w));
				}
			}
		}

	}

	// ----------------------------------------------------------------------------------------------------------

	// Ques 2 :

	static class MstHelper implements Comparable<MstHelper> {
		int v;
		int parent;
		int cost;

		public MstHelper(int v, int parent, int cost) {
			this.v = v;
			this.parent = parent;
			this.cost = cost;
		}

		@Override
		public int compareTo(MstHelper arg0) {
			return this.cost - arg0.cost;
		}

		public String toString() {
			return v + " via " + parent + " @" + cost;
		}
	}

	public static ArrayList<ArrayList<Edge>> Mst(ArrayList<ArrayList<Edge>> graph, int src, boolean[] visited) {

		ArrayList<ArrayList<Edge>> mygraph = new ArrayList<>();

		for (int i = 0; i < graph.size(); i++) {
			mygraph.add(new ArrayList<>());
		}

		PriorityQueue<MstHelper> queue = new PriorityQueue<>();

		queue.add(new MstHelper(src, -1, 0));
		while (queue.size() > 0) {
			MstHelper rem = queue.peek();
			queue.remove();

			if (visited[rem.v] == true) {
				continue;
			} else {
				visited[rem.v] = true;
			}

			if (rem.parent != -1) {
				addEdge(mygraph, rem.v, rem.parent, rem.cost);
			}

			for (int n = 0; n < graph.get(rem.v).size(); n++) {
				Edge ne = graph.get(rem.v).get(n);

				if (visited[ne.n] == false) {
					queue.add(new MstHelper(ne.n, rem.v, ne.w));
				}
			}
		}

		return mygraph;
	}

	// ----------------------------------------------------------------------------------------------------------
//	Ques 3 :Krushkals

	static class Kedge implements Comparable<Kedge> {
		int v1;
		int v2;
		int w;

		public Kedge(int v1, int v2, int w) {
			this.v1 = v1;
			this.v2 = v2;
			this.w = w;

		}

		@Override
		public int compareTo(Kedge arg0) {
			return this.w - arg0.w;
		}

	}

	public static ArrayList<ArrayList<Edge>> krushkals(ArrayList<ArrayList<Edge>> graph) {

		ArrayList<ArrayList<Edge>> mst = new ArrayList<>();

		for (int i = 0; i < graph.size(); i++) {
			mst.add(new ArrayList<>());
		}

		int[] pa = new int[graph.size()];

		for (int i = 0; i < pa.length; i++) {
			pa[i] = i;
		}

		int[] ra = new int[graph.size()];
		Arrays.fill(ra, 1);

		PriorityQueue<Kedge> queue = new PriorityQueue<>();

		for (int v = 0; v < graph.size(); v++) {
			for (int n = 0; n < graph.get(v).size(); n++) {

				Edge ne = graph.get(v).get(n);
				if (v < ne.n) {
					queue.add(new Kedge(v, ne.n, ne.w));
				}

			}
		}
		int counter = 0;
		while (queue.size() > 0 && counter < graph.size() - 1) {

			Kedge rem = queue.remove();

			int v1sl = find(pa, rem.v1);
			int v2sl = find(pa, rem.v2);

			if (v1sl != v2sl) {
				addEdge(mst, rem.v1, rem.v2, rem.w);
				counter++;
				merge(pa, ra, v1sl, v2sl);
			}

		}

		return mst;
	}

	private static void merge(int[] pa, int[] ra, int v1sl, int v2sl) {

		if (ra[v1sl] < ra[v2sl]) {
			pa[v1sl] = v2sl;
		} else if (ra[v2sl] < ra[v1sl]) {
			pa[v2sl] = v1sl;
		} else {
			pa[v1sl] = v2sl;
			ra[v2sl]++;
		}
	}

	private static Integer find(int[] pa, int v1) {
		if (pa[v1] == v1) {
			return v1;
		} else {
			int sl = find(pa, pa[v1]);
			return sl;
		}
	}

	// ----------------------------------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------------------------------
//===========================================================================================

//========================================30 may 2019=======================================
	// ----------------------------------------------------------------------------------------------------------
	// Ques 1 : Hamiltonian Cycle and Path

	static void HamiltonianPathandCycle(ArrayList<ArrayList<Edge>> graph, int src) {

		ArrayList<Integer> psf = new ArrayList<>();
		boolean[] visited = new boolean[graph.size()];

		hamiltonianHelper(src, psf, graph, visited);
	}

	private static void hamiltonianHelper(int src, ArrayList<Integer> psf, ArrayList<ArrayList<Edge>> graph,
			boolean[] visited) {

		if (psf.size() == graph.size() - 1) {

			for (int i = 0; i < psf.size(); i++) {
				System.out.print(psf.get(i) + " ");
			}
			System.out.print(src + " ");

			int first = psf.get(0);
			int last = src;
			boolean cycle = false;

			for (int i = 0; i < graph.get(first).size(); i++) {
				Edge ne = graph.get(first).get(i);
				if (ne.n == last) {
					cycle = true;
				}
			}
			if (cycle) {
				System.out.println("*");
			} else {
				System.out.println(".");
			}
		}

		visited[src] = true;

		for (int n = 0; n < graph.get(src).size(); n++) {
			Edge ne = graph.get(src).get(n);

			if (visited[ne.n] == false) {
				psf.add(src);
				hamiltonianHelper(ne.n, psf, graph, visited);
				psf.remove(psf.size() - 1);
			}
		}

		visited[src] = false;

	}

	// ----------------------------------------------------------------------------------------------------------
	// ques2:Knights Tour

	static int counter = 0;

	static void KnightsTour(int[][] chess, int row, int col, int move) {

		if (move == chess.length * chess.length - 1) {
			++counter;
			chess[row][col] = move;
			System.out.println("------------" + counter + "-------------");
			for (int i = 0; i < chess.length; ++i) {
				for (int j = 0; j < chess.length; ++j) {
					System.out.print(chess[i][j] + "\t ");
				}
				System.out.println();
			}
			chess[row][col] = -1;
			return;
		}

		chess[row][col] = move;
		if (isKvalid(chess, row - 2, col + 1)) {

			KnightsTour(chess, row - 2, col + 1, move + 1);
		}
		if (isKvalid(chess, row - 2, col - 1)) {
			KnightsTour(chess, row - 2, col - 1, move + 1);
		}
		if (isKvalid(chess, row + 2, col + 1)) {
			KnightsTour(chess, row + 2, col + 1, move + 1);
		}
		if (isKvalid(chess, row + 2, col - 1)) {
			KnightsTour(chess, row + 2, col - 1, move + 1);
		}
		if (isKvalid(chess, row + 1, col - 2)) {
			KnightsTour(chess, row + 1, col - 2, move + 1);
		}
		if (isKvalid(chess, row + 1, col + 2)) {
			KnightsTour(chess, row + 1, col + 2, move + 1);
		}
		if (isKvalid(chess, row - 1, col - 2)) {
			KnightsTour(chess, row - 1, col - 2, move + 1);
		}
		if (isKvalid(chess, row - 1, col + 2)) {
			KnightsTour(chess, row - 1, col + 2, move + 1);
		}
		chess[row][col] = -1;

	}

	static boolean isKvalid(int[][] chess, int row, int col) {

		if (row < 0 || row >= chess.length || col < 0 || col >= chess[0].length) {
			return false;
		} else if (chess[row][col] != -1) {
			return false;
		} else {
			return true;
		}

	}

	// ----------------------------------------------------------------------------------------------------------

	// ques3:Floyd Warshall

	static void FloydWarshal(ArrayList<ArrayList<Edge>> graph) {

		int[][] res = new int[graph.size()][graph.size()];

		for (int i = 0; i < res.length; i++) {
			Arrays.fill(res[i], Integer.MAX_VALUE);
		}

//		t=0 all direct paths with 0 intermediates
		for (int i = 0; i < graph.size(); i++) {
			res[i][i] = 0;
			for (int n = 0; n < graph.get(i).size(); n++) {
				Edge ne = graph.get(i).get(n);
				res[i][ne.n] = ne.w;
			}
		}

//		t=1 all path will enter comparision which contain only i1 as intermediate

//		t=1 all path will enter comparision which contain only i1 and i2 as intermediate

		for (int i = 0; i < graph.size(); i++) {

			for (int s = 0; s < graph.size(); s++) {

				for (int d = 0; d < graph.size(); d++) {

					if (i == s || i == d || s == d) {
						continue;
					} else if (res[i][d] == Integer.MAX_VALUE || res[s][i] == Integer.MAX_VALUE) {
						continue;
					} else {
						if (res[s][i] + res[i][d] < res[s][d]) {
							res[s][d] = res[s][i] + res[i][d];
						}
					}
				}
			}
		}

		for (int s = 0; s < graph.size(); s++) {

			for (int d = 0; d < graph.size(); d++) {
				System.out.print(res[s][d] + "\t");
			}
			System.out.println();
		}
	}

	// ----------------------------------------------------------------------------------------------------------

	// ========================================1 june
	// 2019=======================================
	// ----------------------------------------------------------------------------------------------------------

	// ques : Bellman Ford

	public static void bellmanFord(ArrayList<ArrayList<Edge>> graph, int src) {
		int[] res = new int[graph.size()];
		Arrays.fill(res, Integer.MAX_VALUE);
		res[src] = 0;

		ArrayList<Kedge> alledges = new ArrayList<>();

		for (int v = 0; v < graph.size(); ++v) {
			for (int n = 0; n < graph.get(v).size(); ++n) {
				Edge ne = graph.get(v).get(n);
				Kedge ke = new Kedge(v, ne.n, ne.w);
				alledges.add(ke);
			}
		}

		for (int i = 0; i < graph.size() - 1; ++i) {
			for (int j = 0; j < alledges.size(); ++j) {
				Kedge ke = alledges.get(j);
				if (res[ke.v1] != Integer.MAX_VALUE) {
					if (res[ke.v1] + ke.w < res[ke.v2]) {
						res[ke.v2] = res[ke.v1] + ke.w;
					}
				}
			}
		}

		for (int j = 0; j < alledges.size(); ++j) {

			Kedge ke = alledges.get(j);

			if (res[ke.v1] != Integer.MAX_VALUE) {
				if (res[ke.v1] + ke.w < res[ke.v2]) {

					System.out.println("Negative Weight Cycle");
					return;
				}
			}

		}
		for (int i = 0; i < res.length; i++) {
			System.out.print(res[i] + " ");
		}
		System.out.println();

	}

	// ----------------------------------------------------------------------------------------------------------
	// ques:Topoleogical Sort

	static void TopologicalSorMatser(ArrayList<ArrayList<Edge>> graph) {

		boolean[] visited = new boolean[graph.size()];
		LinkedList<Integer> stack = new LinkedList<>();
		for (int i = 0; i < graph.size(); i++) {
			if (visited[i] == false) {
				TopologicalSorHelper(graph, visited, stack, i);

			}
		}

		while (stack.size() > 0) {
			System.out.print(stack.pop() + " ");
		}

	}

	private static void TopologicalSorHelper(ArrayList<ArrayList<Edge>> graph, boolean[] visited,
			LinkedList<Integer> stack, int src) {

		visited[src] = true;

		for (int i = 0; i < graph.get(src).size(); i++) {
			Edge nbr = graph.get(src).get(i);

			if (visited[nbr.n] == false) {
				TopologicalSorHelper(graph, visited, stack, nbr.n);
			}
		}
		stack.push(src);
	}

	// ----------------------------------------------------------------------------------------------------------
	// ques: DFS iterative

	static class DFSHelper {
		int v;
		String psf;
		int dsf;

		public DFSHelper(int v, String psf, int dsf) {
			this.v = v;
			this.psf = psf;
			this.dsf = dsf;
		}
	}

	public static boolean dfs(ArrayList<ArrayList<Edge>> graph, int src, int des) {

		LinkedList<DFSHelper> stack = new LinkedList<>();
		boolean[] visited = new boolean[graph.size()];

		stack.add(new DFSHelper(src, "" + src, 0));

		while (stack.size() > 0) {

			// grmwc

			DFSHelper rem = stack.pop();

			System.out.println(rem.v + " via " + rem.psf + " @ " + rem.dsf);

			if (visited[rem.v] == true) {
				continue;
			} else {
				visited[rem.v] = true;
			}

			if (rem.v == des) {
				return true;
			}

			for (int n = 0; n < graph.get(rem.v).size(); n++) {
				Edge ne = graph.get(rem.v).get(n);

				if (visited[ne.n] == false) {
					DFSHelper nbr = new DFSHelper(ne.n, rem.psf + ne.n, rem.dsf + ne.w);
					stack.push(nbr);
				}
			}
		}

		return false;

	}

	// ----------------------------------------------------------------------------------------------------------
//		Ques:Bridges and Articulation points
	static int timer = 0;

	public static void BridgesandAp(ArrayList<ArrayList<Edge>> graph, boolean[] visited, boolean[] aps, int[] discovery,
			int[] low, int par, int src) {

		visited[src] = true;

		discovery[src] = low[src] = ++timer;
		Integer counter = 0;

		for (int i = 0; i < graph.get(src).size(); i++) {
			Edge ne = graph.get(src).get(i);
			int nbr = ne.n;
			if (visited[nbr] == true && nbr == par) {
				// Do Nothing
			} else if (visited[nbr] == true && nbr != par) {
				// BackEdge
				low[src] = Math.min(low[src], discovery[nbr]);
			} else {
				counter++;
				BridgesandAp(graph, visited, aps, discovery, low, src, nbr);
				// Update Low
				low[src] = Math.min(low[src], low[nbr]);
				// For root
				if (discovery[src] == 1) {
					if (counter >= 2) {
						aps[src] = true;
					}
				} else {
					// Define Ap
					if (low[nbr] >= discovery[src]) {
						aps[src] = true;
					}
					// Define Bride
					if (low[nbr] > discovery[src]) {
						System.out.println("Bridge from " + src + " to " + nbr);
					}

				}
			}
		}

	}

	// ----------------------------------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------------------------------

//===========================================================================================

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int v = 0; v < 9; v++) {
			graph.add(new ArrayList<>());
		}

		for (int v = 0; v < 4; v++) {
			floyd.add(new ArrayList<>());
		}

		for (int v = 0; v < 7; v++) {
			topo.add(new ArrayList<>());
		}

		for (int v = 0; v < 4; v++) {
			bell.add(new ArrayList<>());
		}

		addEdgeSingle(bell, 0, 1, 2);
		addEdgeSingle(bell, 1, 2, 4);
		addEdgeSingle(bell, 2, 3, 8);
		addEdgeSingle(bell, 3, 0, 9);
		addEdgeSingle(bell, 2, 0, -5);

		bellmanFord(bell, 0);

		addEdgeSingle(topo, 0, 1, 1);
		addEdgeSingle(topo, 1, 2, 1);
		addEdgeSingle(topo, 2, 3, 1);
		addEdgeSingle(topo, 0, 4, 1);
		addEdgeSingle(topo, 5, 4, 1);
		addEdgeSingle(topo, 5, 6, 1);
		addEdgeSingle(topo, 6, 3, 1);

		floyd.get(0).add(new Edge(1, 2));
		floyd.get(0).add(new Edge(2, 4));
		floyd.get(0).add(new Edge(3, 8));
		floyd.get(1).add(new Edge(2, 1));
		floyd.get(1).add(new Edge(3, 5));
		floyd.get(2).add(new Edge(3, 1));

//		addEdge(graph, 0, 1, 10);
//		addEdge(graph, 1, 2, 10);
//		addEdge(graph, 2, 3, 10);
//		addEdge(graph, 0, 3, 40);
//		addEdge(graph, 3, 4, 2);
//		addEdge(graph, 4, 5, 3);
//		addEdge(graph, 5, 6, 3);
//		addEdge(graph, 4, 6, 8);
//
//		addEdge(graph, 7, 0, 3);
//		addEdge(graph, 8, 0, 4);
//		addEdge(graph, 7, 8, 2);
		
		addEdge(graph, 0, 1, 10);
        addEdge(graph, 1, 2, 10);
        addEdge(graph, 2, 3, 10);
        addEdge(graph, 0, 3, 40);
        addEdge(graph, 3, 4, 2);
        addEdge(graph, 4, 5, 3);
        addEdge(graph, 5, 6, 3);
        addEdge(graph, 4,6,8);
        addEdge(graph, 7, 0, 1);
        addEdge(graph, 7, 8, 1);
        addEdge(graph, 8, 0, 1);

		boolean[] aps = new boolean[graph.size()];

		BridgesandAp(graph, new boolean[graph.size()], aps, new int[graph.size()], new int[graph.size()], -1, 2);

		for (int i = 0; i < aps.length; i++) {
			if (aps[i] == true) {
				System.out.println(i);
			}
		}
//		for adding a new vertex we have to add an element in first arraylist
//		graph.add(new ArrayList<>());

//		display(graph);

//		boolean[] visited = new boolean[graph.size()];

//		System.out.println(hasPath(graph, 0, 6, visited));

//		printPath(graph, 0, 6, visited, 0+" ",0);

//		multisolver(graph, visited, 0, 6, 38, 40);

//		kthLargest(graph, visited, 0, 6, 3);
//		kLargest(3,45, 47);

//		System.out.println(bfs(graph, 0, 6));
//		int[][] matrix = { { 0, 0, 0 }, { 1, 2, 1 }, { 2, 0, 0 } };
//		fire(matrix);6

//		System.out.println(gcc(graph));
//		int[] arr1 = { 1, 6, 8, 2, 9, 10, 0 };
//		int[] arr2 = { 2, 9, 3, 5, 5, 3, 11 };
//
//		astro(arr1, arr2, 12);
//
//		System.out.println(isbipartite(graph));0
//		Dijkstra(graph, 0, visited);

//		vdismin(graph, 0);
//		ArrayList<ArrayList<Edge>> mygraph = Mst(graph, 0, visited);
//		display(mygraph);

//		display(krushkals(graph));
		HamiltonianPathandCycle(graph, 2);

//		int[][] arr = new int[5][5];
//		for (int i = 0; i < arr.length; i++) {
//			Arrays.fill(arr[i], -1);
//		}
//
//		KnightsTour(arr, 1, 1, 0);
//		FloydWarshal(floyd);

//		display(topo);
//		TopologicalSorMatser(topo);

	}

}
