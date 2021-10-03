package draylar.postmateria.material;

import draylar.postmateria.registry.PMItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class PhantasmiteToolMaterial implements ToolMaterial {

    public static final PhantasmiteToolMaterial INSTANCE = new PhantasmiteToolMaterial();

    @Override
    public int getDurability() {
        return 2250;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 12.0F;
    }

    @Override
    public float getAttackDamage() {
        return 5;
    }

    @Override
    public int getMiningLevel() {
        return 5;
    }

    @Override
    public int getEnchantability() {
        return 20;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(PMItems.PHANTASMITE);
    }
}
