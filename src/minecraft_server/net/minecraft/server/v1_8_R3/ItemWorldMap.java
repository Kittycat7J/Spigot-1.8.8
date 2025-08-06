package net.minecraft.server.v1_8_R3;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multisets;
import org.bukkit.Bukkit;
import org.bukkit.event.server.MapInitializeEvent;

public class ItemWorldMap extends ItemWorldMapBase
{
    protected ItemWorldMap()
    {
        this.a(true);
    }

    public WorldMap getSavedMap(ItemStack p_getSavedMap_1_, World p_getSavedMap_2_)
    {
        World world = (World)p_getSavedMap_2_.getServer().getServer().worlds.get(0);
        String s = "map_" + p_getSavedMap_1_.getData();
        WorldMap worldmap = (WorldMap)world.a(WorldMap.class, s);

        if (worldmap == null && !p_getSavedMap_2_.isClientSide)
        {
            p_getSavedMap_1_.setData(world.b("map"));
            s = "map_" + p_getSavedMap_1_.getData();
            worldmap = new WorldMap(s);
            worldmap.scale = 3;
            worldmap.a((double)p_getSavedMap_2_.getWorldData().c(), (double)p_getSavedMap_2_.getWorldData().e(), worldmap.scale);
            worldmap.map = (byte)((WorldServer)p_getSavedMap_2_).dimension;
            worldmap.c();
            world.a((String)s, (PersistentBase)worldmap);
            MapInitializeEvent mapinitializeevent = new MapInitializeEvent(worldmap.mapView);
            Bukkit.getServer().getPluginManager().callEvent(mapinitializeevent);
        }

        return worldmap;
    }

