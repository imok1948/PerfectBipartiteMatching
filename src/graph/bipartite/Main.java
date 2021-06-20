package graph.bipartite;

import java.util.LinkedList;

public class Main
{
  public static void main(String[] args)
  {
    LinkedList<Node> set1 = new LinkedList<>();
    LinkedList<Node> set2 = new LinkedList<>();

    int n = 4;
    int i;
    for (i = 0; i < n; i++)
    {
      set1.add(new Node("" + i, Node.SET_U));
      set2.add(new Node(getString(i), Node.SET_V));
    }

    set1.get(0).addConnection(set2.get(0), set2.get(1));
    set1.get(1).addConnection(set2.get(0), set2.get(2));
    set1.get(2).addConnection(set2.get(0), set2.get(2));
    set1.get(3).addConnection(set2.get(2), set2.get(3));



    for (i = 0; i < n; i++)
    {
      System.out.println(set1.get(i) + " && " + set2.get(i));
    }

  }

  static private String getString(int n)
  {
    char[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    String s = "" + a[n % 26];
    n /= 26;

    while (n > 0)
    {
      s += a[n % 26];
      n /= 26;
    }
    return s;
  }
}
