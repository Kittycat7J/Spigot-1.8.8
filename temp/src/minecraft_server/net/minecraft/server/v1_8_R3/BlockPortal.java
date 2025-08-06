package net.minecraft.server.v1_8_R3;

import com.google.common.cache.LoadingCache;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockHalfTransparent;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.ItemMonsterEgg;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.ShapeDetector;
import net.minecraft.server.v1_8_R3.ShapeDetectorBlock;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

public class BlockPortal extends BlockHalfTransparent {
   public static final BlockStateEnum<EnumDirection.EnumAxis> AXIS = BlockStateEnum.<EnumDirection.EnumAxis>of("axis", EnumDirection.EnumAxis.class, new EnumDirection.EnumAxis[]{EnumDirection.EnumAxis.X, EnumDirection.EnumAxis.Z});

   public BlockPortal() {
      super(Material.PORTAL, false);
      this.j(this.blockStateList.getBlockData().set(AXIS, EnumDirection.EnumAxis.X));
      this.a(true);
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      super.b(p_b_1_, p_b_2_, p_b_3_, p_b_4_);
      if(p_b_1_.spigotConfig.enableZombiePigmenPortalSpawns && p_b_1_.worldProvider.d() && p_b_1_.getGameRules().getBoolean("doMobSpawning") && p_b_4_.nextInt(2000) < p_b_1_.getDifficulty().a()) {
         int i = p_b_2_.getY();

         BlockPosition blockposition;
         for(blockposition = p_b_2_; !World.a((IBlockAccess)p_b_1_, (BlockPosition)blockposition) && blockposition.getY() > 0; blockposition = blockposition.down()) {
            ;
         }

         if(i > 0 && !p_b_1_.getType(blockposition.up()).getBlock().isOccluding()) {
            Entity entity = ItemMonsterEgg.spawnCreature(p_b_1_, 57, (double)blockposition.getX() + 0.5D, (double)blockposition.getY() + 1.1D, (double)blockposition.getZ() + 0.5D, SpawnReason.NETHER_PORTAL);
            if(entity != null) {
               entity.portalCooldown = entity.aq();
            }
         }
      }

   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      return null;
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      EnumDirection.EnumAxis enumdirection$enumaxis = (EnumDirection.EnumAxis)p_updateShape_1_.getType(p_updateShape_2_).get(AXIS);
      float f = 0.125F;
      float f1 = 0.125F;
      if(enumdirection$enumaxis == EnumDirection.EnumAxis.X) {
         f = 0.5F;
      }

      if(enumdirection$enumaxis == EnumDirection.EnumAxis.Z) {
         f1 = 0.5F;
      }

      this.a(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
   }

   public static int a(EnumDirection.EnumAxis p_a_0_) {
      return p_a_0_ == EnumDirection.EnumAxis.X?1:(p_a_0_ == EnumDirection.EnumAxis.Z?2:0);
   }

   public boolean d() {
      return false;
   }

   public boolean e(World p_e_1_, BlockPosition p_e_2_) {
      BlockPortal.Shape blockportal$shape = new BlockPortal.Shape(p_e_1_, p_e_2_, EnumDirection.EnumAxis.X);
      if(blockportal$shape.d() && blockportal$shape.e == 0) {
         return blockportal$shape.createPortal();
      } else {
         BlockPortal.Shape blockportal$shape1 = new BlockPortal.Shape(p_e_1_, p_e_2_, EnumDirection.EnumAxis.Z);
         return blockportal$shape1.d() && blockportal$shape1.e == 0?blockportal$shape1.createPortal():false;
      }
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      EnumDirection.EnumAxis enumdirection$enumaxis = (EnumDirection.EnumAxis)p_doPhysics_3_.get(AXIS);
      if(enumdirection$enumaxis == EnumDirection.EnumAxis.X) {
         BlockPortal.Shape blockportal$shape = new BlockPortal.Shape(p_doPhysics_1_, p_doPhysics_2_, EnumDirection.EnumAxis.X);
         if(!blockportal$shape.d() || blockportal$shape.e < blockportal$shape.width * blockportal$shape.height) {
            p_doPhysics_1_.setTypeUpdate(p_doPhysics_2_, Blocks.AIR.getBlockData());
         }
      } else if(enumdirection$enumaxis == EnumDirection.EnumAxis.Z) {
         BlockPortal.Shape blockportal$shape1 = new BlockPortal.Shape(p_doPhysics_1_, p_doPhysics_2_, EnumDirection.EnumAxis.Z);
         if(!blockportal$shape1.d() || blockportal$shape1.e < blockportal$shape1.width * blockportal$shape1.height) {
            p_doPhysics_1_.setTypeUpdate(p_doPhysics_2_, Blocks.AIR.getBlockData());
         }
      }

   }

   public int a(Random p_a_1_) {
      return 0;
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Entity p_a_4_) {
      if(p_a_4_.vehicle == null && p_a_4_.passenger == null) {
         EntityPortalEnterEvent entityportalenterevent = new EntityPortalEnterEvent(p_a_4_.getBukkitEntity(), new Location(p_a_1_.getWorld(), (double)p_a_2_.getX(), (double)p_a_2_.getY(), (double)p_a_2_.getZ()));
         p_a_1_.getServer().getPluginManager().callEvent(entityportalenterevent);
         p_a_4_.d(p_a_2_);
      }

   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(AXIS, (p_fromLegacyData_1_ & 3) == 2?EnumDirection.EnumAxis.Z:EnumDirection.EnumAxis.X);
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return a((EnumDirection.EnumAxis)p_toLegacyData_1_.get(AXIS));
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{AXIS});
   }

