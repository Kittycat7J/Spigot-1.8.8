package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.CraftingManager;
import net.minecraft.server.v1_8_R3.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R3.EntityAgeable;
import net.minecraft.server.v1_8_R3.EntityAnimal;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.GroupDataEntity;
import net.minecraft.server.v1_8_R3.InventoryCraftResult;
import net.minecraft.server.v1_8_R3.InventoryCrafting;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathfinderGoalBreed;
import net.minecraft.server.v1_8_R3.PathfinderGoalEatTile;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalFollowParent;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalPanic;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoalTempt;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.InventoryView;

public class EntitySheep extends EntityAnimal {
   private final InventoryCrafting bm = new InventoryCrafting(new Container() {
      public boolean a(EntityHuman p_a_1_) {
         return false;
      }

      public InventoryView getBukkitView() {
         return null;
      }
   }, 2, 1);
   private static final Map<EnumColor, float[]> bo = Maps.newEnumMap(EnumColor.class);
   private int bp;
   private PathfinderGoalEatTile bq = new PathfinderGoalEatTile(this);

   static {
      bo.put(EnumColor.WHITE, new float[]{1.0F, 1.0F, 1.0F});
      bo.put(EnumColor.ORANGE, new float[]{0.85F, 0.5F, 0.2F});
      bo.put(EnumColor.MAGENTA, new float[]{0.7F, 0.3F, 0.85F});
      bo.put(EnumColor.LIGHT_BLUE, new float[]{0.4F, 0.6F, 0.85F});
      bo.put(EnumColor.YELLOW, new float[]{0.9F, 0.9F, 0.2F});
      bo.put(EnumColor.LIME, new float[]{0.5F, 0.8F, 0.1F});
      bo.put(EnumColor.PINK, new float[]{0.95F, 0.5F, 0.65F});
      bo.put(EnumColor.GRAY, new float[]{0.3F, 0.3F, 0.3F});
      bo.put(EnumColor.SILVER, new float[]{0.6F, 0.6F, 0.6F});
      bo.put(EnumColor.CYAN, new float[]{0.3F, 0.5F, 0.6F});
      bo.put(EnumColor.PURPLE, new float[]{0.5F, 0.25F, 0.7F});
      bo.put(EnumColor.BLUE, new float[]{0.2F, 0.3F, 0.7F});
      bo.put(EnumColor.BROWN, new float[]{0.4F, 0.3F, 0.2F});
      bo.put(EnumColor.GREEN, new float[]{0.4F, 0.5F, 0.2F});
      bo.put(EnumColor.RED, new float[]{0.6F, 0.2F, 0.2F});
      bo.put(EnumColor.BLACK, new float[]{0.1F, 0.1F, 0.1F});
   }

   public static float[] a(EnumColor p_a_0_) {
      return (float[])bo.get(p_a_0_);
   }