    public void a(World p_a_1_, Entity p_a_2_, WorldMap p_a_3_)
    {
        if (((WorldServer)p_a_1_).dimension == p_a_3_.map && p_a_2_ instanceof EntityHuman)
        {
            int i = 1 << p_a_3_.scale;
            int j = p_a_3_.centerX;
            int k = p_a_3_.centerZ;
            int l = MathHelper.floor(p_a_2_.locX - (double)j) / i + 64;
            int i1 = MathHelper.floor(p_a_2_.locZ - (double)k) / i + 64;
            int j1 = 128 / i;

            if (p_a_1_.worldProvider.o())
            {
                j1 /= 2;
            }

            WorldMap.WorldMapHumanTracker worldmap$worldmaphumantracker = p_a_3_.a((EntityHuman)p_a_2_);
            ++worldmap$worldmaphumantracker.b;
            boolean flag = false;

            for (int k1 = l - j1 + 1; k1 < l + j1; ++k1)
            {
                if ((k1 & 15) == (worldmap$worldmaphumantracker.b & 15) || flag)
                {
                    flag = false;
                    double d0 = 0.0D;

                    for (int l1 = i1 - j1 - 1; l1 < i1 + j1; ++l1)
                    {
                        if (k1 >= 0 && l1 >= -1 && k1 < 128 && l1 < 128)
                        {
                            int i2 = k1 - l;
                            int j2 = l1 - i1;
                            boolean flag1 = i2 * i2 + j2 * j2 > (j1 - 2) * (j1 - 2);
                            int k2 = (j / i + k1 - 64) * i;
                            int l2 = (k / i + l1 - 64) * i;
                            HashMultiset hashmultiset = HashMultiset.create();
                            Chunk chunk = p_a_1_.getChunkAtWorldCoords(new BlockPosition(k2, 0, l2));

                            if (!chunk.isEmpty())
                            {
                                int i3 = k2 & 15;
                                int j3 = l2 & 15;
                                int k3 = 0;
                                double d1 = 0.0D;

                                if (p_a_1_.worldProvider.o())
                                {
                                    int l3 = k2 + l2 * 231871;
                                    l3 = l3 * l3 * 31287121 + l3 * 11;

                                    if ((l3 >> 20 & 1) == 0)
                                    {
                                        hashmultiset.add(Blocks.DIRT.g(Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.DIRT)), 10);
                                    }
                                    else
                                    {
                                        hashmultiset.add(Blocks.STONE.g(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.STONE)), 100);
                                    }

                                    d1 = 100.0D;
                                }
                                else
                                {
                                    BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                                    for (int i4 = 0; i4 < i; ++i4)
                                    {
                                        for (int j4 = 0; j4 < i; ++j4)
                                        {
                                            int k4 = chunk.b(i4 + i3, j4 + j3) + 1;
                                            IBlockData iblockdata = Blocks.AIR.getBlockData();

                                            if (k4 > 1)
                                            {
                                                label505:
                                                {
                                                    while (true)
                                                    {
                                                        --k4;
                                                        iblockdata = chunk.getBlockData(blockposition$mutableblockposition.c(i4 + i3, k4, j4 + j3));

                                                        if (iblockdata.getBlock().g(iblockdata) != MaterialMapColor.b || k4 <= 0)
                                                        {
                                                            break;
                                                        }
                                                    }

                                                    if (k4 > 0 && iblockdata.getBlock().getMaterial().isLiquid())
                                                    {
                                                        int l4 = k4 - 1;

                                                        while (true)
                                                        {
                                                            Block block = chunk.getTypeAbs(i4 + i3, l4--, j4 + j3);
                                                            ++k3;

                                                            if (l4 <= 0 || !block.getMaterial().isLiquid())
                                                            {
                                                                break label505;
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            d1 += (double)k4 / (double)(i * i);
                                            hashmultiset.add(iblockdata.getBlock().g(iblockdata));
                                        }
                                    }
                                }

                                k3 = k3 / (i * i);
                                double d2 = (d1 - d0) * 4.0D / (double)(i + 4) + ((double)(k1 + l1 & 1) - 0.5D) * 0.4D;
                                byte b0 = 1;

                                if (d2 > 0.6D)
                                {
                                    b0 = 2;
                                }

                                if (d2 < -0.6D)
                                {
                                    b0 = 0;
                                }

                                MaterialMapColor materialmapcolor = (MaterialMapColor)Iterables.getFirst(Multisets.<T>copyHighestCountFirst(hashmultiset), MaterialMapColor.b);

                                if (materialmapcolor == MaterialMapColor.n)
                                {
                                    d2 = (double)k3 * 0.1D + (double)(k1 + l1 & 1) * 0.2D;
                                    b0 = 1;

                                    if (d2 < 0.5D)
                                    {
                                        b0 = 2;
                                    }

                                    if (d2 > 0.9D)
                                    {
                                        b0 = 0;
                                    }
                                }

                                d0 = d1;

                                if (l1 >= 0 && i2 * i2 + j2 * j2 < j1 * j1 && (!flag1 || (k1 + l1 & 1) != 0))
                                {
                                    byte b1 = p_a_3_.colors[k1 + l1 * 128];
                                    byte b2 = (byte)(materialmapcolor.M * 4 + b0);

                                    if (b1 != b2)
                                    {
                                        p_a_3_.colors[k1 + l1 * 128] = b2;
                                        p_a_3_.flagDirty(k1, l1);
                                        flag = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void a(ItemStack p_a_1_, World p_a_2_, Entity p_a_3_, int p_a_4_, boolean p_a_5_)
    {
        if (!p_a_2_.isClientSide)
        {
            WorldMap worldmap = this.getSavedMap(p_a_1_, p_a_2_);

            if (p_a_3_ instanceof EntityHuman)
            {
                EntityHuman entityhuman = (EntityHuman)p_a_3_;
                worldmap.a(entityhuman, p_a_1_);
            }

            if (p_a_5_)
            {
                this.a(p_a_2_, p_a_3_, worldmap);
            }
        }
    }

    public Packet c(ItemStack p_c_1_, World p_c_2_, EntityHuman p_c_3_)
    {
        return this.getSavedMap(p_c_1_, p_c_2_).a(p_c_1_, p_c_2_, p_c_3_);
    }

    public void d(ItemStack p_d_1_, World p_d_2_, EntityHuman p_d_3_)
    {
        if (p_d_1_.hasTag() && p_d_1_.getTag().getBoolean("map_is_scaling"))
        {
            WorldMap worldmap = Items.FILLED_MAP.getSavedMap(p_d_1_, p_d_2_);
            p_d_2_ = (World)p_d_2_.getServer().getServer().worlds.get(0);
            p_d_1_.setData(p_d_2_.b("map"));
            WorldMap worldmap1 = new WorldMap("map_" + p_d_1_.getData());
            worldmap1.scale = (byte)(worldmap.scale + 1);

            if (worldmap1.scale > 4)
            {
                worldmap1.scale = 4;
            }

            worldmap1.a((double)worldmap.centerX, (double)worldmap.centerZ, worldmap1.scale);
            worldmap1.map = worldmap.map;
            worldmap1.c();
            p_d_2_.a((String)("map_" + p_d_1_.getData()), (PersistentBase)worldmap1);
            MapInitializeEvent mapinitializeevent = new MapInitializeEvent(worldmap1.mapView);
            Bukkit.getServer().getPluginManager().callEvent(mapinitializeevent);
        }
    }
}
