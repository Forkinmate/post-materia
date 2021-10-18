package draylar.postmateria.mixin.soulblaze;

import draylar.postmateria.material.SoulblazeArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    // We do not want Soulblaze boots to be damaged when acting as Soul Speed equipment.
    @Inject(method = "addSoulSpeedBoostIfNeeded", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"), cancellable = true)
    private void cancelSoulblazeDamage(CallbackInfo ci) {
        if(getEquippedStack(EquipmentSlot.FEET).getItem() instanceof ArmorItem armorItem) {
            if(armorItem.getMaterial().equals(SoulblazeArmorMaterial.INSTANCE)) {
                ci.cancel();
            }
        }
    }
}
