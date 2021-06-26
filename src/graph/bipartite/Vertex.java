package graph.bipartite;

public class Vertex
{

  public static final int SET_U = 0;
  public static final int SET_V = 1;

  private String name;
  private int setType;

  public Vertex(String name)
  {
    this.name = name;
  }

  @Override
  public String toString()
  {
    return "Vertex{" +
      "name='" + name + '\'' +
      ", setType=" + setType +
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

  public int getSetType()
  {
    return setType;
  }

  public void setSetType(int setType)
  {
    this.setType = setType;
  }
}
