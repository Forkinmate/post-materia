package draylar.postmateria.mixin;

import draylar.postmateria.material.SoulblazeArmorMaterial;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMxin {

    @Inject(method = "getLevel", at = @At("HEAD"), cancellable = true)
    private static void injectSoulblazeSoulspeed(Enchantment enchantment, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        Item item = stack.getItem();
        if(enchantment.equals(Enchantments.SOUL_SPEED) && item instanceof ArmorItem armorItem) {
            if(armorItem.getSlotType().equals(EquipmentSlot.FEET)) {
                if(armorItem.getMaterial().equals(SoulblazeArmorMaterial.INSTANCE)) {
                    cir.setReturnValue(1);
                }
            }
        }
    }
}
