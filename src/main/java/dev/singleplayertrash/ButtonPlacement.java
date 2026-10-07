package dev.singleplayertrash;
import java.util.List;
public class ButtonPlacement {
 public record Rect(int x,int y,int width,int height) {
  public boolean contains(double mx,double my) { return mx>=x && mx<x+width && my>=y && my<y+height; }
 }
 public static Rect find(int screenWidth,int screenHeight,Rect panel,List<Rect> occupied) {
  Rect candidate=new Rect(panel.x+panel.width-26,panel.y+panel.height-4,26,24);
  if(candidate.x<0 || candidate.y<0 || candidate.x+candidate.width>screenWidth || candidate.y+candidate.height>screenHeight)return null;
  for(Rect r:occupied) if(r.width>0 && r.height>0 && overlaps(candidate,r,0))return null;
  return candidate;
 }
 public static boolean overlaps(Rect a,Rect b,int gap) {
  return a.x<b.x+b.width+gap && a.x+a.width>b.x-gap && a.y<b.y+b.height+gap && a.y+a.height>b.y-gap;
 }
}
