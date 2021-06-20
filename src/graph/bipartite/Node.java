package graph.bipartite;

import java.util.LinkedList;

public class Node
{
  public static final int SET_U = 0;
  public static final int SET_V = 1;

  private String name;
  private LinkedList<Node> connections;
  private int setNumber;

  public Node(String name, int setNumber)
  {
    this.name = name;
    this.setNumber = setNumber;
    connections = new LinkedList<>();
  }

  public Node()
  {
    setNumber = SET_U;
    connections = new LinkedList<>();
  }

  public void addConnection(Node node)
  {
    connections.add(node);
  }

  public void addConnection(Node... node)
  {
    for(Node tempNode: node)
    {
      connections.add(tempNode);
    }
  }


  @Override
  public String toString()
  {
    return "Node{" +
      "name='" + name + '\'' +
      ", connections=" + connections +
      ", setNumber=" + setNumber +
      '}';
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public LinkedList<Node> getConnections()
  {
    return connections;
  }

  public void setConnections(LinkedList<Node> connections)
  {
    this.connections = connections;
  }

  public int getSetNumber()
  {
    return setNumber;
  }

  public void setSetNumber(int setNumber)
  {
    this.setNumber = setNumber;
  }
}
