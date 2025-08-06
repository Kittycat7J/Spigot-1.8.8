package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import net.minecraft.server.v1_8_R3.JsonList;
import net.minecraft.server.v1_8_R3.JsonListEntry;
import net.minecraft.server.v1_8_R3.WhiteListEntry;

public class WhiteList extends JsonList<GameProfile, WhiteListEntry> {
   public WhiteList(File p_i1088_1_) {
      super(p_i1088_1_);
   }

   protected JsonListEntry<GameProfile> a(JsonObject p_a_1_) {
      return new WhiteListEntry(p_a_1_);
   }

   public boolean isWhitelisted(GameProfile p_isWhitelisted_1_) {
      return this.d(p_isWhitelisted_1_);
   }

   public String[] getEntries() {
      String[] astring = new String[this.e().size()];
      int i = 0;

      for(WhiteListEntry whitelistentry : this.e().values()) {
         astring[i++] = ((GameProfile)whitelistentry.getKey()).getName();
      }

      return astring;
   }

   protected String b(GameProfile p_b_1_) {
      return p_b_1_.getId().toString();
   }

   public GameProfile a(String p_a_1_) {
      for(WhiteListEntry whitelistentry : this.e().values()) {
         if(p_a_1_.equalsIgnoreCase(((GameProfile)whitelistentry.getKey()).getName())) {
            return (GameProfile)whitelistentry.getKey();
         }
      }

      return null;
   }
}
