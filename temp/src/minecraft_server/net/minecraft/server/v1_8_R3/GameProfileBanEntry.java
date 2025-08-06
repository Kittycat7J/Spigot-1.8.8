package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.Date;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.ExpirableListEntry;

public class GameProfileBanEntry extends ExpirableListEntry<GameProfile> {
   public GameProfileBanEntry(GameProfile p_i491_1_) {
      this(p_i491_1_, (Date)null, (String)null, (Date)null, (String)null);
   }

   public GameProfileBanEntry(GameProfile p_i492_1_, Date p_i492_2_, String p_i492_3_, Date p_i492_4_, String p_i492_5_) {
      super(p_i492_1_, p_i492_2_, p_i492_3_, p_i492_4_, p_i492_5_);
   }

   public GameProfileBanEntry(JsonObject p_i493_1_) {
      super(b(p_i493_1_), p_i493_1_);
   }

   protected void a(JsonObject p_a_1_) {
      if(this.getKey() != null) {
         p_a_1_.addProperty("uuid", ((GameProfile)this.getKey()).getId() == null?"":((GameProfile)this.getKey()).getId().toString());
         p_a_1_.addProperty("name", ((GameProfile)this.getKey()).getName());
         super.a(p_a_1_);
      }

   }

   private static GameProfile b(JsonObject p_b_0_) {
      UUID uuid = null;
      String s = null;
      if(p_b_0_.has("uuid")) {
         String s1 = p_b_0_.get("uuid").getAsString();

         try {
            uuid = UUID.fromString(s1);
         } catch (Throwable var4) {
            ;
         }
      }

      if(p_b_0_.has("name")) {
         s = p_b_0_.get("name").getAsString();
      }

      return uuid == null && s == null?null:new GameProfile(uuid, s);
   }
}
