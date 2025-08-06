package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.JsonListEntry;

public class OpListEntry extends JsonListEntry<GameProfile> {
   private final int a;
   private final boolean b;

   public OpListEntry(GameProfile p_i1083_1_, int p_i1083_2_, boolean p_i1083_3_) {
      super(p_i1083_1_);
      this.a = p_i1083_2_;
      this.b = p_i1083_3_;
   }

   public OpListEntry(JsonObject p_i1084_1_) {
      super(b(p_i1084_1_), p_i1084_1_);
      this.a = p_i1084_1_.has("level")?p_i1084_1_.get("level").getAsInt():0;
      this.b = p_i1084_1_.has("bypassesPlayerLimit") && p_i1084_1_.get("bypassesPlayerLimit").getAsBoolean();
   }

   public int a() {
      return this.a;
   }

   public boolean b() {
      return this.b;
   }

   protected void a(JsonObject p_a_1_) {
      if(this.getKey() != null) {
         p_a_1_.addProperty("uuid", ((GameProfile)this.getKey()).getId() == null?"":((GameProfile)this.getKey()).getId().toString());
         p_a_1_.addProperty("name", ((GameProfile)this.getKey()).getName());
         super.a(p_a_1_);
         p_a_1_.addProperty("level", (Number)Integer.valueOf(this.a));
         p_a_1_.addProperty("bypassesPlayerLimit", Boolean.valueOf(this.b));
      }
   }

   private static GameProfile b(JsonObject p_b_0_) {
      if(p_b_0_.has("uuid") && p_b_0_.has("name")) {
         String s = p_b_0_.get("uuid").getAsString();

         UUID uuid;
         try {
            uuid = UUID.fromString(s);
         } catch (Throwable var4) {
            return null;
         }

         return new GameProfile(uuid, p_b_0_.get("name").getAsString());
      } else {
         return null;
      }
   }
}
