package dev.singleplayertrash;
public class Eligibility {
 public static boolean clientMenu(boolean inventoryMenu,boolean creativeScreen,boolean creativePermission,boolean sameCreativeMenu,boolean emptyCursor) {
  return emptyCursor && (inventoryMenu || creativeScreen && creativePermission && sameCreativeMenu);
 }
 public static boolean allowed(boolean localServer, boolean memoryConnection, boolean published, boolean alive, boolean inventoryMenu) { return localServer && memoryConnection && !published && alive && inventoryMenu; }
}
