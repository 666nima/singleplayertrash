package dev.singleplayertrash;

import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="singleplayertrash", value=Dist.CLIENT)
public final class TrashClient {
 private static final Map<Screen,ButtonPlacement.Rect> drawn=new WeakHashMap<>();
 private static final Map<Screen,Integer> pressed=new WeakHashMap<>();
 private static final class EntrySlot extends Slot {
  EntrySlot(Slot original) { super(original.container,original.getContainerSlot(),original.x,original.y); }
  @Override public boolean mayPickup(Player player) { return false; }
  @Override public boolean mayPlace(ItemStack stack) { return false; }
 }
 private static Slot entry(CreativeModeInventoryScreen screen) {
  if(!screen.isInventoryOpen())return null;
  var slots=screen.getMenu().slots;
  for(int i=0;i<slots.size();i++) {
   var slot=slots.get(i);
   if(slot.x==173 && slot.y==112 && (slot.getClass()==Slot.class || slot instanceof EntrySlot)) {
    if(!(slot instanceof EntrySlot)) { var replacement=new EntrySlot(slot);replacement.index=slot.index;slots.set(i,replacement);slot=replacement; }
    return slot;
   }
  }
  return null;
 }
 static ButtonPlacement.Rect placement(Screen screen) {
  if(!LocalTrash.eligible(Minecraft.getInstance()))return null;
  if(screen instanceof CreativeModeInventoryScreen creative) {
   var slot=entry(creative);
   return slot==null?null:new ButtonPlacement.Rect(creative.getGuiLeft()+slot.x-1,creative.getGuiTop()+slot.y-1,18,18);
  }
  if(!(screen instanceof InventoryScreen container))return null;
  var occupied=new ArrayList<ButtonPlacement.Rect>();
  var existing=ExistingTrash.area();
  if(existing!=null)occupied.add(existing);
  for(var child:screen.children())if(child instanceof AbstractWidget w && w.visible)
   occupied.add(new ButtonPlacement.Rect(w.getX(),w.getY(),w.getWidth(),w.getHeight()));
  for(var slot:container.getMenu().slots)if(slot.isActive())
   occupied.add(new ButtonPlacement.Rect(container.getGuiLeft()+slot.x-1,container.getGuiTop()+slot.y-1,18,18));
  return ButtonPlacement.find(screen.width,screen.height,new ButtonPlacement.Rect(container.getGuiLeft(),container.getGuiTop(),container.getXSize(),container.getYSize()),occupied);
 }
 @SubscribeEvent public static void render(ScreenEvent.Render.Post event) {
  var r=placement(event.getScreen());
  if(r==null) { drawn.remove(event.getScreen());return; }
  drawn.put(event.getScreen(),r);
  var g=event.getGuiGraphics();int x=r.x(),y=r.y();
  boolean hover=r.contains(event.getMouseX(),event.getMouseY());
  g.pose().pushPose();g.pose().translate(0,0,600);
  if(r.width()==26) {
   g.fill(x,y,x+26,y+24,0xFF000000);
   g.fill(x+1,y,x+25,y+23,0xFF555555);
   g.fill(x+2,y,x+24,y+22,0xFFFFFFFF);
   g.fill(x+3,y,x+23,y+21,0xFFC6C6C6);
   g.fill(x+2,y,x+24,y+4,0xFFC6C6C6);
   g.fill(x+24,y+1,x+25,y+22,0xFF555555);
   g.fill(x+2,y+22,x+24,y+23,0xFF555555);
   x+=4;y+=3;
  }
  g.fill(x,y,x+18,y+18,hover?0xFFB0B0B0:0xFF8B8B8B);
  g.fill(x,y,x+18,y+1,0xFF373737);g.fill(x,y,x+1,y+18,0xFF373737);
  g.fill(x+1,y+17,x+18,y+18,0xFFFFFFFF);g.fill(x+17,y+1,x+18,y+17,0xFFFFFFFF);
  g.fill(x+6,y+3,x+12,y+4,0xFF373737);
  g.fill(x+4,y+4,x+14,y+6,0xFF373737);
  g.fill(x+5,y+7,x+13,y+14,0xFF373737);
  g.fill(x+6,y+14,x+12,y+15,0xFF373737);
  g.fill(x+7,y+8,x+8,y+13,0xFFB0B0B0);g.fill(x+10,y+8,x+11,y+13,0xFFB0B0B0);
  g.pose().popPose();
 }
 @SubscribeEvent public static void click(ScreenEvent.MouseButtonPressed.Pre event) {
  var screen=event.getScreen();var current=placement(screen);
  boolean creative=screen instanceof CreativeModeInventoryScreen;
  if(current==null || !current.contains(event.getMouseX(),event.getMouseY()))return;
  if(!creative && !current.equals(drawn.get(screen)))return;
  event.setCanceled(true);pressed.put(screen,event.getButton());
  if(event.getButton()==0 && current.equals(drawn.get(screen)))LocalTrash.request();
 }
 @SubscribeEvent public static void release(ScreenEvent.MouseButtonReleased.Pre event) {
  var button=pressed.remove(event.getScreen());
  var current=placement(event.getScreen());
  if(button!=null || event.getScreen() instanceof CreativeModeInventoryScreen && current!=null && current.contains(event.getMouseX(),event.getMouseY()))event.setCanceled(true);
 }
}
