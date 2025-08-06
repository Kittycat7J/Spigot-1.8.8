package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import net.minecraft.server.v1_8_R3.GameProfileBanEntry;
import net.minecraft.server.v1_8_R3.JsonList;
import net.minecraft.server.v1_8_R3.JsonListEntry;

public class GameProfileBanList extends JsonList<GameProfile, GameProfileBanEntry> {
   public GameProfileBanList(File p_i1087_1_) {
      super(p_i1087_1_);
   }

   protected JsonListEntry<GameProfile> a(JsonObject p_a_1_) {
      return new GameProfileBanEntry(p_a_1_);
   }

   public boolean isBanned(GameProfile p_isBanned_1_) {
      return this.d(p_isBanned_1_);
   }

   public String[] getEntries() {
      String[] astring = new String[this.e().size()];
      int i = 0;

      for(GameProfileBanEntry gameprofilebanentry : this.e().values()) {
         astring[i++] = ((GameProfile)gameprofilebanentry.getKey()).getName();
      }

      return astring;
   }

   protected String b(GameProfile p_b_1_) {
      return p_b_1_.getId().toString();
   }

   public GameProfile a(String p_a_1_) {
      for(GameProfileBanEntry gameprofilebanentry : this.e().values()) {
         if(p_a_1_.equalsIgnoreCase(((GameProfile)gameprofilebanentry.getKey()).getName())) {
            return (GameProfile)gameprofilebanentry.getKey();
         }
      }

      return null;
   }
}
