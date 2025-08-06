package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockFurnace;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerFurnace;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;
import net.minecraft.server.v1_8_R3.IWorldInventory;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemBlock;
import net.minecraft.server.v1_8_R3.ItemHoe;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.ItemSword;
import net.minecraft.server.v1_8_R3.ItemTool;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.RecipesFurnace;
import net.minecraft.server.v1_8_R3.SlotFurnaceFuel;
import net.minecraft.server.v1_8_R3.TileEntityContainer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

public class TileEntityFurnace extends TileEntityContainer implements IUpdatePlayerListBox, IWorldInventory {
   private static final int[] a = new int[1];
   private static final int[] f = new int[]{2, 1};
   private static final int[] g = new int[]{1};
   private ItemStack[] items = new ItemStack[3];
   public int burnTime;
   private int ticksForCurrentFuel;
   public int cookTime;
   private int cookTimeTotal;
   private String m;
   private int lastTick = MinecraftServer.currentTick;
   private int maxStack = 64;
   public List<HumanEntity> transaction = new ArrayList();

   public ItemStack[] getContents() {
      return this.items;
   }

   public void onOpen(CraftHumanEntity p_onOpen_1_) {
      this.transaction.add(p_onOpen_1_);
   }

   public void onClose(CraftHumanEntity p_onClose_1_) {
      this.transaction.remove(p_onClose_1_);
   }

   public List<HumanEntity> getViewers() {
      return this.transaction;
   }

   public void setMaxStackSize(int p_setMaxStackSize_1_) {
      this.maxStack = p_setMaxStackSize_1_;
   }

   public int getSize() {
      return this.items.length;
   }

   public ItemStack getItem(int p_getItem_1_) {
      return this.items[p_getItem_1_];
   }

