package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.InventoryCrafting;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.inventory.Recipe;

public interface IRecipe {
   boolean a(InventoryCrafting var1, World var2);

   ItemStack craftItem(InventoryCrafting var1);

   int a();

   ItemStack b();

   ItemStack[] b(InventoryCrafting var1);

   Recipe toBukkitRecipe();

   List<ItemStack> getIngredients();
}
