package draylar.postmateria.mixin;

import draylar.postmateria.item.PhantasmiteArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class HealMixin {

    @ModifyVariable(
            method = "heal",
            at = @At(value = "HEAD"), index = 1)
    private float adjustHealAmount(float input) {
        if ((LivingEntity) (Object) this instanceof PlayerEntity player) {
            float multiplier = 1.0f;
            int soulblazeArmorPieces = 0;

            // Calculate the total heal multiplier based on HealBonusProvider armor items.
            for (ItemStack armor : player.getArmorItems()) {
                if (armor.getItem() instanceof PhantasmiteArmorItem) {
                    multiplier += 0.1;
                    soulblazeArmorPieces++;
                }
            }

            // If the user is wearing >=4 Soulblaze Armor (full set), they gain an additional 10% bonus.
            if(soulblazeArmorPieces >= 4) {
                multiplier += 0.1;
            }

            // Adjust the return value to use our modified value.
            return input * multiplier;
        }

        return input;
    }
}
