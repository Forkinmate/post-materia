package draylar.postmateria.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import draylar.postmateria.entity.SoulSurferEntity;
import draylar.postmateria.registry.PMEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoulSurferItem extends Item {

    private static final int MAX_USE_TIME = 10;
    private final Multimap<EntityAttribute, EntityAttributeModifier> modifiers;

    public SoulSurferItem(Settings settings) {
        super(settings);

        // Initialize modifiers
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool damage", 10.0D, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool speed", -3D, EntityAttributeModifier.Operation.ADDITION));
        modifiers = builder.build();
    }

    // The player can hold the Soul Surfer indefinitely.
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return super.use(world, user, hand);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    // When the player stops using the Soul Surfer, they will throw it IF they have been using the item for more than 10 ticks.
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int ticksUsedFor = Integer.MAX_VALUE - remainingUseTicks;
        if(!world.isClient && ticksUsedFor >= MAX_USE_TIME) {
            // Increase usage stats for the Soul Surfer item.
            if(user instanceof PlayerEntity player) {
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            }

            // Spawn the Soul Surfer item.
            SoulSurferEntity soulSurfer = new SoulSurferEntity(PMEntities.SOUL_SURFER, world);
            soulSurfer.updatePosition(user.getX(), user.getEyeY(), user.getZ());
            soulSurfer.setVelocity(user.getRotationVector().multiply(3));
            soulSurfer.setDamage(8.0f);
            soulSurfer.setupDamageModifiers(user);
            world.spawnEntity(soulSurfer);
        }
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        // If the player breaks a non-instant-break block (grass, torch) with the Soul Surfer, damage it.
        if(state.getHardness(world, pos) != 0.0) {
            stack.damage(1, miner, entity -> entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, target, entity -> entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? modifiers : super.getAttributeModifiers(slot);
    }
}
