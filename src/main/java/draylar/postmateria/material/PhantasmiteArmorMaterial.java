package draylar.postmateria.material;

import draylar.postmateria.registry.PMItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class PhantasmiteArmorMaterial implements BaseArmorMaterial {

    public static final PhantasmiteArmorMaterial INSTANCE = new PhantasmiteArmorMaterial();
    private static final int[] PROTECTION_AMOUNT = new int[] { 3, 6, 8, 3 };

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * 45;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_AMOUNT[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.BLOCK_CORAL_BLOCK_HIT;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(PMItems.PHANTASMITE);
    }

    @Override
    public String getName() {
        return "phantasmite";
    }

    @Override
    public float getToughness() {
        return 3;
    }

    @Override
    public float getKnockbackResistance() {
        return 3;
    }
}
