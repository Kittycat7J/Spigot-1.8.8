package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockChest;
import net.minecraft.server.v1_8_R3.BlockCommand;
import net.minecraft.server.v1_8_R3.BlockDoor;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ITileInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.ItemSword;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.WorldSettings;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractManager {
   public World world;
   public EntityPlayer player;
   private WorldSettings.EnumGamemode gamemode = WorldSettings.EnumGamemode.NOT_SET;
   private boolean d;
   private int lastDigTick;
   private BlockPosition f = BlockPosition.ZERO;
   private int currentTick;
   private boolean h;
   private BlockPosition i = BlockPosition.ZERO;
   private int j;
   private int k = -1;
   public boolean interactResult = false;
   public boolean firedInteract = false;

   public PlayerInteractManager(World p_i274_1_) {
      this.world = p_i274_1_;
   }

   public void setGameMode(WorldSettings.EnumGamemode p_setGameMode_1_) {
      this.gamemode = p_setGameMode_1_;
      p_setGameMode_1_.a(this.player.abilities);
      this.player.updateAbilities();
      this.player.server.getPlayerList().sendAll(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_GAME_MODE, new EntityPlayer[]{this.player}), (EntityHuman)this.player);
   }

   public WorldSettings.EnumGamemode getGameMode() {
      return this.gamemode;
   }

   public boolean c() {
      return this.gamemode.e();
   }

   public boolean isCreative() {
      return this.gamemode.d();
   }

   public void b(WorldSettings.EnumGamemode p_b_1_) {
      if(this.gamemode == WorldSettings.EnumGamemode.NOT_SET) {
         this.gamemode = p_b_1_;
      }

      this.setGameMode(this.gamemode);
   }

   public void a() {
      this.currentTick = MinecraftServer.currentTick;
      if(this.h) {
         int i = this.currentTick - this.j;
         Block block = this.world.getType(this.i).getBlock();
         if(block.getMaterial() == Material.AIR) {
            this.h = false;
         } else {
            float f = block.getDamage(this.player, this.player.world, this.i) * (float)(i + 1);
            int j = (int)(f * 10.0F);
            if(j != this.k) {
               this.world.c(this.player.getId(), this.i, j);
               this.k = j;
            }

            if(f >= 1.0F) {
               this.h = false;
               this.breakBlock(this.i);
            }
         }
      } else if(this.d) {
         Block block1 = this.world.getType(this.f).getBlock();
         if(block1.getMaterial() == Material.AIR) {
            this.world.c(this.player.getId(), this.f, -1);
            this.k = -1;
            this.d = false;
         } else {
            int k = this.currentTick - this.lastDigTick;
            float f1 = block1.getDamage(this.player, this.player.world, this.i) * (float)(k + 1);
            int l = (int)(f1 * 10.0F);
            if(l != this.k) {
               this.world.c(this.player.getId(), this.f, l);
               this.k = l;
            }
         }
      }

   }

   public void a(BlockPosition p_a_1_, EnumDirection p_a_2_) {
      PlayerInteractEvent playerinteractevent = CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_BLOCK, p_a_1_, p_a_2_, this.player.inventory.getItemInHand());
      if(playerinteractevent.isCancelled()) {
         this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, p_a_1_));
         TileEntity tileentity = this.world.getTileEntity(p_a_1_);
         if(tileentity != null) {
            this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
         }

      } else {
         if(this.isCreative()) {
            if(!this.world.douseFire((EntityHuman)null, p_a_1_, p_a_2_)) {
               this.breakBlock(p_a_1_);
            }
         } else {
            Block block = this.world.getType(p_a_1_).getBlock();
            if(this.gamemode.c()) {
               if(this.gamemode == WorldSettings.EnumGamemode.SPECTATOR) {
                  return;
               }

               if(!this.player.cn()) {
                  ItemStack itemstack = this.player.bZ();
                  if(itemstack == null) {
                     return;
                  }

                  if(!itemstack.c(block)) {
                     return;
                  }
               }
            }

            this.lastDigTick = this.currentTick;
            float f = 1.0F;
            if(playerinteractevent.useInteractedBlock() == Result.DENY) {
               IBlockData iblockdata = this.world.getType(p_a_1_);
               if(block == Blocks.WOODEN_DOOR) {
                  boolean flag = iblockdata.get(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER;
                  this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, p_a_1_));
                  this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, flag?p_a_1_.up():p_a_1_.down()));
               } else if(block == Blocks.TRAPDOOR) {
                  this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, p_a_1_));
               }
            } else if(block.getMaterial() != Material.AIR) {
               block.attack(this.world, p_a_1_, this.player);
               f = block.getDamage(this.player, this.player.world, p_a_1_);
               this.world.douseFire((EntityHuman)null, p_a_1_, p_a_2_);
            }

            if(playerinteractevent.useItemInHand() == Result.DENY) {
               if(f > 1.0F) {
                  this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, p_a_1_));
               }

               return;
            }

            BlockDamageEvent blockdamageevent = CraftEventFactory.callBlockDamageEvent(this.player, p_a_1_.getX(), p_a_1_.getY(), p_a_1_.getZ(), this.player.inventory.getItemInHand(), f >= 1.0F);
            if(blockdamageevent.isCancelled()) {
               this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, p_a_1_));
               return;
            }

            if(blockdamageevent.getInstaBreak()) {
               f = 2.0F;
            }

            if(block.getMaterial() != Material.AIR && f >= 1.0F) {
               this.breakBlock(p_a_1_);
            } else {
               this.d = true;
               this.f = p_a_1_;
               int i = (int)(f * 10.0F);
               this.world.c(this.player.getId(), p_a_1_, i);
               this.k = i;
            }
         }

         this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, p_a_1_);
      }
   }

   public void a(BlockPosition p_a_1_) {
      if(p_a_1_.equals(this.f)) {
         this.currentTick = MinecraftServer.currentTick;
         int i = this.currentTick - this.lastDigTick;
         Block block = this.world.getType(p_a_1_).getBlock();
         if(block.getMaterial() != Material.AIR) {
            float f = block.getDamage(this.player, this.player.world, p_a_1_) * (float)(i + 1);
            if(f >= 0.7F) {
               this.d = false;
               this.world.c(this.player.getId(), p_a_1_, -1);
               this.breakBlock(p_a_1_);
            } else if(!this.h) {
               this.d = false;
               this.h = true;
               this.i = p_a_1_;
               this.j = this.lastDigTick;
            }
         }
      } else {
         this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, p_a_1_));
      }

   }

   public void e() {
      this.d = false;
      this.world.c(this.player.getId(), this.f, -1);
   }

   private boolean c(BlockPosition p_c_1_) {
      IBlockData iblockdata = this.world.getType(p_c_1_);
      iblockdata.getBlock().a((World)this.world, p_c_1_, (IBlockData)iblockdata, (EntityHuman)this.player);
      boolean flag = this.world.setAir(p_c_1_);
      if(flag) {
         iblockdata.getBlock().postBreak(this.world, p_c_1_, iblockdata);
      }

      return flag;
   }

   public boolean breakBlock(BlockPosition p_breakBlock_1_) {
      BlockBreakEvent blockbreakevent = null;
      if(this.player instanceof EntityPlayer) {
         org.bukkit.block.Block block = this.world.getWorld().getBlockAt(p_breakBlock_1_.getX(), p_breakBlock_1_.getY(), p_breakBlock_1_.getZ());
         boolean flag = this.gamemode.d() && this.player.bA() != null && this.player.bA().getItem() instanceof ItemSword;
         if(this.world.getTileEntity(p_breakBlock_1_) == null && !flag) {
            PacketPlayOutBlockChange packetplayoutblockchange = new PacketPlayOutBlockChange(this.world, p_breakBlock_1_);
            packetplayoutblockchange.block = Blocks.AIR.getBlockData();
            this.player.playerConnection.sendPacket(packetplayoutblockchange);
         }

         blockbreakevent = new BlockBreakEvent(block, this.player.getBukkitEntity());
         blockbreakevent.setCancelled(flag);
         IBlockData iblockdata1 = this.world.getType(p_breakBlock_1_);
         Block block1 = iblockdata1.getBlock();
         if(block1 != null && !blockbreakevent.isCancelled() && !this.isCreative() && this.player.b((Block)block1) && (!block1.I() || !EnchantmentManager.hasSilkTouchEnchantment(this.player))) {
            block.getData();
            int i = EnchantmentManager.getBonusBlockLootEnchantmentLevel(this.player);
            blockbreakevent.setExpToDrop(block1.getExpDrop(this.world, iblockdata1, i));
         }

         this.world.getServer().getPluginManager().callEvent(blockbreakevent);
         if(blockbreakevent.isCancelled()) {
            if(flag) {
               return false;
            }

            this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, p_breakBlock_1_));
            TileEntity tileentity1 = this.world.getTileEntity(p_breakBlock_1_);
            if(tileentity1 != null) {
               this.player.playerConnection.sendPacket(tileentity1.getUpdatePacket());
            }

            return false;
         }
      }

      IBlockData iblockdata = this.world.getType(p_breakBlock_1_);
      if(iblockdata.getBlock() == Blocks.AIR) {
         return false;
      } else {
         TileEntity tileentity = this.world.getTileEntity(p_breakBlock_1_);
         if(iblockdata.getBlock() == Blocks.SKULL && !this.isCreative()) {
            iblockdata.getBlock().dropNaturally(this.world, p_breakBlock_1_, iblockdata, 1.0F, 0);
            return this.c(p_breakBlock_1_);
         } else {
            if(this.gamemode.c()) {
               if(this.gamemode == WorldSettings.EnumGamemode.SPECTATOR) {
                  return false;
               }

               if(!this.player.cn()) {
                  ItemStack itemstack = this.player.bZ();
                  if(itemstack == null) {
                     return false;
                  }

                  if(!itemstack.c(iblockdata.getBlock())) {
                     return false;
                  }
               }
            }

            this.world.a(this.player, 2001, p_breakBlock_1_, Block.getCombinedId(iblockdata));
            boolean flag1 = this.c(p_breakBlock_1_);
            if(this.isCreative()) {
               this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, p_breakBlock_1_));
            } else {
               ItemStack itemstack1 = this.player.bZ();
               boolean flag2 = this.player.b((Block)iblockdata.getBlock());
               if(itemstack1 != null) {
                  itemstack1.a(this.world, iblockdata.getBlock(), p_breakBlock_1_, this.player);
                  if(itemstack1.count == 0) {
                     this.player.ca();
                  }
               }

               if(flag1 && flag2) {
                  iblockdata.getBlock().a(this.world, this.player, p_breakBlock_1_, iblockdata, tileentity);
               }
            }

            if(flag1 && blockbreakevent != null) {
               iblockdata.getBlock().dropExperience(this.world, p_breakBlock_1_, blockbreakevent.getExpToDrop());
            }

            return flag1;
         }
      }
   }

   public boolean useItem(EntityHuman p_useItem_1_, World p_useItem_2_, ItemStack p_useItem_3_) {
      if(this.gamemode == WorldSettings.EnumGamemode.SPECTATOR) {
         return false;
      } else {
         int i = p_useItem_3_.count;
         int j = p_useItem_3_.getData();
         ItemStack itemstack = p_useItem_3_.a(p_useItem_2_, p_useItem_1_);
         if(itemstack != p_useItem_3_ || itemstack != null && (itemstack.count != i || itemstack.l() > 0 || itemstack.getData() != j)) {
            p_useItem_1_.inventory.items[p_useItem_1_.inventory.itemInHandIndex] = itemstack;
            if(this.isCreative()) {
               itemstack.count = i;
               if(itemstack.e()) {
                  itemstack.setData(j);
               }
            }

            if(itemstack.count == 0) {
               p_useItem_1_.inventory.items[p_useItem_1_.inventory.itemInHandIndex] = null;
            }

            if(!p_useItem_1_.bS()) {
               ((EntityPlayer)p_useItem_1_).updateInventory(p_useItem_1_.defaultContainer);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public boolean interact(EntityHuman p_interact_1_, World p_interact_2_, ItemStack p_interact_3_, BlockPosition p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      IBlockData iblockdata = p_interact_2_.getType(p_interact_4_);
      boolean flag = false;
      if(iblockdata.getBlock() != Blocks.AIR) {
         boolean flag1 = false;
         if(this.gamemode == WorldSettings.EnumGamemode.SPECTATOR) {
            TileEntity tileentity = p_interact_2_.getTileEntity(p_interact_4_);
            flag1 = !(tileentity instanceof ITileInventory) && !(tileentity instanceof IInventory);
         }

         if(!p_interact_1_.getBukkitEntity().isOp() && p_interact_3_ != null && Block.asBlock(p_interact_3_.getItem()) instanceof BlockCommand) {
            flag1 = true;
         }

         PlayerInteractEvent playerinteractevent = CraftEventFactory.callPlayerInteractEvent(p_interact_1_, Action.RIGHT_CLICK_BLOCK, p_interact_4_, p_interact_5_, p_interact_3_, flag1);
         this.firedInteract = true;
         this.interactResult = playerinteractevent.useItemInHand() == Result.DENY;
         if(playerinteractevent.useInteractedBlock() == Result.DENY) {
            if(iblockdata.getBlock() instanceof BlockDoor) {
               boolean flag2 = iblockdata.get(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER;
               ((EntityPlayer)p_interact_1_).playerConnection.sendPacket(new PacketPlayOutBlockChange(p_interact_2_, flag2?p_interact_4_.up():p_interact_4_.down()));
            }

            flag = playerinteractevent.useItemInHand() != Result.ALLOW;
         } else {
            if(this.gamemode == WorldSettings.EnumGamemode.SPECTATOR) {
               TileEntity tileentity1 = p_interact_2_.getTileEntity(p_interact_4_);
               if(tileentity1 instanceof ITileInventory) {
                  Block block = p_interact_2_.getType(p_interact_4_).getBlock();
                  ITileInventory itileinventory = (ITileInventory)tileentity1;
                  if(itileinventory instanceof TileEntityChest && block instanceof BlockChest) {
                     itileinventory = ((BlockChest)block).f(p_interact_2_, p_interact_4_);
                  }

                  if(itileinventory != null) {
                     p_interact_1_.openContainer(itileinventory);
                     return true;
                  }
               } else if(tileentity1 instanceof IInventory) {
                  p_interact_1_.openContainer((IInventory)tileentity1);
                  return true;
               }

               return false;
            }

            if(!p_interact_1_.isSneaking() || p_interact_3_ == null) {
               flag = iblockdata.getBlock().interact(p_interact_2_, p_interact_4_, iblockdata, p_interact_1_, p_interact_5_, p_interact_6_, p_interact_7_, p_interact_8_);
            }
         }

         if(p_interact_3_ != null && !flag && !this.interactResult) {
            int i = p_interact_3_.getData();
            int j = p_interact_3_.count;
            flag = p_interact_3_.placeItem(p_interact_1_, p_interact_2_, p_interact_4_, p_interact_5_, p_interact_6_, p_interact_7_, p_interact_8_);
            if(this.isCreative()) {
               p_interact_3_.setData(i);
               p_interact_3_.count = j;
            }
         }
      }

      return flag;
   }

   public void a(WorldServer p_a_1_) {
      this.world = p_a_1_;
   }
}
