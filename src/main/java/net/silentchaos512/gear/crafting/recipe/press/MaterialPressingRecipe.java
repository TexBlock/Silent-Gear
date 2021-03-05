package net.silentchaos512.gear.crafting.recipe.press;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.gear.api.material.IMaterialInstance;
import net.silentchaos512.gear.gear.material.MaterialInstance;
import net.silentchaos512.gear.item.CraftedMaterialItem;

public class MaterialPressingRecipe extends PressingRecipe {
    public MaterialPressingRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result) {
        super(id, ingredient, result);

        if (!(result.getItem() instanceof CraftedMaterialItem)) {
            throw new IllegalArgumentException("result must be a CraftedMaterialItem");
        }
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        IMaterialInstance material = MaterialInstance.from(inv.getStackInSlot(0));

        if (material != null && material.isSimple() && this.result.getItem() instanceof CraftedMaterialItem) {
            CraftedMaterialItem item = (CraftedMaterialItem) this.result.getItem();
            return item.create(material, this.result.getCount());
        }

        return ItemStack.EMPTY;
    }
}
