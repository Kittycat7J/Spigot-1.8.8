package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;
import net.minecraft.server.v1_8_R3.IpBanEntry;
import net.minecraft.server.v1_8_R3.JsonList;
import net.minecraft.server.v1_8_R3.JsonListEntry;

public class IpBanList extends JsonList<String, IpBanEntry> {
   public IpBanList(File p_i1078_1_) {
      super(p_i1078_1_);
   }

   protected JsonListEntry<String> a(JsonObject p_a_1_) {
      return new IpBanEntry(p_a_1_);
   }

   public boolean isBanned(SocketAddress p_isBanned_1_) {
      String s = this.c(p_isBanned_1_);
      return this.d(s);
   }

   public IpBanEntry get(SocketAddress p_get_1_) {
      String s = this.c(p_get_1_);
      return (IpBanEntry)this.get(s);
   }

   private String c(SocketAddress p_c_1_) {
      String s = p_c_1_.toString();
      if(s.contains("/")) {
         s = s.substring(s.indexOf(47) + 1);
      }

      if(s.contains(":")) {
         s = s.substring(0, s.indexOf(58));
      }

      return s;
   }
}
