package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.BaseBlockPosition;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockSandStone;
import net.minecraft.server.v1_8_R3.BlockStairs;
import net.minecraft.server.v1_8_R3.BlockTorch;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.GroupDataEntity;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.StructureBoundingBox;
import net.minecraft.server.v1_8_R3.StructurePiece;
import net.minecraft.server.v1_8_R3.StructurePieceTreasure;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldChunkManager;
import net.minecraft.server.v1_8_R3.WorldGenFactory;
import net.minecraft.server.v1_8_R3.WorldGenVillage;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class WorldGenVillagePieces {
   public static void a() {
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageLibrary.class, "ViBH");
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageFarm2.class, "ViDF");
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageFarm.class, "ViF");
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageLight.class, "ViL");
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageButcher.class, "ViPH");
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageHouse.class, "ViSH");
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageHut.class, "ViSmH");
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageTemple.class, "ViST");
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageBlacksmith.class, "ViS");
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageStartPiece.class, "ViStart");
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageRoad.class, "ViSR");
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageHouse2.class, "ViTRH");
      WorldGenFactory.a(WorldGenVillagePieces.WorldGenVillageWell.class, "ViW");
   }

   public static List<WorldGenVillagePieces.WorldGenVillagePieceWeight> a(Random p_a_0_, int p_a_1_) {
      ArrayList arraylist = Lists.newArrayList();
      arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageHouse.class, 4, MathHelper.nextInt(p_a_0_, 2 + p_a_1_, 4 + p_a_1_ * 2)));
      arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageTemple.class, 20, MathHelper.nextInt(p_a_0_, 0 + p_a_1_, 1 + p_a_1_)));
      arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageLibrary.class, 20, MathHelper.nextInt(p_a_0_, 0 + p_a_1_, 2 + p_a_1_)));
      arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageHut.class, 3, MathHelper.nextInt(p_a_0_, 2 + p_a_1_, 5 + p_a_1_ * 3)));
      arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageButcher.class, 15, MathHelper.nextInt(p_a_0_, 0 + p_a_1_, 2 + p_a_1_)));
      arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageFarm2.class, 3, MathHelper.nextInt(p_a_0_, 1 + p_a_1_, 4 + p_a_1_)));
      arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageFarm.class, 3, MathHelper.nextInt(p_a_0_, 2 + p_a_1_, 4 + p_a_1_ * 2)));
      arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageBlacksmith.class, 15, MathHelper.nextInt(p_a_0_, 0, 1 + p_a_1_)));
      arraylist.add(new WorldGenVillagePieces.WorldGenVillagePieceWeight(WorldGenVillagePieces.WorldGenVillageHouse2.class, 8, MathHelper.nextInt(p_a_0_, 0 + p_a_1_, 3 + p_a_1_ * 2)));
      Iterator iterator = arraylist.iterator();

      while(iterator.hasNext()) {
         if(((WorldGenVillagePieces.WorldGenVillagePieceWeight)iterator.next()).d == 0) {
            iterator.remove();
         }
      }

      return arraylist;
   }

   private static int a(List<WorldGenVillagePieces.WorldGenVillagePieceWeight> p_a_0_) {
      boolean flag = false;
      int i = 0;

      for(WorldGenVillagePieces.WorldGenVillagePieceWeight worldgenvillagepieces$worldgenvillagepieceweight : p_a_0_) {
         if(worldgenvillagepieces$worldgenvillagepieceweight.d > 0 && worldgenvillagepieces$worldgenvillagepieceweight.c < worldgenvillagepieces$worldgenvillagepieceweight.d) {
            flag = true;
         }

         i += worldgenvillagepieces$worldgenvillagepieceweight.b;
      }

      return flag?i:-1;
   }

   private static WorldGenVillagePieces.WorldGenVillagePiece a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_0_, WorldGenVillagePieces.WorldGenVillagePieceWeight p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, EnumDirection p_a_7_, int p_a_8_) {
      Class oclass = p_a_1_.a;
      Object object = null;
      if(oclass == WorldGenVillagePieces.WorldGenVillageHouse.class) {
         object = WorldGenVillagePieces.WorldGenVillageHouse.a(p_a_0_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_);
      } else if(oclass == WorldGenVillagePieces.WorldGenVillageTemple.class) {
         object = WorldGenVillagePieces.WorldGenVillageTemple.a(p_a_0_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_);
      } else if(oclass == WorldGenVillagePieces.WorldGenVillageLibrary.class) {
         object = WorldGenVillagePieces.WorldGenVillageLibrary.a(p_a_0_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_);
      } else if(oclass == WorldGenVillagePieces.WorldGenVillageHut.class) {
         object = WorldGenVillagePieces.WorldGenVillageHut.a(p_a_0_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_);
      } else if(oclass == WorldGenVillagePieces.WorldGenVillageButcher.class) {
         object = WorldGenVillagePieces.WorldGenVillageButcher.a(p_a_0_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_);
      } else if(oclass == WorldGenVillagePieces.WorldGenVillageFarm2.class) {
         object = WorldGenVillagePieces.WorldGenVillageFarm2.a(p_a_0_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_);
      } else if(oclass == WorldGenVillagePieces.WorldGenVillageFarm.class) {
         object = WorldGenVillagePieces.WorldGenVillageFarm.a(p_a_0_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_);
      } else if(oclass == WorldGenVillagePieces.WorldGenVillageBlacksmith.class) {
         object = WorldGenVillagePieces.WorldGenVillageBlacksmith.a(p_a_0_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_);
      } else if(oclass == WorldGenVillagePieces.WorldGenVillageHouse2.class) {
         object = WorldGenVillagePieces.WorldGenVillageHouse2.a(p_a_0_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_);
      }

      return (WorldGenVillagePieces.WorldGenVillagePiece)object;
   }

   private static WorldGenVillagePieces.WorldGenVillagePiece c(WorldGenVillagePieces.WorldGenVillageStartPiece p_c_0_, List<StructurePiece> p_c_1_, Random p_c_2_, int p_c_3_, int p_c_4_, int p_c_5_, EnumDirection p_c_6_, int p_c_7_) {
      int i = a(p_c_0_.e);
      if(i <= 0) {
         return null;
      } else {
         int j = 0;

         while(j < 5) {
            ++j;
            int k = p_c_2_.nextInt(i);

            for(WorldGenVillagePieces.WorldGenVillagePieceWeight worldgenvillagepieces$worldgenvillagepieceweight : p_c_0_.e) {
               k -= worldgenvillagepieces$worldgenvillagepieceweight.b;
               if(k < 0) {
                  if(!worldgenvillagepieces$worldgenvillagepieceweight.a(p_c_7_) || worldgenvillagepieces$worldgenvillagepieceweight == p_c_0_.d && p_c_0_.e.size() > 1) {
                     break;
                  }

                  WorldGenVillagePieces.WorldGenVillagePiece worldgenvillagepieces$worldgenvillagepiece = a(p_c_0_, worldgenvillagepieces$worldgenvillagepieceweight, p_c_1_, p_c_2_, p_c_3_, p_c_4_, p_c_5_, p_c_6_, p_c_7_);
                  if(worldgenvillagepieces$worldgenvillagepiece != null) {
                     ++worldgenvillagepieces$worldgenvillagepieceweight.c;
                     p_c_0_.d = worldgenvillagepieces$worldgenvillagepieceweight;
                     if(!worldgenvillagepieces$worldgenvillagepieceweight.a()) {
                        p_c_0_.e.remove(worldgenvillagepieces$worldgenvillagepieceweight);
                     }

                     return worldgenvillagepieces$worldgenvillagepiece;
                  }
               }
            }
         }

         StructureBoundingBox structureboundingbox = WorldGenVillagePieces.WorldGenVillageLight.a(p_c_0_, p_c_1_, p_c_2_, p_c_3_, p_c_4_, p_c_5_, p_c_6_);
         if(structureboundingbox != null) {
            return new WorldGenVillagePieces.WorldGenVillageLight(p_c_0_, p_c_7_, p_c_2_, structureboundingbox, p_c_6_);
         } else {
            return null;
         }
      }
   }

   private static StructurePiece d(WorldGenVillagePieces.WorldGenVillageStartPiece p_d_0_, List<StructurePiece> p_d_1_, Random p_d_2_, int p_d_3_, int p_d_4_, int p_d_5_, EnumDirection p_d_6_, int p_d_7_) {
      if(p_d_7_ > 50) {
         return null;
      } else if(Math.abs(p_d_3_ - p_d_0_.c().a) <= 112 && Math.abs(p_d_5_ - p_d_0_.c().c) <= 112) {
         WorldGenVillagePieces.WorldGenVillagePiece worldgenvillagepieces$worldgenvillagepiece = c(p_d_0_, p_d_1_, p_d_2_, p_d_3_, p_d_4_, p_d_5_, p_d_6_, p_d_7_ + 1);
         if(worldgenvillagepieces$worldgenvillagepiece != null) {
            int i = (worldgenvillagepieces$worldgenvillagepiece.l.a + worldgenvillagepieces$worldgenvillagepiece.l.d) / 2;
            int j = (worldgenvillagepieces$worldgenvillagepiece.l.c + worldgenvillagepieces$worldgenvillagepiece.l.f) / 2;
            int k = worldgenvillagepieces$worldgenvillagepiece.l.d - worldgenvillagepieces$worldgenvillagepiece.l.a;
            int l = worldgenvillagepieces$worldgenvillagepiece.l.f - worldgenvillagepieces$worldgenvillagepiece.l.c;
            int i1 = k > l?k:l;
            if(p_d_0_.e().a(i, j, i1 / 2 + 4, WorldGenVillage.d)) {
               p_d_1_.add(worldgenvillagepieces$worldgenvillagepiece);
               p_d_0_.f.add(worldgenvillagepieces$worldgenvillagepiece);
               return worldgenvillagepieces$worldgenvillagepiece;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static StructurePiece e(WorldGenVillagePieces.WorldGenVillageStartPiece p_e_0_, List<StructurePiece> p_e_1_, Random p_e_2_, int p_e_3_, int p_e_4_, int p_e_5_, EnumDirection p_e_6_, int p_e_7_) {
      if(p_e_7_ > 3 + p_e_0_.c) {
         return null;
      } else if(Math.abs(p_e_3_ - p_e_0_.c().a) <= 112 && Math.abs(p_e_5_ - p_e_0_.c().c) <= 112) {
         StructureBoundingBox structureboundingbox = WorldGenVillagePieces.WorldGenVillageRoad.a(p_e_0_, p_e_1_, p_e_2_, p_e_3_, p_e_4_, p_e_5_, p_e_6_);
         if(structureboundingbox != null && structureboundingbox.b > 10) {
            WorldGenVillagePieces.WorldGenVillageRoad worldgenvillagepieces$worldgenvillageroad = new WorldGenVillagePieces.WorldGenVillageRoad(p_e_0_, p_e_7_, p_e_2_, structureboundingbox, p_e_6_);
            int i = (worldgenvillagepieces$worldgenvillageroad.l.a + worldgenvillagepieces$worldgenvillageroad.l.d) / 2;
            int j = (worldgenvillagepieces$worldgenvillageroad.l.c + worldgenvillagepieces$worldgenvillageroad.l.f) / 2;
            int k = worldgenvillagepieces$worldgenvillageroad.l.d - worldgenvillagepieces$worldgenvillageroad.l.a;
            int l = worldgenvillagepieces$worldgenvillageroad.l.f - worldgenvillagepieces$worldgenvillageroad.l.c;
            int i1 = k > l?k:l;
            if(p_e_0_.e().a(i, j, i1 / 2 + 4, WorldGenVillage.d)) {
               p_e_1_.add(worldgenvillagepieces$worldgenvillageroad);
               p_e_0_.g.add(worldgenvillagepieces$worldgenvillageroad);
               return worldgenvillagepieces$worldgenvillageroad;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   static class SyntheticClass_1 {
      static final int[] a = new int[EnumDirection.values().length];

      static {
         try {
            a[EnumDirection.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            a[EnumDirection.SOUTH.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            a[EnumDirection.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
            ;
         }

         try {
            a[EnumDirection.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var0) {
            ;
         }

      }
   }

   public static class WorldGenVillageBlacksmith extends WorldGenVillagePieces.WorldGenVillagePiece {
      private static final List<StructurePieceTreasure> a = Lists.newArrayList(new StructurePieceTreasure[]{new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 5), new StructurePieceTreasure(Items.BREAD, 0, 1, 3, 15), new StructurePieceTreasure(Items.APPLE, 0, 1, 3, 15), new StructurePieceTreasure(Items.IRON_PICKAXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_SWORD, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_CHESTPLATE, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_HELMET, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_LEGGINGS, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_BOOTS, 0, 1, 1, 5), new StructurePieceTreasure(Item.getItemOf(Blocks.OBSIDIAN), 0, 3, 7, 5), new StructurePieceTreasure(Item.getItemOf(Blocks.SAPLING), 0, 3, 7, 5), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 3), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 1)});
      private boolean b;

      public WorldGenVillageBlacksmith() {
      }

      public WorldGenVillageBlacksmith(WorldGenVillagePieces.WorldGenVillageStartPiece p_i97_1_, int p_i97_2_, Random p_i97_3_, StructureBoundingBox p_i97_4_, EnumDirection p_i97_5_) {
         super(p_i97_1_, p_i97_2_);
         this.m = p_i97_5_;
         this.l = p_i97_4_;
      }

      public static WorldGenVillagePieces.WorldGenVillageBlacksmith a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_0_, List<StructurePiece> p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_, int p_a_7_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_3_, p_a_4_, p_a_5_, 0, 0, 0, 10, 6, 7, p_a_6_);
         return a(structureboundingbox) && StructurePiece.a(p_a_1_, structureboundingbox) == null?new WorldGenVillagePieces.WorldGenVillageBlacksmith(p_a_0_, p_a_7_, p_a_2_, structureboundingbox, p_a_6_):null;
      }

      protected void a(NBTTagCompound p_a_1_) {
         super.a(p_a_1_);
         p_a_1_.setBoolean("Chest", this.b);
      }

      protected void b(NBTTagCompound p_b_1_) {
         super.b(p_b_1_);
         this.b = p_b_1_.getBoolean("Chest");
      }

      public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_) {
         if(this.h < 0) {
            this.h = this.b(p_a_1_, p_a_3_);
            if(this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 6 - 1, 0);
         }

         this.a(p_a_1_, p_a_3_, 0, 1, 0, 9, 4, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 0, 0, 9, 0, 6, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 4, 0, 9, 4, 6, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 5, 0, 9, 5, 6, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 5, 1, 8, 5, 5, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 1, 0, 2, 3, 0, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 1, 0, 0, 4, 0, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 3, 1, 0, 3, 4, 0, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 1, 6, 0, 4, 6, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 3, 3, 1, p_a_3_);
         this.a(p_a_1_, p_a_3_, 3, 1, 2, 3, 3, 2, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 4, 1, 3, 5, 3, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 1, 1, 0, 3, 5, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 1, 6, 5, 3, 6, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 5, 1, 0, 5, 3, 0, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 9, 1, 0, 9, 3, 0, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 6, 1, 4, 9, 4, 6, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, Blocks.FLOWING_LAVA.getBlockData(), 7, 1, 5, p_a_3_);
         this.a(p_a_1_, Blocks.FLOWING_LAVA.getBlockData(), 8, 1, 5, p_a_3_);
         this.a(p_a_1_, Blocks.IRON_BARS.getBlockData(), 9, 2, 5, p_a_3_);
         this.a(p_a_1_, Blocks.IRON_BARS.getBlockData(), 9, 2, 4, p_a_3_);
         this.a(p_a_1_, p_a_3_, 7, 2, 4, 8, 2, 5, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 6, 1, 3, p_a_3_);
         this.a(p_a_1_, Blocks.FURNACE.getBlockData(), 6, 2, 3, p_a_3_);
         this.a(p_a_1_, Blocks.FURNACE.getBlockData(), 6, 3, 3, p_a_3_);
         this.a(p_a_1_, Blocks.DOUBLE_STONE_SLAB.getBlockData(), 8, 1, 1, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 2, 4, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 2, 2, 6, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 4, 2, 6, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 2, 1, 4, p_a_3_);
         this.a(p_a_1_, Blocks.WOODEN_PRESSURE_PLATE.getBlockData(), 2, 2, 4, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 1, 1, 5, p_a_3_);
         this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(this.a(Blocks.OAK_STAIRS, 3)), 2, 1, 5, p_a_3_);
         this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(this.a(Blocks.OAK_STAIRS, 1)), 1, 1, 4, p_a_3_);
         if(!this.b && p_a_3_.b((BaseBlockPosition)(new BlockPosition(this.a(5, 5), this.d(1), this.b(5, 5))))) {
            this.b = true;
            this.a(p_a_1_, p_a_3_, p_a_2_, 5, 1, 5, a, 3 + p_a_2_.nextInt(6));
         }

         for(int i = 6; i <= 8; ++i) {
            if(this.a(p_a_1_, i, 0, -1, p_a_3_).getBlock().getMaterial() == Material.AIR && this.a(p_a_1_, i, -1, -1, p_a_3_).getBlock().getMaterial() != Material.AIR) {
               this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(this.a(Blocks.STONE_STAIRS, 3)), i, 0, -1, p_a_3_);
            }
         }

         for(int k = 0; k < 7; ++k) {
            for(int j = 0; j < 10; ++j) {
               this.b(p_a_1_, j, 6, k, p_a_3_);
               this.b(p_a_1_, Blocks.COBBLESTONE.getBlockData(), j, -1, k, p_a_3_);
            }
         }

         this.a(p_a_1_, p_a_3_, 7, 1, 1, 1);
         return true;
      }

      protected int c(int p_c_1_, int p_c_2_) {
         return 3;
      }
   }

   public static class WorldGenVillageButcher extends WorldGenVillagePieces.WorldGenVillagePiece {
      public WorldGenVillageButcher() {
      }

      public WorldGenVillageButcher(WorldGenVillagePieces.WorldGenVillageStartPiece p_i53_1_, int p_i53_2_, Random p_i53_3_, StructureBoundingBox p_i53_4_, EnumDirection p_i53_5_) {
         super(p_i53_1_, p_i53_2_);
         this.m = p_i53_5_;
         this.l = p_i53_4_;
      }

      public static WorldGenVillagePieces.WorldGenVillageButcher a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_0_, List<StructurePiece> p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_, int p_a_7_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_3_, p_a_4_, p_a_5_, 0, 0, 0, 9, 7, 11, p_a_6_);
         return a(structureboundingbox) && StructurePiece.a(p_a_1_, structureboundingbox) == null?new WorldGenVillagePieces.WorldGenVillageButcher(p_a_0_, p_a_7_, p_a_2_, structureboundingbox, p_a_6_):null;
      }

      public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_) {
         if(this.h < 0) {
            this.h = this.b(p_a_1_, p_a_3_);
            if(this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 7 - 1, 0);
         }

         this.a(p_a_1_, p_a_3_, 1, 1, 1, 7, 4, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 2, 1, 6, 8, 4, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 2, 0, 6, 8, 0, 10, Blocks.DIRT.getBlockData(), Blocks.DIRT.getBlockData(), false);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 6, 0, 6, p_a_3_);
         this.a(p_a_1_, p_a_3_, 2, 1, 6, 2, 1, 10, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 8, 1, 6, 8, 1, 10, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 3, 1, 10, 7, 1, 10, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 1, 7, 0, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 0, 0, 0, 3, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 8, 0, 0, 8, 3, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 0, 7, 1, 0, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 5, 7, 1, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 2, 0, 7, 3, 0, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 2, 5, 7, 3, 5, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 4, 1, 8, 4, 1, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 4, 4, 8, 4, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 5, 2, 8, 5, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 0, 4, 2, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 0, 4, 3, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 8, 4, 2, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 8, 4, 3, p_a_3_);
         int i = this.a(Blocks.OAK_STAIRS, 3);
         int j = this.a(Blocks.OAK_STAIRS, 2);

         for(int k = -1; k <= 2; ++k) {
            for(int l = 0; l <= 8; ++l) {
               this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(i), l, 4 + k, k, p_a_3_);
               this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(j), l, 4 + k, 5 - k, p_a_3_);
            }
         }

         this.a(p_a_1_, Blocks.LOG.getBlockData(), 0, 2, 1, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 0, 2, 4, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 8, 2, 1, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 8, 2, 4, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 2, 3, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 8, 2, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 8, 2, 3, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 2, 2, 5, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 3, 2, 5, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 5, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 6, 2, 5, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 2, 1, 3, p_a_3_);
         this.a(p_a_1_, Blocks.WOODEN_PRESSURE_PLATE.getBlockData(), 2, 2, 3, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 1, 1, 4, p_a_3_);
         this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(this.a(Blocks.OAK_STAIRS, 3)), 2, 1, 4, p_a_3_);
         this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(this.a(Blocks.OAK_STAIRS, 1)), 1, 1, 3, p_a_3_);
         this.a(p_a_1_, p_a_3_, 5, 0, 1, 7, 0, 3, Blocks.DOUBLE_STONE_SLAB.getBlockData(), Blocks.DOUBLE_STONE_SLAB.getBlockData(), false);
         this.a(p_a_1_, Blocks.DOUBLE_STONE_SLAB.getBlockData(), 6, 1, 1, p_a_3_);
         this.a(p_a_1_, Blocks.DOUBLE_STONE_SLAB.getBlockData(), 6, 1, 2, p_a_3_);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 2, 1, 0, p_a_3_);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 2, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m), 2, 3, 1, p_a_3_);
         this.a(p_a_1_, p_a_3_, p_a_2_, 2, 1, 0, EnumDirection.fromType2(this.a(Blocks.WOODEN_DOOR, 1)));
         if(this.a(p_a_1_, 2, 0, -1, p_a_3_).getBlock().getMaterial() == Material.AIR && this.a(p_a_1_, 2, -1, -1, p_a_3_).getBlock().getMaterial() != Material.AIR) {
            this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(this.a(Blocks.STONE_STAIRS, 3)), 2, 0, -1, p_a_3_);
         }

         this.a(p_a_1_, Blocks.AIR.getBlockData(), 6, 1, 5, p_a_3_);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 6, 2, 5, p_a_3_);
         this.a(p_a_1_, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.opposite()), 6, 3, 4, p_a_3_);
         this.a(p_a_1_, p_a_3_, p_a_2_, 6, 1, 5, EnumDirection.fromType2(this.a(Blocks.WOODEN_DOOR, 1)));

         for(int i1 = 0; i1 < 5; ++i1) {
            for(int j1 = 0; j1 < 9; ++j1) {
               this.b(p_a_1_, j1, 7, i1, p_a_3_);
               this.b(p_a_1_, Blocks.COBBLESTONE.getBlockData(), j1, -1, i1, p_a_3_);
            }
         }

         this.a(p_a_1_, p_a_3_, 4, 1, 2, 2);
         return true;
      }

      protected int c(int p_c_1_, int p_c_2_) {
         return p_c_1_ == 0?4:super.c(p_c_1_, p_c_2_);
      }
   }

   public static class WorldGenVillageFarm extends WorldGenVillagePieces.WorldGenVillagePiece {
      private Block a;
      private Block b;

      public WorldGenVillageFarm() {
      }

      public WorldGenVillageFarm(WorldGenVillagePieces.WorldGenVillageStartPiece p_i430_1_, int p_i430_2_, Random p_i430_3_, StructureBoundingBox p_i430_4_, EnumDirection p_i430_5_) {
         super(p_i430_1_, p_i430_2_);
         this.m = p_i430_5_;
         this.l = p_i430_4_;
         this.a = this.a(p_i430_3_);
         this.b = this.a(p_i430_3_);
      }

      protected void a(NBTTagCompound p_a_1_) {
         super.a(p_a_1_);
         p_a_1_.setInt("CA", Block.REGISTRY.b(this.a));
         p_a_1_.setInt("CB", Block.REGISTRY.b(this.b));
      }

      protected void b(NBTTagCompound p_b_1_) {
         super.b(p_b_1_);
         this.a = Block.getById(p_b_1_.getInt("CA"));
         this.b = Block.getById(p_b_1_.getInt("CB"));
      }

      private Block a(Random p_a_1_) {
         switch(p_a_1_.nextInt(5)) {
         case 0:
            return Blocks.CARROTS;
         case 1:
            return Blocks.POTATOES;
         default:
            return Blocks.WHEAT;
         }
      }

      public static WorldGenVillagePieces.WorldGenVillageFarm a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_0_, List<StructurePiece> p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_, int p_a_7_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_3_, p_a_4_, p_a_5_, 0, 0, 0, 7, 4, 9, p_a_6_);
         return a((StructureBoundingBox)structureboundingbox) && StructurePiece.a(p_a_1_, structureboundingbox) == null?new WorldGenVillagePieces.WorldGenVillageFarm(p_a_0_, p_a_7_, p_a_2_, structureboundingbox, p_a_6_):null;
      }

      public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_) {
         if(this.h < 0) {
            this.h = this.b(p_a_1_, p_a_3_);
            if(this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 4 - 1, 0);
         }

         this.a(p_a_1_, p_a_3_, 0, 1, 0, 6, 4, 8, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 0, 0, 0, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 6, 0, 0, 6, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 0, 5, 0, 0, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 8, 5, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 3, 0, 1, 3, 0, 7, Blocks.WATER.getBlockData(), Blocks.WATER.getBlockData(), false);

         for(int i = 1; i <= 7; ++i) {
            this.a(p_a_1_, this.a.fromLegacyData(MathHelper.nextInt(p_a_2_, 2, 7)), 1, 1, i, p_a_3_);
            this.a(p_a_1_, this.a.fromLegacyData(MathHelper.nextInt(p_a_2_, 2, 7)), 2, 1, i, p_a_3_);
            this.a(p_a_1_, this.b.fromLegacyData(MathHelper.nextInt(p_a_2_, 2, 7)), 4, 1, i, p_a_3_);
            this.a(p_a_1_, this.b.fromLegacyData(MathHelper.nextInt(p_a_2_, 2, 7)), 5, 1, i, p_a_3_);
         }

         for(int k = 0; k < 9; ++k) {
            for(int j = 0; j < 7; ++j) {
               this.b(p_a_1_, j, 4, k, p_a_3_);
               this.b(p_a_1_, Blocks.DIRT.getBlockData(), j, -1, k, p_a_3_);
            }
         }

         return true;
      }
   }

   public static class WorldGenVillageFarm2 extends WorldGenVillagePieces.WorldGenVillagePiece {
      private Block a;
      private Block b;
      private Block c;
      private Block d;

      public WorldGenVillageFarm2() {
      }

      public WorldGenVillageFarm2(WorldGenVillagePieces.WorldGenVillageStartPiece p_i340_1_, int p_i340_2_, Random p_i340_3_, StructureBoundingBox p_i340_4_, EnumDirection p_i340_5_) {
         super(p_i340_1_, p_i340_2_);
         this.m = p_i340_5_;
         this.l = p_i340_4_;
         this.a = this.a(p_i340_3_);
         this.b = this.a(p_i340_3_);
         this.c = this.a(p_i340_3_);
         this.d = this.a(p_i340_3_);
      }

      protected void a(NBTTagCompound p_a_1_) {
         super.a(p_a_1_);
         p_a_1_.setInt("CA", Block.REGISTRY.b(this.a));
         p_a_1_.setInt("CB", Block.REGISTRY.b(this.b));
         p_a_1_.setInt("CC", Block.REGISTRY.b(this.c));
         p_a_1_.setInt("CD", Block.REGISTRY.b(this.d));
      }

      protected void b(NBTTagCompound p_b_1_) {
         super.b(p_b_1_);
         this.a = Block.getById(p_b_1_.getInt("CA"));
         this.b = Block.getById(p_b_1_.getInt("CB"));
         this.c = Block.getById(p_b_1_.getInt("CC"));
         this.d = Block.getById(p_b_1_.getInt("CD"));
      }

      private Block a(Random p_a_1_) {
         switch(p_a_1_.nextInt(5)) {
         case 0:
            return Blocks.CARROTS;
         case 1:
            return Blocks.POTATOES;
         default:
            return Blocks.WHEAT;
         }
      }

      public static WorldGenVillagePieces.WorldGenVillageFarm2 a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_0_, List<StructurePiece> p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_, int p_a_7_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_3_, p_a_4_, p_a_5_, 0, 0, 0, 13, 4, 9, p_a_6_);
         return a((StructureBoundingBox)structureboundingbox) && StructurePiece.a(p_a_1_, structureboundingbox) == null?new WorldGenVillagePieces.WorldGenVillageFarm2(p_a_0_, p_a_7_, p_a_2_, structureboundingbox, p_a_6_):null;
      }

      public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_) {
         if(this.h < 0) {
            this.h = this.b(p_a_1_, p_a_3_);
            if(this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 4 - 1, 0);
         }

         this.a(p_a_1_, p_a_3_, 0, 1, 0, 12, 4, 8, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 7, 0, 1, 8, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 10, 0, 1, 11, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 0, 0, 0, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 6, 0, 0, 6, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 12, 0, 0, 12, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 0, 11, 0, 0, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 8, 11, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 3, 0, 1, 3, 0, 7, Blocks.WATER.getBlockData(), Blocks.WATER.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 9, 0, 1, 9, 0, 7, Blocks.WATER.getBlockData(), Blocks.WATER.getBlockData(), false);

         for(int i = 1; i <= 7; ++i) {
            this.a(p_a_1_, this.a.fromLegacyData(MathHelper.nextInt(p_a_2_, 2, 7)), 1, 1, i, p_a_3_);
            this.a(p_a_1_, this.a.fromLegacyData(MathHelper.nextInt(p_a_2_, 2, 7)), 2, 1, i, p_a_3_);
            this.a(p_a_1_, this.b.fromLegacyData(MathHelper.nextInt(p_a_2_, 2, 7)), 4, 1, i, p_a_3_);
            this.a(p_a_1_, this.b.fromLegacyData(MathHelper.nextInt(p_a_2_, 2, 7)), 5, 1, i, p_a_3_);
            this.a(p_a_1_, this.c.fromLegacyData(MathHelper.nextInt(p_a_2_, 2, 7)), 7, 1, i, p_a_3_);
            this.a(p_a_1_, this.c.fromLegacyData(MathHelper.nextInt(p_a_2_, 2, 7)), 8, 1, i, p_a_3_);
            this.a(p_a_1_, this.d.fromLegacyData(MathHelper.nextInt(p_a_2_, 2, 7)), 10, 1, i, p_a_3_);
            this.a(p_a_1_, this.d.fromLegacyData(MathHelper.nextInt(p_a_2_, 2, 7)), 11, 1, i, p_a_3_);
         }

         for(int k = 0; k < 9; ++k) {
            for(int j = 0; j < 13; ++j) {
               this.b(p_a_1_, j, 4, k, p_a_3_);
               this.b(p_a_1_, Blocks.DIRT.getBlockData(), j, -1, k, p_a_3_);
            }
         }

         return true;
      }
   }

   public static class WorldGenVillageHouse extends WorldGenVillagePieces.WorldGenVillagePiece {
      private boolean a;

      public WorldGenVillageHouse() {
      }

      public WorldGenVillageHouse(WorldGenVillagePieces.WorldGenVillageStartPiece p_i51_1_, int p_i51_2_, Random p_i51_3_, StructureBoundingBox p_i51_4_, EnumDirection p_i51_5_) {
         super(p_i51_1_, p_i51_2_);
         this.m = p_i51_5_;
         this.l = p_i51_4_;
         this.a = p_i51_3_.nextBoolean();
      }

      protected void a(NBTTagCompound p_a_1_) {
         super.a(p_a_1_);
         p_a_1_.setBoolean("Terrace", this.a);
      }

      protected void b(NBTTagCompound p_b_1_) {
         super.b(p_b_1_);
         this.a = p_b_1_.getBoolean("Terrace");
      }

      public static WorldGenVillagePieces.WorldGenVillageHouse a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_0_, List<StructurePiece> p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_, int p_a_7_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_3_, p_a_4_, p_a_5_, 0, 0, 0, 5, 6, 5, p_a_6_);
         return StructurePiece.a(p_a_1_, structureboundingbox) != null?null:new WorldGenVillagePieces.WorldGenVillageHouse(p_a_0_, p_a_7_, p_a_2_, structureboundingbox, p_a_6_);
      }

      public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_) {
         if(this.h < 0) {
            this.h = this.b(p_a_1_, p_a_3_);
            if(this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 6 - 1, 0);
         }

         this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 0, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 4, 0, 4, 4, 4, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 4, 1, 3, 4, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 0, 1, 0, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 0, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 0, 3, 0, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 4, 1, 0, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 4, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 4, 3, 0, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 0, 1, 4, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 0, 2, 4, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 0, 3, 4, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 4, 1, 4, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 4, 2, 4, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 4, 3, 4, p_a_3_);
         this.a(p_a_1_, p_a_3_, 0, 1, 1, 0, 3, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 4, 1, 1, 4, 3, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 1, 4, 3, 3, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 2, 2, 4, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 4, 2, 2, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 1, 1, 0, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 1, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 1, 3, 0, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 2, 3, 0, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 3, 3, 0, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 3, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 3, 1, 0, p_a_3_);
         if(this.a(p_a_1_, 2, 0, -1, p_a_3_).getBlock().getMaterial() == Material.AIR && this.a(p_a_1_, 2, -1, -1, p_a_3_).getBlock().getMaterial() != Material.AIR) {
            this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(this.a(Blocks.STONE_STAIRS, 3)), 2, 0, -1, p_a_3_);
         }

         this.a(p_a_1_, p_a_3_, 1, 1, 1, 3, 3, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         if(this.a) {
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 0, 5, 0, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 1, 5, 0, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 2, 5, 0, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 3, 5, 0, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 4, 5, 0, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 0, 5, 4, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 1, 5, 4, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 2, 5, 4, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 3, 5, 4, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 4, 5, 4, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 4, 5, 1, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 4, 5, 2, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 4, 5, 3, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 0, 5, 1, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 0, 5, 2, p_a_3_);
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), 0, 5, 3, p_a_3_);
         }

         if(this.a) {
            int i = this.a(Blocks.LADDER, 3);
            this.a(p_a_1_, Blocks.LADDER.fromLegacyData(i), 3, 1, 3, p_a_3_);
            this.a(p_a_1_, Blocks.LADDER.fromLegacyData(i), 3, 2, 3, p_a_3_);
            this.a(p_a_1_, Blocks.LADDER.fromLegacyData(i), 3, 3, 3, p_a_3_);
            this.a(p_a_1_, Blocks.LADDER.fromLegacyData(i), 3, 4, 3, p_a_3_);
         }

         this.a(p_a_1_, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m), 2, 3, 1, p_a_3_);

         for(int k = 0; k < 5; ++k) {
            for(int j = 0; j < 5; ++j) {
               this.b(p_a_1_, j, 6, k, p_a_3_);
               this.b(p_a_1_, Blocks.COBBLESTONE.getBlockData(), j, -1, k, p_a_3_);
            }
         }

         this.a(p_a_1_, p_a_3_, 1, 1, 2, 1);
         return true;
      }
   }

   public static class WorldGenVillageHouse2 extends WorldGenVillagePieces.WorldGenVillagePiece {
      public WorldGenVillageHouse2() {
      }

      public WorldGenVillageHouse2(WorldGenVillagePieces.WorldGenVillageStartPiece p_i178_1_, int p_i178_2_, Random p_i178_3_, StructureBoundingBox p_i178_4_, EnumDirection p_i178_5_) {
         super(p_i178_1_, p_i178_2_);
         this.m = p_i178_5_;
         this.l = p_i178_4_;
      }

      public static WorldGenVillagePieces.WorldGenVillageHouse2 a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_0_, List<StructurePiece> p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_, int p_a_7_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_3_, p_a_4_, p_a_5_, 0, 0, 0, 9, 7, 12, p_a_6_);
         return a(structureboundingbox) && StructurePiece.a(p_a_1_, structureboundingbox) == null?new WorldGenVillagePieces.WorldGenVillageHouse2(p_a_0_, p_a_7_, p_a_2_, structureboundingbox, p_a_6_):null;
      }

      public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_) {
         if(this.h < 0) {
            this.h = this.b(p_a_1_, p_a_3_);
            if(this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 7 - 1, 0);
         }

         this.a(p_a_1_, p_a_3_, 1, 1, 1, 7, 4, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 2, 1, 6, 8, 4, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 2, 0, 5, 8, 0, 10, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 1, 7, 0, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 0, 0, 0, 3, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 8, 0, 0, 8, 3, 10, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 0, 7, 2, 0, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 5, 2, 1, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 2, 0, 6, 2, 3, 10, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 3, 0, 10, 7, 3, 10, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 2, 0, 7, 3, 0, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 2, 5, 2, 3, 5, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 4, 1, 8, 4, 1, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 4, 4, 3, 4, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 5, 2, 8, 5, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 0, 4, 2, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 0, 4, 3, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 8, 4, 2, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 8, 4, 3, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 8, 4, 4, p_a_3_);
         int i = this.a(Blocks.OAK_STAIRS, 3);
         int j = this.a(Blocks.OAK_STAIRS, 2);

         for(int k = -1; k <= 2; ++k) {
            for(int l = 0; l <= 8; ++l) {
               this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(i), l, 4 + k, k, p_a_3_);
               if((k > -1 || l <= 1) && (k > 0 || l <= 3) && (k > 1 || l <= 4 || l >= 6)) {
                  this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(j), l, 4 + k, 5 - k, p_a_3_);
               }
            }
         }

         this.a(p_a_1_, p_a_3_, 3, 4, 5, 3, 4, 10, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 7, 4, 2, 7, 4, 10, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 4, 5, 4, 4, 5, 10, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 6, 5, 4, 6, 5, 10, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 5, 6, 3, 5, 6, 10, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         int k1 = this.a(Blocks.OAK_STAIRS, 0);

         for(int l1 = 4; l1 >= 1; --l1) {
            this.a(p_a_1_, Blocks.PLANKS.getBlockData(), l1, 2 + l1, 7 - l1, p_a_3_);

            for(int i1 = 8 - l1; i1 <= 10; ++i1) {
               this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(k1), l1, 2 + l1, i1, p_a_3_);
            }
         }

         int i2 = this.a(Blocks.OAK_STAIRS, 1);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 6, 6, 3, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 7, 5, 4, p_a_3_);
         this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(i2), 6, 6, 4, p_a_3_);

         for(int j2 = 6; j2 <= 8; ++j2) {
            for(int j1 = 5; j1 <= 10; ++j1) {
               this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(i2), j2, 12 - j2, j1, p_a_3_);
            }
         }

         this.a(p_a_1_, Blocks.LOG.getBlockData(), 0, 2, 1, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 0, 2, 4, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 2, 3, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 4, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 5, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 6, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 8, 2, 1, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 8, 2, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 8, 2, 3, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 8, 2, 4, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 8, 2, 5, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 8, 2, 6, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 8, 2, 7, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 8, 2, 8, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 8, 2, 9, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 2, 2, 6, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 2, 2, 7, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 2, 2, 8, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 2, 2, 9, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 4, 4, 10, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 5, 4, 10, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 6, 4, 10, p_a_3_);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 5, 5, 10, p_a_3_);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 2, 1, 0, p_a_3_);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 2, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m), 2, 3, 1, p_a_3_);
         this.a(p_a_1_, p_a_3_, p_a_2_, 2, 1, 0, EnumDirection.fromType2(this.a(Blocks.WOODEN_DOOR, 1)));
         this.a(p_a_1_, p_a_3_, 1, 0, -1, 3, 2, -1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         if(this.a(p_a_1_, 2, 0, -1, p_a_3_).getBlock().getMaterial() == Material.AIR && this.a(p_a_1_, 2, -1, -1, p_a_3_).getBlock().getMaterial() != Material.AIR) {
            this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(this.a(Blocks.STONE_STAIRS, 3)), 2, 0, -1, p_a_3_);
         }

         for(int k2 = 0; k2 < 5; ++k2) {
            for(int i3 = 0; i3 < 9; ++i3) {
               this.b(p_a_1_, i3, 7, k2, p_a_3_);
               this.b(p_a_1_, Blocks.COBBLESTONE.getBlockData(), i3, -1, k2, p_a_3_);
            }
         }

         for(int l2 = 5; l2 < 11; ++l2) {
            for(int j3 = 2; j3 < 9; ++j3) {
               this.b(p_a_1_, j3, 7, l2, p_a_3_);
               this.b(p_a_1_, Blocks.COBBLESTONE.getBlockData(), j3, -1, l2, p_a_3_);
            }
         }

         this.a(p_a_1_, p_a_3_, 4, 1, 2, 2);
         return true;
      }
   }

   public static class WorldGenVillageHut extends WorldGenVillagePieces.WorldGenVillagePiece {
      private boolean a;
      private int b;

      public WorldGenVillageHut() {
      }

      public WorldGenVillageHut(WorldGenVillagePieces.WorldGenVillageStartPiece p_i411_1_, int p_i411_2_, Random p_i411_3_, StructureBoundingBox p_i411_4_, EnumDirection p_i411_5_) {
         super(p_i411_1_, p_i411_2_);
         this.m = p_i411_5_;
         this.l = p_i411_4_;
         this.a = p_i411_3_.nextBoolean();
         this.b = p_i411_3_.nextInt(3);
      }

      protected void a(NBTTagCompound p_a_1_) {
         super.a(p_a_1_);
         p_a_1_.setInt("T", this.b);
         p_a_1_.setBoolean("C", this.a);
      }

      protected void b(NBTTagCompound p_b_1_) {
         super.b(p_b_1_);
         this.b = p_b_1_.getInt("T");
         this.a = p_b_1_.getBoolean("C");
      }

      public static WorldGenVillagePieces.WorldGenVillageHut a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_0_, List<StructurePiece> p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_, int p_a_7_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_3_, p_a_4_, p_a_5_, 0, 0, 0, 4, 6, 5, p_a_6_);
         return a(structureboundingbox) && StructurePiece.a(p_a_1_, structureboundingbox) == null?new WorldGenVillagePieces.WorldGenVillageHut(p_a_0_, p_a_7_, p_a_2_, structureboundingbox, p_a_6_):null;
      }

      public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_) {
         if(this.h < 0) {
            this.h = this.b(p_a_1_, p_a_3_);
            if(this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 6 - 1, 0);
         }

         this.a(p_a_1_, p_a_3_, 1, 1, 1, 3, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 0, 0, 3, 0, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 1, 2, 0, 3, Blocks.DIRT.getBlockData(), Blocks.DIRT.getBlockData(), false);
         if(this.a) {
            this.a(p_a_1_, p_a_3_, 1, 4, 1, 2, 4, 3, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         } else {
            this.a(p_a_1_, p_a_3_, 1, 5, 1, 2, 5, 3, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         }

         this.a(p_a_1_, Blocks.LOG.getBlockData(), 1, 4, 0, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 2, 4, 0, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 1, 4, 4, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 2, 4, 4, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 0, 4, 1, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 0, 4, 2, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 0, 4, 3, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 3, 4, 1, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 3, 4, 2, p_a_3_);
         this.a(p_a_1_, Blocks.LOG.getBlockData(), 3, 4, 3, p_a_3_);
         this.a(p_a_1_, p_a_3_, 0, 1, 0, 0, 3, 0, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 3, 1, 0, 3, 3, 0, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 1, 4, 0, 3, 4, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 3, 1, 4, 3, 3, 4, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 1, 1, 0, 3, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 3, 1, 1, 3, 3, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 1, 0, 2, 3, 0, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 1, 4, 2, 3, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 3, 2, 2, p_a_3_);
         if(this.b > 0) {
            this.a(p_a_1_, Blocks.FENCE.getBlockData(), this.b, 1, 3, p_a_3_);
            this.a(p_a_1_, Blocks.WOODEN_PRESSURE_PLATE.getBlockData(), this.b, 2, 3, p_a_3_);
         }

         this.a(p_a_1_, Blocks.AIR.getBlockData(), 1, 1, 0, p_a_3_);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 1, 2, 0, p_a_3_);
         this.a(p_a_1_, p_a_3_, p_a_2_, 1, 1, 0, EnumDirection.fromType2(this.a(Blocks.WOODEN_DOOR, 1)));
         if(this.a(p_a_1_, 1, 0, -1, p_a_3_).getBlock().getMaterial() == Material.AIR && this.a(p_a_1_, 1, -1, -1, p_a_3_).getBlock().getMaterial() != Material.AIR) {
            this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(this.a(Blocks.STONE_STAIRS, 3)), 1, 0, -1, p_a_3_);
         }

         for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 4; ++j) {
               this.b(p_a_1_, j, 6, i, p_a_3_);
               this.b(p_a_1_, Blocks.COBBLESTONE.getBlockData(), j, -1, i, p_a_3_);
            }
         }

         this.a(p_a_1_, p_a_3_, 1, 1, 2, 1);
         return true;
      }
   }

   public static class WorldGenVillageLibrary extends WorldGenVillagePieces.WorldGenVillagePiece {
      public WorldGenVillageLibrary() {
      }

      public WorldGenVillageLibrary(WorldGenVillagePieces.WorldGenVillageStartPiece p_i309_1_, int p_i309_2_, Random p_i309_3_, StructureBoundingBox p_i309_4_, EnumDirection p_i309_5_) {
         super(p_i309_1_, p_i309_2_);
         this.m = p_i309_5_;
         this.l = p_i309_4_;
      }

      public static WorldGenVillagePieces.WorldGenVillageLibrary a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_0_, List<StructurePiece> p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_, int p_a_7_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_3_, p_a_4_, p_a_5_, 0, 0, 0, 9, 9, 6, p_a_6_);
         return a(structureboundingbox) && StructurePiece.a(p_a_1_, structureboundingbox) == null?new WorldGenVillagePieces.WorldGenVillageLibrary(p_a_0_, p_a_7_, p_a_2_, structureboundingbox, p_a_6_):null;
      }

      public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_) {
         if(this.h < 0) {
            this.h = this.b(p_a_1_, p_a_3_);
            if(this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 9 - 1, 0);
         }

         this.a(p_a_1_, p_a_3_, 1, 1, 1, 7, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 0, 0, 8, 0, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 5, 0, 8, 5, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 6, 1, 8, 6, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 7, 2, 8, 7, 3, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         int i = this.a(Blocks.OAK_STAIRS, 3);
         int j = this.a(Blocks.OAK_STAIRS, 2);

         for(int k = -1; k <= 2; ++k) {
            for(int l = 0; l <= 8; ++l) {
               this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(i), l, 6 + k, k, p_a_3_);
               this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(j), l, 6 + k, 5 - k, p_a_3_);
            }
         }

         this.a(p_a_1_, p_a_3_, 0, 1, 0, 0, 1, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 1, 5, 8, 1, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 8, 1, 0, 8, 1, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 2, 1, 0, 7, 1, 0, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 2, 0, 0, 4, 0, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 2, 5, 0, 4, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 8, 2, 5, 8, 4, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 8, 2, 0, 8, 4, 0, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 2, 1, 0, 4, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 2, 5, 7, 4, 5, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 8, 2, 1, 8, 4, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 2, 0, 7, 4, 0, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 4, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 5, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 6, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 4, 3, 0, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 5, 3, 0, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 6, 3, 0, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 2, 3, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 3, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 3, 3, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 8, 2, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 8, 2, 3, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 8, 3, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 8, 3, 3, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 2, 2, 5, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 3, 2, 5, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 5, 2, 5, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 6, 2, 5, p_a_3_);
         this.a(p_a_1_, p_a_3_, 1, 4, 1, 7, 4, 1, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 4, 4, 7, 4, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 3, 4, 7, 3, 4, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
         this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 7, 1, 4, p_a_3_);
         this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(this.a(Blocks.OAK_STAIRS, 0)), 7, 1, 3, p_a_3_);
         int j1 = this.a(Blocks.OAK_STAIRS, 3);
         this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(j1), 6, 1, 4, p_a_3_);
         this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(j1), 5, 1, 4, p_a_3_);
         this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(j1), 4, 1, 4, p_a_3_);
         this.a(p_a_1_, Blocks.OAK_STAIRS.fromLegacyData(j1), 3, 1, 4, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 6, 1, 3, p_a_3_);
         this.a(p_a_1_, Blocks.WOODEN_PRESSURE_PLATE.getBlockData(), 6, 2, 3, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 4, 1, 3, p_a_3_);
         this.a(p_a_1_, Blocks.WOODEN_PRESSURE_PLATE.getBlockData(), 4, 2, 3, p_a_3_);
         this.a(p_a_1_, Blocks.CRAFTING_TABLE.getBlockData(), 7, 1, 1, p_a_3_);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 1, 1, 0, p_a_3_);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 1, 2, 0, p_a_3_);
         this.a(p_a_1_, p_a_3_, p_a_2_, 1, 1, 0, EnumDirection.fromType2(this.a(Blocks.WOODEN_DOOR, 1)));
         if(this.a(p_a_1_, 1, 0, -1, p_a_3_).getBlock().getMaterial() == Material.AIR && this.a(p_a_1_, 1, -1, -1, p_a_3_).getBlock().getMaterial() != Material.AIR) {
            this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(this.a(Blocks.STONE_STAIRS, 3)), 1, 0, -1, p_a_3_);
         }

         for(int k1 = 0; k1 < 6; ++k1) {
            for(int i1 = 0; i1 < 9; ++i1) {
               this.b(p_a_1_, i1, 9, k1, p_a_3_);
               this.b(p_a_1_, Blocks.COBBLESTONE.getBlockData(), i1, -1, k1, p_a_3_);
            }
         }

         this.a(p_a_1_, p_a_3_, 2, 1, 2, 1);
         return true;
      }

      protected int c(int p_c_1_, int p_c_2_) {
         return 1;
      }
   }

   public static class WorldGenVillageLight extends WorldGenVillagePieces.WorldGenVillagePiece {
      public WorldGenVillageLight() {
      }

      public WorldGenVillageLight(WorldGenVillagePieces.WorldGenVillageStartPiece p_i405_1_, int p_i405_2_, Random p_i405_3_, StructureBoundingBox p_i405_4_, EnumDirection p_i405_5_) {
         super(p_i405_1_, p_i405_2_);
         this.m = p_i405_5_;
         this.l = p_i405_4_;
      }

      public static StructureBoundingBox a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_0_, List<StructurePiece> p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_3_, p_a_4_, p_a_5_, 0, 0, 0, 3, 4, 2, p_a_6_);
         return StructurePiece.a(p_a_1_, structureboundingbox) != null?null:structureboundingbox;
      }

      public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_) {
         if(this.h < 0) {
            this.h = this.b(p_a_1_, p_a_3_);
            if(this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 4 - 1, 0);
         }

         this.a(p_a_1_, p_a_3_, 0, 0, 0, 2, 3, 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 1, 0, 0, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 1, 1, 0, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 1, 2, 0, p_a_3_);
         this.a(p_a_1_, Blocks.WOOL.fromLegacyData(EnumColor.WHITE.getInvColorIndex()), 1, 3, 0, p_a_3_);
         boolean flag = this.m == EnumDirection.EAST || this.m == EnumDirection.NORTH;
         this.a(p_a_1_, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.e()), flag?2:0, 3, 0, p_a_3_);
         this.a(p_a_1_, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m), 1, 3, 1, p_a_3_);
         this.a(p_a_1_, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.f()), flag?0:2, 3, 0, p_a_3_);
         this.a(p_a_1_, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.opposite()), 1, 3, -1, p_a_3_);
         return true;
      }
   }

   abstract static class WorldGenVillagePiece extends StructurePiece {
      protected int h = -1;
      private int a;
      private boolean b;

      public WorldGenVillagePiece() {
      }

      protected WorldGenVillagePiece(WorldGenVillagePieces.WorldGenVillageStartPiece p_i108_1_, int p_i108_2_) {
         super(p_i108_2_);
         if(p_i108_1_ != null) {
            this.b = p_i108_1_.b;
         }

      }

      protected void a(NBTTagCompound p_a_1_) {
         p_a_1_.setInt("HPos", this.h);
         p_a_1_.setInt("VCount", this.a);
         p_a_1_.setBoolean("Desert", this.b);
      }

      protected void b(NBTTagCompound p_b_1_) {
         this.h = p_b_1_.getInt("HPos");
         this.a = p_b_1_.getInt("VCount");
         this.b = p_b_1_.getBoolean("Desert");
      }

      protected StructurePiece a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_, int p_a_4_, int p_a_5_) {
         if(this.m != null) {
            switch(WorldGenVillagePieces.SyntheticClass_1.a[this.m.ordinal()]) {
            case 1:
               return WorldGenVillagePieces.d(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b + p_a_4_, this.l.c + p_a_5_, EnumDirection.WEST, this.d());
            case 2:
               return WorldGenVillagePieces.d(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b + p_a_4_, this.l.c + p_a_5_, EnumDirection.WEST, this.d());
            case 3:
               return WorldGenVillagePieces.d(p_a_1_, p_a_2_, p_a_3_, this.l.a + p_a_5_, this.l.b + p_a_4_, this.l.c - 1, EnumDirection.NORTH, this.d());
            case 4:
               return WorldGenVillagePieces.d(p_a_1_, p_a_2_, p_a_3_, this.l.a + p_a_5_, this.l.b + p_a_4_, this.l.c - 1, EnumDirection.NORTH, this.d());
            }
         }

         return null;
      }

      protected StructurePiece b(WorldGenVillagePieces.WorldGenVillageStartPiece p_b_1_, List<StructurePiece> p_b_2_, Random p_b_3_, int p_b_4_, int p_b_5_) {
         if(this.m != null) {
            switch(WorldGenVillagePieces.SyntheticClass_1.a[this.m.ordinal()]) {
            case 1:
               return WorldGenVillagePieces.d(p_b_1_, p_b_2_, p_b_3_, this.l.d + 1, this.l.b + p_b_4_, this.l.c + p_b_5_, EnumDirection.EAST, this.d());
            case 2:
               return WorldGenVillagePieces.d(p_b_1_, p_b_2_, p_b_3_, this.l.d + 1, this.l.b + p_b_4_, this.l.c + p_b_5_, EnumDirection.EAST, this.d());
            case 3:
               return WorldGenVillagePieces.d(p_b_1_, p_b_2_, p_b_3_, this.l.a + p_b_5_, this.l.b + p_b_4_, this.l.f + 1, EnumDirection.SOUTH, this.d());
            case 4:
               return WorldGenVillagePieces.d(p_b_1_, p_b_2_, p_b_3_, this.l.a + p_b_5_, this.l.b + p_b_4_, this.l.f + 1, EnumDirection.SOUTH, this.d());
            }
         }

         return null;
      }

      protected int b(World p_b_1_, StructureBoundingBox p_b_2_) {
         int i = 0;
         int j = 0;
         BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(int k = this.l.c; k <= this.l.f; ++k) {
            for(int l = this.l.a; l <= this.l.d; ++l) {
               blockposition$mutableblockposition.c(l, 64, k);
               if(p_b_2_.b((BaseBlockPosition)blockposition$mutableblockposition)) {
                  i += Math.max(p_b_1_.r(blockposition$mutableblockposition).getY(), p_b_1_.worldProvider.getSeaLevel());
                  ++j;
               }
            }
         }

         if(j == 0) {
            return -1;
         } else {
            return i / j;
         }
      }

      protected static boolean a(StructureBoundingBox p_a_0_) {
         return p_a_0_ != null && p_a_0_.b > 10;
      }

      protected void a(World p_a_1_, StructureBoundingBox p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_) {
         if(this.a < p_a_6_) {
            for(int i = this.a; i < p_a_6_; ++i) {
               int j = this.a(p_a_3_ + i, p_a_5_);
               int k = this.d(p_a_4_);
               int l = this.b(p_a_3_ + i, p_a_5_);
               if(!p_a_2_.b((BaseBlockPosition)(new BlockPosition(j, k, l)))) {
                  break;
               }

               ++this.a;
               EntityVillager entityvillager = new EntityVillager(p_a_1_);
               entityvillager.setPositionRotation((double)j + 0.5D, (double)k, (double)l + 0.5D, 0.0F, 0.0F);
               entityvillager.prepare(p_a_1_.E(new BlockPosition(entityvillager)), (GroupDataEntity)null);
               entityvillager.setProfession(this.c(i, entityvillager.getProfession()));
               p_a_1_.addEntity(entityvillager, SpawnReason.CHUNK_GEN);
            }
         }

      }

      protected int c(int p_c_1_, int p_c_2_) {
         return p_c_2_;
      }

      protected IBlockData a(IBlockData p_a_1_) {
         if(this.b) {
            if(p_a_1_.getBlock() == Blocks.LOG || p_a_1_.getBlock() == Blocks.LOG2) {
               return Blocks.SANDSTONE.getBlockData();
            }

            if(p_a_1_.getBlock() == Blocks.COBBLESTONE) {
               return Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.DEFAULT.a());
            }

            if(p_a_1_.getBlock() == Blocks.PLANKS) {
               return Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a());
            }

            if(p_a_1_.getBlock() == Blocks.OAK_STAIRS) {
               return Blocks.SANDSTONE_STAIRS.getBlockData().set(BlockStairs.FACING, (EnumDirection)p_a_1_.get(BlockStairs.FACING));
            }

            if(p_a_1_.getBlock() == Blocks.STONE_STAIRS) {
               return Blocks.SANDSTONE_STAIRS.getBlockData().set(BlockStairs.FACING, (EnumDirection)p_a_1_.get(BlockStairs.FACING));
            }

            if(p_a_1_.getBlock() == Blocks.GRAVEL) {
               return Blocks.SANDSTONE.getBlockData();
            }
         }

         return p_a_1_;
      }

      protected void a(World p_a_1_, IBlockData p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, StructureBoundingBox p_a_6_) {
         IBlockData iblockdata = this.a(p_a_2_);
         super.a(p_a_1_, iblockdata, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      }

      protected void a(World p_a_1_, StructureBoundingBox p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, int p_a_8_, IBlockData p_a_9_, IBlockData p_a_10_, boolean p_a_11_) {
         IBlockData iblockdata = this.a(p_a_9_);
         IBlockData iblockdata1 = this.a(p_a_10_);
         super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_, iblockdata, iblockdata1, p_a_11_);
      }

      protected void b(World p_b_1_, IBlockData p_b_2_, int p_b_3_, int p_b_4_, int p_b_5_, StructureBoundingBox p_b_6_) {
         IBlockData iblockdata = this.a(p_b_2_);
         super.b(p_b_1_, iblockdata, p_b_3_, p_b_4_, p_b_5_, p_b_6_);
      }

      protected void a(boolean p_a_1_) {
         this.b = p_a_1_;
      }
   }

   public static class WorldGenVillagePieceWeight {
      public Class<? extends WorldGenVillagePieces.WorldGenVillagePiece> a;
      public final int b;
      public int c;
      public int d;

      public WorldGenVillagePieceWeight(Class<? extends WorldGenVillagePieces.WorldGenVillagePiece> p_i188_1_, int p_i188_2_, int p_i188_3_) {
         this.a = p_i188_1_;
         this.b = p_i188_2_;
         this.d = p_i188_3_;
      }

      public boolean a(int p_a_1_) {
         return this.d == 0 || this.c < this.d;
      }

      public boolean a() {
         return this.d == 0 || this.c < this.d;
      }
   }

   public static class WorldGenVillageRoad extends WorldGenVillagePieces.WorldGenVillageRoadPiece {
      private int a;

      public WorldGenVillageRoad() {
      }

      public WorldGenVillageRoad(WorldGenVillagePieces.WorldGenVillageStartPiece p_i66_1_, int p_i66_2_, Random p_i66_3_, StructureBoundingBox p_i66_4_, EnumDirection p_i66_5_) {
         super(p_i66_1_, p_i66_2_);
         this.m = p_i66_5_;
         this.l = p_i66_4_;
         this.a = Math.max(p_i66_4_.c(), p_i66_4_.e());
      }

      protected void a(NBTTagCompound p_a_1_) {
         super.a(p_a_1_);
         p_a_1_.setInt("Length", this.a);
      }

      protected void b(NBTTagCompound p_b_1_) {
         super.b(p_b_1_);
         this.a = p_b_1_.getInt("Length");
      }

      public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_) {
         boolean flag = false;

         for(int i = p_a_3_.nextInt(5); i < this.a - 8; i += 2 + p_a_3_.nextInt(5)) {
            StructurePiece structurepiece = this.a((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, 0, i);
            if(structurepiece != null) {
               i += Math.max(structurepiece.l.c(), structurepiece.l.e());
               flag = true;
            }
         }

         for(int j = p_a_3_.nextInt(5); j < this.a - 8; j += 2 + p_a_3_.nextInt(5)) {
            StructurePiece structurepiece1 = this.b((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, 0, j);
            if(structurepiece1 != null) {
               j += Math.max(structurepiece1.l.c(), structurepiece1.l.e());
               flag = true;
            }
         }

         if(flag && p_a_3_.nextInt(3) > 0 && this.m != null) {
            switch(WorldGenVillagePieces.SyntheticClass_1.a[this.m.ordinal()]) {
            case 1:
               WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b, this.l.c, EnumDirection.WEST, this.d());
               break;
            case 2:
               WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b, this.l.f - 2, EnumDirection.WEST, this.d());
               break;
            case 3:
               WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, this.l.a, this.l.b, this.l.c - 1, EnumDirection.NORTH, this.d());
               break;
            case 4:
               WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, this.l.d - 2, this.l.b, this.l.c - 1, EnumDirection.NORTH, this.d());
            }
         }

         if(flag && p_a_3_.nextInt(3) > 0 && this.m != null) {
            switch(WorldGenVillagePieces.SyntheticClass_1.a[this.m.ordinal()]) {
            case 1:
               WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b, this.l.c, EnumDirection.EAST, this.d());
               break;
            case 2:
               WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b, this.l.f - 2, EnumDirection.EAST, this.d());
               break;
            case 3:
               WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, this.l.a, this.l.b, this.l.f + 1, EnumDirection.SOUTH, this.d());
               break;
            case 4:
               WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, this.l.d - 2, this.l.b, this.l.f + 1, EnumDirection.SOUTH, this.d());
            }
         }

      }

      public static StructureBoundingBox a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_0_, List<StructurePiece> p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_) {
         for(int i = 7 * MathHelper.nextInt(p_a_2_, 3, 5); i >= 7; i -= 7) {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_3_, p_a_4_, p_a_5_, 0, 0, 0, 3, 3, i, p_a_6_);
            if(StructurePiece.a(p_a_1_, structureboundingbox) == null) {
               return structureboundingbox;
            }
         }

         return null;
      }

      public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_) {
         IBlockData iblockdata = this.a(Blocks.GRAVEL.getBlockData());
         IBlockData iblockdata1 = this.a(Blocks.COBBLESTONE.getBlockData());

         for(int i = this.l.a; i <= this.l.d; ++i) {
            for(int j = this.l.c; j <= this.l.f; ++j) {
               BlockPosition blockposition = new BlockPosition(i, 64, j);
               if(p_a_3_.b((BaseBlockPosition)blockposition)) {
                  blockposition = p_a_1_.r(blockposition).down();
                  p_a_1_.setTypeAndData(blockposition, iblockdata, 2);
                  p_a_1_.setTypeAndData(blockposition.down(), iblockdata1, 2);
               }
            }
         }

         return true;
      }
   }

   public abstract static class WorldGenVillageRoadPiece extends WorldGenVillagePieces.WorldGenVillagePiece {
      public WorldGenVillageRoadPiece() {
      }

      protected WorldGenVillageRoadPiece(WorldGenVillagePieces.WorldGenVillageStartPiece p_i104_1_, int p_i104_2_) {
         super(p_i104_1_, p_i104_2_);
      }
   }

   public static class WorldGenVillageStartPiece extends WorldGenVillagePieces.WorldGenVillageWell {
      public WorldChunkManager a;
      public boolean b;
      public int c;
      public WorldGenVillagePieces.WorldGenVillagePieceWeight d;
      public List<WorldGenVillagePieces.WorldGenVillagePieceWeight> e;
      public List<StructurePiece> f = Lists.<StructurePiece>newArrayList();
      public List<StructurePiece> g = Lists.<StructurePiece>newArrayList();

      public WorldGenVillageStartPiece() {
      }

      public WorldGenVillageStartPiece(WorldChunkManager p_i310_1_, int p_i310_2_, Random p_i310_3_, int p_i310_4_, int p_i310_5_, List<WorldGenVillagePieces.WorldGenVillagePieceWeight> p_i310_6_, int p_i310_7_) {
         super((WorldGenVillagePieces.WorldGenVillageStartPiece)null, 0, p_i310_3_, p_i310_4_, p_i310_5_);
         this.a = p_i310_1_;
         this.e = p_i310_6_;
         this.c = p_i310_7_;
         BiomeBase biomebase = p_i310_1_.getBiome(new BlockPosition(p_i310_4_, 0, p_i310_5_), BiomeBase.ad);
         this.b = biomebase == BiomeBase.DESERT || biomebase == BiomeBase.DESERT_HILLS;
         this.a(this.b);
      }

      public WorldChunkManager e() {
         return this.a;
      }
   }

   public static class WorldGenVillageTemple extends WorldGenVillagePieces.WorldGenVillagePiece {
      public WorldGenVillageTemple() {
      }

      public WorldGenVillageTemple(WorldGenVillagePieces.WorldGenVillageStartPiece p_i332_1_, int p_i332_2_, Random p_i332_3_, StructureBoundingBox p_i332_4_, EnumDirection p_i332_5_) {
         super(p_i332_1_, p_i332_2_);
         this.m = p_i332_5_;
         this.l = p_i332_4_;
      }

      public static WorldGenVillagePieces.WorldGenVillageTemple a(WorldGenVillagePieces.WorldGenVillageStartPiece p_a_0_, List<StructurePiece> p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_, int p_a_7_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_3_, p_a_4_, p_a_5_, 0, 0, 0, 5, 12, 9, p_a_6_);
         return a(structureboundingbox) && StructurePiece.a(p_a_1_, structureboundingbox) == null?new WorldGenVillagePieces.WorldGenVillageTemple(p_a_0_, p_a_7_, p_a_2_, structureboundingbox, p_a_6_):null;
      }

      public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_) {
         if(this.h < 0) {
            this.h = this.b(p_a_1_, p_a_3_);
            if(this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 12 - 1, 0);
         }

         this.a(p_a_1_, p_a_3_, 1, 1, 1, 3, 3, 7, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 5, 1, 3, 9, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 0, 0, 3, 0, 8, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 1, 0, 3, 10, 0, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 1, 1, 0, 10, 3, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 4, 1, 1, 4, 10, 3, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 0, 4, 0, 4, 7, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 4, 0, 4, 4, 4, 7, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 1, 8, 3, 4, 8, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 5, 4, 3, 10, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 1, 5, 5, 3, 5, 7, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 9, 0, 4, 9, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, p_a_3_, 0, 4, 0, 4, 4, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 0, 11, 2, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 4, 11, 2, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 2, 11, 0, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 2, 11, 4, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 1, 1, 6, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 1, 1, 7, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 2, 1, 7, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 3, 1, 6, p_a_3_);
         this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 3, 1, 7, p_a_3_);
         this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(this.a(Blocks.STONE_STAIRS, 3)), 1, 1, 5, p_a_3_);
         this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(this.a(Blocks.STONE_STAIRS, 3)), 2, 1, 6, p_a_3_);
         this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(this.a(Blocks.STONE_STAIRS, 3)), 3, 1, 5, p_a_3_);
         this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(this.a(Blocks.STONE_STAIRS, 1)), 1, 2, 7, p_a_3_);
         this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(this.a(Blocks.STONE_STAIRS, 0)), 3, 2, 7, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 3, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 4, 2, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 4, 3, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 6, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 7, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 4, 6, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 4, 7, 2, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 2, 6, 0, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 2, 7, 0, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 2, 6, 4, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 2, 7, 4, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 0, 3, 6, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 4, 3, 6, p_a_3_);
         this.a(p_a_1_, Blocks.GLASS_PANE.getBlockData(), 2, 3, 8, p_a_3_);
         this.a(p_a_1_, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.opposite()), 2, 4, 7, p_a_3_);
         this.a(p_a_1_, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.e()), 1, 4, 6, p_a_3_);
         this.a(p_a_1_, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.f()), 3, 4, 6, p_a_3_);
         this.a(p_a_1_, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m), 2, 4, 5, p_a_3_);
         int i = this.a(Blocks.LADDER, 4);

         for(int j = 1; j <= 9; ++j) {
            this.a(p_a_1_, Blocks.LADDER.fromLegacyData(i), 3, j, 3, p_a_3_);
         }

         this.a(p_a_1_, Blocks.AIR.getBlockData(), 2, 1, 0, p_a_3_);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 2, 2, 0, p_a_3_);
         this.a(p_a_1_, p_a_3_, p_a_2_, 2, 1, 0, EnumDirection.fromType2(this.a(Blocks.WOODEN_DOOR, 1)));
         if(this.a(p_a_1_, 2, 0, -1, p_a_3_).getBlock().getMaterial() == Material.AIR && this.a(p_a_1_, 2, -1, -1, p_a_3_).getBlock().getMaterial() != Material.AIR) {
            this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(this.a(Blocks.STONE_STAIRS, 3)), 2, 0, -1, p_a_3_);
         }

         for(int l = 0; l < 9; ++l) {
            for(int k = 0; k < 5; ++k) {
               this.b(p_a_1_, k, 12, l, p_a_3_);
               this.b(p_a_1_, Blocks.COBBLESTONE.getBlockData(), k, -1, l, p_a_3_);
            }
         }

         this.a(p_a_1_, p_a_3_, 2, 1, 2, 1);
         return true;
      }

      protected int c(int p_c_1_, int p_c_2_) {
         return 2;
      }
   }

   public static class WorldGenVillageWell extends WorldGenVillagePieces.WorldGenVillagePiece {
      public WorldGenVillageWell() {
      }

      public WorldGenVillageWell(WorldGenVillagePieces.WorldGenVillageStartPiece p_i373_1_, int p_i373_2_, Random p_i373_3_, int p_i373_4_, int p_i373_5_) {
         super(p_i373_1_, p_i373_2_);
         this.m = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(p_i373_3_);
         switch(WorldGenVillagePieces.SyntheticClass_1.a[this.m.ordinal()]) {
         case 1:
         case 2:
            this.l = new StructureBoundingBox(p_i373_4_, 64, p_i373_5_, p_i373_4_ + 6 - 1, 78, p_i373_5_ + 6 - 1);
            break;
         default:
            this.l = new StructureBoundingBox(p_i373_4_, 64, p_i373_5_, p_i373_4_ + 6 - 1, 78, p_i373_5_ + 6 - 1);
         }

      }

      public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_) {
         WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.e - 4, this.l.c + 1, EnumDirection.WEST, this.d());
         WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.e - 4, this.l.c + 1, EnumDirection.EAST, this.d());
         WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, this.l.a + 1, this.l.e - 4, this.l.c - 1, EnumDirection.NORTH, this.d());
         WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)p_a_1_, p_a_2_, p_a_3_, this.l.a + 1, this.l.e - 4, this.l.f + 1, EnumDirection.SOUTH, this.d());
      }

      public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_) {
         if(this.h < 0) {
            this.h = this.b(p_a_1_, p_a_3_);
            if(this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 3, 0);
         }

         this.a(p_a_1_, p_a_3_, 1, 0, 1, 4, 12, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.FLOWING_WATER.getBlockData(), false);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 2, 12, 2, p_a_3_);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 3, 12, 2, p_a_3_);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 2, 12, 3, p_a_3_);
         this.a(p_a_1_, Blocks.AIR.getBlockData(), 3, 12, 3, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 1, 13, 1, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 1, 14, 1, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 4, 13, 1, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 4, 14, 1, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 1, 13, 4, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 1, 14, 4, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 4, 13, 4, p_a_3_);
         this.a(p_a_1_, Blocks.FENCE.getBlockData(), 4, 14, 4, p_a_3_);
         this.a(p_a_1_, p_a_3_, 1, 15, 1, 4, 15, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);

         for(int i = 0; i <= 5; ++i) {
            for(int j = 0; j <= 5; ++j) {
               if(j == 0 || j == 5 || i == 0 || i == 5) {
                  this.a(p_a_1_, Blocks.GRAVEL.getBlockData(), j, 11, i, p_a_3_);
                  this.b(p_a_1_, j, 12, i, p_a_3_);
               }
            }
         }

         return true;
      }
   }
}
