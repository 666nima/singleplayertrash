package dev.singleplayertrash;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.widget.Bounds;
@EmiEntrypoint
public final class TrashEmiPlugin implements EmiPlugin {
 @Override public void register(EmiRegistry registry) {
  registry.addExclusionArea(InventoryScreen.class,(screen,consumer)->{
   var r=TrashClient.placement(screen);
   if(r!=null)consumer.accept(new Bounds(r.x(),r.y(),r.width(),r.height()));
  });
 }
}
