package net.minecraft.server.v1_8_R3;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_8_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_8_R3.PacketLoginInStart;
import net.minecraft.server.v1_8_R3.PacketLoginOutDisconnect;
import net.minecraft.server.v1_8_R3.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_8_R3.PacketLoginOutSetCompression;
import net.minecraft.server.v1_8_R3.PacketLoginOutSuccess;
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
import net.minecraft.server.v1_8_R3.PacketStatusInPing;
import net.minecraft.server.v1_8_R3.PacketStatusInStart;
import net.minecraft.server.v1_8_R3.PacketStatusOutPong;
import net.minecraft.server.v1_8_R3.PacketStatusOutServerInfo;
import org.apache.logging.log4j.LogManager;

public enum EnumProtocol {
   HANDSHAKING(-1) {
      {
         this.a(EnumProtocolDirection.SERVERBOUND, PacketHandshakingInSetProtocol.class);
      }
   },
   PLAY(0) {
      {
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutKeepAlive.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutLogin.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutChat.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutUpdateTime.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutEntityEquipment.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutSpawnPosition.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutUpdateHealth.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutRespawn.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutPosition.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutHeldItemSlot.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutBed.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutAnimation.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutNamedEntitySpawn.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutCollect.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutSpawnEntity.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutSpawnEntityLiving.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutSpawnEntityPainting.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutSpawnEntityExperienceOrb.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutEntityVelocity.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutEntityDestroy.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutEntity.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutEntity.PacketPlayOutRelEntityMove.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutEntity.PacketPlayOutEntityLook.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutEntityTeleport.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutEntityHeadRotation.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutEntityStatus.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutAttachEntity.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutEntityMetadata.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutEntityEffect.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutRemoveEntityEffect.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutExperience.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutUpdateAttributes.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutMapChunk.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutMultiBlockChange.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutBlockChange.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutBlockAction.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutBlockBreakAnimation.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutMapChunkBulk.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutExplosion.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutWorldEvent.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutNamedSoundEffect.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutWorldParticles.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutGameStateChange.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutSpawnEntityWeather.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutOpenWindow.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutCloseWindow.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutSetSlot.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutWindowItems.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutWindowData.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutTransaction.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutUpdateSign.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutMap.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutTileEntityData.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutOpenSignEditor.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutStatistic.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutPlayerInfo.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutAbilities.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutTabComplete.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutScoreboardObjective.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutScoreboardScore.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutScoreboardDisplayObjective.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutScoreboardTeam.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutCustomPayload.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutKickDisconnect.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutServerDifficulty.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutCombatEvent.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutCamera.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutWorldBorder.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutTitle.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutSetCompression.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutPlayerListHeaderFooter.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutResourcePackSend.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketPlayOutUpdateEntityNBT.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInKeepAlive.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInChat.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInUseEntity.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInFlying.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInFlying.PacketPlayInPosition.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInFlying.PacketPlayInLook.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInFlying.PacketPlayInPositionLook.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInBlockDig.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInBlockPlace.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInHeldItemSlot.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInArmAnimation.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInEntityAction.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInSteerVehicle.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInCloseWindow.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInWindowClick.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInTransaction.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInSetCreativeSlot.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInEnchantItem.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInUpdateSign.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInAbilities.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInTabComplete.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInSettings.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInClientCommand.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInCustomPayload.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInSpectate.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketPlayInResourcePackStatus.class);
      }
   },
   STATUS(1) {
      {
         this.a(EnumProtocolDirection.SERVERBOUND, PacketStatusInStart.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketStatusOutServerInfo.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketStatusInPing.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketStatusOutPong.class);
      }
   },
   LOGIN(2) {
      {
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketLoginOutDisconnect.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketLoginOutEncryptionBegin.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketLoginOutSuccess.class);
         this.a(EnumProtocolDirection.CLIENTBOUND, PacketLoginOutSetCompression.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketLoginInStart.class);
         this.a(EnumProtocolDirection.SERVERBOUND, PacketLoginInEncryptionBegin.class);
      }
   };

   private static int e = -1;
   private static int f = 2;
   private static final EnumProtocol[] g = new EnumProtocol[f - e + 1];
   private static final Map<Class<? extends Packet>, EnumProtocol> h = Maps.<Class<? extends Packet>, EnumProtocol>newHashMap();
   private final int i;
   private final Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet>>> j;

   private EnumProtocol(int p_i931_3_) {
      this.j = Maps.newEnumMap(EnumProtocolDirection.class);
      this.i = p_i931_3_;
   }

   protected EnumProtocol a(EnumProtocolDirection p_a_1_, Class<? extends Packet> p_a_2_) {
      Object object = (BiMap)this.j.get(p_a_1_);
      if(object == null) {
         object = HashBiMap.create();
         this.j.put(p_a_1_, object);
      }

      if(((BiMap)object).containsValue(p_a_2_)) {
         String s = p_a_1_ + " packet " + p_a_2_ + " is already known to ID " + ((BiMap)object).inverse().get(p_a_2_);
         LogManager.getLogger().fatal(s);
         throw new IllegalArgumentException(s);
      } else {
         ((BiMap)object).put(Integer.valueOf(((BiMap)object).size()), p_a_2_);
         return this;
      }
   }

   public Integer a(EnumProtocolDirection p_a_1_, Packet p_a_2_) {
      return (Integer)((BiMap)this.j.get(p_a_1_)).inverse().get(p_a_2_.getClass());
   }

   public Packet a(EnumProtocolDirection p_a_1_, int p_a_2_) throws IllegalAccessException, InstantiationException {
      Class oclass = (Class)((BiMap)this.j.get(p_a_1_)).get(Integer.valueOf(p_a_2_));
      return oclass == null?null:(Packet)oclass.newInstance();
   }

   public int a() {
      return this.i;
   }

   public static EnumProtocol a(int p_a_0_) {
      return p_a_0_ >= e && p_a_0_ <= f?g[p_a_0_ - e]:null;
   }

   public static EnumProtocol a(Packet p_a_0_) {
      return (EnumProtocol)h.get(p_a_0_.getClass());
   }

   static {
      for(EnumProtocol enumprotocol : values()) {
         int ix = enumprotocol.a();
         if(ix < e || ix > f) {
            throw new Error("Invalid protocol ID " + Integer.toString(ix));
         }

         g[ix - e] = enumprotocol;

         for(EnumProtocolDirection enumprotocoldirection : enumprotocol.j.keySet()) {
            for(Class oclass : ((BiMap)enumprotocol.j.get(enumprotocoldirection)).values()) {
               if(h.containsKey(oclass) && h.get(oclass) != enumprotocol) {
                  throw new Error("Packet " + oclass + " is already assigned to protocol " + h.get(oclass) + " - can\'t reassign to " + enumprotocol);
               }

               try {
                  oclass.newInstance();
               } catch (Throwable var10) {
                  throw new Error("Packet " + oclass + " fails instantiation checks! " + oclass);
               }

               h.put(oclass, enumprotocol);
            }
         }
      }

   }
}
