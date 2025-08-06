package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketStatusOutPong;
import net.minecraft.server.v1_8_R3.PacketStatusOutServerInfo;

public interface PacketStatusOutListener extends PacketListener {
   void a(PacketStatusOutServerInfo var1);

   void a(PacketStatusOutPong var1);
}
