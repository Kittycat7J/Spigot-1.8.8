package net.minecraft.server.v1_8_R3;

import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class ItemMonsterEgg extends Item
{
    public ItemMonsterEgg()
    {
        this.a(true);
        this.a(CreativeModeTab.f);
    }

    public String a(ItemStack p_a_1_)
    {
        String s = LocaleI18n.get(this.getName() + ".name").trim();
        String s1 = EntityTypes.b(p_a_1_.getData());

        if (s1 != null)
        {
            s = s + " " + LocaleI18n.get("entity." + s1 + ".name");
        }

        return s;
    }

    public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_)
    {
        if (p_interactWith_3_.isClientSide)
        {
            return true;
        }
        else if (!p_interactWith_2_.a(p_interactWith_4_.shift(p_interactWith_5_), p_interactWith_5_, p_interactWith_1_))
        {
            return false;
        }
        else
        {
            IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);

            if (iblockdata.getBlock() == Blocks.MOB_SPAWNER)
            {
                TileEntity tileentity = p_interactWith_3_.getTileEntity(p_interactWith_4_);

                if (tileentity instanceof TileEntityMobSpawner)
                {
                    MobSpawnerAbstract mobspawnerabstract = ((TileEntityMobSpawner)tileentity).getSpawner();
                    mobspawnerabstract.setMobName(EntityTypes.b(p_interactWith_1_.getData()));
                    tileentity.update();
                    p_interactWith_3_.notify(p_interactWith_4_);

                    if (!p_interactWith_2_.abilities.canInstantlyBuild)
                    {
                        --p_interactWith_1_.count;
                    }

                    return true;
                }
            }

            p_interactWith_4_ = p_interactWith_4_.shift(p_interactWith_5_);
            double d0 = 0.0D;

            if (p_interactWith_5_ == EnumDirection.UP && iblockdata instanceof BlockFence)
            {
                d0 = 0.5D;
            }

            Entity entity = a(p_interactWith_3_, p_interactWith_1_.getData(), (double)p_interactWith_4_.getX() + 0.5D, (double)p_interactWith_4_.getY() + d0, (double)p_interactWith_4_.getZ() + 0.5D);

            if (entity != null)
            {
                if (entity instanceof EntityLiving && p_interactWith_1_.hasName())
                {
                    entity.setCustomName(p_interactWith_1_.getName());
                }

                if (!p_interactWith_2_.abilities.canInstantlyBuild)
                {
                    --p_interactWith_1_.count;
                }
            }

            return true;
        }
    }

    public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_)
    {
        if (p_a_2_.isClientSide)
        {
            return p_a_1_;
        }
        else
        {
            MovingObjectPosition movingobjectposition = this.a(p_a_2_, p_a_3_, true);

            if (movingobjectposition == null)
            {
                return p_a_1_;
            }
            else
            {
                if (movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK)
                {
                    BlockPosition blockposition = movingobjectposition.a();

                    if (!p_a_2_.a(p_a_3_, blockposition))
                    {
                        return p_a_1_;
                    }

                    if (!p_a_3_.a(blockposition, movingobjectposition.direction, p_a_1_))
                    {
                        return p_a_1_;
                    }

                    if (p_a_2_.getType(blockposition).getBlock() instanceof BlockFluids)
                    {
                        Entity entity = a(p_a_2_, p_a_1_.getData(), (double)blockposition.getX() + 0.5D, (double)blockposition.getY() + 0.5D, (double)blockposition.getZ() + 0.5D);

                        if (entity != null)
                        {
                            if (entity instanceof EntityLiving && p_a_1_.hasName())
                            {
                                ((EntityInsentient)entity).setCustomName(p_a_1_.getName());
                            }

                            if (!p_a_3_.abilities.canInstantlyBuild)
                            {
                                --p_a_1_.count;
                            }

                            p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
                        }
                    }
                }

                return p_a_1_;
            }
        }
    }

    public static Entity a(World p_a_0_, int p_a_1_, double p_a_2_, double p_a_4_, double p_a_6_)
    {
        return spawnCreature(p_a_0_, p_a_1_, p_a_2_, p_a_4_, p_a_6_, SpawnReason.SPAWNER_EGG);
    }

    public static Entity spawnCreature(World p_spawnCreature_0_, int p_spawnCreature_1_, double p_spawnCreature_2_, double p_spawnCreature_4_, double p_spawnCreature_6_, SpawnReason p_spawnCreature_8_)
    {
        if (!EntityTypes.eggInfo.containsKey(Integer.valueOf(p_spawnCreature_1_)))
        {
            return null;
        }
        else
        {
            Entity entity = null;

            for (int i = 0; i < 1; ++i)
            {
                entity = EntityTypes.a(p_spawnCreature_1_, p_spawnCreature_0_);

                if (entity instanceof EntityLiving)
                {
                    EntityInsentient entityinsentient = (EntityInsentient)entity;
                    entity.setPositionRotation(p_spawnCreature_2_, p_spawnCreature_4_, p_spawnCreature_6_, MathHelper.g(p_spawnCreature_0_.random.nextFloat() * 360.0F), 0.0F);
                    entityinsentient.aK = entityinsentient.yaw;
                    entityinsentient.aI = entityinsentient.yaw;
                    entityinsentient.prepare(p_spawnCreature_0_.E(new BlockPosition(entityinsentient)), (GroupDataEntity)null);

                    if (!p_spawnCreature_0_.addEntity(entity, p_spawnCreature_8_))
                    {
                        entity = null;
                    }
                    else
                    {
                        entityinsentient.x();
                    }
                }
            }

            return entity;
        }
    }
}
