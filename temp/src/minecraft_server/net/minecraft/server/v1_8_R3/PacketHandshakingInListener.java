package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_8_R3.PacketListener;

public interface PacketHandshakingInListener extends PacketListener {
   void a(PacketHandshakingInSetProtocol var1);
}
