package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_8_R3.PacketLoginInStart;

public interface PacketLoginInListener extends PacketListener {
   void a(PacketLoginInStart var1);

   void a(PacketLoginInEncryptionBegin var1);
}
