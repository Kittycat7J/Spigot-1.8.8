package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.FurnaceExtractEvent;

public class SlotFurnaceResult extends Slot
{
    private EntityHuman a;
    private int b;

    public SlotFurnaceResult(EntityHuman p_i16_1_, IInventory p_i16_2_, int p_i16_3_, int p_i16_4_, int p_i16_5_)
    {
        super(p_i16_2_, p_i16_3_, p_i16_4_, p_i16_5_);
        this.a = p_i16_1_;
    }

    public boolean isAllowed(ItemStack p_isAllowed_1_)
    {
        return false;
    }

    public ItemStack a(int p_a_1_)
    {
        if (this.hasItem())
        {
            this.b += Math.min(p_a_1_, this.getItem().count);
        }

        return super.a(p_a_1_);
    }

    public void a(EntityHuman p_a_1_, ItemStack p_a_2_)
    {
        this.c(p_a_2_);
        super.a(p_a_1_, p_a_2_);
    }

    protected void a(ItemStack p_a_1_, int p_a_2_)
    {
        this.b += p_a_2_;
        this.c(p_a_1_);
    }

    protected void c(ItemStack p_c_1_)
    {
        p_c_1_.a(this.a.world, this.a, this.b);

        if (!this.a.world.isClientSide)
        {
            int i = this.b;
            float f = RecipesFurnace.getInstance().b(p_c_1_);

            if (f == 0.0F)
            {
                i = 0;
            }
            else if (f < 1.0F)
            {
                int j = MathHelper.d((float)i * f);

                if (j < MathHelper.f((float)i * f) && Math.random() < (double)((float)i * f - (float)j))
                {
                    ++j;
                }

                i = j;
            }

            Player player = (Player)this.a.getBukkitEntity();
            TileEntityFurnace tileentityfurnace = (TileEntityFurnace)this.inventory;
            org.bukkit.block.Block block = this.a.world.getWorld().getBlockAt(tileentityfurnace.position.getX(), tileentityfurnace.position.getY(), tileentityfurnace.position.getZ());

            if (this.b != 0)
            {
                FurnaceExtractEvent furnaceextractevent = new FurnaceExtractEvent(player, block, CraftMagicNumbers.getMaterial(p_c_1_.getItem()), this.b, i);
                this.a.world.getServer().getPluginManager().callEvent(furnaceextractevent);
                i = furnaceextractevent.getExpToDrop();
            }

            while (i > 0)
            {
                int k = EntityExperienceOrb.getOrbValue(i);
                i -= k;
                this.a.world.addEntity(new EntityExperienceOrb(this.a.world, this.a.locX, this.a.locY + 0.5D, this.a.locZ + 0.5D, k));
            }
        }

        this.b = 0;

        if (p_c_1_.getItem() == Items.IRON_INGOT)
        {
            this.a.b((Statistic)AchievementList.k);
        }

        if (p_c_1_.getItem() == Items.COOKED_FISH)
        {
            this.a.b((Statistic)AchievementList.p);
        }
    }
}