   public ShapeDetector.ShapeDetectorCollection f(World p_f_1_, BlockPosition p_f_2_) {
      EnumDirection.EnumAxis enumdirection$enumaxis = EnumDirection.EnumAxis.Z;
      BlockPortal.Shape blockportal$shape = new BlockPortal.Shape(p_f_1_, p_f_2_, EnumDirection.EnumAxis.X);
      LoadingCache loadingcache = ShapeDetector.a(p_f_1_, true);
      if(!blockportal$shape.d()) {
         enumdirection$enumaxis = EnumDirection.EnumAxis.X;
         blockportal$shape = new BlockPortal.Shape(p_f_1_, p_f_2_, EnumDirection.EnumAxis.Z);
      }

      if(!blockportal$shape.d()) {
         return new ShapeDetector.ShapeDetectorCollection(p_f_2_, EnumDirection.NORTH, EnumDirection.UP, loadingcache, 1, 1, 1);
      } else {
         int[] aint = new int[EnumDirection.EnumAxisDirection.values().length];
         EnumDirection enumdirection = blockportal$shape.c.f();
         BlockPosition blockposition = blockportal$shape.position.up(blockportal$shape.a() - 1);

         for(EnumDirection.EnumAxisDirection enumdirection$enumaxisdirection : EnumDirection.EnumAxisDirection.values()) {
            ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection = new ShapeDetector.ShapeDetectorCollection(enumdirection.c() == enumdirection$enumaxisdirection?blockposition:blockposition.shift(blockportal$shape.c, blockportal$shape.b() - 1), EnumDirection.a(enumdirection$enumaxisdirection, enumdirection$enumaxis), EnumDirection.UP, loadingcache, blockportal$shape.b(), blockportal$shape.a(), 1);

            for(int i = 0; i < blockportal$shape.b(); ++i) {
               for(int j = 0; j < blockportal$shape.a(); ++j) {
                  ShapeDetectorBlock shapedetectorblock = shapedetector$shapedetectorcollection.a(i, j, 1);
                  if(shapedetectorblock.a() != null && shapedetectorblock.a().getBlock().getMaterial() != Material.AIR) {
                     ++aint[enumdirection$enumaxisdirection.ordinal()];
                  }
               }
            }
         }

         EnumDirection.EnumAxisDirection enumdirection$enumaxisdirection1 = EnumDirection.EnumAxisDirection.POSITIVE;

         for(EnumDirection.EnumAxisDirection enumdirection$enumaxisdirection2 : EnumDirection.EnumAxisDirection.values()) {
            if(aint[enumdirection$enumaxisdirection2.ordinal()] < aint[enumdirection$enumaxisdirection1.ordinal()]) {
               enumdirection$enumaxisdirection1 = enumdirection$enumaxisdirection2;
            }
         }

         return new ShapeDetector.ShapeDetectorCollection(enumdirection.c() == enumdirection$enumaxisdirection1?blockposition:blockposition.shift(blockportal$shape.c, blockportal$shape.b() - 1), EnumDirection.a(enumdirection$enumaxisdirection1, enumdirection$enumaxis), EnumDirection.UP, loadingcache, blockportal$shape.b(), blockportal$shape.a(), 1);
      }
   }

   public static class Shape {
      private final World a;
      private final EnumDirection.EnumAxis b;
      private final EnumDirection c;
      private final EnumDirection d;
      private int e = 0;
      private BlockPosition position;
      private int height;
      private int width;
      Collection<org.bukkit.block.Block> blocks = new HashSet();

