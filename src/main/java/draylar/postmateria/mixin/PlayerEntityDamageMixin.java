package draylar.postmateria.mixin;

import draylar.postmateria.item.PhantasmiteSwordItem;
import draylar.postmateria.item.SoulblazeSwordItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityDamageMixin extends LivingEntity {

    public PlayerEntityDamageMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;postHit(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/player/PlayerEntity;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void soulblazeHealing(
            Entity target,
            CallbackInfo ci,
            float finalDamage,
            float toolDamage,
            boolean finishedCooldown,
            boolean appliedKnockback,
            int q,
            boolean canCrit,
            boolean canSweep,
            float previousTargetHealth,
            boolean inflictedFire,
            int fireAspect,
            ItemStack heldStack,
            Entity entity) {
        if(heldStack.getItem() instanceof SoulblazeSwordItem) {
            float healAmount = finalDamage / 10.0f;
            heal(healAmount);
        }
    }
}
