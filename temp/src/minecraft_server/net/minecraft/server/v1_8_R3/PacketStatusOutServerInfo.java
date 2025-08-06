package net.minecraft.server.v1_8_R3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import net.minecraft.server.v1_8_R3.ChatModifier;
import net.minecraft.server.v1_8_R3.ChatTypeAdapterFactory;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketStatusOutListener;
import net.minecraft.server.v1_8_R3.ServerPing;

public class PacketStatusOutServerInfo implements Packet<PacketStatusOutListener> {
   private static final Gson a = (new GsonBuilder()).registerTypeAdapter(ServerPing.ServerData.class, new ServerPing.ServerData.Serializer()).registerTypeAdapter(ServerPing.ServerPingPlayerSample.class, new ServerPing.ServerPingPlayerSample.Serializer()).registerTypeAdapter(ServerPing.class, new ServerPing.Serializer()).registerTypeHierarchyAdapter(IChatBaseComponent.class, new IChatBaseComponent.ChatSerializer()).registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifier.ChatModifierSerializer()).registerTypeAdapterFactory(new ChatTypeAdapterFactory()).create();
   private ServerPing b;

   public PacketStatusOutServerInfo() {
   }

   public PacketStatusOutServerInfo(ServerPing p_i1052_1_) {
      this.b = p_i1052_1_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.b = (ServerPing)a.fromJson(p_a_1_.c(32767), ServerPing.class);
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(a.toJson((Object)this.b));
   }

   public void a(PacketStatusOutListener p_a_1_) {
      p_a_1_.a(this);
   }
}
