package draylar.postmateria.mixin;

import draylar.postmateria.PostMateria;
import draylar.postmateria.data.WorldWitherData;
import draylar.worlddata.api.WorldData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageRecord;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class WitherDeathMixin extends Entity {

    @Shadow
    @Final
    private DamageTracker damageTracker;

    public WitherDeathMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "onDeath",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageTracker;update()V"))
    private void updateWitherDeathState(DamageSource source, CallbackInfo ci) {
        if (!world.isClient && getServer() != null) {
            WorldWitherData status = WorldData.getGlobalData(getServer(), PostMateria.WITHER_SLAIN_DATA);

            // If this is the first time the Wither has been slain...
            if (!status.isWitherSlain()) {

                // & at least 1 player had Netherite while battling the wither...
                if (wasSlainByNetherite()) {

                    // Tag the Wither as slain, broadcast a message, and re-gen Soulblaze.
                    status.setWitherSlain(true);

                    // Assemble the message
                    MutableText text = new TranslatableText("postmateria.translation.soulblaze_generation");

                    // Broadcast
                    for (ServerPlayerEntity player : getServer().getPlayerManager().getPlayerList()) {
                        player.sendMessage(text, false);
                    }
                }
            }
        }
    }

    @Unique
    private boolean wasSlainByNetherite() {
        for (DamageRecord record : ((DamageTrackerAccessor) damageTracker).getRecentDamage()) {
            if (record.getAttacker() instanceof PlayerEntity player) {
                // Check armor items
                for (ItemStack armorStack : player.getArmorItems()) {
                    if (armorStack.getItem() instanceof ArmorItem armorItem) {
                        if (armorItem.getMaterial().equals(ArmorMaterials.NETHERITE)) {
                            return true;
                        }
                    }
                }

                // Check inventory
                for (ItemStack inventoryStack : player.getInventory().main) {
                    if (inventoryStack.getItem() instanceof ToolItem toolItem) {
                        if (toolItem.getMaterial().equals(ToolMaterials.NETHERITE)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
