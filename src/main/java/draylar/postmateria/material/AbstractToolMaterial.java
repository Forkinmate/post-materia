package draylar.postmateria.material;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public record AbstractToolMaterial(Ingredient repairIngredient, int durability, float speed, float damage, int miningLevel, int enchant) implements ToolMaterial {

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return speed;
    }

    @Override
    public float getAttackDamage() {
        return damage;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }

    @Override
    public int getEnchantability() {
        return enchant;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }
}
