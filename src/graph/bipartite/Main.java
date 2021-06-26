package graph.bipartite;

import java.util.*;

public class Main
{
 
 private static final boolean DEBUG = false;
 
 private static final boolean DISCONNECTED = false;
 private static final boolean CONNECTED = true;
 
 static HashSet<String> matchedEdges = new LinkedHashSet<>();
 static HashSet<Integer> matchedVertices = new LinkedHashSet<>();
 
 public static void main(String[] args)
 {
  boolean[][] matrix = getRandomBipartiteGraph(500);
  int n = matrix.length;
  
  if(DEBUG)
  {
   for(boolean[] row : matrix)
   {
    for(boolean b : row)
    {
     System.out.print((b == true ? 1 : 0) + " ");
    }
    System.out.println();
   }
  }
  
  int count = 0;
  for(HashSet<String> augmentedPath = getAugmentedPath(matrix); augmentedPath != null && augmentedPath.size() != 0; count++)
  {
   if(count != 0 && count % 10 == 0)
   {
    System.out.println("Current Size : " + augmentedPath.size());
   }
   
   if(DEBUG)
   {
    System.out.println("Augmented Path : ");
    printEdges(augmentedPath, n);
   }
   updateVertices(augmentedPath, n);
   
   if(DEBUG)
   {
    System.out.println("New Matched Edges : ");
    printEdges(matchedEdges, n);
    System.out.println();
   }
   
   augmentedPath = getAugmentedPath(matrix);
  }
  
  if(matchedEdges.size() == n / 2)
  {
   System.out.println("Perfect Matching found with edges : ");
  }
  else
  {
   System.out.println("Perfect Matching not found but Maximum Matching is : ");
  }
  printEdges(matchedEdges, n);
 }
 
 
 public static HashSet<String> getAugmentedPath(boolean[][] matrix)
 {
  
  //Try to do DFS
  //Backtrack if you found dead end
  //Return if you found an unmatched vertex with alternating path of odd length
  Stack<Integer> stack = new Stack<>();
  Stack<Integer> index = new Stack<>();
  HashSet<Integer> visited = new LinkedHashSet<>();
  Stack<String> currentlyMatchedEdges = new Stack<>();
  
  int n = matrix.length;
  int i;
  
  //Start from an unmatched vertex
  for(i = 0; i < n; i++)
  {
   if(!matchedVertices.contains(i))
   {
    stack.push(i);
    //Index to keep track current neighbour of root
    index.push(0);
    break;
   }
  }
  
  while(stack.size() != 0)
  {
   int root = stack.peek();
   visited.add(root);
   
   //From the root node, try to find an unvisited vertex
   while(index.peek() < n && (matrix[root][index.peek()] == DISCONNECTED || visited.contains(index.peek())))
   {
    index.push(index.pop() + 1);
   }
   
   //If index < n means you successfully find an unvisited vertex
   if(index.peek() < n)
   {
    
    //If the vertex is unmatched == you successfully got an alternating path starting and ending with unmatched vertex
    //Yes, the path length is odd (With even vertex)
    if(!matchedVertices.contains(index.peek()))
    {
     //Finish here by putting current edge
     currentlyMatchedEdges.push(root + " " + index.peek());
     
     //I am returning hashset, even it is in loop, it is the last iteration of loop hence it will not increase TC
     HashSet<String> augmentPath = new HashSet<>();
     for(String edge : currentlyMatchedEdges)
     {
      augmentPath.add(edge);
     }
     return augmentPath;
    }
    else
    {
     //If current vertex is matched, then there exist a matched path from this vertex, add the edge from root to current (This is unmatched edge)
     currentlyMatchedEdges.push(root + " " + index.peek());
     stack.push(index.peek());//Put the next vertex to visit
     index.push(index.pop() + 1);//If path not found I will start from the next vertex
     index.push(0);//Will start searching from 0th index in matrix
    }
   }
   else
   {
    //If index goes out of bound, it means you were not able to find any unvisited vertex (Neither matched nor unmatched)
    //Go back to previous checkpoint
    stack.pop();
    index.pop();
    if(stack.size() == 0)
    {
     return null;//We have started from this unmatched vertex, but it could not find any suitable path from it, hence perfect matching cannot be found
    }
    currentlyMatchedEdges.pop();
   }
   
   
   //Same as mentioned above, but this edge would be matched edge
   root = stack.peek();
   while(index.peek() < n && (matrix[root][index.peek()] == DISCONNECTED || visited.contains(index.peek()) || matchedEdges.contains(index.peek() + " " + root) == false))
   {
    index.push(index.pop() + 1);
   }
   
   if(index.peek() < n)
   {
    currentlyMatchedEdges.push(index.peek() + " " + root);
    stack.push(index.peek());//Put the next vertex to visit
    index.push(index.pop() + 1);//If path not found I will start from the next vertex
    index.push(0);//Will start searching from 0th index in matrix
   }
   else
   {
    
    //If index goes out of bound, it means you were not able to find any unvisited vertex (Neither matched nor unmatched)
    //Go back to previous checkpoint
    stack.pop();
    index.pop();
    
    if(stack.size() == 0)
    {
     return null;//We have started from this unmatched vertex, but it could not find any suitable path from it, hence perfect matching cannot be found
    }
    currentlyMatchedEdges.pop();
   }
  }
  return null;
 }
 
