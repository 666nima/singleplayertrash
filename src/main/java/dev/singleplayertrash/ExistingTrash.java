package dev.singleplayertrash;
import java.lang.reflect.Method;
import net.minecraft.client.renderer.Rect2i;
import net.minecraftforge.fml.ModList;
final class ExistingTrash {
 private static boolean resolved,failed;
 private static Method component,visible,rectangle;
 static ButtonPlacement.Rect area() {
  if(!ModList.get().isLoaded("trashslot"))return null;
  try {
   if(!resolved) {
    resolved=true;
    component=Class.forName("net.blay09.mods.trashslot.client.TrashSlotGuiHandler").getMethod("getTrashSlotComponent");
    var type=Class.forName("net.blay09.mods.trashslot.client.gui.TrashSlotComponent");
    visible=type.getMethod("isVisible");rectangle=type.getMethod("getRectangle");
   }
   if(failed)return blocked();
   var value=component.invoke(null);
   if(value==null || !((Boolean)visible.invoke(value)))return null;
   var r=(Rect2i)rectangle.invoke(value);
   return new ButtonPlacement.Rect(r.getX(),r.getY(),r.getWidth(),r.getHeight());
  } catch(ReflectiveOperationException | LinkageError | ClassCastException e) {
   failed=true;return blocked();
  }
 }
 private static ButtonPlacement.Rect blocked() { return new ButtonPlacement.Rect(0,0,Integer.MAX_VALUE/2,Integer.MAX_VALUE/2); }
}
