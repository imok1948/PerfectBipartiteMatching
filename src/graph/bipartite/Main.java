package graph.bipartite;

import java.util.*;

public class Main
{
 static HashSet<String> matchedEdges = new LinkedHashSet<>();
 static HashSet<Integer> matchedVertices = new LinkedHashSet<>();
 
 static String[] setU = "1 2 3 4 5 6 7 8 9 0".split(" ");
 static String[] setV = "A B C D E F G H I J".split(" ");
 
 public static void main(String[] args)
 {
  int i, j;
  int[][] matrix = getGraph();
  
  int n = matrix.length;
  for(HashSet<String> augmentedPath = getAugmentedPath(matrix); augmentedPath != null && augmentedPath.size() != 0; )
  {
   
   System.out.print("P : ");
   printer(augmentedPath);
   
   updateVertices(augmentedPath, n);
   
   System.out.print("M : ");
   printer(matchedEdges);
   System.out.println();
   
   augmentedPath = getAugmentedPath(matrix);
  }
  
  System.out.println("Matched Edges:");
  printMatchedEdges(n);
 }
 
 
 public static void printMatchedEdges(int n)
 {
  for(String edge : matchedEdges)
  {
   int u = Integer.parseInt(edge.split(" ")[0]);
   int v = Integer.parseInt(edge.split(" ")[1]) - n / 2;
   System.out.println(setU[u] + "----" + setV[v]);
  }
 }
 
 public static void printAugmentedPath(HashSet<String> augmentedPath, int n)
 {
  for(String edge : augmentedPath)
  {
   int u = Integer.parseInt(edge.split(" ")[0]);
   int v = Integer.parseInt(edge.split(" ")[1]) - n / 2;
   System.out.println(setU[u] + "----" + setV[v]);
  }
 }
 
 public static void printer(HashSet<String> hashSet)
 {
  for(String s : hashSet)
  {
   System.out.print(s + ", ");
  }
  System.out.println();
 }
 
 
 public static HashSet<String> getAugmentedPath(int[][] matrix)
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
   while(index.peek() < n && (matrix[root][index.peek()] == 0 || visited.contains(index.peek())))
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
   
   if(currentlyMatchedEdges.size() != 0)
   {
    System.out.println("P1 : " + currentlyMatchedEdges.peek());
   }
   
   root = stack.peek();
   while(index.peek() < n && (matrix[root][index.peek()] == 0 || visited.contains(index.peek()) || matchedEdges.contains(index.peek() + " " + root) == false))
   {
    //    System.out.println();
    //    System.out.println("Try2 : " + root + " " + index.peek());
    //    System.out.println("Neighbour : " + (matrix[root][index.peek()] == 0));
    //    System.out.println("Visited : " + visited.contains(index.peek()));
    //    System.out.println("Matched : " + (matchedEdges.contains(index.peek() + " " + root)));
    //    System.out.println();
    
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
   
   //   if(currentlyMatchedEdges.size() != 0)
   //   {
   //    System.out.println("P2 : " + currentlyMatchedEdges.peek());
   //   }
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
 
 public static int[][] getGraph()
 {
  String[] matrixString = {
   "00001101", "00001100", "00001110", "00000100", "11100000", "11110000", "00100000", "10000000"
  };
  
  matrixString = new String[]{
   "0000011010", "0000011011", "0000001100", "0000001001", "0000011000", "1100100000", "1111100000", "0010000000", "1100000000", "0101000000"
  };
  int[][] matrix = new int[matrixString.length][];
  for(int i = 0; i < matrixString.length; i++)
  {
   matrix[i] = new int[matrixString.length];
   for(int j = 0; j < matrixString.length; j++)
   {
    matrix[i][j] = Integer.parseInt("" + matrixString[i].charAt(j));
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