   public EntitySheep(World p_i479_1_) {
      super(p_i479_1_);
      this.setSize(0.9F, 1.3F);
      ((Navigation)this.getNavigation()).a(true);
      this.goalSelector.a(0, new PathfinderGoalFloat(this));
      this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
      this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
      this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.1D, Items.WHEAT, false));
      this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.1D));
      this.goalSelector.a(5, this.bq);
      this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
      this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
      this.bm.setItem(0, new ItemStack(Items.DYE, 1, 0));
      this.bm.setItem(1, new ItemStack(Items.DYE, 1, 0));
      this.bm.resultInventory = new InventoryCraftResult();
   }

   protected void E() {
      this.bp = this.bq.f();
      super.E();
   }

   public void m() {
      if(this.world.isClientSide) {
         this.bp = Math.max(0, this.bp - 1);
      }

      super.m();
   }

   protected void initAttributes() {
      super.initAttributes();
      this.getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
      this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.23000000417232513D);
   }

   protected void h() {
      super.h();
      this.datawatcher.a(16, new Byte((byte)0));
   }

   protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_) {
      if(!this.isSheared()) {
         this.a(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, this.getColor().getColorIndex()), 0.0F);
      }

      int i = this.random.nextInt(2) + 1 + this.random.nextInt(1 + p_dropDeathLoot_2_);

      for(int j = 0; j < i; ++j) {
         if(this.isBurning()) {
            this.a(Items.COOKED_MUTTON, 1);
         } else {
            this.a(Items.MUTTON, 1);
         }
      }

   }

   protected Item getLoot() {
      return Item.getItemOf(Blocks.WOOL);
   }

   public boolean a(EntityHuman p_a_1_) {
      ItemStack itemstack = p_a_1_.inventory.getItemInHand();
      if(itemstack != null && itemstack.getItem() == Items.SHEARS && !this.isSheared() && !this.isBaby()) {
         if(!this.world.isClientSide) {
            PlayerShearEntityEvent playershearentityevent = new PlayerShearEntityEvent((Player)p_a_1_.getBukkitEntity(), this.getBukkitEntity());
            this.world.getServer().getPluginManager().callEvent(playershearentityevent);
            if(playershearentityevent.isCancelled()) {
               return false;
            }

            this.setSheared(true);
            int i = 1 + this.random.nextInt(3);

            for(int j = 0; j < i; ++j) {
               EntityItem entityitem = this.a(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, this.getColor().getColorIndex()), 1.0F);
               entityitem.motY += (double)(this.random.nextFloat() * 0.05F);
               entityitem.motX += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
               entityitem.motZ += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
            }
         }

         itemstack.damage(1, p_a_1_);
         this.makeSound("mob.sheep.shear", 1.0F, 1.0F);
      }

      return super.a(p_a_1_);
   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.setBoolean("Sheared", this.isSheared());
      p_b_1_.setByte("Color", (byte)this.getColor().getColorIndex());
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      this.setSheared(p_a_1_.getBoolean("Sheared"));
      this.setColor(EnumColor.fromColorIndex(p_a_1_.getByte("Color")));
   }

   protected String z() {
      return "mob.sheep.say";
   }

   protected String bo() {
      return "mob.sheep.say";
   }

   protected String bp() {
      return "mob.sheep.say";
   }

   protected void a(BlockPosition p_a_1_, Block p_a_2_) {
      this.makeSound("mob.sheep.step", 0.15F, 1.0F);
   }

   public EnumColor getColor() {
      return EnumColor.fromColorIndex(this.datawatcher.getByte(16) & 15);
   }

   public void setColor(EnumColor p_setColor_1_) {
      byte b0 = this.datawatcher.getByte(16);
      this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 240 | p_setColor_1_.getColorIndex() & 15)));
   }

   public boolean isSheared() {
      return (this.datawatcher.getByte(16) & 16) != 0;
   }

   public void setSheared(boolean p_setSheared_1_) {
      byte b0 = this.datawatcher.getByte(16);
      if(p_setSheared_1_) {
         this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 16)));
      } else {
         this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & -17)));
      }

   }

   public static EnumColor a(Random p_a_0_) {
      int i = p_a_0_.nextInt(100);
      return i < 5?EnumColor.BLACK:(i < 10?EnumColor.GRAY:(i < 15?EnumColor.SILVER:(i < 18?EnumColor.BROWN:(p_a_0_.nextInt(500) == 0?EnumColor.PINK:EnumColor.WHITE))));
   }

   public EntitySheep b(EntityAgeable p_b_1_) {
      EntitySheep entitysheep = (EntitySheep)p_b_1_;
      EntitySheep entitysheep1 = new EntitySheep(this.world);
      entitysheep1.setColor(this.a((EntityAnimal)this, (EntityAnimal)entitysheep));
      return entitysheep1;
   }

   public void v() {
      SheepRegrowWoolEvent sheepregrowwoolevent = new SheepRegrowWoolEvent((Sheep)this.getBukkitEntity());
      this.world.getServer().getPluginManager().callEvent(sheepregrowwoolevent);
      if(!sheepregrowwoolevent.isCancelled()) {
         this.setSheared(false);
      }

      if(this.isBaby()) {
         this.setAge(60);
      }

   }

   public GroupDataEntity prepare(DifficultyDamageScaler p_prepare_1_, GroupDataEntity p_prepare_2_) {
      p_prepare_2_ = super.prepare(p_prepare_1_, p_prepare_2_);
      this.setColor(a(this.world.random));
      return p_prepare_2_;
   }

   private EnumColor a(EntityAnimal p_a_1_, EntityAnimal p_a_2_) {
      int i = ((EntitySheep)p_a_1_).getColor().getInvColorIndex();
      int j = ((EntitySheep)p_a_2_).getColor().getInvColorIndex();
      this.bm.getItem(0).setData(i);
      this.bm.getItem(1).setData(j);
      ItemStack itemstack = CraftingManager.getInstance().craft(this.bm, ((EntitySheep)p_a_1_).world);
      int k;
      if(itemstack != null && itemstack.getItem() == Items.DYE) {
         k = itemstack.getData();
      } else {
         k = this.world.random.nextBoolean()?i:j;
      }

      return EnumColor.fromInvColorIndex(k);
   }

   public float getHeadHeight() {
      return 0.95F * this.length;
   }

   public EntityAgeable createChild(EntityAgeable p_createChild_1_) {
      return this.b(p_createChild_1_);
   }
}