      public Shape(World p_i111_1_, BlockPosition p_i111_2_, EnumDirection.EnumAxis p_i111_3_) {
         this.a = p_i111_1_;
         this.b = p_i111_3_;
         if(p_i111_3_ == EnumDirection.EnumAxis.X) {
            this.d = EnumDirection.EAST;
            this.c = EnumDirection.WEST;
         } else {
            this.d = EnumDirection.NORTH;
            this.c = EnumDirection.SOUTH;
         }

         for(BlockPosition blockposition = p_i111_2_; p_i111_2_.getY() > blockposition.getY() - 21 && p_i111_2_.getY() > 0 && this.a(p_i111_1_.getType(p_i111_2_.down()).getBlock()); p_i111_2_ = p_i111_2_.down()) {
            ;
         }

         int i = this.a(p_i111_2_, this.d) - 1;
         if(i >= 0) {
            this.position = p_i111_2_.shift(this.d, i);
            this.width = this.a(this.position, this.c);
            if(this.width < 2 || this.width > 21) {
               this.position = null;
               this.width = 0;
            }
         }

         if(this.position != null) {
            this.height = this.c();
         }

      }

      protected int a(BlockPosition p_a_1_, EnumDirection p_a_2_) {
         int i;
         for(i = 0; i < 22; ++i) {
            BlockPosition blockposition = p_a_1_.shift(p_a_2_, i);
            if(!this.a(this.a.getType(blockposition).getBlock()) || this.a.getType(blockposition.down()).getBlock() != Blocks.OBSIDIAN) {
               break;
            }
         }

         Block block = this.a.getType(p_a_1_.shift(p_a_2_, i)).getBlock();
         return block == Blocks.OBSIDIAN?i:0;
      }

      public int a() {
         return this.height;
      }

      public int b() {
         return this.width;
      }

      protected int c() {
         this.blocks.clear();
         org.bukkit.World world = this.a.getWorld();

         label30:
         for(this.height = 0; this.height < 21; ++this.height) {
            for(int i = 0; i < this.width; ++i) {
               BlockPosition blockposition = this.position.shift(this.c, i).up(this.height);
               Block block = this.a.getType(blockposition).getBlock();
               if(!this.a(block)) {
                  break label30;
               }

               if(block == Blocks.PORTAL) {
                  ++this.e;
               }

               if(i == 0) {
                  block = this.a.getType(blockposition.shift(this.d)).getBlock();
                  if(block != Blocks.OBSIDIAN) {
                     break label30;
                  }

                  BlockPosition blockposition1 = blockposition.shift(this.d);
                  this.blocks.add(world.getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ()));
               } else if(i == this.width - 1) {
                  block = this.a.getType(blockposition.shift(this.c)).getBlock();
                  if(block != Blocks.OBSIDIAN) {
                     break label30;
                  }

                  BlockPosition blockposition3 = blockposition.shift(this.c);
                  this.blocks.add(world.getBlockAt(blockposition3.getX(), blockposition3.getY(), blockposition3.getZ()));
               }
            }
         }

         for(int j = 0; j < this.width; ++j) {
            if(this.a.getType(this.position.shift(this.c, j).up(this.height)).getBlock() != Blocks.OBSIDIAN) {
               this.height = 0;
               break;
            }

            BlockPosition blockposition2 = this.position.shift(this.c, j).up(this.height);
            this.blocks.add(world.getBlockAt(blockposition2.getX(), blockposition2.getY(), blockposition2.getZ()));
         }

         if(this.height <= 21 && this.height >= 3) {
            return this.height;
         } else {
            this.position = null;
            this.width = 0;
            this.height = 0;
            return 0;
         }
      }

      protected boolean a(Block p_a_1_) {
         return p_a_1_.material == Material.AIR || p_a_1_ == Blocks.FIRE || p_a_1_ == Blocks.PORTAL;
      }

      public boolean d() {
         return this.position != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
      }

      public boolean createPortal() {
         org.bukkit.World world = this.a.getWorld();

         for(int i = 0; i < this.width; ++i) {
            BlockPosition blockposition = this.position.shift(this.c, i);

            for(int j = 0; j < this.height; ++j) {
               BlockPosition blockposition1 = blockposition.up(j);
               this.blocks.add(world.getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ()));
            }
         }

         PortalCreateEvent portalcreateevent = new PortalCreateEvent(this.blocks, world, CreateReason.FIRE);
         this.a.getServer().getPluginManager().callEvent(portalcreateevent);
         if(portalcreateevent.isCancelled()) {
            return false;
         } else {
            for(int k = 0; k < this.width; ++k) {
               BlockPosition blockposition2 = this.position.shift(this.c, k);

               for(int l = 0; l < this.height; ++l) {
                  this.a.setTypeAndData(blockposition2.up(l), Blocks.PORTAL.getBlockData().set(BlockPortal.AXIS, this.b), 2);
               }
            }

            return true;
         }
      }
   }
}
