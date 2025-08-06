package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketPlayOutAbilities;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutBed;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayOutCollect;
import net.minecraft.server.v1_8_R3.PacketPlayOutCombatEvent;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_8_R3.PacketPlayOutExperience;
import net.minecraft.server.v1_8_R3.PacketPlayOutExplosion;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_8_R3.PacketPlayOutLogin;
import net.minecraft.server.v1_8_R3.PacketPlayOutMap;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunkBulk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutRemoveEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutResourcePackSend;
import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.PacketPlayOutServerDifficulty;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetCompression;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityWeather;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutStatistic;
import net.minecraft.server.v1_8_R3.PacketPlayOutTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTransaction;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateEntityNBT;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateSign;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateTime;
import net.minecraft.server.v1_8_R3.PacketPlayOutWindowData;
import net.minecraft.server.v1_8_R3.PacketPlayOutWindowItems;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public interface PacketListenerPlayOut extends PacketListener {
   void a(PacketPlayOutSpawnEntity var1);

   void a(PacketPlayOutSpawnEntityExperienceOrb var1);

   void a(PacketPlayOutSpawnEntityWeather var1);

   void a(PacketPlayOutSpawnEntityLiving var1);

   void a(PacketPlayOutScoreboardObjective var1);

   void a(PacketPlayOutSpawnEntityPainting var1);

   void a(PacketPlayOutNamedEntitySpawn var1);

   void a(PacketPlayOutAnimation var1);

   void a(PacketPlayOutStatistic var1);

   void a(PacketPlayOutBlockBreakAnimation var1);

   void a(PacketPlayOutOpenSignEditor var1);

   void a(PacketPlayOutTileEntityData var1);

   void a(PacketPlayOutBlockAction var1);

   void a(PacketPlayOutBlockChange var1);

   void a(PacketPlayOutChat var1);

   void a(PacketPlayOutTabComplete var1);

   void a(PacketPlayOutMultiBlockChange var1);

   void a(PacketPlayOutMap var1);

   void a(PacketPlayOutTransaction var1);

   void a(PacketPlayOutCloseWindow var1);

   void a(PacketPlayOutWindowItems var1);

   void a(PacketPlayOutOpenWindow var1);

   void a(PacketPlayOutWindowData var1);

   void a(PacketPlayOutSetSlot var1);

   void a(PacketPlayOutCustomPayload var1);

   void a(PacketPlayOutKickDisconnect var1);

   void a(PacketPlayOutBed var1);

   void a(PacketPlayOutEntityStatus var1);

   void a(PacketPlayOutAttachEntity var1);

   void a(PacketPlayOutExplosion var1);

   void a(PacketPlayOutGameStateChange var1);

   void a(PacketPlayOutKeepAlive var1);

   void a(PacketPlayOutMapChunk var1);

   void a(PacketPlayOutMapChunkBulk var1);

   void a(PacketPlayOutWorldEvent var1);

   void a(PacketPlayOutLogin var1);

   void a(PacketPlayOutEntity var1);

   void a(PacketPlayOutPosition var1);

   void a(PacketPlayOutWorldParticles var1);

   void a(PacketPlayOutAbilities var1);

   void a(PacketPlayOutPlayerInfo var1);

   void a(PacketPlayOutEntityDestroy var1);

   void a(PacketPlayOutRemoveEntityEffect var1);

   void a(PacketPlayOutRespawn var1);

   void a(PacketPlayOutEntityHeadRotation var1);

   void a(PacketPlayOutHeldItemSlot var1);

   void a(PacketPlayOutScoreboardDisplayObjective var1);

   void a(PacketPlayOutEntityMetadata var1);

   void a(PacketPlayOutEntityVelocity var1);

   void a(PacketPlayOutEntityEquipment var1);

   void a(PacketPlayOutExperience var1);

   void a(PacketPlayOutUpdateHealth var1);

   void a(PacketPlayOutScoreboardTeam var1);

   void a(PacketPlayOutScoreboardScore var1);

   void a(PacketPlayOutSpawnPosition var1);

   void a(PacketPlayOutUpdateTime var1);

   void a(PacketPlayOutUpdateSign var1);

   void a(PacketPlayOutNamedSoundEffect var1);

   void a(PacketPlayOutCollect var1);

   void a(PacketPlayOutEntityTeleport var1);

   void a(PacketPlayOutUpdateAttributes var1);

   void a(PacketPlayOutEntityEffect var1);

   void a(PacketPlayOutCombatEvent var1);

   void a(PacketPlayOutServerDifficulty var1);

   void a(PacketPlayOutCamera var1);

   void a(PacketPlayOutWorldBorder var1);

   void a(PacketPlayOutTitle var1);

   void a(PacketPlayOutSetCompression var1);

   void a(PacketPlayOutPlayerListHeaderFooter var1);

   void a(PacketPlayOutResourcePackSend var1);

   void a(PacketPlayOutUpdateEntityNBT var1);
}
