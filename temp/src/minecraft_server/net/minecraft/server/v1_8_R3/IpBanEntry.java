package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonObject;
import java.util.Date;
import net.minecraft.server.v1_8_R3.ExpirableListEntry;

public class IpBanEntry extends ExpirableListEntry<String> {
   public IpBanEntry(String p_i1079_1_) {
      this(p_i1079_1_, (Date)null, (String)null, (Date)null, (String)null);
   }

   public IpBanEntry(String p_i1080_1_, Date p_i1080_2_, String p_i1080_3_, Date p_i1080_4_, String p_i1080_5_) {
      super(p_i1080_1_, p_i1080_2_, p_i1080_3_, p_i1080_4_, p_i1080_5_);
   }

   public IpBanEntry(JsonObject p_i1081_1_) {
      super(b(p_i1081_1_), p_i1081_1_);
   }

   private static String b(JsonObject p_b_0_) {
      return p_b_0_.has("ip")?p_b_0_.get("ip").getAsString():null;
   }

   protected void a(JsonObject p_a_1_) {
      if(this.getKey() != null) {
         p_a_1_.addProperty("ip", (String)this.getKey());
         super.a(p_a_1_);
      }
   }
}
