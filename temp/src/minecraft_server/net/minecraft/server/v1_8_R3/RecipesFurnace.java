package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockSmoothBrick;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemFish;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;

public class RecipesFurnace {
   private static final RecipesFurnace a = new RecipesFurnace();
   public Map<ItemStack, ItemStack> recipes = Maps.<ItemStack, ItemStack>newHashMap();
   private Map<ItemStack, Float> c = Maps.<ItemStack, Float>newHashMap();
   public Map customRecipes = Maps.newHashMap();

   public static RecipesFurnace getInstance() {
      return a;
   }

   public RecipesFurnace() {
      this.registerRecipe(Blocks.IRON_ORE, new ItemStack(Items.IRON_INGOT), 0.7F);
      this.registerRecipe(Blocks.GOLD_ORE, new ItemStack(Items.GOLD_INGOT), 1.0F);
      this.registerRecipe(Blocks.DIAMOND_ORE, new ItemStack(Items.DIAMOND), 1.0F);
      this.registerRecipe(Blocks.SAND, new ItemStack(Blocks.GLASS), 0.1F);
      this.a(Items.PORKCHOP, new ItemStack(Items.COOKED_PORKCHOP), 0.35F);
      this.a(Items.BEEF, new ItemStack(Items.COOKED_BEEF), 0.35F);
      this.a(Items.CHICKEN, new ItemStack(Items.COOKED_CHICKEN), 0.35F);
      this.a(Items.RABBIT, new ItemStack(Items.COOKED_RABBIT), 0.35F);
      this.a(Items.MUTTON, new ItemStack(Items.COOKED_MUTTON), 0.35F);
      this.registerRecipe(Blocks.COBBLESTONE, new ItemStack(Blocks.STONE), 0.1F);
      this.a(new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.b), new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.O), 0.1F);
      this.a(Items.CLAY_BALL, new ItemStack(Items.BRICK), 0.3F);
      this.registerRecipe(Blocks.CLAY, new ItemStack(Blocks.HARDENED_CLAY), 0.35F);
      this.registerRecipe(Blocks.CACTUS, new ItemStack(Items.DYE, 1, EnumColor.GREEN.getInvColorIndex()), 0.2F);
      this.registerRecipe(Blocks.LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
      this.registerRecipe(Blocks.LOG2, new ItemStack(Items.COAL, 1, 1), 0.15F);
      this.registerRecipe(Blocks.EMERALD_ORE, new ItemStack(Items.EMERALD), 1.0F);
      this.a(Items.POTATO, new ItemStack(Items.BAKED_POTATO), 0.35F);
      this.registerRecipe(Blocks.NETHERRACK, new ItemStack(Items.NETHERBRICK), 0.1F);
      this.a(new ItemStack(Blocks.SPONGE, 1, 1), new ItemStack(Blocks.SPONGE, 1, 0), 0.15F);

      for(ItemFish.EnumFish itemfish$enumfish : ItemFish.EnumFish.values()) {
         if(itemfish$enumfish.g()) {
            this.a(new ItemStack(Items.FISH, 1, itemfish$enumfish.a()), new ItemStack(Items.COOKED_FISH, 1, itemfish$enumfish.a()), 0.35F);
         }
      }

      this.registerRecipe(Blocks.COAL_ORE, new ItemStack(Items.COAL), 0.1F);
      this.registerRecipe(Blocks.REDSTONE_ORE, new ItemStack(Items.REDSTONE), 0.7F);
      this.registerRecipe(Blocks.LAPIS_ORE, new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), 0.2F);
      this.registerRecipe(Blocks.QUARTZ_ORE, new ItemStack(Items.QUARTZ), 0.2F);
   }

   public void registerRecipe(ItemStack p_registerRecipe_1_, ItemStack p_registerRecipe_2_) {
      this.customRecipes.put(p_registerRecipe_1_, p_registerRecipe_2_);
   }

   public void registerRecipe(Block p_registerRecipe_1_, ItemStack p_registerRecipe_2_, float p_registerRecipe_3_) {
      this.a(Item.getItemOf(p_registerRecipe_1_), p_registerRecipe_2_, p_registerRecipe_3_);
   }

   public void a(Item p_a_1_, ItemStack p_a_2_, float p_a_3_) {
      this.a(new ItemStack(p_a_1_, 1, 32767), p_a_2_, p_a_3_);
   }

   public void a(ItemStack p_a_1_, ItemStack p_a_2_, float p_a_3_) {
      this.recipes.put(p_a_1_, p_a_2_);
      this.c.put(p_a_2_, Float.valueOf(p_a_3_));
   }

   public ItemStack getResult(ItemStack p_getResult_1_) {
      boolean flag = false;
      Iterator iterator = this.customRecipes.entrySet().iterator();

      Entry entry;
      while(true) {
         if(!iterator.hasNext()) {
            if(flag || this.recipes.isEmpty()) {
               return null;
            }

            iterator = this.recipes.entrySet().iterator();
            flag = true;
         }

         entry = (Entry)iterator.next();
         if(this.a(p_getResult_1_, (ItemStack)entry.getKey())) {
            break;
         }
      }

      return (ItemStack)entry.getValue();
   }

   private boolean a(ItemStack p_a_1_, ItemStack p_a_2_) {
      return p_a_2_.getItem() == p_a_1_.getItem() && (p_a_2_.getData() == 32767 || p_a_2_.getData() == p_a_1_.getData());
   }

   public Map<ItemStack, ItemStack> getRecipes() {
      return this.recipes;
   }

   public float b(ItemStack p_b_1_) {
      for(Entry entry : this.c.entrySet()) {
         if(this.a(p_b_1_, (ItemStack)entry.getKey())) {
            return ((Float)entry.getValue()).floatValue();
         }
      }

      return 0.0F;
   }
}
