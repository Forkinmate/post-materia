package draylar.postmateria.entity;

import draylar.postmateria.api.ParticleHelper;
import draylar.postmateria.api.SoulBlazeHelper;
import draylar.postmateria.registry.PMItems;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class SoulSurferEntity extends PersistentProjectileEntity {

    public SoulSurferEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        if(inGround && !isRemoved()) {
            returnToOwner();
        }
    }

    @Override
    public void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);

        if(!state.isAir()) {
            returnToOwner();
        }
    }

    @Override
    public void onEntityHit(EntityHitResult result) {
        super.onEntityHit(result);

        if(result.getEntity() != getOwner()) {
            returnToOwner();
        }
    }

    public void setupDamageModifiers(LivingEntity entity) {
        int sharpness = EnchantmentHelper.getEquipmentLevel(Enchantments.POWER, entity);
        int fireAspect = EnchantmentHelper.getEquipmentLevel(Enchantments.FIRE_ASPECT, entity);

        // Apply sharpness
        if(sharpness > 0) {
            setDamage(getDamage() + (double) sharpness * 0.5D + 0.5D);
        }

        // Apply Fire Aspect
        if(fireAspect > 0) {
            setOnFireFor(100);
        }
    }

    public void returnToOwner() {
        discard();

        // This method is not called on the client, but double-check world status.
        if(!world.isClient) {
            // Spawn particles.
            {
                for (int i = 0; i < 100; i++) {
                    ParticleHelper.spawnForcedParticles(
                            ((ServerWorld) world),
                            ParticleTypes.SOUL_FIRE_FLAME,
                            getX(),
                            getY(),
                            getZ(),
                            0,
                            world.random.nextDouble() * 2 - 1,
                            world.random.nextDouble() * 2 - 1,
                            world.random.nextDouble() * 2 - 1,
                            world.random.nextDouble() * 0.2);

                    ParticleHelper.spawnForcedParticles(
                            ((ServerWorld) world),
                            ParticleTypes.SOUL,
                            getX(),
                            getY(),
                            getZ(),
                            0,
                            world.random.nextDouble() * 2 - 1,
                            world.random.nextDouble() * 2 - 1,
                            world.random.nextDouble() * 2 - 1,
                            world.random.nextDouble() * 0.2);
                }
            }

            // Deal AOE damage & inflict Soul Fire around the hit location.
            world.getEntitiesByClass(LivingEntity.class, new Box(getBlockPos()).expand(5), it -> true)
                    .stream()
                    .filter(it -> it != getOwner())
                    .filter(it -> {
                        if(getOwner() != null) {
                            if(it instanceof Tameable tameable) {
                                return tameable.getOwner() != getOwner();
                            }
                        }

                        return true;
                    }).forEach(entity -> {
                        DamageSource source = getOwner() instanceof LivingEntity ? DamageSource.mobProjectile(this, (LivingEntity) getOwner()) : DamageSource.GENERIC;
                        entity.damage(source, (float) getDamage() / 4.0f);
                        SoulBlazeHelper.setSoulBlazed(entity, 20 * 3);
                    }
            );
        }
    }

    @Override
    public ItemStack asItemStack() {
        return new ItemStack(PMItems.SOUL_SURFER);
    }
}
