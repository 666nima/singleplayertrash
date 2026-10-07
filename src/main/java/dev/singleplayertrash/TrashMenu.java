package dev.singleplayertrash;
public class TrashMenu extends net.minecraft.world.inventory.ChestMenu {
 public TrashMenu(int id,net.minecraft.world.entity.player.Inventory inventory) {
  super(net.minecraft.world.inventory.MenuType.GENERIC_9x4,id,inventory,new net.minecraft.world.SimpleContainer(36),4);
 }
 @Override public void removed(net.minecraft.world.entity.player.Player player) {
  // Let vanilla return the carried cursor stack safely; never clear it.
  super.removed(player);
  if (!player.level().isClientSide) getContainer().clearContent();
 }
}