 public static void updateVertices(HashSet<String> augmentedPath, int n)
 {
  HashSet<String> matchingExcludingAugmentedPath = new HashSet<>();
  HashSet<String> augmentedPathExcludingMatching = new HashSet<>();
  
  
  //Symmetric difference matchedEdges - augmentedPath
  for(String edge : matchedEdges)
  {
   if(!augmentedPath.contains(edge))
   {
    matchingExcludingAugmentedPath.add(edge);
   }
  }
  //Symmetric difference augmentedPath - matchedEdges
  for(String edge : augmentedPath)
  {
   if(!matchedEdges.contains(edge))
   {
    augmentedPathExcludingMatching.add(edge);
   }
  }
  
  //Union both difference to get complete symmetric difference
  matchedEdges = new LinkedHashSet<>();
  for(String edge : matchingExcludingAugmentedPath)
  {
   matchedEdges.add(edge);
  }
  
  for(String edge : augmentedPathExcludingMatching)
  {
   matchedEdges.add(edge);
  }
  
  
  //Update current matching
  matchedVertices = new LinkedHashSet<>();
  for(String edge : matchedEdges)
  {
   int u = Integer.parseInt(edge.split(" ")[0]);
   int v = Integer.parseInt(edge.split(" ")[1]);
   matchedVertices.add(u);
   matchedVertices.add(v);
  }
 }
 
 public static void printEdges(HashSet<String> edges, int n)
 {
  //Utility function to print map
  //Integers are vertices of set U
  //String are vertices of set V
  
  for(String edge : edges)
  {
   int u = Integer.parseInt(edge.split(" ")[0]);
   int v = Integer.parseInt(edge.split(" ")[1]) - n / 2;
   System.out.println((u + 1) + "----" + getString(v));
  }
 }
 
 public static boolean[][] getRandomBipartiteGraph(int nodes)
 {
  /*
   * Utility function to generate a bipartite graph (May not be connected)
   * nodes ==> Total number of nodes in the graph (|U+V|) */
  
  boolean[][] matrix = new boolean[nodes][nodes];
  
  Random random = new Random();
  int connections = 0;
  int i, j;
  
  //Iterate over all nodes in set U
  for(i = 0; i < nodes / 2; i++)
  {
   //It have to have at least 1 connection with vertex in set V
   connections = (random.nextInt(nodes / 2) + 1);
   for(j = 0; j < connections; j++)
   {
    matrix[i][nodes / 2 + j] = CONNECTED;
    matrix[nodes / 2 + j][i] = CONNECTED;
   }
  }
  return matrix;
 }
 
 public static boolean[][] getGraph()
 {
  /*
   * Utility function to feed input manual adjacency matrix
   * Not in use now
   * */
  
  String[] matrixString = {
   "00001101", "00001100", "00001110", "00000100", "11100000", "11110000", "00100000", "10000000"
  };
  
  matrixString = new String[]{
   "0000011010", "0000011011", "0000001100", "0000001001", "0000011000", "1100100000", "1111100000", "0010000000", "1100000000", "0101000000"
  };
  
  boolean[][] matrix = new boolean[matrixString.length][];
  for(int i = 0; i < matrixString.length; i++)
  {
   matrix[i] = new boolean[matrixString.length];
   for(int j = 0; j < matrixString.length; j++)
   {
    matrix[i][j] = matrixString[i].charAt(j) == '1';
   }
  }
  return matrix;
 }
 
 static private String getString(int n)
 {
  char[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  
  String s = "" + a[n % 26];
  n /= 26;
  
  while(n > 0)
  {
   s += a[n % 26];
   n /= 26;
  }
  return s;
 }
}
