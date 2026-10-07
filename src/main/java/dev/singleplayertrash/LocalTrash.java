package dev.singleplayertrash;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.network.chat.Component;
/** No custom network channel: only the client's actual in-process server is targeted. */
public class LocalTrash {
 public static void request() {
  Minecraft mc=Minecraft.getInstance();
  var server=mc.getSingleplayerServer();
  if(!available(mc) || server==null) return;
  var uuid=mc.player.getUUID();
  server.execute(()->{
   // Re-check on the server thread: the client may have disconnected meanwhile.
   if(mc.getSingleplayerServer()!=server || !available(mc)) return;
   ServerPlayer p=server.getPlayerList().getPlayer(uuid);
   if(p==null || !server.isSingleplayer() || server.isPublished() ||
      !p.connection.connection.isMemoryConnection() || !p.isAlive() || p.isSpectator() ||
      p.containerMenu!=p.inventoryMenu || !p.containerMenu.getCarried().isEmpty()) return;
   p.openMenu(new SimpleMenuProvider((id,inv,player)->new TrashMenu(id,inv),Component.translatable("container.singleplayertrash")));
  });
 }
 public static boolean available(Minecraft mc) { return available(mc,true); }
 public static boolean eligible(Minecraft mc) { return available(mc,false); }
 private static boolean available(Minecraft mc,boolean emptyCursor) {
  var server=mc.getSingleplayerServer(); var p=mc.player; var connection=mc.getConnection();
  return Eligibility.allowed(server!=null && mc.isLocalServer(),
   connection!=null && connection.getConnection().isMemoryConnection(),
   server!=null && server.isPublished(), p!=null && p.isAlive() && !p.isSpectator(),
   p!=null && Eligibility.clientMenu(p.containerMenu==p.inventoryMenu,
    mc.screen instanceof net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen,
    p.getAbilities().instabuild && mc.gameMode!=null && mc.gameMode.hasInfiniteItems(),
    mc.screen instanceof net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen creative && p.containerMenu==creative.getMenu(),
    !emptyCursor || p.containerMenu.getCarried().isEmpty()));
 }
}
