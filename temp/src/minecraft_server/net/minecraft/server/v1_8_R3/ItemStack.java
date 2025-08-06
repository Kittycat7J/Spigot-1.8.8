package net.minecraft.server.v1_8_R3;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockContainer;
import net.minecraft.server.v1_8_R3.BlockJukeBox;
import net.minecraft.server.v1_8_R3.BlockMushroom;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockSapling;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatHoverable;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentDurability;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItemFrame;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumAnimation;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumItemRarity;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemBow;
import net.minecraft.server.v1_8_R3.ItemBucket;
import net.minecraft.server.v1_8_R3.ItemDye;
import net.minecraft.server.v1_8_R3.ItemRecord;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntitySkull;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.world.StructureGrowEvent;

public final class ItemStack {
   public static final DecimalFormat a = new DecimalFormat("#.###");
   public int count;
   public int c;
   private Item item;
   private NBTTagCompound tag;
   private int damage;
   private EntityItemFrame g;
   private Block h;
   private boolean i;
   private Block j;
   private boolean k;

   public ItemStack(Block p_i300_1_) {
      this((Block)p_i300_1_, 1);
   }

   public ItemStack(Block p_i301_1_, int p_i301_2_) {
      this((Block)p_i301_1_, p_i301_2_, 0);
   }

   public ItemStack(Block p_i302_1_, int p_i302_2_, int p_i302_3_) {
      this(Item.getItemOf(p_i302_1_), p_i302_2_, p_i302_3_);
   }

   public ItemStack(Item p_i303_1_) {
      this((Item)p_i303_1_, 1);
   }

   public ItemStack(Item p_i304_1_, int p_i304_2_) {
      this((Item)p_i304_1_, p_i304_2_, 0);
   }

   public ItemStack(Item p_i305_1_, int p_i305_2_, int p_i305_3_) {
      this.h = null;
      this.i = false;
      this.j = null;
      this.k = false;
      this.item = p_i305_1_;
      this.count = p_i305_2_;
      this.setData(p_i305_3_);
   }

   public static ItemStack createStack(NBTTagCompound p_createStack_0_) {
      ItemStack itemstack = new ItemStack();
      itemstack.c(p_createStack_0_);
      return itemstack.getItem() != null?itemstack:null;
   }

   private ItemStack() {
      this.h = null;
      this.i = false;
      this.j = null;
      this.k = false;
   }

   public ItemStack cloneAndSubtract(int p_cloneAndSubtract_1_) {
      ItemStack itemstack = new ItemStack(this.item, p_cloneAndSubtract_1_, this.damage);
      if(this.tag != null) {
         itemstack.tag = (NBTTagCompound)this.tag.clone();
      }

      this.count -= p_cloneAndSubtract_1_;
      return itemstack;
   }

   public Item getItem() {
      return this.item;
   }

