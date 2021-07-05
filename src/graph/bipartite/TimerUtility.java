package graph.bipartite;

public class TimerUtility
{
 private long startTimer = 0;
 private long endTimer = 0;
 
 public void startTimer()
 {
  startTimer = System.currentTimeMillis();
 }
 
 public void endTimer()
 {
  endTimer = System.currentTimeMillis();
 }
 
 public long getTimeDifference()
 {
  long difference = endTimer - startTimer;
  System.out.println("Millis : " + difference + " ( " + difference / (1000 * 60 * 60) + "H " + difference / (1000 * 60) + "M " + difference / (1000.0) + "S )");
  return difference;
 }
}
