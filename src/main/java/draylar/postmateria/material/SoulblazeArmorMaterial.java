package draylar.postmateria.material;

import draylar.postmateria.registry.PMItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class SoulblazeArmorMaterial implements ArmorMaterial {

    public static final SoulblazeArmorMaterial INSTANCE = new SoulblazeArmorMaterial();
    private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
    private static final int[] PROTECTION_AMOUNT = new int[] {3, 6, 8, 3};

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
        return SoundEvents.PARTICLE_SOUL_ESCAPE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(PMItems.SOULBLAZE);
    }

    @Override
    public String getName() {
        return "soulblaze";
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