   public boolean placeItem(EntityHuman p_placeItem_1_, World p_placeItem_2_, BlockPosition p_placeItem_3_, EnumDirection p_placeItem_4_, float p_placeItem_5_, float p_placeItem_6_, float p_placeItem_7_) {
      int i = this.getData();
      int j = this.count;
      if(!(this.getItem() instanceof ItemBucket)) {
         p_placeItem_2_.captureBlockStates = true;
         if(this.getItem() instanceof ItemDye && this.getData() == 15) {
            Block block = p_placeItem_2_.getType(p_placeItem_3_).getBlock();
            if(block == Blocks.SAPLING || block instanceof BlockMushroom) {
               p_placeItem_2_.captureTreeGeneration = true;
            }
         }
      }

      boolean flag = this.getItem().interactWith(this, p_placeItem_1_, p_placeItem_2_, p_placeItem_3_, p_placeItem_4_, p_placeItem_5_, p_placeItem_6_, p_placeItem_7_);
      int k = this.getData();
      int l = this.count;
      this.count = j;
      this.setData(i);
      p_placeItem_2_.captureBlockStates = false;
      if(flag && p_placeItem_2_.captureTreeGeneration && p_placeItem_2_.capturedBlockStates.size() > 0) {
         p_placeItem_2_.captureTreeGeneration = false;
         Location location = new Location(p_placeItem_2_.getWorld(), (double)p_placeItem_3_.getX(), (double)p_placeItem_3_.getY(), (double)p_placeItem_3_.getZ());
         TreeType treetype = BlockSapling.treeType;
         BlockSapling.treeType = null;
         List<org.bukkit.block.BlockState> list1 = (List)p_placeItem_2_.capturedBlockStates.clone();
         p_placeItem_2_.capturedBlockStates.clear();
         StructureGrowEvent structuregrowevent = null;
         if(treetype != null) {
            boolean flag1 = this.getItem() == Items.DYE && i == 15;
            structuregrowevent = new StructureGrowEvent(location, treetype, flag1, (Player)p_placeItem_1_.getBukkitEntity(), list1);
            Bukkit.getPluginManager().callEvent(structuregrowevent);
         }

         if(structuregrowevent == null || !structuregrowevent.isCancelled()) {
            if(this.count == j && this.getData() == i) {
               this.setData(k);
               this.count = l;
            }

            for(org.bukkit.block.BlockState blockstate2 : list1) {
               blockstate2.update(true);
            }
         }

         return flag;
      } else {
         p_placeItem_2_.captureTreeGeneration = false;
         if(flag) {
            BlockPlaceEvent blockplaceevent = null;
            List<org.bukkit.block.BlockState> list = (List)p_placeItem_2_.capturedBlockStates.clone();
            p_placeItem_2_.capturedBlockStates.clear();
            if(list.size() > 1) {
               blockplaceevent = CraftEventFactory.callBlockMultiPlaceEvent(p_placeItem_2_, p_placeItem_1_, list, p_placeItem_3_.getX(), p_placeItem_3_.getY(), p_placeItem_3_.getZ());
            } else if(list.size() == 1) {
               blockplaceevent = CraftEventFactory.callBlockPlaceEvent(p_placeItem_2_, p_placeItem_1_, (org.bukkit.block.BlockState)list.get(0), p_placeItem_3_.getX(), p_placeItem_3_.getY(), p_placeItem_3_.getZ());
            }

            if(blockplaceevent != null && (blockplaceevent.isCancelled() || !blockplaceevent.canBuild())) {
               flag = false;

               for(org.bukkit.block.BlockState blockstate1 : list) {
                  blockstate1.update(true, false);
               }
            } else {
               if(this.count == j && this.getData() == i) {
                  this.setData(k);
                  this.count = l;
               }

               for(org.bukkit.block.BlockState blockstate : list) {
                  int i1 = blockstate.getX();
                  int j1 = blockstate.getY();
                  int k1 = blockstate.getZ();
                  int l1 = ((CraftBlockState)blockstate).getFlag();
                  org.bukkit.Material material = blockstate.getType();
                  Block block1 = CraftMagicNumbers.getBlock(material);
                  BlockPosition blockposition = new BlockPosition(i1, j1, k1);
                  IBlockData iblockdata = p_placeItem_2_.getType(blockposition);
                  if(!(iblockdata instanceof BlockContainer)) {
                     iblockdata.getBlock().onPlace(p_placeItem_2_, blockposition, iblockdata);
                  }

                  p_placeItem_2_.notifyAndUpdatePhysics(blockposition, (Chunk)null, block1, iblockdata.getBlock(), l1);
               }

               for(Entry<BlockPosition, TileEntity> entry : p_placeItem_2_.capturedTileEntities.entrySet()) {
                  p_placeItem_2_.setTileEntity((BlockPosition)entry.getKey(), (TileEntity)entry.getValue());
               }

               if(this.getItem() instanceof ItemRecord) {
                  ((BlockJukeBox)Blocks.JUKEBOX).a(p_placeItem_2_, p_placeItem_3_, p_placeItem_2_.getType(p_placeItem_3_), this);
                  p_placeItem_2_.a((EntityHuman)null, 1005, p_placeItem_3_, Item.getId(this.getItem()));
                  --this.count;
                  p_placeItem_1_.b(StatisticList.X);
               }

               if(this.getItem() == Items.SKULL) {
                  BlockPosition blockposition1 = p_placeItem_3_;
                  if(!p_placeItem_2_.getType(p_placeItem_3_).getBlock().a(p_placeItem_2_, p_placeItem_3_)) {
                     if(!p_placeItem_2_.getType(p_placeItem_3_).getBlock().getMaterial().isBuildable()) {
                        blockposition1 = null;
                     } else {
                        blockposition1 = p_placeItem_3_.shift(p_placeItem_4_);
                     }
                  }

                  if(blockposition1 != null) {
                     TileEntity tileentity = p_placeItem_2_.getTileEntity(blockposition1);
                     if(tileentity instanceof TileEntitySkull) {
                        Blocks.SKULL.a(p_placeItem_2_, blockposition1, (TileEntitySkull)tileentity);
                     }
                  }
               }

               p_placeItem_1_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this.item)]);
            }
         }

         p_placeItem_2_.capturedTileEntities.clear();
         p_placeItem_2_.capturedBlockStates.clear();
         return flag;
      }
   }

   public float a(Block p_a_1_) {
      return this.getItem().getDestroySpeed(this, p_a_1_);
   }

   public ItemStack a(World p_a_1_, EntityHuman p_a_2_) {
      return this.getItem().a(this, p_a_1_, p_a_2_);
   }

   public ItemStack b(World p_b_1_, EntityHuman p_b_2_) {
      return this.getItem().b(this, p_b_1_, p_b_2_);
   }

   public NBTTagCompound save(NBTTagCompound p_save_1_) {
      MinecraftKey minecraftkey = (MinecraftKey)Item.REGISTRY.c(this.item);
      p_save_1_.setString("id", minecraftkey == null?"minecraft:air":minecraftkey.toString());
      p_save_1_.setByte("Count", (byte)this.count);
      p_save_1_.setShort("Damage", (short)this.damage);
      if(this.tag != null) {
         p_save_1_.set("tag", this.tag.clone());
      }

      return p_save_1_;
   }

   public void c(NBTTagCompound p_c_1_) {
      if(p_c_1_.hasKeyOfType("id", 8)) {
         this.item = Item.d(p_c_1_.getString("id"));
      } else {
         this.item = Item.getById(p_c_1_.getShort("id"));
      }

      this.count = p_c_1_.getByte("Count");
      this.setData(p_c_1_.getShort("Damage"));
      if(p_c_1_.hasKeyOfType("tag", 10)) {
         this.tag = (NBTTagCompound)p_c_1_.getCompound("tag").clone();
         if(this.item != null) {
            this.item.a(this.tag);
         }
      }

   }

   public int getMaxStackSize() {
      return this.getItem().getMaxStackSize();
   }

   public boolean isStackable() {
      return this.getMaxStackSize() > 1 && (!this.e() || !this.g());
   }

   public boolean e() {
      return this.item.getMaxDurability() <= 0?false:!this.hasTag() || !this.getTag().getBoolean("Unbreakable");
   }

   public boolean usesData() {
      return this.item.k();
   }

   public boolean g() {
      return this.e() && this.damage > 0;
   }

   public int h() {
      return this.damage;
   }

   public int getData() {
      return this.damage;
   }

   public void setData(int p_setData_1_) {
      if(p_setData_1_ == 32767) {
         this.damage = p_setData_1_;
      } else {
         if(CraftMagicNumbers.getBlock(CraftMagicNumbers.getId(this.getItem())) != Blocks.AIR && !this.usesData() && !this.getItem().usesDurability()) {
            p_setData_1_ = 0;
         }

         if(CraftMagicNumbers.getBlock(CraftMagicNumbers.getId(this.getItem())) == Blocks.DOUBLE_PLANT && (p_setData_1_ > 5 || p_setData_1_ < 0)) {
            p_setData_1_ = 0;
         }

         this.damage = p_setData_1_;
         if(this.damage < -1) {
            this.damage = 0;
         }

      }
   }

   public int j() {
      return this.item.getMaxDurability();
   }

   public boolean isDamaged(int p_isDamaged_1_, Random p_isDamaged_2_) {
      return this.isDamaged(p_isDamaged_1_, p_isDamaged_2_, (EntityLiving)null);
   }

   public boolean isDamaged(int p_isDamaged_1_, Random p_isDamaged_2_, EntityLiving p_isDamaged_3_) {
      if(!this.e()) {
         return false;
      } else {
         if(p_isDamaged_1_ > 0) {
            int i = EnchantmentManager.getEnchantmentLevel(Enchantment.DURABILITY.id, this);
            int j = 0;

            for(int k = 0; i > 0 && k < p_isDamaged_1_; ++k) {
               if(EnchantmentDurability.a(this, i, p_isDamaged_2_)) {
                  ++j;
               }
            }

            p_isDamaged_1_ -= j;
            if(p_isDamaged_3_ instanceof EntityPlayer) {
               CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(this);
               PlayerItemDamageEvent playeritemdamageevent = new PlayerItemDamageEvent((Player)p_isDamaged_3_.getBukkitEntity(), craftitemstack, p_isDamaged_1_);
               Bukkit.getServer().getPluginManager().callEvent(playeritemdamageevent);
               if(playeritemdamageevent.isCancelled()) {
                  return false;
               }

               p_isDamaged_1_ = playeritemdamageevent.getDamage();
            }

            if(p_isDamaged_1_ <= 0) {
               return false;
            }
         }

         this.damage += p_isDamaged_1_;
         return this.damage > this.j();
      }
   }

   public void damage(int p_damage_1_, EntityLiving p_damage_2_) {
      if((!(p_damage_2_ instanceof EntityHuman) || !((EntityHuman)p_damage_2_).abilities.canInstantlyBuild) && this.e() && this.isDamaged(p_damage_1_, p_damage_2_.bc(), p_damage_2_)) {
         p_damage_2_.b(this);
         --this.count;
         if(p_damage_2_ instanceof EntityHuman) {
            EntityHuman entityhuman = (EntityHuman)p_damage_2_;
            entityhuman.b(StatisticList.BREAK_ITEM_COUNT[Item.getId(this.item)]);
            if(this.count == 0 && this.getItem() instanceof ItemBow) {
               entityhuman.ca();
            }
         }

         if(this.count < 0) {
            this.count = 0;
         }

         if(this.count == 0 && p_damage_2_ instanceof EntityHuman) {
            CraftEventFactory.callPlayerItemBreakEvent((EntityHuman)p_damage_2_, this);
         }

         this.damage = 0;
      }

   }

   public void a(EntityLiving p_a_1_, EntityHuman p_a_2_) {
      boolean flag = this.item.a(this, (EntityLiving)p_a_1_, (EntityLiving)p_a_2_);
      if(flag) {
         p_a_2_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this.item)]);
      }

   }

   public void a(World p_a_1_, Block p_a_2_, BlockPosition p_a_3_, EntityHuman p_a_4_) {
      boolean flag = this.item.a(this, p_a_1_, p_a_2_, p_a_3_, p_a_4_);
      if(flag) {
         p_a_4_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this.item)]);
      }

   }

   public boolean b(Block p_b_1_) {
      return this.item.canDestroySpecialBlock(p_b_1_);
   }

   public boolean a(EntityHuman p_a_1_, EntityLiving p_a_2_) {
      return this.item.a(this, p_a_1_, p_a_2_);
   }

   public ItemStack cloneItemStack() {
      ItemStack itemstack = new ItemStack(this.item, this.count, this.damage);
      if(this.tag != null) {
         itemstack.tag = (NBTTagCompound)this.tag.clone();
      }

      return itemstack;
   }

   public static boolean equals(ItemStack p_equals_0_, ItemStack p_equals_1_) {
      return p_equals_0_ == null && p_equals_1_ == null?true:(p_equals_0_ != null && p_equals_1_ != null?(p_equals_0_.tag == null && p_equals_1_.tag != null?false:p_equals_0_.tag == null || p_equals_0_.tag.equals(p_equals_1_.tag)):false);
   }

   public static boolean fastMatches(ItemStack p_fastMatches_0_, ItemStack p_fastMatches_1_) {
      return p_fastMatches_0_ == null && p_fastMatches_1_ == null?true:(p_fastMatches_0_ != null && p_fastMatches_1_ != null?p_fastMatches_0_.count == p_fastMatches_1_.count && p_fastMatches_0_.item == p_fastMatches_1_.item && p_fastMatches_0_.damage == p_fastMatches_1_.damage:false);
   }

   public static boolean matches(ItemStack p_matches_0_, ItemStack p_matches_1_) {
      return p_matches_0_ == null && p_matches_1_ == null?true:(p_matches_0_ != null && p_matches_1_ != null?p_matches_0_.d(p_matches_1_):false);
   }

   private boolean d(ItemStack p_d_1_) {
      return this.count != p_d_1_.count?false:(this.item != p_d_1_.item?false:(this.damage != p_d_1_.damage?false:(this.tag == null && p_d_1_.tag != null?false:this.tag == null || this.tag.equals(p_d_1_.tag))));
   }

   public static boolean c(ItemStack p_c_0_, ItemStack p_c_1_) {
      return p_c_0_ == null && p_c_1_ == null?true:(p_c_0_ != null && p_c_1_ != null?p_c_0_.doMaterialsMatch(p_c_1_):false);
   }

   public boolean doMaterialsMatch(ItemStack p_doMaterialsMatch_1_) {
      return p_doMaterialsMatch_1_ != null && this.item == p_doMaterialsMatch_1_.item && this.damage == p_doMaterialsMatch_1_.damage;
   }

   public String a() {
      return this.item.e_(this);
   }

   public static ItemStack b(ItemStack p_b_0_) {
      return p_b_0_ == null?null:p_b_0_.cloneItemStack();
   }

   public String toString() {
      return this.count + "x" + this.item.getName() + "@" + this.damage;
   }

   public void a(World p_a_1_, Entity p_a_2_, int p_a_3_, boolean p_a_4_) {
      if(this.c > 0) {
         --this.c;
      }

      this.item.a(this, p_a_1_, p_a_2_, p_a_3_, p_a_4_);
   }

   public void a(World p_a_1_, EntityHuman p_a_2_, int p_a_3_) {
      p_a_2_.a(StatisticList.CRAFT_BLOCK_COUNT[Item.getId(this.item)], p_a_3_);
      this.item.d(this, p_a_1_, p_a_2_);
   }

   public int l() {
      return this.getItem().d(this);
   }

   public EnumAnimation m() {
      return this.getItem().e(this);
   }

   public void b(World p_b_1_, EntityHuman p_b_2_, int p_b_3_) {
      this.getItem().a(this, p_b_1_, p_b_2_, p_b_3_);
   }

   public boolean hasTag() {
      return this.tag != null;
   }

   public NBTTagCompound getTag() {
      return this.tag;
   }

   public NBTTagCompound a(String p_a_1_, boolean p_a_2_) {
      if(this.tag != null && this.tag.hasKeyOfType(p_a_1_, 10)) {
         return this.tag.getCompound(p_a_1_);
      } else if(p_a_2_) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         this.a((String)p_a_1_, (NBTBase)nbttagcompound);
         return nbttagcompound;
      } else {
         return null;
      }
   }

   public NBTTagList getEnchantments() {
      return this.tag == null?null:this.tag.getList("ench", 10);
   }

   public void setTag(NBTTagCompound p_setTag_1_) {
      this.tag = p_setTag_1_;
   }

   public String getName() {
      String s = this.getItem().a(this);
      if(this.tag != null && this.tag.hasKeyOfType("display", 10)) {
         NBTTagCompound nbttagcompound = this.tag.getCompound("display");
         if(nbttagcompound.hasKeyOfType("Name", 8)) {
            s = nbttagcompound.getString("Name");
         }
      }

      return s;
   }

   public ItemStack c(String p_c_1_) {
      if(this.tag == null) {
         this.tag = new NBTTagCompound();
      }

      if(!this.tag.hasKeyOfType("display", 10)) {
         this.tag.set("display", new NBTTagCompound());
      }

      this.tag.getCompound("display").setString("Name", p_c_1_);
      return this;
   }

   public void r() {
      if(this.tag != null && this.tag.hasKeyOfType("display", 10)) {
         NBTTagCompound nbttagcompound = this.tag.getCompound("display");
         nbttagcompound.remove("Name");
         if(nbttagcompound.isEmpty()) {
            this.tag.remove("display");
            if(this.tag.isEmpty()) {
               this.setTag((NBTTagCompound)null);
            }
         }
      }

   }

   public boolean hasName() {
      return this.tag == null?false:(!this.tag.hasKeyOfType("display", 10)?false:this.tag.getCompound("display").hasKeyOfType("Name", 8));
   }

   public EnumItemRarity u() {
      return this.getItem().g(this);
   }

   public boolean v() {
      return !this.getItem().f_(this)?false:!this.hasEnchantments();
   }

   public void addEnchantment(Enchantment p_addEnchantment_1_, int p_addEnchantment_2_) {
      if(this.tag == null) {
         this.setTag(new NBTTagCompound());
      }

      if(!this.tag.hasKeyOfType("ench", 9)) {
         this.tag.set("ench", new NBTTagList());
      }

      NBTTagList nbttaglist = this.tag.getList("ench", 10);
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.setShort("id", (short)p_addEnchantment_1_.id);
      nbttagcompound.setShort("lvl", (short)((byte)p_addEnchantment_2_));
      nbttaglist.add(nbttagcompound);
   }

   public boolean hasEnchantments() {
      return this.tag != null && this.tag.hasKeyOfType("ench", 9);
   }

   public void a(String p_a_1_, NBTBase p_a_2_) {
      if(this.tag == null) {
         this.setTag(new NBTTagCompound());
      }

      this.tag.set(p_a_1_, p_a_2_);
   }

   public boolean x() {
      return this.getItem().s();
   }

   public boolean y() {
      return this.g != null;
   }

   public void a(EntityItemFrame p_a_1_) {
      this.g = p_a_1_;
   }

   public EntityItemFrame z() {
      return this.g;
   }

   public int getRepairCost() {
      return this.hasTag() && this.tag.hasKeyOfType("RepairCost", 3)?this.tag.getInt("RepairCost"):0;
   }

   public void setRepairCost(int p_setRepairCost_1_) {
      if(!this.hasTag()) {
         this.tag = new NBTTagCompound();
      }

      this.tag.setInt("RepairCost", p_setRepairCost_1_);
   }

   public Multimap<String, AttributeModifier> B() {
      Object object;
      if(this.hasTag() && this.tag.hasKeyOfType("AttributeModifiers", 9)) {
         object = HashMultimap.create();
         NBTTagList nbttaglist = this.tag.getList("AttributeModifiers", 10);

         for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.get(i);
            AttributeModifier attributemodifier = GenericAttributes.a(nbttagcompound);
            if(attributemodifier != null && attributemodifier.a().getLeastSignificantBits() != 0L && attributemodifier.a().getMostSignificantBits() != 0L) {
               ((Multimap)object).put(nbttagcompound.getString("AttributeName"), attributemodifier);
            }
         }
      } else {
         object = this.getItem().i();
      }

      return (Multimap)object;
   }

   public void setItem(Item p_setItem_1_) {
      this.item = p_setItem_1_;
      this.setData(this.getData());
   }

   public IChatBaseComponent C() {
      ChatComponentText chatcomponenttext = new ChatComponentText(this.getName());
      if(this.hasName()) {
         chatcomponenttext.getChatModifier().setItalic(Boolean.valueOf(true));
      }

      IChatBaseComponent ichatbasecomponent = (new ChatComponentText("[")).addSibling(chatcomponenttext).a("]");
      if(this.item != null) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         this.save(nbttagcompound);
         ichatbasecomponent.getChatModifier().setChatHoverable(new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_ITEM, new ChatComponentText(nbttagcompound.toString())));
         ichatbasecomponent.getChatModifier().setColor(this.u().e);
      }

      return ichatbasecomponent;
   }

   public boolean c(Block p_c_1_) {
      if(p_c_1_ == this.h) {
         return this.i;
      } else {
         this.h = p_c_1_;
         if(this.hasTag() && this.tag.hasKeyOfType("CanDestroy", 9)) {
            NBTTagList nbttaglist = this.tag.getList("CanDestroy", 8);

            for(int i = 0; i < nbttaglist.size(); ++i) {
               Block block = Block.getByName(nbttaglist.getString(i));
               if(block == p_c_1_) {
                  this.i = true;
                  return true;
               }
            }
         }

         this.i = false;
         return false;
      }
   }

   public boolean d(Block p_d_1_) {
      if(p_d_1_ == this.j) {
         return this.k;
      } else {
         this.j = p_d_1_;
         if(this.hasTag() && this.tag.hasKeyOfType("CanPlaceOn", 9)) {
            NBTTagList nbttaglist = this.tag.getList("CanPlaceOn", 8);

            for(int i = 0; i < nbttaglist.size(); ++i) {
               Block block = Block.getByName(nbttaglist.getString(i));
               if(block == p_d_1_) {
                  this.k = true;
                  return true;
               }
            }
         }

         this.k = false;
         return false;
      }
   }
}
