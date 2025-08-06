package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CancelledPacketHandleException;
import net.minecraft.server.v1_8_R3.IAsyncTaskHandler;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;

public class PlayerConnectionUtils {
   public static <T extends PacketListener> void ensureMainThread(final Packet<T> p_ensureMainThread_0_, final T p_ensureMainThread_1_, IAsyncTaskHandler p_ensureMainThread_2_) throws CancelledPacketHandleException {
      if(!p_ensureMainThread_2_.isMainThread()) {
         p_ensureMainThread_2_.postToMainThread(new Runnable() {
            public void run() {
               p_ensureMainThread_0_.a(p_ensureMainThread_1_);
            }
         });
         throw CancelledPacketHandleException.INSTANCE;
      }
   }
}
