package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.BaseBlockPosition;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockDirectional;
import net.minecraft.server.v1_8_R3.BlockDoor;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemDoor;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.StructureBoundingBox;
import net.minecraft.server.v1_8_R3.StructurePieceTreasure;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.minecraft.server.v1_8_R3.TileEntityDispenser;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenFactory;

public abstract class StructurePiece {
   protected StructureBoundingBox l;
   protected EnumDirection m;
   protected int n;

   public StructurePiece() {
   }

   protected StructurePiece(int p_i796_1_) {
      this.n = p_i796_1_;
   }

   public NBTTagCompound b() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.setString("id", WorldGenFactory.a(this));
      nbttagcompound.set("BB", this.l.g());
      nbttagcompound.setInt("O", this.m == null?-1:this.m.b());
      nbttagcompound.setInt("GD", this.n);
      this.a(nbttagcompound);
      return nbttagcompound;
   }

   protected abstract void a(NBTTagCompound var1);

   public void a(World p_a_1_, NBTTagCompound p_a_2_) {
      if(p_a_2_.hasKey("BB")) {
         this.l = new StructureBoundingBox(p_a_2_.getIntArray("BB"));
      }

      int i = p_a_2_.getInt("O");
      this.m = i == -1?null:EnumDirection.fromType2(i);
      this.n = p_a_2_.getInt("GD");
      this.b(p_a_2_);
   }

   protected abstract void b(NBTTagCompound var1);

   public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_) {
   }

   public abstract boolean a(World var1, Random var2, StructureBoundingBox var3);

   public StructureBoundingBox c() {
      return this.l;
   }

   public int d() {
      return this.n;
   }

   public static StructurePiece a(List<StructurePiece> p_a_0_, StructureBoundingBox p_a_1_) {
      for(StructurePiece structurepiece : p_a_0_) {
         if(structurepiece.c() != null && structurepiece.c().a(p_a_1_)) {
            return structurepiece;
         }
      }

      return null;
   }

   public BlockPosition a() {
      return new BlockPosition(this.l.f());
   }

   protected boolean a(World p_a_1_, StructureBoundingBox p_a_2_) {
      int i = Math.max(this.l.a - 1, p_a_2_.a);
      int j = Math.max(this.l.b - 1, p_a_2_.b);
      int k = Math.max(this.l.c - 1, p_a_2_.c);
      int lx = Math.min(this.l.d + 1, p_a_2_.d);
      int i1 = Math.min(this.l.e + 1, p_a_2_.e);
      int j1 = Math.min(this.l.f + 1, p_a_2_.f);
      BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int k1 = i; k1 <= lx; ++k1) {
         for(int l1 = k; l1 <= j1; ++l1) {
            if(p_a_1_.getType(blockposition$mutableblockposition.c(k1, j, l1)).getBlock().getMaterial().isLiquid()) {
               return true;
            }

            if(p_a_1_.getType(blockposition$mutableblockposition.c(k1, i1, l1)).getBlock().getMaterial().isLiquid()) {
               return true;
            }
         }
      }

      for(int i2 = i; i2 <= lx; ++i2) {
         for(int k2 = j; k2 <= i1; ++k2) {
            if(p_a_1_.getType(blockposition$mutableblockposition.c(i2, k2, k)).getBlock().getMaterial().isLiquid()) {
               return true;
            }

            if(p_a_1_.getType(blockposition$mutableblockposition.c(i2, k2, j1)).getBlock().getMaterial().isLiquid()) {
               return true;
            }
         }
      }

      for(int j2 = k; j2 <= j1; ++j2) {
         for(int l2 = j; l2 <= i1; ++l2) {
            if(p_a_1_.getType(blockposition$mutableblockposition.c(i, l2, j2)).getBlock().getMaterial().isLiquid()) {
               return true;
            }

            if(p_a_1_.getType(blockposition$mutableblockposition.c(lx, l2, j2)).getBlock().getMaterial().isLiquid()) {
               return true;
            }
         }
      }

      return false;
   }

   protected int a(int p_a_1_, int p_a_2_) {
      if(this.m == null) {
         return p_a_1_;
      } else {
         switch(this.m) {
         case NORTH:
         case SOUTH:
            return this.l.a + p_a_1_;
         case WEST:
            return this.l.d - p_a_2_;
         case EAST:
            return this.l.a + p_a_2_;
         default:
            return p_a_1_;
         }
      }
   }

   protected int d(int p_d_1_) {
      return this.m == null?p_d_1_:p_d_1_ + this.l.b;
   }

   protected int b(int p_b_1_, int p_b_2_) {
      if(this.m == null) {
         return p_b_2_;
      } else {
         switch(this.m) {
         case NORTH:
            return this.l.f - p_b_2_;
         case SOUTH:
            return this.l.c + p_b_2_;
         case WEST:
         case EAST:
            return this.l.c + p_b_1_;
         default:
            return p_b_2_;
         }
      }
   }

   protected int a(Block p_a_1_, int p_a_2_) {
      if(p_a_1_ == Blocks.RAIL) {
         if(this.m == EnumDirection.WEST || this.m == EnumDirection.EAST) {
            if(p_a_2_ == 1) {
               return 0;
            }

            return 1;
         }
      } else if(p_a_1_ instanceof BlockDoor) {
         if(this.m == EnumDirection.SOUTH) {
            if(p_a_2_ == 0) {
               return 2;
            }

            if(p_a_2_ == 2) {
               return 0;
            }
         } else {
            if(this.m == EnumDirection.WEST) {
               return p_a_2_ + 1 & 3;
            }

            if(this.m == EnumDirection.EAST) {
               return p_a_2_ + 3 & 3;
            }
         }
      } else if(p_a_1_ != Blocks.STONE_STAIRS && p_a_1_ != Blocks.OAK_STAIRS && p_a_1_ != Blocks.NETHER_BRICK_STAIRS && p_a_1_ != Blocks.STONE_BRICK_STAIRS && p_a_1_ != Blocks.SANDSTONE_STAIRS) {
         if(p_a_1_ == Blocks.LADDER) {
            if(this.m == EnumDirection.SOUTH) {
               if(p_a_2_ == EnumDirection.NORTH.a()) {
                  return EnumDirection.SOUTH.a();
               }

               if(p_a_2_ == EnumDirection.SOUTH.a()) {
                  return EnumDirection.NORTH.a();
               }
            } else if(this.m == EnumDirection.WEST) {
               if(p_a_2_ == EnumDirection.NORTH.a()) {
                  return EnumDirection.WEST.a();
               }

               if(p_a_2_ == EnumDirection.SOUTH.a()) {
                  return EnumDirection.EAST.a();
               }

               if(p_a_2_ == EnumDirection.WEST.a()) {
                  return EnumDirection.NORTH.a();
               }

               if(p_a_2_ == EnumDirection.EAST.a()) {
                  return EnumDirection.SOUTH.a();
               }
            } else if(this.m == EnumDirection.EAST) {
               if(p_a_2_ == EnumDirection.NORTH.a()) {
                  return EnumDirection.EAST.a();
               }

               if(p_a_2_ == EnumDirection.SOUTH.a()) {
                  return EnumDirection.WEST.a();
               }

               if(p_a_2_ == EnumDirection.WEST.a()) {
                  return EnumDirection.NORTH.a();
               }

               if(p_a_2_ == EnumDirection.EAST.a()) {
                  return EnumDirection.SOUTH.a();
               }
            }
         } else if(p_a_1_ == Blocks.STONE_BUTTON) {
            if(this.m == EnumDirection.SOUTH) {
               if(p_a_2_ == 3) {
                  return 4;
               }

               if(p_a_2_ == 4) {
                  return 3;
               }
            } else if(this.m == EnumDirection.WEST) {
               if(p_a_2_ == 3) {
                  return 1;
               }

               if(p_a_2_ == 4) {
                  return 2;
               }

               if(p_a_2_ == 2) {
                  return 3;
               }

               if(p_a_2_ == 1) {
                  return 4;
               }
            } else if(this.m == EnumDirection.EAST) {
               if(p_a_2_ == 3) {
                  return 2;
               }

               if(p_a_2_ == 4) {
                  return 1;
               }

               if(p_a_2_ == 2) {
                  return 3;
               }

               if(p_a_2_ == 1) {
                  return 4;
               }
            }
         } else if(p_a_1_ != Blocks.TRIPWIRE_HOOK && !(p_a_1_ instanceof BlockDirectional)) {
            if(p_a_1_ == Blocks.PISTON || p_a_1_ == Blocks.STICKY_PISTON || p_a_1_ == Blocks.LEVER || p_a_1_ == Blocks.DISPENSER) {
               if(this.m == EnumDirection.SOUTH) {
                  if(p_a_2_ == EnumDirection.NORTH.a() || p_a_2_ == EnumDirection.SOUTH.a()) {
                     return EnumDirection.fromType1(p_a_2_).opposite().a();
                  }
               } else if(this.m == EnumDirection.WEST) {
                  if(p_a_2_ == EnumDirection.NORTH.a()) {
                     return EnumDirection.WEST.a();
                  }

                  if(p_a_2_ == EnumDirection.SOUTH.a()) {
                     return EnumDirection.EAST.a();
                  }

                  if(p_a_2_ == EnumDirection.WEST.a()) {
                     return EnumDirection.NORTH.a();
                  }

                  if(p_a_2_ == EnumDirection.EAST.a()) {
                     return EnumDirection.SOUTH.a();
                  }
               } else if(this.m == EnumDirection.EAST) {
                  if(p_a_2_ == EnumDirection.NORTH.a()) {
                     return EnumDirection.EAST.a();
                  }

                  if(p_a_2_ == EnumDirection.SOUTH.a()) {
                     return EnumDirection.WEST.a();
                  }

                  if(p_a_2_ == EnumDirection.WEST.a()) {
                     return EnumDirection.NORTH.a();
                  }

                  if(p_a_2_ == EnumDirection.EAST.a()) {
                     return EnumDirection.SOUTH.a();
                  }
               }
            }
         } else {
            EnumDirection enumdirection = EnumDirection.fromType2(p_a_2_);
            if(this.m == EnumDirection.SOUTH) {
               if(enumdirection == EnumDirection.SOUTH || enumdirection == EnumDirection.NORTH) {
                  return enumdirection.opposite().b();
               }
            } else if(this.m == EnumDirection.WEST) {
               if(enumdirection == EnumDirection.NORTH) {
                  return EnumDirection.WEST.b();
               }

               if(enumdirection == EnumDirection.SOUTH) {
                  return EnumDirection.EAST.b();
               }

               if(enumdirection == EnumDirection.WEST) {
                  return EnumDirection.NORTH.b();
               }

               if(enumdirection == EnumDirection.EAST) {
                  return EnumDirection.SOUTH.b();
               }
            } else if(this.m == EnumDirection.EAST) {
               if(enumdirection == EnumDirection.NORTH) {
                  return EnumDirection.EAST.b();
               }

               if(enumdirection == EnumDirection.SOUTH) {
                  return EnumDirection.WEST.b();
               }

               if(enumdirection == EnumDirection.WEST) {
                  return EnumDirection.NORTH.b();
               }

               if(enumdirection == EnumDirection.EAST) {
                  return EnumDirection.SOUTH.b();
               }
            }
         }
      } else if(this.m == EnumDirection.SOUTH) {
         if(p_a_2_ == 2) {
            return 3;
         }

         if(p_a_2_ == 3) {
            return 2;
         }
      } else if(this.m == EnumDirection.WEST) {
         if(p_a_2_ == 0) {
            return 2;
         }

         if(p_a_2_ == 1) {
            return 3;
         }

         if(p_a_2_ == 2) {
            return 0;
         }

         if(p_a_2_ == 3) {
            return 1;
         }
      } else if(this.m == EnumDirection.EAST) {
         if(p_a_2_ == 0) {
            return 2;
         }

         if(p_a_2_ == 1) {
            return 3;
         }

         if(p_a_2_ == 2) {
            return 1;
         }

         if(p_a_2_ == 3) {
            return 0;
         }
      }

      return p_a_2_;
   }

   protected void a(World p_a_1_, IBlockData p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, StructureBoundingBox p_a_6_) {
      BlockPosition blockposition = new BlockPosition(this.a(p_a_3_, p_a_5_), this.d(p_a_4_), this.b(p_a_3_, p_a_5_));
      if(p_a_6_.b((BaseBlockPosition)blockposition)) {
         p_a_1_.setTypeAndData(blockposition, p_a_2_, 2);
      }
   }

   protected IBlockData a(World p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, StructureBoundingBox p_a_5_) {
      int i = this.a(p_a_2_, p_a_4_);
      int j = this.d(p_a_3_);
      int k = this.b(p_a_2_, p_a_4_);
      BlockPosition blockposition = new BlockPosition(i, j, k);
      return !p_a_5_.b((BaseBlockPosition)blockposition)?Blocks.AIR.getBlockData():p_a_1_.getType(blockposition);
   }

   protected void a(World p_a_1_, StructureBoundingBox p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, int p_a_8_) {
      for(int i = p_a_4_; i <= p_a_7_; ++i) {
         for(int j = p_a_3_; j <= p_a_6_; ++j) {
            for(int k = p_a_5_; k <= p_a_8_; ++k) {
               this.a(p_a_1_, Blocks.AIR.getBlockData(), j, i, k, p_a_2_);
            }
         }
      }

   }

   protected void a(World p_a_1_, StructureBoundingBox p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, int p_a_8_, IBlockData p_a_9_, IBlockData p_a_10_, boolean p_a_11_) {
      for(int i = p_a_4_; i <= p_a_7_; ++i) {
         for(int j = p_a_3_; j <= p_a_6_; ++j) {
            for(int k = p_a_5_; k <= p_a_8_; ++k) {
               if(!p_a_11_ || this.a(p_a_1_, j, i, k, p_a_2_).getBlock().getMaterial() != Material.AIR) {
                  if(i != p_a_4_ && i != p_a_7_ && j != p_a_3_ && j != p_a_6_ && k != p_a_5_ && k != p_a_8_) {
                     this.a(p_a_1_, p_a_10_, j, i, k, p_a_2_);
                  } else {
                     this.a(p_a_1_, p_a_9_, j, i, k, p_a_2_);
                  }
               }
            }
         }
      }

   }

   protected void a(World p_a_1_, StructureBoundingBox p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, int p_a_8_, boolean p_a_9_, Random p_a_10_, StructurePiece.StructurePieceBlockSelector p_a_11_) {
      for(int i = p_a_4_; i <= p_a_7_; ++i) {
         for(int j = p_a_3_; j <= p_a_6_; ++j) {
            for(int k = p_a_5_; k <= p_a_8_; ++k) {
               if(!p_a_9_ || this.a(p_a_1_, j, i, k, p_a_2_).getBlock().getMaterial() != Material.AIR) {
                  p_a_11_.a(p_a_10_, j, i, k, i == p_a_4_ || i == p_a_7_ || j == p_a_3_ || j == p_a_6_ || k == p_a_5_ || k == p_a_8_);
                  this.a(p_a_1_, p_a_11_.a(), j, i, k, p_a_2_);
               }
            }
         }
      }

   }

   protected void a(World p_a_1_, StructureBoundingBox p_a_2_, Random p_a_3_, float p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, int p_a_8_, int p_a_9_, int p_a_10_, IBlockData p_a_11_, IBlockData p_a_12_, boolean p_a_13_) {
      for(int i = p_a_6_; i <= p_a_9_; ++i) {
         for(int j = p_a_5_; j <= p_a_8_; ++j) {
            for(int k = p_a_7_; k <= p_a_10_; ++k) {
               if(p_a_3_.nextFloat() <= p_a_4_ && (!p_a_13_ || this.a(p_a_1_, j, i, k, p_a_2_).getBlock().getMaterial() != Material.AIR)) {
                  if(i != p_a_6_ && i != p_a_9_ && j != p_a_5_ && j != p_a_8_ && k != p_a_7_ && k != p_a_10_) {
                     this.a(p_a_1_, p_a_12_, j, i, k, p_a_2_);
                  } else {
                     this.a(p_a_1_, p_a_11_, j, i, k, p_a_2_);
                  }
               }
            }
         }
      }

   }

   protected void a(World p_a_1_, StructureBoundingBox p_a_2_, Random p_a_3_, float p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, IBlockData p_a_8_) {
      if(p_a_3_.nextFloat() < p_a_4_) {
         this.a(p_a_1_, p_a_8_, p_a_5_, p_a_6_, p_a_7_, p_a_2_);
      }

   }

   protected void a(World p_a_1_, StructureBoundingBox p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, int p_a_8_, IBlockData p_a_9_, boolean p_a_10_) {
      float f = (float)(p_a_6_ - p_a_3_ + 1);
      float f1 = (float)(p_a_7_ - p_a_4_ + 1);
      float f2 = (float)(p_a_8_ - p_a_5_ + 1);
      float f3 = (float)p_a_3_ + f / 2.0F;
      float f4 = (float)p_a_5_ + f2 / 2.0F;

      for(int i = p_a_4_; i <= p_a_7_; ++i) {
         float f5 = (float)(i - p_a_4_) / f1;

         for(int j = p_a_3_; j <= p_a_6_; ++j) {
            float f6 = ((float)j - f3) / (f * 0.5F);

            for(int k = p_a_5_; k <= p_a_8_; ++k) {
               float f7 = ((float)k - f4) / (f2 * 0.5F);
               if(!p_a_10_ || this.a(p_a_1_, j, i, k, p_a_2_).getBlock().getMaterial() != Material.AIR) {
                  float f8 = f6 * f6 + f5 * f5 + f7 * f7;
                  if(f8 <= 1.05F) {
                     this.a(p_a_1_, p_a_9_, j, i, k, p_a_2_);
                  }
               }
            }
         }
      }

   }

   protected void b(World p_b_1_, int p_b_2_, int p_b_3_, int p_b_4_, StructureBoundingBox p_b_5_) {
      BlockPosition blockposition = new BlockPosition(this.a(p_b_2_, p_b_4_), this.d(p_b_3_), this.b(p_b_2_, p_b_4_));
      if(p_b_5_.b((BaseBlockPosition)blockposition)) {
         while(!p_b_1_.isEmpty(blockposition) && blockposition.getY() < 255) {
            p_b_1_.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 2);
            blockposition = blockposition.up();
         }

      }
   }

   protected void b(World p_b_1_, IBlockData p_b_2_, int p_b_3_, int p_b_4_, int p_b_5_, StructureBoundingBox p_b_6_) {
      int i = this.a(p_b_3_, p_b_5_);
      int j = this.d(p_b_4_);
      int k = this.b(p_b_3_, p_b_5_);
      if(p_b_6_.b((BaseBlockPosition)(new BlockPosition(i, j, k)))) {
         while((p_b_1_.isEmpty(new BlockPosition(i, j, k)) || p_b_1_.getType(new BlockPosition(i, j, k)).getBlock().getMaterial().isLiquid()) && j > 1) {
            p_b_1_.setTypeAndData(new BlockPosition(i, j, k), p_b_2_, 2);
            --j;
         }

      }
   }

   protected boolean a(World p_a_1_, StructureBoundingBox p_a_2_, Random p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, List<StructurePieceTreasure> p_a_7_, int p_a_8_) {
      BlockPosition blockposition = new BlockPosition(this.a(p_a_4_, p_a_6_), this.d(p_a_5_), this.b(p_a_4_, p_a_6_));
      if(p_a_2_.b((BaseBlockPosition)blockposition) && p_a_1_.getType(blockposition).getBlock() != Blocks.CHEST) {
         IBlockData iblockdata = Blocks.CHEST.getBlockData();
         p_a_1_.setTypeAndData(blockposition, Blocks.CHEST.f(p_a_1_, blockposition, iblockdata), 2);
         TileEntity tileentity = p_a_1_.getTileEntity(blockposition);
         if(tileentity instanceof TileEntityChest) {
            StructurePieceTreasure.a(p_a_3_, p_a_7_, (IInventory)((TileEntityChest)tileentity), p_a_8_);
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean a(World p_a_1_, StructureBoundingBox p_a_2_, Random p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, List<StructurePieceTreasure> p_a_8_, int p_a_9_) {
      BlockPosition blockposition = new BlockPosition(this.a(p_a_4_, p_a_6_), this.d(p_a_5_), this.b(p_a_4_, p_a_6_));
      if(p_a_2_.b((BaseBlockPosition)blockposition) && p_a_1_.getType(blockposition).getBlock() != Blocks.DISPENSER) {
         p_a_1_.setTypeAndData(blockposition, Blocks.DISPENSER.fromLegacyData(this.a(Blocks.DISPENSER, p_a_7_)), 2);
         TileEntity tileentity = p_a_1_.getTileEntity(blockposition);
         if(tileentity instanceof TileEntityDispenser) {
            StructurePieceTreasure.a(p_a_3_, p_a_8_, (TileEntityDispenser)tileentity, p_a_9_);
         }

         return true;
      } else {
         return false;
      }
   }

   protected void a(World p_a_1_, StructureBoundingBox p_a_2_, Random p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, EnumDirection p_a_7_) {
      BlockPosition blockposition = new BlockPosition(this.a(p_a_4_, p_a_6_), this.d(p_a_5_), this.b(p_a_4_, p_a_6_));
      if(p_a_2_.b((BaseBlockPosition)blockposition)) {
         ItemDoor.a(p_a_1_, blockposition, p_a_7_.f(), Blocks.WOODEN_DOOR);
      }

   }

   public void a(int p_a_1_, int p_a_2_, int p_a_3_) {
      this.l.a(p_a_1_, p_a_2_, p_a_3_);
   }

   public abstract static class StructurePieceBlockSelector {
      protected IBlockData a = Blocks.AIR.getBlockData();

      public abstract void a(Random var1, int var2, int var3, int var4, boolean var5);

      public IBlockData a() {
         return this.a;
      }
   }
}
