package dev.singleplayertrash;
public class PolicyTest {
 public static void main(String[] args) {
  if(!Eligibility.allowed(true,true,false,true,true)) throw new AssertionError("local singleplayer must be allowed");
  for(int mask=0;mask<32;mask++) {
   boolean a=(mask&1)!=0,b=(mask&2)!=0,c=(mask&4)!=0,d=(mask&8)!=0,e=(mask&16)!=0;
   if(Eligibility.allowed(a,b,c,d,e)!=(a&&b&&!c&&d&&e)) throw new AssertionError("unsafe eligibility mask "+mask);
  }
  for(int mask=0;mask<32;mask++) {
   boolean a=(mask&1)!=0,b=(mask&2)!=0,c=(mask&4)!=0,d=(mask&8)!=0,e=(mask&16)!=0;
   if(Eligibility.clientMenu(a,b,c,d,e)!=(e&&(a||b&&c&&d)))throw new AssertionError("creative/menu/cursor mask "+mask);
  }
  System.out.println("PASS 32 local eligibility + 32 client menu/cursor combinations: creative permission and exact menu required, occupied cursor rejected; remote/LAN rejected");
 }
}
