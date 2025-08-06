package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayInEnchantItem;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_8_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import net.minecraft.server.v1_8_R3.PacketPlayInSpectate;
import net.minecraft.server.v1_8_R3.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_8_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayInTransaction;
import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;

public interface PacketListenerPlayIn extends PacketListener {
   void a(PacketPlayInArmAnimation var1);

   void a(PacketPlayInChat var1);

   void a(PacketPlayInTabComplete var1);

   void a(PacketPlayInClientCommand var1);

   void a(PacketPlayInSettings var1);

   void a(PacketPlayInTransaction var1);

   void a(PacketPlayInEnchantItem var1);

   void a(PacketPlayInWindowClick var1);

   void a(PacketPlayInCloseWindow var1);

   void a(PacketPlayInCustomPayload var1);

   void a(PacketPlayInUseEntity var1);

   void a(PacketPlayInKeepAlive var1);

   void a(PacketPlayInFlying var1);

   void a(PacketPlayInAbilities var1);

   void a(PacketPlayInBlockDig var1);

   void a(PacketPlayInEntityAction var1);

   void a(PacketPlayInSteerVehicle var1);

   void a(PacketPlayInHeldItemSlot var1);

   void a(PacketPlayInSetCreativeSlot var1);

   void a(PacketPlayInUpdateSign var1);

   void a(PacketPlayInBlockPlace var1);

   void a(PacketPlayInSpectate var1);

   void a(PacketPlayInResourcePackStatus var1);
}
