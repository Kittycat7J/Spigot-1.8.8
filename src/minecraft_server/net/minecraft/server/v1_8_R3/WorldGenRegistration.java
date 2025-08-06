package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class WorldGenRegistration
{
    public static void a()
    {
        WorldGenFactory.a(WorldGenRegistration.WorldGenPyramidPiece.class, "TeDP");
        WorldGenFactory.a(WorldGenRegistration.WorldGenJungleTemple.class, "TeJP");
        WorldGenFactory.a(WorldGenRegistration.WorldGenWitchHut.class, "TeSH");
    }

    static class SyntheticClass_1
    {
        static final int[] a = new int[EnumDirection.values().length];

        static
        {
            try
            {
                a[EnumDirection.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }

            try
            {
                a[EnumDirection.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError var0)
            {
                ;
            }
        }
    }

    public static class WorldGenJungleTemple extends WorldGenRegistration.WorldGenScatteredPiece
    {
        private boolean e;
        private boolean f;
        private boolean g;
        private boolean h;
        private static final List<StructurePieceTreasure> i = Lists.newArrayList(new StructurePieceTreasure[] {new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 2, 7, 15), new StructurePieceTreasure(Items.EMERALD, 0, 1, 3, 2), new StructurePieceTreasure(Items.BONE, 0, 4, 6, 20), new StructurePieceTreasure(Items.ROTTEN_FLESH, 0, 3, 7, 16), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 3), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 1)});
        private static final List<StructurePieceTreasure> j = Lists.newArrayList(new StructurePieceTreasure[] {new StructurePieceTreasure(Items.ARROW, 0, 2, 7, 30)});
        private static WorldGenRegistration.WorldGenJungleTemple.WorldGenJungleTemplePiece k = new WorldGenRegistration.WorldGenJungleTemple.WorldGenJungleTemplePiece((WorldGenRegistration.SyntheticClass_1)null);

        public WorldGenJungleTemple()
        {
        }

        public WorldGenJungleTemple(Random p_i433_1_, int p_i433_2_, int p_i433_3_)
        {
            super(p_i433_1_, p_i433_2_, 64, p_i433_3_, 12, 10, 15);
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("placedMainChest", this.e);
            p_a_1_.setBoolean("placedHiddenChest", this.f);
            p_a_1_.setBoolean("placedTrap1", this.g);
            p_a_1_.setBoolean("placedTrap2", this.h);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.e = p_b_1_.getBoolean("placedMainChest");
            this.f = p_b_1_.getBoolean("placedHiddenChest");
            this.g = p_b_1_.getBoolean("placedTrap1");
            this.h = p_b_1_.getBoolean("placedTrap2");
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (!this.a(p_a_1_, p_a_3_, 0))
            {
                return false;
            }
            else
            {
                int i = this.a(Blocks.STONE_STAIRS, 3);
                int j = this.a(Blocks.STONE_STAIRS, 2);
                int k = this.a(Blocks.STONE_STAIRS, 0);
                int l = this.a(Blocks.STONE_STAIRS, 1);
                this.a(p_a_1_, p_a_3_, 0, -4, 0, this.a - 1, 0, this.c - 1, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 2, 1, 2, 9, 2, 2, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 2, 1, 12, 9, 2, 12, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 2, 1, 3, 2, 2, 11, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 9, 1, 3, 9, 2, 11, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 1, 3, 1, 10, 6, 1, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 1, 3, 13, 10, 6, 13, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 1, 3, 2, 1, 6, 12, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 10, 3, 2, 10, 6, 12, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 2, 3, 2, 9, 3, 12, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 2, 6, 2, 9, 6, 12, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 3, 7, 3, 8, 7, 11, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 4, 8, 4, 7, 8, 10, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 3, 1, 3, 8, 2, 11);
                this.a(p_a_1_, p_a_3_, 4, 3, 6, 7, 3, 9);
                this.a(p_a_1_, p_a_3_, 2, 4, 2, 9, 5, 12);
                this.a(p_a_1_, p_a_3_, 4, 6, 5, 7, 6, 9);
                this.a(p_a_1_, p_a_3_, 5, 7, 6, 6, 7, 8);
                this.a(p_a_1_, p_a_3_, 5, 1, 2, 6, 2, 2);
                this.a(p_a_1_, p_a_3_, 5, 2, 12, 6, 2, 12);
                this.a(p_a_1_, p_a_3_, 5, 5, 1, 6, 5, 1);
                this.a(p_a_1_, p_a_3_, 5, 5, 13, 6, 5, 13);
                this.a(p_a_1_, Blocks.AIR.getBlockData(), 1, 5, 5, p_a_3_);
                this.a(p_a_1_, Blocks.AIR.getBlockData(), 10, 5, 5, p_a_3_);
                this.a(p_a_1_, Blocks.AIR.getBlockData(), 1, 5, 9, p_a_3_);
                this.a(p_a_1_, Blocks.AIR.getBlockData(), 10, 5, 9, p_a_3_);

                for (int i1 = 0; i1 <= 14; i1 += 14)
                {
                    this.a(p_a_1_, p_a_3_, 2, 4, i1, 2, 5, i1, false, p_a_2_, k);
                    this.a(p_a_1_, p_a_3_, 4, 4, i1, 4, 5, i1, false, p_a_2_, k);
                    this.a(p_a_1_, p_a_3_, 7, 4, i1, 7, 5, i1, false, p_a_2_, k);
                    this.a(p_a_1_, p_a_3_, 9, 4, i1, 9, 5, i1, false, p_a_2_, k);
                }

                this.a(p_a_1_, p_a_3_, 5, 6, 0, 6, 6, 0, false, p_a_2_, k);

                for (int k1 = 0; k1 <= 11; k1 += 11)
                {
                    for (int j1 = 2; j1 <= 12; j1 += 2)
                    {
                        this.a(p_a_1_, p_a_3_, k1, 4, j1, k1, 5, j1, false, p_a_2_, k);
                    }

                    this.a(p_a_1_, p_a_3_, k1, 6, 5, k1, 6, 5, false, p_a_2_, k);
                    this.a(p_a_1_, p_a_3_, k1, 6, 9, k1, 6, 9, false, p_a_2_, k);
                }

                this.a(p_a_1_, p_a_3_, 2, 7, 2, 2, 9, 2, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 9, 7, 2, 9, 9, 2, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 2, 7, 12, 2, 9, 12, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 9, 7, 12, 9, 9, 12, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 4, 9, 4, 4, 9, 4, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 7, 9, 4, 7, 9, 4, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 4, 9, 10, 4, 9, 10, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 7, 9, 10, 7, 9, 10, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 5, 9, 7, 6, 9, 7, false, p_a_2_, k);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 5, 9, 6, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 6, 9, 6, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(j), 5, 9, 8, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(j), 6, 9, 8, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 4, 0, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 5, 0, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 6, 0, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 7, 0, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 4, 1, 8, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 4, 2, 9, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 4, 3, 10, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 7, 1, 8, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 7, 2, 9, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 7, 3, 10, p_a_3_);
                this.a(p_a_1_, p_a_3_, 4, 1, 9, 4, 1, 9, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 7, 1, 9, 7, 1, 9, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 4, 1, 10, 7, 2, 10, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 5, 4, 5, 6, 4, 5, false, p_a_2_, k);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(k), 4, 4, 5, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(l), 7, 4, 5, p_a_3_);

                for (int l1 = 0; l1 < 4; ++l1)
                {
                    this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(j), 5, 0 - l1, 6 + l1, p_a_3_);
                    this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(j), 6, 0 - l1, 6 + l1, p_a_3_);
                    this.a(p_a_1_, p_a_3_, 5, 0 - l1, 7 + l1, 6, 0 - l1, 9 + l1);
                }

                this.a(p_a_1_, p_a_3_, 1, -3, 12, 10, -1, 13);
                this.a(p_a_1_, p_a_3_, 1, -3, 1, 3, -1, 13);
                this.a(p_a_1_, p_a_3_, 1, -3, 1, 9, -1, 5);

                for (int i2 = 1; i2 <= 13; i2 += 2)
                {
                    this.a(p_a_1_, p_a_3_, 1, -3, i2, 1, -2, i2, false, p_a_2_, k);
                }

                for (int j2 = 2; j2 <= 12; j2 += 2)
                {
                    this.a(p_a_1_, p_a_3_, 1, -1, j2, 3, -1, j2, false, p_a_2_, k);
                }

                this.a(p_a_1_, p_a_3_, 2, -2, 1, 5, -2, 1, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 7, -2, 1, 9, -2, 1, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 6, -3, 1, 6, -3, 1, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 6, -1, 1, 6, -1, 1, false, p_a_2_, k);
                this.a(p_a_1_, Blocks.TRIPWIRE_HOOK.fromLegacyData(this.a(Blocks.TRIPWIRE_HOOK, EnumDirection.EAST.b())).set(BlockTripwireHook.ATTACHED, Boolean.valueOf(true)), 1, -3, 8, p_a_3_);
                this.a(p_a_1_, Blocks.TRIPWIRE_HOOK.fromLegacyData(this.a(Blocks.TRIPWIRE_HOOK, EnumDirection.WEST.b())).set(BlockTripwireHook.ATTACHED, Boolean.valueOf(true)), 4, -3, 8, p_a_3_);
                this.a(p_a_1_, Blocks.TRIPWIRE.getBlockData().set(BlockTripwire.ATTACHED, Boolean.valueOf(true)), 2, -3, 8, p_a_3_);
                this.a(p_a_1_, Blocks.TRIPWIRE.getBlockData().set(BlockTripwire.ATTACHED, Boolean.valueOf(true)), 3, -3, 8, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 7, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 6, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 5, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 4, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 3, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 2, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 1, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 4, -3, 1, p_a_3_);
                this.a(p_a_1_, Blocks.MOSSY_COBBLESTONE.getBlockData(), 3, -3, 1, p_a_3_);

                if (!this.g)
                {
                    this.g = this.a(p_a_1_, p_a_3_, p_a_2_, 3, -2, 1, EnumDirection.NORTH.a(), j, 2);
                }

                this.a(p_a_1_, Blocks.VINE.fromLegacyData(15), 3, -2, 2, p_a_3_);
                this.a(p_a_1_, Blocks.TRIPWIRE_HOOK.fromLegacyData(this.a(Blocks.TRIPWIRE_HOOK, EnumDirection.NORTH.b())).set(BlockTripwireHook.ATTACHED, Boolean.valueOf(true)), 7, -3, 1, p_a_3_);
                this.a(p_a_1_, Blocks.TRIPWIRE_HOOK.fromLegacyData(this.a(Blocks.TRIPWIRE_HOOK, EnumDirection.SOUTH.b())).set(BlockTripwireHook.ATTACHED, Boolean.valueOf(true)), 7, -3, 5, p_a_3_);
                this.a(p_a_1_, Blocks.TRIPWIRE.getBlockData().set(BlockTripwire.ATTACHED, Boolean.valueOf(true)), 7, -3, 2, p_a_3_);
                this.a(p_a_1_, Blocks.TRIPWIRE.getBlockData().set(BlockTripwire.ATTACHED, Boolean.valueOf(true)), 7, -3, 3, p_a_3_);
                this.a(p_a_1_, Blocks.TRIPWIRE.getBlockData().set(BlockTripwire.ATTACHED, Boolean.valueOf(true)), 7, -3, 4, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 8, -3, 6, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 9, -3, 6, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 9, -3, 5, p_a_3_);
                this.a(p_a_1_, Blocks.MOSSY_COBBLESTONE.getBlockData(), 9, -3, 4, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 9, -2, 4, p_a_3_);

                if (!this.h)
                {
                    this.h = this.a(p_a_1_, p_a_3_, p_a_2_, 9, -2, 3, EnumDirection.WEST.a(), j, 2);
                }

                this.a(p_a_1_, Blocks.VINE.fromLegacyData(15), 8, -1, 3, p_a_3_);
                this.a(p_a_1_, Blocks.VINE.fromLegacyData(15), 8, -2, 3, p_a_3_);

                if (!this.e)
                {
                    this.e = this.a(p_a_1_, p_a_3_, p_a_2_, 8, -3, 3, StructurePieceTreasure.a(i, new StructurePieceTreasure[] {Items.ENCHANTED_BOOK.b(p_a_2_)}), 2 + p_a_2_.nextInt(5));
                }

                this.a(p_a_1_, Blocks.MOSSY_COBBLESTONE.getBlockData(), 9, -3, 2, p_a_3_);
                this.a(p_a_1_, Blocks.MOSSY_COBBLESTONE.getBlockData(), 8, -3, 1, p_a_3_);
                this.a(p_a_1_, Blocks.MOSSY_COBBLESTONE.getBlockData(), 4, -3, 5, p_a_3_);
                this.a(p_a_1_, Blocks.MOSSY_COBBLESTONE.getBlockData(), 5, -2, 5, p_a_3_);
                this.a(p_a_1_, Blocks.MOSSY_COBBLESTONE.getBlockData(), 5, -1, 5, p_a_3_);
                this.a(p_a_1_, Blocks.MOSSY_COBBLESTONE.getBlockData(), 6, -3, 5, p_a_3_);
                this.a(p_a_1_, Blocks.MOSSY_COBBLESTONE.getBlockData(), 7, -2, 5, p_a_3_);
                this.a(p_a_1_, Blocks.MOSSY_COBBLESTONE.getBlockData(), 7, -1, 5, p_a_3_);
                this.a(p_a_1_, Blocks.MOSSY_COBBLESTONE.getBlockData(), 8, -3, 5, p_a_3_);
                this.a(p_a_1_, p_a_3_, 9, -1, 1, 9, -1, 5, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 8, -3, 8, 10, -1, 10);
                this.a(p_a_1_, Blocks.STONEBRICK.fromLegacyData(BlockSmoothBrick.P), 8, -2, 11, p_a_3_);
                this.a(p_a_1_, Blocks.STONEBRICK.fromLegacyData(BlockSmoothBrick.P), 9, -2, 11, p_a_3_);
                this.a(p_a_1_, Blocks.STONEBRICK.fromLegacyData(BlockSmoothBrick.P), 10, -2, 11, p_a_3_);
                this.a(p_a_1_, Blocks.LEVER.fromLegacyData(BlockLever.a(EnumDirection.fromType1(this.a(Blocks.LEVER, EnumDirection.NORTH.a())))), 8, -2, 12, p_a_3_);
                this.a(p_a_1_, Blocks.LEVER.fromLegacyData(BlockLever.a(EnumDirection.fromType1(this.a(Blocks.LEVER, EnumDirection.NORTH.a())))), 9, -2, 12, p_a_3_);
                this.a(p_a_1_, Blocks.LEVER.fromLegacyData(BlockLever.a(EnumDirection.fromType1(this.a(Blocks.LEVER, EnumDirection.NORTH.a())))), 10, -2, 12, p_a_3_);
                this.a(p_a_1_, p_a_3_, 8, -3, 8, 8, -3, 10, false, p_a_2_, k);
                this.a(p_a_1_, p_a_3_, 10, -3, 8, 10, -3, 10, false, p_a_2_, k);
                this.a(p_a_1_, Blocks.MOSSY_COBBLESTONE.getBlockData(), 10, -2, 9, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 8, -2, 9, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 8, -2, 10, p_a_3_);
                this.a(p_a_1_, Blocks.REDSTONE_WIRE.getBlockData(), 10, -1, 9, p_a_3_);
                this.a(p_a_1_, Blocks.STICKY_PISTON.fromLegacyData(EnumDirection.UP.a()), 9, -2, 8, p_a_3_);
                this.a(p_a_1_, Blocks.STICKY_PISTON.fromLegacyData(this.a(Blocks.STICKY_PISTON, EnumDirection.WEST.a())), 10, -2, 8, p_a_3_);
                this.a(p_a_1_, Blocks.STICKY_PISTON.fromLegacyData(this.a(Blocks.STICKY_PISTON, EnumDirection.WEST.a())), 10, -1, 8, p_a_3_);
                this.a(p_a_1_, Blocks.UNPOWERED_REPEATER.fromLegacyData(this.a(Blocks.UNPOWERED_REPEATER, EnumDirection.NORTH.b())), 10, -2, 10, p_a_3_);

                if (!this.f)
                {
                    this.f = this.a(p_a_1_, p_a_3_, p_a_2_, 9, -3, 10, StructurePieceTreasure.a(i, new StructurePieceTreasure[] {Items.ENCHANTED_BOOK.b(p_a_2_)}), 2 + p_a_2_.nextInt(5));
                }

                return true;
            }
        }

        static class WorldGenJungleTemplePiece extends StructurePiece.StructurePieceBlockSelector
        {
            private WorldGenJungleTemplePiece()
            {
            }

            public void a(Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, boolean p_a_5_)
            {
                if (p_a_1_.nextFloat() < 0.4F)
                {
                    this.a = Blocks.COBBLESTONE.getBlockData();
                }
                else
                {
                    this.a = Blocks.MOSSY_COBBLESTONE.getBlockData();
                }
            }

            WorldGenJungleTemplePiece(WorldGenRegistration.SyntheticClass_1 p_i387_1_)
            {
                this();
            }
        }
    }

    public static class WorldGenPyramidPiece extends WorldGenRegistration.WorldGenScatteredPiece
    {
        private boolean[] e = new boolean[4];
        private static final List<StructurePieceTreasure> f = Lists.newArrayList(new StructurePieceTreasure[] {new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 2, 7, 15), new StructurePieceTreasure(Items.EMERALD, 0, 1, 3, 2), new StructurePieceTreasure(Items.BONE, 0, 4, 6, 20), new StructurePieceTreasure(Items.ROTTEN_FLESH, 0, 3, 7, 16), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 3), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 1)});

        public WorldGenPyramidPiece()
        {
        }

        public WorldGenPyramidPiece(Random p_i392_1_, int p_i392_2_, int p_i392_3_)
        {
            super(p_i392_1_, p_i392_2_, 64, p_i392_3_, 21, 15, 21);
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("hasPlacedChest0", this.e[0]);
            p_a_1_.setBoolean("hasPlacedChest1", this.e[1]);
            p_a_1_.setBoolean("hasPlacedChest2", this.e[2]);
            p_a_1_.setBoolean("hasPlacedChest3", this.e[3]);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.e[0] = p_b_1_.getBoolean("hasPlacedChest0");
            this.e[1] = p_b_1_.getBoolean("hasPlacedChest1");
            this.e[2] = p_b_1_.getBoolean("hasPlacedChest2");
            this.e[3] = p_b_1_.getBoolean("hasPlacedChest3");
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, -4, 0, this.a - 1, 0, this.c - 1, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);

            for (int i = 1; i <= 9; ++i)
            {
                this.a(p_a_1_, p_a_3_, i, i, i, this.a - 1 - i, i, this.c - 1 - i, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, i + 1, i, i + 1, this.a - 2 - i, i, this.c - 2 - i, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            }

            for (int i21 = 0; i21 < this.a; ++i21)
            {
                for (int j = 0; j < this.c; ++j)
                {
                    byte b0 = -5;
                    this.b(p_a_1_, Blocks.SANDSTONE.getBlockData(), i21, b0, j, p_a_3_);
                }
            }

            int j2 = this.a(Blocks.SANDSTONE_STAIRS, 3);
            int k2 = this.a(Blocks.SANDSTONE_STAIRS, 2);
            int l2 = this.a(Blocks.SANDSTONE_STAIRS, 0);
            int k = this.a(Blocks.SANDSTONE_STAIRS, 1);
            int l = ~EnumColor.ORANGE.getInvColorIndex() & 15;
            int i1 = ~EnumColor.BLUE.getInvColorIndex() & 15;
            this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 9, 4, Blocks.SANDSTONE.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 10, 1, 3, 10, 3, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(j2), 2, 10, 0, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(k2), 2, 10, 4, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(l2), 0, 10, 2, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(k), 4, 10, 2, p_a_3_);
            this.a(p_a_1_, p_a_3_, this.a - 5, 0, 0, this.a - 1, 9, 4, Blocks.SANDSTONE.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, this.a - 4, 10, 1, this.a - 2, 10, 3, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(j2), this.a - 3, 10, 0, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(k2), this.a - 3, 10, 4, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(l2), this.a - 5, 10, 2, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(k), this.a - 1, 10, 2, p_a_3_);
            this.a(p_a_1_, p_a_3_, 8, 0, 0, 12, 4, 4, Blocks.SANDSTONE.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 9, 1, 0, 11, 3, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 9, 1, 1, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 9, 2, 1, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 9, 3, 1, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 10, 3, 1, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 11, 3, 1, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 11, 2, 1, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 11, 1, 1, p_a_3_);
            this.a(p_a_1_, p_a_3_, 4, 1, 1, 8, 3, 3, Blocks.SANDSTONE.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 1, 2, 8, 2, 2, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 12, 1, 1, 16, 3, 3, Blocks.SANDSTONE.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 12, 1, 2, 16, 2, 2, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 4, 5, this.a - 6, 4, this.c - 6, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 9, 4, 9, 11, 4, 11, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 8, 1, 8, 8, 3, 8, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
            this.a(p_a_1_, p_a_3_, 12, 1, 8, 12, 3, 8, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
            this.a(p_a_1_, p_a_3_, 8, 1, 12, 8, 3, 12, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
            this.a(p_a_1_, p_a_3_, 12, 1, 12, 12, 3, 12, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
            this.a(p_a_1_, p_a_3_, 1, 1, 5, 4, 4, 11, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, this.a - 5, 1, 5, this.a - 2, 4, 11, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 7, 9, 6, 7, 11, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, this.a - 7, 7, 9, this.a - 7, 7, 11, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 5, 9, 5, 7, 11, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
            this.a(p_a_1_, p_a_3_, this.a - 6, 5, 9, this.a - 6, 7, 11, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 5, 5, 10, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 5, 6, 10, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 6, 6, 10, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), this.a - 6, 5, 10, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), this.a - 6, 6, 10, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), this.a - 7, 6, 10, p_a_3_);
            this.a(p_a_1_, p_a_3_, 2, 4, 4, 2, 6, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, this.a - 3, 4, 4, this.a - 3, 6, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(j2), 2, 4, 5, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(j2), 2, 3, 4, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(j2), this.a - 3, 4, 5, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(j2), this.a - 3, 3, 4, p_a_3_);
            this.a(p_a_1_, p_a_3_, 1, 1, 3, 2, 2, 3, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, this.a - 3, 1, 3, this.a - 2, 2, 3, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.getBlockData(), 1, 1, 2, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.getBlockData(), this.a - 2, 1, 2, p_a_3_);
            this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SAND.a()), 1, 2, 2, p_a_3_);
            this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SAND.a()), this.a - 2, 2, 2, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(k), 2, 1, 2, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE_STAIRS.fromLegacyData(l2), this.a - 3, 1, 2, p_a_3_);
            this.a(p_a_1_, p_a_3_, 4, 3, 5, 4, 3, 18, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, this.a - 5, 3, 5, this.a - 5, 3, 17, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 3, 1, 5, 4, 2, 16, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, this.a - 6, 1, 5, this.a - 5, 2, 16, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);

            for (int j1 = 5; j1 <= 17; j1 += 2)
            {
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 4, 1, j1, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), 4, 2, j1, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), this.a - 5, 1, j1, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), this.a - 5, 2, j1, p_a_3_);
            }

            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 10, 0, 7, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 10, 0, 8, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 9, 0, 9, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 11, 0, 9, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 8, 0, 10, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 12, 0, 10, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 7, 0, 10, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 13, 0, 10, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 9, 0, 11, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 11, 0, 11, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 10, 0, 12, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 10, 0, 13, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 10, 0, 10, p_a_3_);

            for (int i3 = 0; i3 <= this.a - 1; i3 += this.a - 1)
            {
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), i3, 2, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), i3, 2, 2, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), i3, 2, 3, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), i3, 3, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), i3, 3, 2, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), i3, 3, 3, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), i3, 4, 1, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), i3, 4, 2, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), i3, 4, 3, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), i3, 5, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), i3, 5, 2, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), i3, 5, 3, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), i3, 6, 1, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), i3, 6, 2, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), i3, 6, 3, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), i3, 7, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), i3, 7, 2, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), i3, 7, 3, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), i3, 8, 1, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), i3, 8, 2, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), i3, 8, 3, p_a_3_);
            }

            for (int j3 = 2; j3 <= this.a - 3; j3 += this.a - 3 - 2)
            {
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), j3 - 1, 2, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), j3, 2, 0, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), j3 + 1, 2, 0, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), j3 - 1, 3, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), j3, 3, 0, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), j3 + 1, 3, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), j3 - 1, 4, 0, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), j3, 4, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), j3 + 1, 4, 0, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), j3 - 1, 5, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), j3, 5, 0, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), j3 + 1, 5, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), j3 - 1, 6, 0, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), j3, 6, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), j3 + 1, 6, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), j3 - 1, 7, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), j3, 7, 0, p_a_3_);
                this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), j3 + 1, 7, 0, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), j3 - 1, 8, 0, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), j3, 8, 0, p_a_3_);
                this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), j3 + 1, 8, 0, p_a_3_);
            }

            this.a(p_a_1_, p_a_3_, 8, 4, 0, 12, 6, 0, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 8, 6, 0, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 12, 6, 0, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 9, 5, 0, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), 10, 5, 0, p_a_3_);
            this.a(p_a_1_, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(l), 11, 5, 0, p_a_3_);
            this.a(p_a_1_, p_a_3_, 8, -14, 8, 12, -11, 12, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
            this.a(p_a_1_, p_a_3_, 8, -10, 8, 12, -10, 12, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), false);
            this.a(p_a_1_, p_a_3_, 8, -9, 8, 12, -9, 12, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
            this.a(p_a_1_, p_a_3_, 8, -8, 8, 12, -1, 12, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 9, -11, 9, 11, -1, 11, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, Blocks.STONE_PRESSURE_PLATE.getBlockData(), 10, -11, 10, p_a_3_);
            this.a(p_a_1_, p_a_3_, 9, -13, 9, 11, -13, 11, Blocks.TNT.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 8, -11, 10, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 8, -10, 10, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), 7, -10, 10, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 7, -11, 10, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 12, -11, 10, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 12, -10, 10, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), 13, -10, 10, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 13, -11, 10, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 10, -11, 8, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 10, -10, 8, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), 10, -10, 7, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 10, -11, 7, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 10, -11, 12, p_a_3_);
            this.a(p_a_1_, Blocks.AIR.getBlockData(), 10, -10, 12, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), 10, -10, 13, p_a_3_);
            this.a(p_a_1_, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 10, -11, 13, p_a_3_);

            for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
            {
                if (!this.e[enumdirection.b()])
                {
                    int k1 = enumdirection.getAdjacentX() * 2;
                    int l1 = enumdirection.getAdjacentZ() * 2;
                    this.e[enumdirection.b()] = this.a(p_a_1_, p_a_3_, p_a_2_, 10 + k1, -11, 10 + l1, StructurePieceTreasure.a(f, new StructurePieceTreasure[] {Items.ENCHANTED_BOOK.b(p_a_2_)}), 2 + p_a_2_.nextInt(5));
                }
            }

            return true;
        }
    }

    abstract static class WorldGenScatteredPiece extends StructurePiece
    {
        protected int a;
        protected int b;
        protected int c;
        protected int d = -1;

        public WorldGenScatteredPiece()
        {
        }

        protected WorldGenScatteredPiece(Random p_i389_1_, int p_i389_2_, int p_i389_3_, int p_i389_4_, int p_i389_5_, int p_i389_6_, int p_i389_7_)
        {
            super(0);
            this.a = p_i389_5_;
            this.b = p_i389_6_;
            this.c = p_i389_7_;
            this.m = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(p_i389_1_);

            switch (WorldGenRegistration.SyntheticClass_1.a[this.m.ordinal()])
            {
                case 1:
                case 2:
                    this.l = new StructureBoundingBox(p_i389_2_, p_i389_3_, p_i389_4_, p_i389_2_ + p_i389_5_ - 1, p_i389_3_ + p_i389_6_ - 1, p_i389_4_ + p_i389_7_ - 1);
                    break;

                default:
                    this.l = new StructureBoundingBox(p_i389_2_, p_i389_3_, p_i389_4_, p_i389_2_ + p_i389_7_ - 1, p_i389_3_ + p_i389_6_ - 1, p_i389_4_ + p_i389_5_ - 1);
            }
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            p_a_1_.setInt("Width", this.a);
            p_a_1_.setInt("Height", this.b);
            p_a_1_.setInt("Depth", this.c);
            p_a_1_.setInt("HPos", this.d);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            this.a = p_b_1_.getInt("Width");
            this.b = p_b_1_.getInt("Height");
            this.c = p_b_1_.getInt("Depth");
            this.d = p_b_1_.getInt("HPos");
        }

        protected boolean a(World p_a_1_, StructureBoundingBox p_a_2_, int p_a_3_)
        {
            if (this.d >= 0)
            {
                return true;
            }
            else
            {
                int i = 0;
                int j = 0;
                BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                for (int k = this.l.c; k <= this.l.f; ++k)
                {
                    for (int l = this.l.a; l <= this.l.d; ++l)
                    {
                        blockposition$mutableblockposition.c(l, 64, k);

                        if (p_a_2_.b((BaseBlockPosition)blockposition$mutableblockposition))
                        {
                            i += Math.max(p_a_1_.r(blockposition$mutableblockposition).getY(), p_a_1_.worldProvider.getSeaLevel());
                            ++j;
                        }
                    }
                }

                if (j == 0)
                {
                    return false;
                }
                else
                {
                    this.d = i / j;
                    this.l.a(0, this.d - this.l.b + p_a_3_, 0);
                    return true;
                }
            }
        }
    }

    public static class WorldGenWitchHut extends WorldGenRegistration.WorldGenScatteredPiece
    {
        private boolean e;

        public WorldGenWitchHut()
        {
        }

        public WorldGenWitchHut(Random p_i381_1_, int p_i381_2_, int p_i381_3_)
        {
            super(p_i381_1_, p_i381_2_, 64, p_i381_3_, 7, 7, 9);
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("Witch", this.e);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.e = p_b_1_.getBoolean("Witch");
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (!this.a(p_a_1_, p_a_3_, 0))
            {
                return false;
            }
            else
            {
                this.a(p_a_1_, p_a_3_, 1, 1, 1, 5, 1, 7, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
                this.a(p_a_1_, p_a_3_, 1, 4, 2, 5, 4, 7, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
                this.a(p_a_1_, p_a_3_, 2, 1, 0, 4, 1, 0, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
                this.a(p_a_1_, p_a_3_, 2, 2, 2, 3, 3, 2, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
                this.a(p_a_1_, p_a_3_, 1, 2, 3, 1, 3, 6, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
                this.a(p_a_1_, p_a_3_, 5, 2, 3, 5, 3, 6, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
                this.a(p_a_1_, p_a_3_, 2, 2, 7, 4, 3, 7, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
                this.a(p_a_1_, p_a_3_, 1, 0, 2, 1, 3, 2, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 5, 0, 2, 5, 3, 2, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 1, 0, 7, 1, 3, 7, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 5, 0, 7, 5, 3, 7, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
                this.a(p_a_1_, Blocks.FENCE.getBlockData(), 2, 3, 2, p_a_3_);
                this.a(p_a_1_, Blocks.FENCE.getBlockData(), 3, 3, 7, p_a_3_);
                this.a(p_a_1_, Blocks.AIR.getBlockData(), 1, 3, 4, p_a_3_);
                this.a(p_a_1_, Blocks.AIR.getBlockData(), 5, 3, 4, p_a_3_);
                this.a(p_a_1_, Blocks.AIR.getBlockData(), 5, 3, 5, p_a_3_);
                this.a(p_a_1_, Blocks.FLOWER_POT.getBlockData().set(BlockFlowerPot.CONTENTS, BlockFlowerPot.EnumFlowerPotContents.MUSHROOM_RED), 1, 3, 5, p_a_3_);
                this.a(p_a_1_, Blocks.CRAFTING_TABLE.getBlockData(), 3, 2, 6, p_a_3_);
                this.a(p_a_1_, Blocks.cauldron.getBlockData(), 4, 2, 6, p_a_3_);
                this.a(p_a_1_, Blocks.FENCE.getBlockData(), 1, 2, 1, p_a_3_);
                this.a(p_a_1_, Blocks.FENCE.getBlockData(), 5, 2, 1, p_a_3_);
                int i = this.a(Blocks.OAK_STAIRS, 3);
                int j = this.a(Blocks.OAK_STAIRS, 1);
                int k = this.a(Blocks.OAK_STAIRS, 0);
                int l = this.a(Blocks.OAK_STAIRS, 2);
                this.a(p_a_1_, p_a_3_, 0, 4, 1, 6, 4, 1, Blocks.SPRUCE_STAIRS.fromLegacyData(i), Blocks.SPRUCE_STAIRS.fromLegacyData(i), false);
                this.a(p_a_1_, p_a_3_, 0, 4, 2, 0, 4, 7, Blocks.SPRUCE_STAIRS.fromLegacyData(k), Blocks.SPRUCE_STAIRS.fromLegacyData(k), false);
                this.a(p_a_1_, p_a_3_, 6, 4, 2, 6, 4, 7, Blocks.SPRUCE_STAIRS.fromLegacyData(j), Blocks.SPRUCE_STAIRS.fromLegacyData(j), false);
                this.a(p_a_1_, p_a_3_, 0, 4, 8, 6, 4, 8, Blocks.SPRUCE_STAIRS.fromLegacyData(l), Blocks.SPRUCE_STAIRS.fromLegacyData(l), false);

                for (int i1 = 2; i1 <= 7; i1 += 5)
                {
                    for (int j1 = 1; j1 <= 5; j1 += 4)
                    {
                        this.b(p_a_1_, Blocks.LOG.getBlockData(), j1, -1, i1, p_a_3_);
                    }
                }

                if (!this.e)
                {
                    int l1 = this.a(2, 5);
                    int i2 = this.d(2);
                    int k1 = this.b(2, 5);

                    if (p_a_3_.b((BaseBlockPosition)(new BlockPosition(l1, i2, k1))))
                    {
                        this.e = true;
                        EntityWitch entitywitch = new EntityWitch(p_a_1_);
                        entitywitch.setPositionRotation((double)l1 + 0.5D, (double)i2, (double)k1 + 0.5D, 0.0F, 0.0F);
                        entitywitch.prepare(p_a_1_.E(new BlockPosition(l1, i2, k1)), (GroupDataEntity)null);
                        p_a_1_.addEntity(entitywitch, SpawnReason.CHUNK_GEN);
                    }
                }

                return true;
            }
        }
    }
}
