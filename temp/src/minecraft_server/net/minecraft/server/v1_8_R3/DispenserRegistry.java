package net.minecraft.server.v1_8_R3;

import com.mojang.authlib.GameProfile;
import java.io.PrintStream;
import java.util.Random;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockDispenser;
import net.minecraft.server.v1_8_R3.BlockFire;
import net.minecraft.server.v1_8_R3.BlockFluids;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockPumpkin;
import net.minecraft.server.v1_8_R3.BlockSkull;
import net.minecraft.server.v1_8_R3.BlockTNT;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.DispenseBehaviorItem;
import net.minecraft.server.v1_8_R3.DispenseBehaviorProjectile;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityBoat;
import net.minecraft.server.v1_8_R3.EntityEgg;
import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPotion;
import net.minecraft.server.v1_8_R3.EntitySmallFireball;
import net.minecraft.server.v1_8_R3.EntitySnowball;
import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import net.minecraft.server.v1_8_R3.EntityThrownExpBottle;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.GameProfileSerializer;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IDispenseBehavior;
import net.minecraft.server.v1_8_R3.IPosition;
import net.minecraft.server.v1_8_R3.IProjectile;
import net.minecraft.server.v1_8_R3.ISourceBlock;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemBucket;
import net.minecraft.server.v1_8_R3.ItemDye;
import net.minecraft.server.v1_8_R3.ItemMonsterEgg;
import net.minecraft.server.v1_8_R3.ItemPotion;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.RedirectStream;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityDispenser;
import net.minecraft.server.v1_8_R3.TileEntitySkull;
import net.minecraft.server.v1_8_R3.UtilColor;
import net.minecraft.server.v1_8_R3.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.projectiles.CraftBlockProjectileSource;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.util.Vector;

public class DispenserRegistry {
   private static final PrintStream a = System.out;
   private static boolean b = false;
   private static final Logger c = LogManager.getLogger();

   public static boolean a() {
      return b;
   }

