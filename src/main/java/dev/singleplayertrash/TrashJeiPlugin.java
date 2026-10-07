package dev.singleplayertrash;
import java.util.List;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
@JeiPlugin
public final class TrashJeiPlugin implements IModPlugin {
 @Override public ResourceLocation getPluginUid() { return new ResourceLocation("singleplayertrash","entry"); }
 @Override public void registerGuiHandlers(IGuiHandlerRegistration registration) {
  registration.addGuiContainerHandler(InventoryScreen.class,new IGuiContainerHandler<InventoryScreen>() {
   @Override public List<Rect2i> getGuiExtraAreas(InventoryScreen screen) {
    var r=TrashClient.placement(screen);
    return r==null?List.of():List.of(new Rect2i(r.x(),r.y(),r.width(),r.height()));
   }
  });
 }
}