   public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_) {
      if(this.items[p_splitStack_1_] != null) {
         if(this.items[p_splitStack_1_].count <= p_splitStack_2_) {
            ItemStack itemstack1 = this.items[p_splitStack_1_];
            this.items[p_splitStack_1_] = null;
            return itemstack1;
         } else {
            ItemStack itemstack = this.items[p_splitStack_1_].cloneAndSubtract(p_splitStack_2_);
            if(this.items[p_splitStack_1_].count == 0) {
               this.items[p_splitStack_1_] = null;
            }

            return itemstack;
         }
      } else {
         return null;
      }
   }

   public ItemStack splitWithoutUpdate(int p_splitWithoutUpdate_1_) {
      if(this.items[p_splitWithoutUpdate_1_] != null) {
         ItemStack itemstack = this.items[p_splitWithoutUpdate_1_];
         this.items[p_splitWithoutUpdate_1_] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public void setItem(int p_setItem_1_, ItemStack p_setItem_2_) {
      boolean flag = p_setItem_2_ != null && p_setItem_2_.doMaterialsMatch(this.items[p_setItem_1_]) && ItemStack.equals(p_setItem_2_, this.items[p_setItem_1_]);
      this.items[p_setItem_1_] = p_setItem_2_;
      if(p_setItem_2_ != null && p_setItem_2_.count > this.getMaxStackSize()) {
         p_setItem_2_.count = this.getMaxStackSize();
      }

      if(p_setItem_1_ == 0 && !flag) {
         this.cookTimeTotal = this.a(p_setItem_2_);
         this.cookTime = 0;
         this.update();
      }

   }

   public String getName() {
      return this.hasCustomName()?this.m:"container.furnace";
   }

   public boolean hasCustomName() {
      return this.m != null && this.m.length() > 0;
   }

   public void a(String p_a_1_) {
      this.m = p_a_1_;
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      NBTTagList nbttaglist = p_a_1_.getList("Items", 10);
      this.items = new ItemStack[this.getSize()];

      for(int i = 0; i < nbttaglist.size(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.get(i);
         byte b0 = nbttagcompound.getByte("Slot");
         if(b0 >= 0 && b0 < this.items.length) {
            this.items[b0] = ItemStack.createStack(nbttagcompound);
         }
      }

      this.burnTime = p_a_1_.getShort("BurnTime");
      this.cookTime = p_a_1_.getShort("CookTime");
      this.cookTimeTotal = p_a_1_.getShort("CookTimeTotal");
      this.ticksForCurrentFuel = fuelTime(this.items[1]);
      if(p_a_1_.hasKeyOfType("CustomName", 8)) {
         this.m = p_a_1_.getString("CustomName");
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.setShort("BurnTime", (short)this.burnTime);
      p_b_1_.setShort("CookTime", (short)this.cookTime);
      p_b_1_.setShort("CookTimeTotal", (short)this.cookTimeTotal);
      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < this.items.length; ++i) {
         if(this.items[i] != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("Slot", (byte)i);
            this.items[i].save(nbttagcompound);
            nbttaglist.add(nbttagcompound);
         }
      }

      p_b_1_.set("Items", nbttaglist);
      if(this.hasCustomName()) {
         p_b_1_.setString("CustomName", this.m);
      }

   }

   public int getMaxStackSize() {
      return this.maxStack;
   }

   public boolean isBurning() {
      return this.burnTime > 0;
   }

   public void c() {
      boolean flag = this.w() == Blocks.LIT_FURNACE;
      boolean flag1 = false;
      int i = MinecraftServer.currentTick - this.lastTick;
      this.lastTick = MinecraftServer.currentTick;
      if(this.isBurning() && this.canBurn()) {
         this.cookTime += i;
         if(this.cookTime >= this.cookTimeTotal) {
            this.cookTime = 0;
            this.cookTimeTotal = this.a(this.items[0]);
            this.burn();
            flag1 = true;
         }
      } else {
         this.cookTime = 0;
      }

      if(this.isBurning()) {
         this.burnTime -= i;
      }

      if(!this.world.isClientSide) {
         if(!this.isBurning() && (this.items[1] == null || this.items[0] == null)) {
            if(!this.isBurning() && this.cookTime > 0) {
               this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }
         } else if(this.burnTime <= 0 && this.canBurn()) {
            CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(this.items[1]);
            FurnaceBurnEvent furnaceburnevent = new FurnaceBurnEvent(this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ()), craftitemstack, fuelTime(this.items[1]));
            this.world.getServer().getPluginManager().callEvent(furnaceburnevent);
            if(furnaceburnevent.isCancelled()) {
               return;
            }

            this.ticksForCurrentFuel = furnaceburnevent.getBurnTime();
            this.burnTime += this.ticksForCurrentFuel;
            if(this.burnTime > 0 && furnaceburnevent.isBurning()) {
               flag1 = true;
               if(this.items[1] != null) {
                  --this.items[1].count;
                  if(this.items[1].count == 0) {
                     Item item = this.items[1].getItem().q();
                     this.items[1] = item != null?new ItemStack(item):null;
                  }
               }
            }
         }

         if(flag != this.isBurning()) {
            flag1 = true;
            BlockFurnace.a(this.isBurning(), this.world, this.position);
            this.E();
         }
      }

      if(flag1) {
         this.update();
      }

   }

   public int a(ItemStack p_a_1_) {
      return 200;
   }

   private boolean canBurn() {
      if(this.items[0] == null) {
         return false;
      } else {
         ItemStack itemstack = RecipesFurnace.getInstance().getResult(this.items[0]);
         return itemstack == null?false:(this.items[2] == null?true:(!this.items[2].doMaterialsMatch(itemstack)?false:(this.items[2].count + itemstack.count <= this.getMaxStackSize() && this.items[2].count < this.items[2].getMaxStackSize()?true:this.items[2].count + itemstack.count <= itemstack.getMaxStackSize())));
      }
   }

   public void burn() {
      if(this.canBurn()) {
         ItemStack itemstack = RecipesFurnace.getInstance().getResult(this.items[0]);
         CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(this.items[0]);
         org.bukkit.inventory.ItemStack itemstack1 = CraftItemStack.asBukkitCopy(itemstack);
         FurnaceSmeltEvent furnacesmeltevent = new FurnaceSmeltEvent(this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ()), craftitemstack, itemstack1);
         this.world.getServer().getPluginManager().callEvent(furnacesmeltevent);
         if(furnacesmeltevent.isCancelled()) {
            return;
         }

         itemstack1 = furnacesmeltevent.getResult();
         itemstack = CraftItemStack.asNMSCopy(itemstack1);
         if(itemstack != null) {
            if(this.items[2] == null) {
               this.items[2] = itemstack;
            } else {
               if(!CraftItemStack.asCraftMirror(this.items[2]).isSimilar(itemstack1)) {
                  return;
               }

               this.items[2].count += itemstack.count;
            }
         }

         if(this.items[0].getItem() == Item.getItemOf(Blocks.SPONGE) && this.items[0].getData() == 1 && this.items[1] != null && this.items[1].getItem() == Items.BUCKET) {
            this.items[1] = new ItemStack(Items.WATER_BUCKET);
         }

         --this.items[0].count;
         if(this.items[0].count <= 0) {
            this.items[0] = null;
         }
      }

   }

   public static int fuelTime(ItemStack p_fuelTime_0_) {
      if(p_fuelTime_0_ == null) {
         return 0;
      } else {
         Item item = p_fuelTime_0_.getItem();
         if(item instanceof ItemBlock && Block.asBlock(item) != Blocks.AIR) {
            Block block = Block.asBlock(item);
            if(block == Blocks.WOODEN_SLAB) {
               return 150;
            }

            if(block.getMaterial() == Material.WOOD) {
               return 300;
            }

            if(block == Blocks.COAL_BLOCK) {
               return 16000;
            }
         }

         return item instanceof ItemTool && ((ItemTool)item).h().equals("WOOD")?200:(item instanceof ItemSword && ((ItemSword)item).h().equals("WOOD")?200:(item instanceof ItemHoe && ((ItemHoe)item).g().equals("WOOD")?200:(item == Items.STICK?100:(item == Items.COAL?1600:(item == Items.LAVA_BUCKET?20000:(item == Item.getItemOf(Blocks.SAPLING)?100:(item == Items.BLAZE_ROD?2400:0)))))));
      }
   }

   public static boolean isFuel(ItemStack p_isFuel_0_) {
      return fuelTime(p_isFuel_0_) > 0;
   }

   public boolean a(EntityHuman p_a_1_) {
      return this.world.getTileEntity(this.position) != this?false:p_a_1_.e((double)this.position.getX() + 0.5D, (double)this.position.getY() + 0.5D, (double)this.position.getZ() + 0.5D) <= 64.0D;
   }

   public void startOpen(EntityHuman p_startOpen_1_) {
   }

   public void closeContainer(EntityHuman p_closeContainer_1_) {
   }

   public boolean b(int p_b_1_, ItemStack p_b_2_) {
      return p_b_1_ == 2?false:(p_b_1_ != 1?true:isFuel(p_b_2_) || SlotFurnaceFuel.c_(p_b_2_));
   }

   public int[] getSlotsForFace(EnumDirection p_getSlotsForFace_1_) {
      return p_getSlotsForFace_1_ == EnumDirection.DOWN?f:(p_getSlotsForFace_1_ == EnumDirection.UP?a:g);
   }

   public boolean canPlaceItemThroughFace(int p_canPlaceItemThroughFace_1_, ItemStack p_canPlaceItemThroughFace_2_, EnumDirection p_canPlaceItemThroughFace_3_) {
      return this.b(p_canPlaceItemThroughFace_1_, p_canPlaceItemThroughFace_2_);
   }

   public boolean canTakeItemThroughFace(int p_canTakeItemThroughFace_1_, ItemStack p_canTakeItemThroughFace_2_, EnumDirection p_canTakeItemThroughFace_3_) {
      if(p_canTakeItemThroughFace_3_ == EnumDirection.DOWN && p_canTakeItemThroughFace_1_ == 1) {
         Item item = p_canTakeItemThroughFace_2_.getItem();
         if(item != Items.WATER_BUCKET && item != Items.BUCKET) {
            return false;
         }
      }

      return true;
   }

   public String getContainerName() {
      return "minecraft:furnace";
   }

   public Container createContainer(PlayerInventory p_createContainer_1_, EntityHuman p_createContainer_2_) {
      return new ContainerFurnace(p_createContainer_1_, this);
   }

   public int getProperty(int p_getProperty_1_) {
      switch(p_getProperty_1_) {
      case 0:
         return this.burnTime;
      case 1:
         return this.ticksForCurrentFuel;
      case 2:
         return this.cookTime;
      case 3:
         return this.cookTimeTotal;
      default:
         return 0;
      }
   }

   public void b(int p_b_1_, int p_b_2_) {
      switch(p_b_1_) {
      case 0:
         this.burnTime = p_b_2_;
         break;
      case 1:
         this.ticksForCurrentFuel = p_b_2_;
         break;
      case 2:
         this.cookTime = p_b_2_;
         break;
      case 3:
         this.cookTimeTotal = p_b_2_;
      }

   }

   public int g() {
      return 4;
   }

   public void l() {
      for(int i = 0; i < this.items.length; ++i) {
         this.items[i] = null;
      }

   }
}
