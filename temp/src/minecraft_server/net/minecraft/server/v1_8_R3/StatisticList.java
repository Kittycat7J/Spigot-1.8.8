package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_8_R3.AchievementList;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.CounterStatistic;
import net.minecraft.server.v1_8_R3.CraftingManager;
import net.minecraft.server.v1_8_R3.CraftingStatistic;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.IRecipe;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemBlock;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.RecipesFurnace;
import net.minecraft.server.v1_8_R3.Statistic;

public class StatisticList {
   protected static Map<String, Statistic> a = Maps.<String, Statistic>newHashMap();
   public static List<Statistic> stats = Lists.<Statistic>newArrayList();
   public static List<Statistic> c = Lists.<Statistic>newArrayList();
   public static List<CraftingStatistic> d = Lists.<CraftingStatistic>newArrayList();
   public static List<CraftingStatistic> e = Lists.<CraftingStatistic>newArrayList();
   public static Statistic f = (new CounterStatistic("stat.leaveGame", new ChatMessage("stat.leaveGame", new Object[0]))).i().h();
   public static Statistic g = (new CounterStatistic("stat.playOneMinute", new ChatMessage("stat.playOneMinute", new Object[0]), Statistic.h)).i().h();
   public static Statistic h = (new CounterStatistic("stat.timeSinceDeath", new ChatMessage("stat.timeSinceDeath", new Object[0]), Statistic.h)).i().h();
   public static Statistic i = (new CounterStatistic("stat.walkOneCm", new ChatMessage("stat.walkOneCm", new Object[0]), Statistic.i)).i().h();
   public static Statistic j = (new CounterStatistic("stat.crouchOneCm", new ChatMessage("stat.crouchOneCm", new Object[0]), Statistic.i)).i().h();
   public static Statistic k = (new CounterStatistic("stat.sprintOneCm", new ChatMessage("stat.sprintOneCm", new Object[0]), Statistic.i)).i().h();
   public static Statistic l = (new CounterStatistic("stat.swimOneCm", new ChatMessage("stat.swimOneCm", new Object[0]), Statistic.i)).i().h();
   public static Statistic m = (new CounterStatistic("stat.fallOneCm", new ChatMessage("stat.fallOneCm", new Object[0]), Statistic.i)).i().h();
   public static Statistic n = (new CounterStatistic("stat.climbOneCm", new ChatMessage("stat.climbOneCm", new Object[0]), Statistic.i)).i().h();
   public static Statistic o = (new CounterStatistic("stat.flyOneCm", new ChatMessage("stat.flyOneCm", new Object[0]), Statistic.i)).i().h();
   public static Statistic p = (new CounterStatistic("stat.diveOneCm", new ChatMessage("stat.diveOneCm", new Object[0]), Statistic.i)).i().h();
   public static Statistic q = (new CounterStatistic("stat.minecartOneCm", new ChatMessage("stat.minecartOneCm", new Object[0]), Statistic.i)).i().h();
   public static Statistic r = (new CounterStatistic("stat.boatOneCm", new ChatMessage("stat.boatOneCm", new Object[0]), Statistic.i)).i().h();
   public static Statistic s = (new CounterStatistic("stat.pigOneCm", new ChatMessage("stat.pigOneCm", new Object[0]), Statistic.i)).i().h();
   public static Statistic t = (new CounterStatistic("stat.horseOneCm", new ChatMessage("stat.horseOneCm", new Object[0]), Statistic.i)).i().h();
   public static Statistic u = (new CounterStatistic("stat.jump", new ChatMessage("stat.jump", new Object[0]))).i().h();
   public static Statistic v = (new CounterStatistic("stat.drop", new ChatMessage("stat.drop", new Object[0]))).i().h();
   public static Statistic w = (new CounterStatistic("stat.damageDealt", new ChatMessage("stat.damageDealt", new Object[0]), Statistic.j)).h();
   public static Statistic x = (new CounterStatistic("stat.damageTaken", new ChatMessage("stat.damageTaken", new Object[0]), Statistic.j)).h();
   public static Statistic y = (new CounterStatistic("stat.deaths", new ChatMessage("stat.deaths", new Object[0]))).h();
   public static Statistic z = (new CounterStatistic("stat.mobKills", new ChatMessage("stat.mobKills", new Object[0]))).h();
   public static Statistic A = (new CounterStatistic("stat.animalsBred", new ChatMessage("stat.animalsBred", new Object[0]))).h();
   public static Statistic B = (new CounterStatistic("stat.playerKills", new ChatMessage("stat.playerKills", new Object[0]))).h();
   public static Statistic C = (new CounterStatistic("stat.fishCaught", new ChatMessage("stat.fishCaught", new Object[0]))).h();
   public static Statistic D = (new CounterStatistic("stat.junkFished", new ChatMessage("stat.junkFished", new Object[0]))).h();
   public static Statistic E = (new CounterStatistic("stat.treasureFished", new ChatMessage("stat.treasureFished", new Object[0]))).h();
   public static Statistic F = (new CounterStatistic("stat.talkedToVillager", new ChatMessage("stat.talkedToVillager", new Object[0]))).h();
   public static Statistic G = (new CounterStatistic("stat.tradedWithVillager", new ChatMessage("stat.tradedWithVillager", new Object[0]))).h();
   public static Statistic H = (new CounterStatistic("stat.cakeSlicesEaten", new ChatMessage("stat.cakeSlicesEaten", new Object[0]))).h();
   public static Statistic I = (new CounterStatistic("stat.cauldronFilled", new ChatMessage("stat.cauldronFilled", new Object[0]))).h();
   public static Statistic J = (new CounterStatistic("stat.cauldronUsed", new ChatMessage("stat.cauldronUsed", new Object[0]))).h();
   public static Statistic K = (new CounterStatistic("stat.armorCleaned", new ChatMessage("stat.armorCleaned", new Object[0]))).h();
   public static Statistic L = (new CounterStatistic("stat.bannerCleaned", new ChatMessage("stat.bannerCleaned", new Object[0]))).h();
   public static Statistic M = (new CounterStatistic("stat.brewingstandInteraction", new ChatMessage("stat.brewingstandInteraction", new Object[0]))).h();
   public static Statistic N = (new CounterStatistic("stat.beaconInteraction", new ChatMessage("stat.beaconInteraction", new Object[0]))).h();
   public static Statistic O = (new CounterStatistic("stat.dropperInspected", new ChatMessage("stat.dropperInspected", new Object[0]))).h();
   public static Statistic P = (new CounterStatistic("stat.hopperInspected", new ChatMessage("stat.hopperInspected", new Object[0]))).h();
   public static Statistic Q = (new CounterStatistic("stat.dispenserInspected", new ChatMessage("stat.dispenserInspected", new Object[0]))).h();
   public static Statistic R = (new CounterStatistic("stat.noteblockPlayed", new ChatMessage("stat.noteblockPlayed", new Object[0]))).h();
   public static Statistic S = (new CounterStatistic("stat.noteblockTuned", new ChatMessage("stat.noteblockTuned", new Object[0]))).h();
   public static Statistic T = (new CounterStatistic("stat.flowerPotted", new ChatMessage("stat.flowerPotted", new Object[0]))).h();
   public static Statistic U = (new CounterStatistic("stat.trappedChestTriggered", new ChatMessage("stat.trappedChestTriggered", new Object[0]))).h();
   public static Statistic V = (new CounterStatistic("stat.enderchestOpened", new ChatMessage("stat.enderchestOpened", new Object[0]))).h();
   public static Statistic W = (new CounterStatistic("stat.itemEnchanted", new ChatMessage("stat.itemEnchanted", new Object[0]))).h();
   public static Statistic X = (new CounterStatistic("stat.recordPlayed", new ChatMessage("stat.recordPlayed", new Object[0]))).h();
   public static Statistic Y = (new CounterStatistic("stat.furnaceInteraction", new ChatMessage("stat.furnaceInteraction", new Object[0]))).h();
   public static Statistic Z = (new CounterStatistic("stat.craftingTableInteraction", new ChatMessage("stat.workbenchInteraction", new Object[0]))).h();
   public static Statistic aa = (new CounterStatistic("stat.chestOpened", new ChatMessage("stat.chestOpened", new Object[0]))).h();
   public static final Statistic[] MINE_BLOCK_COUNT = new Statistic[4096];
   public static final Statistic[] CRAFT_BLOCK_COUNT = new Statistic[32000];
   public static final Statistic[] USE_ITEM_COUNT = new Statistic[32000];
   public static final Statistic[] BREAK_ITEM_COUNT = new Statistic[32000];

