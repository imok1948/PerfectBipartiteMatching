package graph.bipartite;

import java.util.*;

public class Main
{
 private static final boolean DISCONNECTED = false;
 private static final boolean CONNECTED = true;
 
 static HashSet<String> matchedEdges = new LinkedHashSet<>();
 static HashSet<Integer> matchedVertices = new LinkedHashSet<>();
 
 public static void main(String[] args)
 {
  boolean[][] matrix = getGraph();
  
  int n = matrix.length;
  for(HashSet<String> augmentedPath = getAugmentedPath(matrix); augmentedPath != null && augmentedPath.size() != 0; )
  {
   
   System.out.println("Augmented Path : ");
   printEdges(augmentedPath, n);
   
   updateVertices(augmentedPath, n);
   
   System.out.println("New Matched Edges : ");
   printEdges(matchedEdges, n);
   System.out.println();
   
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
 
 public static void printEdges(HashSet<String> edges, int n)
 {
  for(String edge : edges)
  {
   int u = Integer.parseInt(edge.split(" ")[0]);
   int v = Integer.parseInt(edge.split(" ")[1]) - n / 2;
   System.out.println(u + "----" + getString(v));
  }
 }
 
 public static HashSet<String> getAugmentedPath(boolean[][] matrix)
 {
  Stack<Integer> stack = new Stack<>();
  Stack<Integer> index = new Stack<>();
  HashSet<Integer> visited = new LinkedHashSet<>();
  Stack<String> currentlyMatchedEdges = new Stack<>();
  
  int n = matrix.length;
  int i, j;
  
  for(i = 0; i < n; i++)
  {
   if(!matchedVertices.contains(i))
   {
    stack.push(i);
    index.push(0);
    break;
   }
  }
  
  while(stack.size() != 0)
  {
   int root = stack.peek();
   visited.add(root);
   while(index.peek() < n && (matrix[root][index.peek()] == DISCONNECTED || visited.contains(index.peek())))
   {
    index.push(index.pop() + 1);
   }
   
   
   if(index.peek() < n)
   {
    if(!matchedVertices.contains(index.peek()))
    {
     //Finish here
     currentlyMatchedEdges.push(root + " " + index.peek());
     HashSet<String> augmentPath = new HashSet<>();
     for(String edge : currentlyMatchedEdges)
     {
      augmentPath.add(edge);
     }
     return augmentPath;
    }
    else
    {
     currentlyMatchedEdges.push(root + " " + index.peek());
     stack.push(index.peek());//Put the next vertex to visit
     index.push(index.pop() + 1);//If path not found I will start from the next vertex
     index.push(0);//Will start searching from 0th index in matrix
    }
   }
   else
   {
    stack.pop();
    index.pop();
    currentlyMatchedEdges.pop();
   }
   
   
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
    currentlyMatchedEdges.pop();
    stack.pop();
    index.pop();
   }
  }
  return null;
 }
 
 public static void updateVertices(HashSet<String> augmentedPath, int n)
 {
  HashSet<String> mExcludeP = new HashSet<>();
  HashSet<String> pExcludeM = new HashSet<>();
  
  for(String edge : matchedEdges)
  {
   if(!augmentedPath.contains(edge))
   {
    mExcludeP.add(edge);
   }
  }
  
  for(String edge : augmentedPath)
  {
   if(!matchedEdges.contains(edge))
   {
    pExcludeM.add(edge);
   }
  }
  
  matchedEdges = new LinkedHashSet<>();
  for(String edge : mExcludeP)
  {
   matchedEdges.add(edge);
  }
  
  for(String edge : pExcludeM)
  {
   matchedEdges.add(edge);
  }
  
  matchedVertices = new LinkedHashSet<>();
  for(String edge : matchedEdges)
  {
   int u = Integer.parseInt(edge.split(" ")[0]);
   int v = Integer.parseInt(edge.split(" ")[1]);
   matchedVertices.add(u);
   matchedVertices.add(v);
  }
 }
 
 public static boolean[][] getGraph()
 {
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
 
 public static boolean[][] getRandomBipartiteGraph(int nodes)
 {
  boolean[][] matrix = new boolean[nodes][];
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
