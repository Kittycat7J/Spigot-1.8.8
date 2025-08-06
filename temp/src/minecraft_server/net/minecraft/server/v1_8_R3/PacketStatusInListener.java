package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketStatusInPing;
import net.minecraft.server.v1_8_R3.PacketStatusInStart;

public interface PacketStatusInListener extends PacketListener {
   void a(PacketStatusInPing var1);

   void a(PacketStatusInStart var1);
}