   public static void a() {
      c();
      d();
      e();
      b();
      AchievementList.a();
      EntityTypes.a();
   }

   private static void b() {
      HashSet hashset = Sets.newHashSet();

      for(IRecipe irecipe : CraftingManager.getInstance().getRecipes()) {
         if(irecipe.b() != null) {
            hashset.add(irecipe.b().getItem());
         }
      }

      for(ItemStack itemstack : RecipesFurnace.getInstance().getRecipes().values()) {
         hashset.add(itemstack.getItem());
      }

      for(Item item : hashset) {
         if(item != null) {
            int ix = Item.getId(item);
            String sx = a(item);
            if(sx != null) {
               CRAFT_BLOCK_COUNT[ix] = (new CraftingStatistic("stat.craftItem.", sx, new ChatMessage("stat.craftItem", new Object[]{(new ItemStack(item)).C()}), item)).h();
            }
         }
      }

      a(CRAFT_BLOCK_COUNT);
   }

   private static void c() {
      for(Block block : Block.REGISTRY) {
         Item item = Item.getItemOf(block);
         if(item != null) {
            int ix = Block.getId(block);
            String sx = a(item);
            if(sx != null && block.J()) {
               MINE_BLOCK_COUNT[ix] = (new CraftingStatistic("stat.mineBlock.", sx, new ChatMessage("stat.mineBlock", new Object[]{(new ItemStack(block)).C()}), item)).h();
               e.add((CraftingStatistic)MINE_BLOCK_COUNT[ix]);
            }
         }
      }

      a(MINE_BLOCK_COUNT);
   }