   static void b() {
      BlockDispenser.REGISTRY.a(Items.ARROW, new DispenseBehaviorProjectile() {
         protected IProjectile a(World p_a_1_, IPosition p_a_2_) {
            EntityArrow entityarrow = new EntityArrow(p_a_1_, p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ());
            entityarrow.fromPlayer = 1;
            return entityarrow;
         }
      });
      BlockDispenser.REGISTRY.a(Items.EGG, new DispenseBehaviorProjectile() {
         protected IProjectile a(World p_a_1_, IPosition p_a_2_) {
            return new EntityEgg(p_a_1_, p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ());
         }
      });
      BlockDispenser.REGISTRY.a(Items.SNOWBALL, new DispenseBehaviorProjectile() {
         protected IProjectile a(World p_a_1_, IPosition p_a_2_) {
            return new EntitySnowball(p_a_1_, p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ());
         }
      });
      BlockDispenser.REGISTRY.a(Items.EXPERIENCE_BOTTLE, new DispenseBehaviorProjectile() {
         protected IProjectile a(World p_a_1_, IPosition p_a_2_) {
            return new EntityThrownExpBottle(p_a_1_, p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ());
         }

         protected float a() {
            return super.a() * 0.5F;
         }

         protected float getPower() {
            return super.getPower() * 1.25F;
         }
      });
      BlockDispenser.REGISTRY.a(Items.POTION, new IDispenseBehavior() {
         private final DispenseBehaviorItem b = new DispenseBehaviorItem();

         public ItemStack a(ISourceBlock p_a_1_, final ItemStack p_a_2_) {
            return ItemPotion.f(p_a_2_.getData())?(new DispenseBehaviorProjectile() {
               protected IProjectile a(World p_a_1_, IPosition p_a_2_x) {
                  return new EntityPotion(p_a_1_, p_a_2_x.getX(), p_a_2_x.getY(), p_a_2_x.getZ(), p_a_2_.cloneItemStack());
               }

               protected float a() {
                  return super.a() * 0.5F;
               }

               protected float getPower() {
                  return super.getPower() * 1.25F;
               }
            }).a(p_a_1_, p_a_2_):this.b.a(p_a_1_, p_a_2_);
         }
      });
      BlockDispenser.REGISTRY.a(Items.SPAWN_EGG, new DispenseBehaviorItem() {
         public ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
            EnumDirection enumdirection = BlockDispenser.b(p_b_1_.f());
            double d0 = p_b_1_.getX() + (double)enumdirection.getAdjacentX();
            double d1 = (double)((float)p_b_1_.getBlockPosition().getY() + 0.2F);
            double d2 = p_b_1_.getZ() + (double)enumdirection.getAdjacentZ();
            World world = p_b_1_.getWorld();
            ItemStack itemstack = p_b_2_.cloneAndSubtract(1);
            org.bukkit.block.Block block = world.getWorld().getBlockAt(p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ());
            CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(itemstack);
            BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block, craftitemstack.clone(), new Vector(d0, d1, d2));
            if(!BlockDispenser.eventFired) {
               world.getServer().getPluginManager().callEvent(blockdispenseevent);
            }

            if(blockdispenseevent.isCancelled()) {
               ++p_b_2_.count;
               return p_b_2_;
            } else {
               if(!blockdispenseevent.getItem().equals(craftitemstack)) {
                  ++p_b_2_.count;
                  ItemStack itemstack1 = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
                  IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack1.getItem());
                  if(idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this) {
                     idispensebehavior.a(p_b_1_, itemstack1);
                     return p_b_2_;
                  }
               }

               itemstack = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
               Entity entity = ItemMonsterEgg.spawnCreature(p_b_1_.getWorld(), p_b_2_.getData(), blockdispenseevent.getVelocity().getX(), blockdispenseevent.getVelocity().getY(), blockdispenseevent.getVelocity().getZ(), SpawnReason.DISPENSE_EGG);
               if(entity instanceof EntityLiving && p_b_2_.hasName()) {
                  ((EntityInsentient)entity).setCustomName(p_b_2_.getName());
               }

               return p_b_2_;
            }
         }
      });
      BlockDispenser.REGISTRY.a(Items.FIREWORKS, new DispenseBehaviorItem() {
         public ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
            EnumDirection enumdirection = BlockDispenser.b(p_b_1_.f());
            double d0 = p_b_1_.getX() + (double)enumdirection.getAdjacentX();
            double d1 = (double)((float)p_b_1_.getBlockPosition().getY() + 0.2F);
            double d2 = p_b_1_.getZ() + (double)enumdirection.getAdjacentZ();
            World world = p_b_1_.getWorld();
            ItemStack itemstack = p_b_2_.cloneAndSubtract(1);
            org.bukkit.block.Block block = world.getWorld().getBlockAt(p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ());
            CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(itemstack);
            BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block, craftitemstack.clone(), new Vector(d0, d1, d2));
            if(!BlockDispenser.eventFired) {
               world.getServer().getPluginManager().callEvent(blockdispenseevent);
            }

            if(blockdispenseevent.isCancelled()) {
               ++p_b_2_.count;
               return p_b_2_;
            } else {
               if(!blockdispenseevent.getItem().equals(craftitemstack)) {
                  ++p_b_2_.count;
                  ItemStack itemstack1 = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
                  IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack1.getItem());
                  if(idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this) {
                     idispensebehavior.a(p_b_1_, itemstack1);
                     return p_b_2_;
                  }
               }

               itemstack = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
               EntityFireworks entityfireworks = new EntityFireworks(p_b_1_.getWorld(), blockdispenseevent.getVelocity().getX(), blockdispenseevent.getVelocity().getY(), blockdispenseevent.getVelocity().getZ(), itemstack);
               p_b_1_.getWorld().addEntity(entityfireworks);
               return p_b_2_;
            }
         }

         protected void a(ISourceBlock p_a_1_) {
            p_a_1_.getWorld().triggerEffect(1002, p_a_1_.getBlockPosition(), 0);
         }
      });
      BlockDispenser.REGISTRY.a(Items.FIRE_CHARGE, new DispenseBehaviorItem() {
         public ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
            EnumDirection enumdirection = BlockDispenser.b(p_b_1_.f());
            IPosition iposition = BlockDispenser.a(p_b_1_);
            double d0 = iposition.getX() + (double)((float)enumdirection.getAdjacentX() * 0.3F);
            double d1 = iposition.getY() + (double)((float)enumdirection.getAdjacentY() * 0.3F);
            double d2 = iposition.getZ() + (double)((float)enumdirection.getAdjacentZ() * 0.3F);
            World world = p_b_1_.getWorld();
            Random random = world.random;
            double d3 = random.nextGaussian() * 0.05D + (double)enumdirection.getAdjacentX();
            double d4 = random.nextGaussian() * 0.05D + (double)enumdirection.getAdjacentY();
            double d5 = random.nextGaussian() * 0.05D + (double)enumdirection.getAdjacentZ();
            ItemStack itemstack = p_b_2_.cloneAndSubtract(1);
            org.bukkit.block.Block block = world.getWorld().getBlockAt(p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ());
            CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(itemstack);
            BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block, craftitemstack.clone(), new Vector(d3, d4, d5));
            if(!BlockDispenser.eventFired) {
               world.getServer().getPluginManager().callEvent(blockdispenseevent);
            }

            if(blockdispenseevent.isCancelled()) {
               ++p_b_2_.count;
               return p_b_2_;
            } else {
               if(!blockdispenseevent.getItem().equals(craftitemstack)) {
                  ++p_b_2_.count;
                  ItemStack itemstack1 = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
                  IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack1.getItem());
                  if(idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this) {
                     idispensebehavior.a(p_b_1_, itemstack1);
                     return p_b_2_;
                  }
               }

               EntitySmallFireball entitysmallfireball = new EntitySmallFireball(world, d0, d1, d2, blockdispenseevent.getVelocity().getX(), blockdispenseevent.getVelocity().getY(), blockdispenseevent.getVelocity().getZ());
               entitysmallfireball.projectileSource = new CraftBlockProjectileSource((TileEntityDispenser)p_b_1_.getTileEntity());
               world.addEntity(entitysmallfireball);
               return p_b_2_;
            }
         }

         protected void a(ISourceBlock p_a_1_) {
            p_a_1_.getWorld().triggerEffect(1009, p_a_1_.getBlockPosition(), 0);
         }
      });
      BlockDispenser.REGISTRY.a(Items.BOAT, new DispenseBehaviorItem() {
         private final DispenseBehaviorItem b = new DispenseBehaviorItem();

         public ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
            EnumDirection enumdirection = BlockDispenser.b(p_b_1_.f());
            World world = p_b_1_.getWorld();
            double d0 = p_b_1_.getX() + (double)((float)enumdirection.getAdjacentX() * 1.125F);
            double d1 = p_b_1_.getY() + (double)((float)enumdirection.getAdjacentY() * 1.125F);
            double d2 = p_b_1_.getZ() + (double)((float)enumdirection.getAdjacentZ() * 1.125F);
            BlockPosition blockposition = p_b_1_.getBlockPosition().shift(enumdirection);
            Material material = world.getType(blockposition).getBlock().getMaterial();
            double d3;
            if(Material.WATER.equals(material)) {
               d3 = 1.0D;
            } else {
               if(!Material.AIR.equals(material) || !Material.WATER.equals(world.getType(blockposition.down()).getBlock().getMaterial())) {
                  return this.b.a(p_b_1_, p_b_2_);
               }

               d3 = 0.0D;
            }

            ItemStack itemstack = p_b_2_.cloneAndSubtract(1);
            org.bukkit.block.Block block = world.getWorld().getBlockAt(p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ());
            CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(itemstack);
            BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block, craftitemstack.clone(), new Vector(d0, d1 + d3, d2));
            if(!BlockDispenser.eventFired) {
               world.getServer().getPluginManager().callEvent(blockdispenseevent);
            }

            if(blockdispenseevent.isCancelled()) {
               ++p_b_2_.count;
               return p_b_2_;
            } else {
               if(!blockdispenseevent.getItem().equals(craftitemstack)) {
                  ++p_b_2_.count;
                  ItemStack itemstack1 = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
                  IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack1.getItem());
                  if(idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this) {
                     idispensebehavior.a(p_b_1_, itemstack1);
                     return p_b_2_;
                  }
               }

               EntityBoat entityboat = new EntityBoat(world, blockdispenseevent.getVelocity().getX(), blockdispenseevent.getVelocity().getY(), blockdispenseevent.getVelocity().getZ());
               world.addEntity(entityboat);
               return p_b_2_;
            }
         }

         protected void a(ISourceBlock p_a_1_) {
            p_a_1_.getWorld().triggerEffect(1000, p_a_1_.getBlockPosition(), 0);
         }
      });
      DispenseBehaviorItem dispensebehavioritem = new DispenseBehaviorItem() {
         private final DispenseBehaviorItem b = new DispenseBehaviorItem();

         public ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
            ItemBucket itembucket = (ItemBucket)p_b_2_.getItem();
            BlockPosition blockposition = p_b_1_.getBlockPosition().shift(BlockDispenser.b(p_b_1_.f()));
            World world = p_b_1_.getWorld();
            int i = blockposition.getX();
            int j = blockposition.getY();
            int k = blockposition.getZ();
            if(world.isEmpty(blockposition) || !world.getType(blockposition).getBlock().getMaterial().isBuildable()) {
               org.bukkit.block.Block block = world.getWorld().getBlockAt(p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ());
               CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(p_b_2_);
               BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block, craftitemstack.clone(), new Vector(i, j, k));
               if(!BlockDispenser.eventFired) {
                  world.getServer().getPluginManager().callEvent(blockdispenseevent);
               }

               if(blockdispenseevent.isCancelled()) {
                  return p_b_2_;
               }

               if(!blockdispenseevent.getItem().equals(craftitemstack)) {
                  ItemStack itemstack = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
                  IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack.getItem());
                  if(idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this) {
                     idispensebehavior.a(p_b_1_, itemstack);
                     return p_b_2_;
                  }
               }

               itembucket = (ItemBucket)CraftItemStack.asNMSCopy(blockdispenseevent.getItem()).getItem();
            }

            if(itembucket.a(p_b_1_.getWorld(), blockposition)) {
               Item item = Items.BUCKET;
               if(--p_b_2_.count == 0) {
                  p_b_2_.setItem(Items.BUCKET);
                  p_b_2_.count = 1;
               } else if(((TileEntityDispenser)p_b_1_.getTileEntity()).addItem(new ItemStack(item)) < 0) {
                  this.b.a(p_b_1_, new ItemStack(item));
               }

               return p_b_2_;
            } else {
               return this.b.a(p_b_1_, p_b_2_);
            }
         }
      };
      BlockDispenser.REGISTRY.a(Items.LAVA_BUCKET, dispensebehavioritem);
      BlockDispenser.REGISTRY.a(Items.WATER_BUCKET, dispensebehavioritem);
      BlockDispenser.REGISTRY.a(Items.BUCKET, new DispenseBehaviorItem() {
         private final DispenseBehaviorItem b = new DispenseBehaviorItem();

         public ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
            World world = p_b_1_.getWorld();
            BlockPosition blockposition = p_b_1_.getBlockPosition().shift(BlockDispenser.b(p_b_1_.f()));
            IBlockData iblockdata = world.getType(blockposition);
            Block block = iblockdata.getBlock();
            Material material = block.getMaterial();
            Item item;
            if(Material.WATER.equals(material) && block instanceof BlockFluids && ((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() == 0) {
               item = Items.WATER_BUCKET;
            } else {
               if(!Material.LAVA.equals(material) || !(block instanceof BlockFluids) || ((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() != 0) {
                  return super.b(p_b_1_, p_b_2_);
               }

               item = Items.LAVA_BUCKET;
            }

            org.bukkit.block.Block block1 = world.getWorld().getBlockAt(p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ());
            CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(p_b_2_);
            BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block1, craftitemstack.clone(), new Vector(blockposition.getX(), blockposition.getY(), blockposition.getZ()));
            if(!BlockDispenser.eventFired) {
               world.getServer().getPluginManager().callEvent(blockdispenseevent);
            }

            if(blockdispenseevent.isCancelled()) {
               return p_b_2_;
            } else {
               if(!blockdispenseevent.getItem().equals(craftitemstack)) {
                  ItemStack itemstack = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
                  IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack.getItem());
                  if(idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this) {
                     idispensebehavior.a(p_b_1_, itemstack);
                     return p_b_2_;
                  }
               }

               world.setAir(blockposition);
               if(--p_b_2_.count == 0) {
                  p_b_2_.setItem(item);
                  p_b_2_.count = 1;
               } else if(((TileEntityDispenser)p_b_1_.getTileEntity()).addItem(new ItemStack(item)) < 0) {
                  this.b.a(p_b_1_, new ItemStack(item));
               }

               return p_b_2_;
            }
         }
      });
      BlockDispenser.REGISTRY.a(Items.FLINT_AND_STEEL, new DispenseBehaviorItem() {
         private boolean b = true;

         protected ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
            World world = p_b_1_.getWorld();
            BlockPosition blockposition = p_b_1_.getBlockPosition().shift(BlockDispenser.b(p_b_1_.f()));
            org.bukkit.block.Block block = world.getWorld().getBlockAt(p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ());
            CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(p_b_2_);
            BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block, craftitemstack.clone(), new Vector(0, 0, 0));
            if(!BlockDispenser.eventFired) {
               world.getServer().getPluginManager().callEvent(blockdispenseevent);
            }

            if(blockdispenseevent.isCancelled()) {
               return p_b_2_;
            } else {
               if(!blockdispenseevent.getItem().equals(craftitemstack)) {
                  ItemStack itemstack = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
                  IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack.getItem());
                  if(idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this) {
                     idispensebehavior.a(p_b_1_, itemstack);
                     return p_b_2_;
                  }
               }

               if(world.isEmpty(blockposition)) {
                  if(!CraftEventFactory.callBlockIgniteEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ()).isCancelled()) {
                     world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
                     if(p_b_2_.isDamaged(1, world.random)) {
                        p_b_2_.count = 0;
                     }
                  }
               } else if(world.getType(blockposition).getBlock() == Blocks.TNT) {
                  Blocks.TNT.postBreak(world, blockposition, Blocks.TNT.getBlockData().set(BlockTNT.EXPLODE, Boolean.valueOf(true)));
                  world.setAir(blockposition);
               } else {
                  this.b = false;
               }

               return p_b_2_;
            }
         }

         protected void a(ISourceBlock p_a_1_) {
            if(this.b) {
               p_a_1_.getWorld().triggerEffect(1000, p_a_1_.getBlockPosition(), 0);
            } else {
               p_a_1_.getWorld().triggerEffect(1001, p_a_1_.getBlockPosition(), 0);
            }

         }
      });
      BlockDispenser.REGISTRY.a(Items.DYE, new DispenseBehaviorItem() {
         private boolean b = true;

         protected ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
            if(EnumColor.WHITE == EnumColor.fromInvColorIndex(p_b_2_.getData())) {
               World world = p_b_1_.getWorld();
               BlockPosition blockposition = p_b_1_.getBlockPosition().shift(BlockDispenser.b(p_b_1_.f()));
               org.bukkit.block.Block block = world.getWorld().getBlockAt(p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ());
               CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(p_b_2_);
               BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block, craftitemstack.clone(), new Vector(0, 0, 0));
               if(!BlockDispenser.eventFired) {
                  world.getServer().getPluginManager().callEvent(blockdispenseevent);
               }

               if(blockdispenseevent.isCancelled()) {
                  return p_b_2_;
               } else {
                  if(!blockdispenseevent.getItem().equals(craftitemstack)) {
                     ItemStack itemstack = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
                     IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack.getItem());
                     if(idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this) {
                        idispensebehavior.a(p_b_1_, itemstack);
                        return p_b_2_;
                     }
                  }

                  if(ItemDye.a(p_b_2_, world, blockposition)) {
                     if(!world.isClientSide) {
                        world.triggerEffect(2005, blockposition, 0);
                     }
                  } else {
                     this.b = false;
                  }

                  return p_b_2_;
               }
            } else {
               return super.b(p_b_1_, p_b_2_);
            }
         }

         protected void a(ISourceBlock p_a_1_) {
            if(this.b) {
               p_a_1_.getWorld().triggerEffect(1000, p_a_1_.getBlockPosition(), 0);
            } else {
               p_a_1_.getWorld().triggerEffect(1001, p_a_1_.getBlockPosition(), 0);
            }

         }
      });
      BlockDispenser.REGISTRY.a(Item.getItemOf(Blocks.TNT), new DispenseBehaviorItem() {
         protected ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
            World world = p_b_1_.getWorld();
            BlockPosition blockposition = p_b_1_.getBlockPosition().shift(BlockDispenser.b(p_b_1_.f()));
            ItemStack itemstack = p_b_2_.cloneAndSubtract(1);
            org.bukkit.block.Block block = world.getWorld().getBlockAt(p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ());
            CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(itemstack);
            BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block, craftitemstack.clone(), new Vector((double)blockposition.getX() + 0.5D, (double)blockposition.getY() + 0.5D, (double)blockposition.getZ() + 0.5D));
            if(!BlockDispenser.eventFired) {
               world.getServer().getPluginManager().callEvent(blockdispenseevent);
            }

            if(blockdispenseevent.isCancelled()) {
               ++p_b_2_.count;
               return p_b_2_;
            } else {
               if(!blockdispenseevent.getItem().equals(craftitemstack)) {
                  ++p_b_2_.count;
                  ItemStack itemstack1 = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
                  IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack1.getItem());
                  if(idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this) {
                     idispensebehavior.a(p_b_1_, itemstack1);
                     return p_b_2_;
                  }
               }

               EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, blockdispenseevent.getVelocity().getX(), blockdispenseevent.getVelocity().getY(), blockdispenseevent.getVelocity().getZ(), (EntityLiving)null);
               world.addEntity(entitytntprimed);
               world.makeSound(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
               return p_b_2_;
            }
         }
      });
      BlockDispenser.REGISTRY.a(Items.SKULL, new DispenseBehaviorItem() {
         private boolean b = true;

         protected ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
            World world = p_b_1_.getWorld();
            EnumDirection enumdirection = BlockDispenser.b(p_b_1_.f());
            BlockPosition blockposition = p_b_1_.getBlockPosition().shift(enumdirection);
            BlockSkull blockskull = Blocks.SKULL;
            if(world.isEmpty(blockposition) && blockskull.b(world, blockposition, p_b_2_)) {
               if(!world.isClientSide) {
                  world.setTypeAndData(blockposition, blockskull.getBlockData().set(BlockSkull.FACING, EnumDirection.UP), 3);
                  TileEntity tileentity = world.getTileEntity(blockposition);
                  if(tileentity instanceof TileEntitySkull) {
                     if(p_b_2_.getData() == 3) {
                        GameProfile gameprofile = null;
                        if(p_b_2_.hasTag()) {
                           NBTTagCompound nbttagcompound = p_b_2_.getTag();
                           if(nbttagcompound.hasKeyOfType("SkullOwner", 10)) {
                              gameprofile = GameProfileSerializer.deserialize(nbttagcompound.getCompound("SkullOwner"));
                           } else if(nbttagcompound.hasKeyOfType("SkullOwner", 8)) {
                              String s = nbttagcompound.getString("SkullOwner");
                              if(!UtilColor.b(s)) {
                                 gameprofile = new GameProfile((UUID)null, s);
                              }
                           }
                        }

                        ((TileEntitySkull)tileentity).setGameProfile(gameprofile);
                     } else {
                        ((TileEntitySkull)tileentity).setSkullType(p_b_2_.getData());
                     }

                     ((TileEntitySkull)tileentity).setRotation(enumdirection.opposite().b() * 4);
                     Blocks.SKULL.a(world, blockposition, (TileEntitySkull)tileentity);
                  }

                  --p_b_2_.count;
               }
            } else {
               this.b = false;
            }

            return p_b_2_;
         }

         protected void a(ISourceBlock p_a_1_) {
            if(this.b) {
               p_a_1_.getWorld().triggerEffect(1000, p_a_1_.getBlockPosition(), 0);
            } else {
               p_a_1_.getWorld().triggerEffect(1001, p_a_1_.getBlockPosition(), 0);
            }

         }
      });
      BlockDispenser.REGISTRY.a(Item.getItemOf(Blocks.PUMPKIN), new DispenseBehaviorItem() {
         private boolean b = true;

         protected ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
            World world = p_b_1_.getWorld();
            BlockPosition blockposition = p_b_1_.getBlockPosition().shift(BlockDispenser.b(p_b_1_.f()));
            BlockPumpkin blockpumpkin = (BlockPumpkin)Blocks.PUMPKIN;
            if(world.isEmpty(blockposition) && blockpumpkin.e(world, blockposition)) {
               if(!world.isClientSide) {
                  world.setTypeAndData(blockposition, blockpumpkin.getBlockData(), 3);
               }

               --p_b_2_.count;
            } else {
               this.b = false;
            }

            return p_b_2_;
         }

         protected void a(ISourceBlock p_a_1_) {
            if(this.b) {
               p_a_1_.getWorld().triggerEffect(1000, p_a_1_.getBlockPosition(), 0);
            } else {
               p_a_1_.getWorld().triggerEffect(1001, p_a_1_.getBlockPosition(), 0);
            }

         }
      });
   }

   public static void c() {
      if(!b) {
         b = true;
         if(c.isDebugEnabled()) {
            d();
         }

         Block.S();
         BlockFire.l();
         Item.t();
         StatisticList.a();
         b();
      }

   }

   private static void d() {
      System.setErr(new RedirectStream("STDERR", System.err));
      System.setOut(new RedirectStream("STDOUT", a));
   }
}
