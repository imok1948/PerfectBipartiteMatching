package graph.bipartite;

public class Edge
{
 private Vertex u;
 private Vertex v;
 
 public Edge(Vertex u, Vertex v)
 {
  this.u = u;
  this.v = v;
 }
 
 @Override
 public String toString()
 {
  return "Edge{" + "u=" + u + ", v=" + v + '}';
 }
 
}