   private static void d() {
      for(Item item : Item.REGISTRY) {
         if(item != null) {
            int ix = Item.getId(item);
            String sx = a(item);
            if(sx != null) {
               USE_ITEM_COUNT[ix] = (new CraftingStatistic("stat.useItem.", sx, new ChatMessage("stat.useItem", new Object[]{(new ItemStack(item)).C()}), item)).h();
               if(!(item instanceof ItemBlock)) {
                  d.add((CraftingStatistic)USE_ITEM_COUNT[ix]);
               }
            }
         }
      }

      a(USE_ITEM_COUNT);
   }

   private static void e() {
      for(Item item : Item.REGISTRY) {
         if(item != null) {
            int ix = Item.getId(item);
            String sx = a(item);
            if(sx != null && item.usesDurability()) {
               BREAK_ITEM_COUNT[ix] = (new CraftingStatistic("stat.breakItem.", sx, new ChatMessage("stat.breakItem", new Object[]{(new ItemStack(item)).C()}), item)).h();
            }
         }
      }

      a(BREAK_ITEM_COUNT);
   }

   private static String a(Item p_a_0_) {
      MinecraftKey minecraftkey = (MinecraftKey)Item.REGISTRY.c(p_a_0_);
      return minecraftkey != null?minecraftkey.toString().replace(':', '.'):null;
   }

   private static void a(Statistic[] p_a_0_) {
      a(p_a_0_, Blocks.WATER, Blocks.FLOWING_WATER);
      a(p_a_0_, Blocks.LAVA, Blocks.FLOWING_LAVA);
      a(p_a_0_, Blocks.LIT_PUMPKIN, Blocks.PUMPKIN);
      a(p_a_0_, Blocks.LIT_FURNACE, Blocks.FURNACE);
      a(p_a_0_, Blocks.LIT_REDSTONE_ORE, Blocks.REDSTONE_ORE);
      a(p_a_0_, Blocks.POWERED_REPEATER, Blocks.UNPOWERED_REPEATER);
      a(p_a_0_, Blocks.POWERED_COMPARATOR, Blocks.UNPOWERED_COMPARATOR);
      a(p_a_0_, Blocks.REDSTONE_TORCH, Blocks.UNLIT_REDSTONE_TORCH);
      a(p_a_0_, Blocks.LIT_REDSTONE_LAMP, Blocks.REDSTONE_LAMP);
      a(p_a_0_, Blocks.DOUBLE_STONE_SLAB, Blocks.STONE_SLAB);
      a(p_a_0_, Blocks.DOUBLE_WOODEN_SLAB, Blocks.WOODEN_SLAB);
      a(p_a_0_, Blocks.DOUBLE_STONE_SLAB2, Blocks.STONE_SLAB2);
      a(p_a_0_, Blocks.GRASS, Blocks.DIRT);
      a(p_a_0_, Blocks.FARMLAND, Blocks.DIRT);
   }

   private static void a(Statistic[] p_a_0_, Block p_a_1_, Block p_a_2_) {
      int ix = Block.getId(p_a_1_);
      int jx = Block.getId(p_a_2_);
      if(p_a_0_[ix] != null && p_a_0_[jx] == null) {
         p_a_0_[jx] = p_a_0_[ix];
      } else {
         stats.remove(p_a_0_[ix]);
         e.remove(p_a_0_[ix]);
         c.remove(p_a_0_[ix]);
         p_a_0_[ix] = p_a_0_[jx];
      }
   }

   public static Statistic a(EntityTypes.MonsterEggInfo p_a_0_) {
      String sx = EntityTypes.b(p_a_0_.a);
      return sx == null?null:(new Statistic("stat.killEntity." + sx, new ChatMessage("stat.entityKill", new Object[]{new ChatMessage("entity." + sx + ".name", new Object[0])}))).h();
   }

   public static Statistic b(EntityTypes.MonsterEggInfo p_b_0_) {
      String sx = EntityTypes.b(p_b_0_.a);
      return sx == null?null:(new Statistic("stat.entityKilledBy." + sx, new ChatMessage("stat.entityKilledBy", new Object[]{new ChatMessage("entity." + sx + ".name", new Object[0])}))).h();
   }

   public static Statistic getStatistic(String p_getStatistic_0_) {
      return (Statistic)a.get(p_getStatistic_0_);
   }
}
