package net.minecraft.server.v1_8_R3;

public class SlotResult extends Slot
{
    private final InventoryCrafting a;
    private final EntityHuman b;
    private int c;

    public SlotResult(EntityHuman p_i1253_1_, InventoryCrafting p_i1253_2_, IInventory p_i1253_3_, int p_i1253_4_, int p_i1253_5_, int p_i1253_6_)
    {
        super(p_i1253_3_, p_i1253_4_, p_i1253_5_, p_i1253_6_);
        this.b = p_i1253_1_;
        this.a = p_i1253_2_;
    }

    public boolean isAllowed(ItemStack p_isAllowed_1_)
    {
        return false;
    }

    public ItemStack a(int p_a_1_)
    {
        if (this.hasItem())
        {
            this.c += Math.min(p_a_1_, this.getItem().count);
        }

        return super.a(p_a_1_);
    }

    protected void a(ItemStack p_a_1_, int p_a_2_)
    {
        this.c += p_a_2_;
        this.c(p_a_1_);
    }

    protected void c(ItemStack p_c_1_)
    {
        if (this.c > 0)
        {
            p_c_1_.a(this.b.world, this.b, this.c);
        }

        this.c = 0;

        if (p_c_1_.getItem() == Item.getItemOf(Blocks.CRAFTING_TABLE))
        {
            this.b.b((Statistic)AchievementList.h);
        }

        if (p_c_1_.getItem() instanceof ItemPickaxe)
        {
            this.b.b((Statistic)AchievementList.i);
        }

        if (p_c_1_.getItem() == Item.getItemOf(Blocks.FURNACE))
        {
            this.b.b((Statistic)AchievementList.j);
        }

        if (p_c_1_.getItem() instanceof ItemHoe)
        {
            this.b.b((Statistic)AchievementList.l);
        }

        if (p_c_1_.getItem() == Items.BREAD)
        {
            this.b.b((Statistic)AchievementList.m);
        }

        if (p_c_1_.getItem() == Items.CAKE)
        {
            this.b.b((Statistic)AchievementList.n);
        }

        if (p_c_1_.getItem() instanceof ItemPickaxe && ((ItemPickaxe)p_c_1_.getItem()).g() != Item.EnumToolMaterial.WOOD)
        {
            this.b.b((Statistic)AchievementList.o);
        }

        if (p_c_1_.getItem() instanceof ItemSword)
        {
            this.b.b((Statistic)AchievementList.r);
        }

        if (p_c_1_.getItem() == Item.getItemOf(Blocks.ENCHANTING_TABLE))
        {
            this.b.b((Statistic)AchievementList.E);
        }

        if (p_c_1_.getItem() == Item.getItemOf(Blocks.BOOKSHELF))
        {
            this.b.b((Statistic)AchievementList.G);
        }

        if (p_c_1_.getItem() == Items.GOLDEN_APPLE && p_c_1_.getData() == 1)
        {
            this.b.b((Statistic)AchievementList.M);
        }
    }

    public void a(EntityHuman p_a_1_, ItemStack p_a_2_)
    {
        this.c(p_a_2_);
        ItemStack[] aitemstack = CraftingManager.getInstance().b(this.a, p_a_1_.world);

        for (int i = 0; i < aitemstack.length; ++i)
        {
            ItemStack itemstack = this.a.getItem(i);
            ItemStack itemstack1 = aitemstack[i];

            if (itemstack != null)
            {
                this.a.splitStack(i, 1);
            }

            if (itemstack1 != null)
            {
                if (this.a.getItem(i) == null)
                {
                    this.a.setItem(i, itemstack1);
                }
                else if (!this.b.inventory.pickup(itemstack1))
                {
                    this.b.drop(itemstack1, false);
                }
            }
        }
    }
}
