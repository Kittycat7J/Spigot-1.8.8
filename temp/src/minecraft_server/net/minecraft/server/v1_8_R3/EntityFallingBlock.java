package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockAnvil;
import net.minecraft.server.v1_8_R3.BlockFalling;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IContainer;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class EntityFallingBlock extends Entity {
   private IBlockData block;
   public int ticksLived;
   public boolean dropItem = true;
   private boolean e;
   public boolean hurtEntities;
   private int fallHurtMax = 40;
   private float fallHurtAmount = 2.0F;
   public NBTTagCompound tileEntityData;

   public EntityFallingBlock(World p_i171_1_) {
      super(p_i171_1_);
   }

   public EntityFallingBlock(World p_i172_1_, double p_i172_2_, double p_i172_4_, double p_i172_6_, IBlockData p_i172_8_) {
      super(p_i172_1_);
      this.block = p_i172_8_;
      this.k = true;
      this.setSize(0.98F, 0.98F);
      this.setPosition(p_i172_2_, p_i172_4_, p_i172_6_);
      this.motX = 0.0D;
      this.motY = 0.0D;
      this.motZ = 0.0D;
      this.lastX = p_i172_2_;
      this.lastY = p_i172_4_;
      this.lastZ = p_i172_6_;
   }

   protected boolean s_() {
      return false;
   }

   protected void h() {
   }

   public boolean ad() {
      return !this.dead;
   }

   public void t_() {
      Block block = this.block.getBlock();
      if(block.getMaterial() == Material.AIR) {
         this.die();
      } else {
         this.lastX = this.locX;
         this.lastY = this.locY;
         this.lastZ = this.locZ;
         if(this.ticksLived++ == 0) {
            BlockPosition blockposition = new BlockPosition(this);
            if(this.world.getType(blockposition).getBlock() == block && !CraftEventFactory.callEntityChangeBlockEvent(this, blockposition.getX(), blockposition.getY(), blockposition.getZ(), Blocks.AIR, 0).isCancelled()) {
               this.world.setAir(blockposition);
               this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, blockposition);
            } else if(!this.world.isClientSide) {
               this.die();
               return;
            }
         }

         this.motY -= 0.03999999910593033D;
         this.move(this.motX, this.motY, this.motZ);
         this.motX *= 0.9800000190734863D;
         this.motY *= 0.9800000190734863D;
         this.motZ *= 0.9800000190734863D;
         if(!this.world.isClientSide) {
            BlockPosition blockposition1 = new BlockPosition(this);
            if(this.onGround) {
               this.motX *= 0.699999988079071D;
               this.motZ *= 0.699999988079071D;
               this.motY *= -0.5D;
               if(this.world.getType(blockposition1).getBlock() != Blocks.PISTON_EXTENSION) {
                  this.die();
                  if(!this.e) {
                     if(this.world.a(block, blockposition1, true, EnumDirection.UP, (Entity)null, (ItemStack)null) && !BlockFalling.canFall(this.world, blockposition1.down()) && blockposition1.getX() >= -30000000 && blockposition1.getZ() >= -30000000 && blockposition1.getX() < 30000000 && blockposition1.getZ() < 30000000 && blockposition1.getY() >= 0 && blockposition1.getY() < 256 && this.world.getType(blockposition1) != this.block) {
                        if(CraftEventFactory.callEntityChangeBlockEvent(this, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), this.block.getBlock(), this.block.getBlock().toLegacyData(this.block)).isCancelled()) {
                           return;
                        }

                        this.world.setTypeAndData(blockposition1, this.block, 3);
                        this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, blockposition1);
                        if(block instanceof BlockFalling) {
                           ((BlockFalling)block).a_(this.world, blockposition1);
                        }

                        if(this.tileEntityData != null && block instanceof IContainer) {
                           TileEntity tileentity = this.world.getTileEntity(blockposition1);
                           if(tileentity != null) {
                              NBTTagCompound nbttagcompound = new NBTTagCompound();
                              tileentity.b(nbttagcompound);

                              for(String s : this.tileEntityData.c()) {
                                 NBTBase nbtbase = this.tileEntityData.get(s);
                                 if(!s.equals("x") && !s.equals("y") && !s.equals("z")) {
                                    nbttagcompound.set(s, nbtbase.clone());
                                 }
                              }

                              tileentity.a(nbttagcompound);
                              tileentity.update();
                           }
                        }
                     } else if(this.dropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
                        this.a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
                     }
                  }
               }
            } else if(this.ticksLived > 100 && !this.world.isClientSide && (blockposition1.getY() < 1 || blockposition1.getY() > 256) || this.ticksLived > 600) {
               if(this.dropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
                  this.a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
               }

               this.die();
            }
         }
      }

   }

   public void e(float p_e_1_, float p_e_2_) {
      Block block = this.block.getBlock();
      if(this.hurtEntities) {
         int i = MathHelper.f(p_e_1_ - 1.0F);
         if(i > 0) {
            ArrayList arraylist = Lists.newArrayList(this.world.getEntities(this, this.getBoundingBox()));
            boolean flag = block == Blocks.ANVIL;
            DamageSource damagesource = flag?DamageSource.ANVIL:DamageSource.FALLING_BLOCK;

            for(Entity entity : arraylist) {
               CraftEventFactory.entityDamage = this;
               entity.damageEntity(damagesource, (float)Math.min(MathHelper.d((float)i * this.fallHurtAmount), this.fallHurtMax));
               CraftEventFactory.entityDamage = null;
            }

            if(flag && (double)this.random.nextFloat() < 0.05000000074505806D + (double)i * 0.05D) {
               int j = ((Integer)this.block.get(BlockAnvil.DAMAGE)).intValue();
               ++j;
               if(j > 2) {
                  this.e = true;
               } else {
                  this.block = this.block.set(BlockAnvil.DAMAGE, Integer.valueOf(j));
               }
            }
         }
      }

   }

   protected void b(NBTTagCompound p_b_1_) {
      Block block = this.block != null?this.block.getBlock():Blocks.AIR;
      MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(block);
      p_b_1_.setString("Block", minecraftkey == null?"":minecraftkey.toString());
      p_b_1_.setByte("Data", (byte)block.toLegacyData(this.block));
      p_b_1_.setByte("Time", (byte)this.ticksLived);
      p_b_1_.setBoolean("DropItem", this.dropItem);
      p_b_1_.setBoolean("HurtEntities", this.hurtEntities);
      p_b_1_.setFloat("FallHurtAmount", this.fallHurtAmount);
      p_b_1_.setInt("FallHurtMax", this.fallHurtMax);
      if(this.tileEntityData != null) {
         p_b_1_.set("TileEntityData", this.tileEntityData);
      }

   }

   protected void a(NBTTagCompound p_a_1_) {
      int i = p_a_1_.getByte("Data") & 255;
      if(p_a_1_.hasKeyOfType("Block", 8)) {
         this.block = Block.getByName(p_a_1_.getString("Block")).fromLegacyData(i);
      } else if(p_a_1_.hasKeyOfType("TileID", 99)) {
         this.block = Block.getById(p_a_1_.getInt("TileID")).fromLegacyData(i);
      } else {
         this.block = Block.getById(p_a_1_.getByte("Tile") & 255).fromLegacyData(i);
      }

      this.ticksLived = p_a_1_.getByte("Time") & 255;
      Block block = this.block.getBlock();
      if(p_a_1_.hasKeyOfType("HurtEntities", 99)) {
         this.hurtEntities = p_a_1_.getBoolean("HurtEntities");
         this.fallHurtAmount = p_a_1_.getFloat("FallHurtAmount");
         this.fallHurtMax = p_a_1_.getInt("FallHurtMax");
      } else if(block == Blocks.ANVIL) {
         this.hurtEntities = true;
      }

      if(p_a_1_.hasKeyOfType("DropItem", 99)) {
         this.dropItem = p_a_1_.getBoolean("DropItem");
      }

      if(p_a_1_.hasKeyOfType("TileEntityData", 10)) {
         this.tileEntityData = p_a_1_.getCompound("TileEntityData");
      }

      if(block == null || block.getMaterial() == Material.AIR) {
         this.block = Blocks.SAND.getBlockData();
      }

   }

   public void a(boolean p_a_1_) {
      this.hurtEntities = p_a_1_;
   }

   public void appendEntityCrashDetails(CrashReportSystemDetails p_appendEntityCrashDetails_1_) {
      super.appendEntityCrashDetails(p_appendEntityCrashDetails_1_);
      if(this.block != null) {
         Block block = this.block.getBlock();
         p_appendEntityCrashDetails_1_.a((String)"Immitating block ID", Integer.valueOf(Block.getId(block)));
         p_appendEntityCrashDetails_1_.a((String)"Immitating block data", Integer.valueOf(block.toLegacyData(this.block)));
      }

   }

   public IBlockData getBlock() {
      return this.block;
   }
}
