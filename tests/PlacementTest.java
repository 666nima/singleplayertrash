package dev.singleplayertrash;
import java.util.*;
public class PlacementTest {
 public static void main(String[] args) {
  var panel=new ButtonPlacement.Rect(72,37,176,166);
  var r=ButtonPlacement.find(400,300,panel,List.of());
  if(!r.equals(new ButtonPlacement.Rect(222,199,26,24)))throw new AssertionError("attached corner "+r);
  if(!r.contains(222,199) || r.contains(248,199) || r.contains(222,223))throw new AssertionError("half-open hit bounds");
  if(ButtonPlacement.find(400,300,panel,List.of(r))!=null)throw new AssertionError("collision must hide");
  if(ButtonPlacement.find(247,300,panel,List.of())!=null || ButtonPlacement.find(400,222,panel,List.of())!=null)throw new AssertionError("offscreen must hide");
  if(ButtonPlacement.find(248,223,panel,List.of())==null)throw new AssertionError("exact screen edge");
  var slots=new ArrayList<ButtonPlacement.Rect>();
  for(int row=0;row<4;row++)for(int col=0;col<9;col++)slots.add(new ButtonPlacement.Rect(72+7+18*col,37+(row==3?141:83+18*row),18,18));
  if(ButtonPlacement.find(400,300,panel,slots)==null)throw new AssertionError("vanilla slots clearance");
  for(var obstacle:List.of(new ButtonPlacement.Rect(220,202,28,28),new ButtonPlacement.Rect(240,210,20,20),new ButtonPlacement.Rect(222,195,1,5)))
   if(ButtonPlacement.find(400,300,panel,List.of(obstacle))!=null)throw new AssertionError("trash/viewer/widget collision");
  if(ButtonPlacement.find(400,300,panel,List.of(new ButtonPlacement.Rect(248,199,20,24)))==null)throw new AssertionError("touching not overlap");
  var random=new Random(20261007);
  for(int i=0;i<1000;i++) {
   int w=200+random.nextInt(800),h=180+random.nextInt(500);
   var p=new ButtonPlacement.Rect(random.nextInt(w),random.nextInt(h),176,166);
   var occupied=new ArrayList<ButtonPlacement.Rect>();
   for(int j=0;j<20;j++)occupied.add(new ButtonPlacement.Rect(random.nextInt(w),random.nextInt(h),1+random.nextInt(150),1+random.nextInt(100)));
   var out=ButtonPlacement.find(w,h,p,occupied);
   if(out!=null) {
    if(out.x()!=p.x()+p.width()-26 || out.y()!=p.y()+p.height()-4 || out.x()+out.width()>w || out.y()+out.height()>h)throw new AssertionError("anchor/bounds");
    for(var q:occupied)if(ButtonPlacement.overlaps(out,q,0))throw new AssertionError("overlap");
   }
  }
  System.out.println("PASS attached 26x24/36 vanilla slots/half-open hit/duplicate-trash-viewer-widget collision/exact edge/offscreen/1000 seeded cases; NOT runtime viewer proof");
 }
}
