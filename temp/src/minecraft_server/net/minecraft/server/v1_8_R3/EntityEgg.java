package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityProjectile;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class EntityEgg extends EntityProjectile {
   public EntityEgg(World p_i421_1_) {
      super(p_i421_1_);
   }

   public EntityEgg(World p_i422_1_, EntityLiving p_i422_2_) {
      super(p_i422_1_, p_i422_2_);
   }

   public EntityEgg(World p_i423_1_, double p_i423_2_, double p_i423_4_, double p_i423_6_) {
      super(p_i423_1_, p_i423_2_, p_i423_4_, p_i423_6_);
   }

   protected void a(MovingObjectPosition p_a_1_) {
      if(p_a_1_.entity != null) {
         p_a_1_.entity.damageEntity(DamageSource.projectile(this, this.getShooter()), 0.0F);
      }

      boolean flag = !this.world.isClientSide && this.random.nextInt(8) == 0;
      int i = this.random.nextInt(32) == 0?4:1;
      if(!flag) {
         i = 0;
      }

      EntityType entitytype = EntityType.CHICKEN;
      Entity entity = this.getShooter();
      if(entity instanceof EntityPlayer) {
         Player player = entity == null?null:(Player)entity.getBukkitEntity();
         PlayerEggThrowEvent playereggthrowevent = new PlayerEggThrowEvent(player, (Egg)this.getBukkitEntity(), flag, (byte)i, entitytype);
         this.world.getServer().getPluginManager().callEvent(playereggthrowevent);
         flag = playereggthrowevent.isHatching();
         i = playereggthrowevent.getNumHatches();
         entitytype = playereggthrowevent.getHatchingType();
      }

      if(flag) {
         for(int j = 0; j < i; ++j) {
            Entity entity1 = this.world.getWorld().createEntity(new Location(this.world.getWorld(), this.locX, this.locY, this.locZ, this.yaw, 0.0F), entitytype.getEntityClass());
            if(entity1.getBukkitEntity() instanceof Ageable) {
               ((Ageable)entity1.getBukkitEntity()).setBaby();
            }

            this.world.getWorld().addEntity(entity1, SpawnReason.EGG);
         }
      }

      for(int k = 0; k < 8; ++k) {
         this.world.addParticle(EnumParticle.ITEM_CRACK, this.locX, this.locY, this.locZ, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, new int[]{Item.getId(Items.EGG)});
      }

      if(!this.world.isClientSide) {
         this.die();
      }

   }
}
