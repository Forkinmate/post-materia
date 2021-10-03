package draylar.postmateria.mixin;

import draylar.postmateria.impl.SoulBlazing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO: Migrate mixin to Entity and use a custom packet to sync status over a DataTracker entry.
@Mixin(LivingEntity.class)
public abstract class EntitySoulBlazeMixin extends Entity implements SoulBlazing {

    @Unique private static final TrackedData<Boolean> SOUL_BLAZE = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    @Unique private int soulBlazeTicksRemaining = 0;

    public EntitySoulBlazeMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD"))
    private void tickSoulBlaze(CallbackInfo ci) {
        // Tick Soul Blaze
        if (!world.isClient) {
            if (postMateria_isSoulBlazed()) {
                // Tick damage
                if(age % 20 == 0) {
                    // TODO: non-generic damage source
                    damage(DamageSource.GENERIC, 2.0f);
                }

                // Tick cooldown
                soulBlazeTicksRemaining = Math.max(0, soulBlazeTicksRemaining - 1);
                if (soulBlazeTicksRemaining <= 0) {
                    postMateria_setSoulBlaze(0);
                }
            }
        }
    }

    @Inject(
            method = "initDataTracker",
            at = @At("RETURN"))
    private void appendDataTrackers(CallbackInfo ci) {
        dataTracker.startTracking(SOUL_BLAZE, false);
    }

    @Override
    public boolean postMateria_isSoulBlazed() {
        return dataTracker.get(SOUL_BLAZE);
    }

    @Override
    public void postMateria_setSoulBlaze(int ticks) {
        if (ticks > 0) {
            dataTracker.set(SOUL_BLAZE, true);
        } else {
            dataTracker.set(SOUL_BLAZE, false);
        }

        soulBlazeTicksRemaining = ticks;
    }
}
