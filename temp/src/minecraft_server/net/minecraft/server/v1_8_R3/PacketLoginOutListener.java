package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketLoginOutDisconnect;
import net.minecraft.server.v1_8_R3.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_8_R3.PacketLoginOutSetCompression;
import net.minecraft.server.v1_8_R3.PacketLoginOutSuccess;

public interface PacketLoginOutListener extends PacketListener {
   void a(PacketLoginOutEncryptionBegin var1);

   void a(PacketLoginOutSuccess var1);

   void a(PacketLoginOutDisconnect var1);

   void a(PacketLoginOutSetCompression var1);
}
