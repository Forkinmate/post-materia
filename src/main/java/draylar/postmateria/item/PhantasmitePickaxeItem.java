package draylar.postmateria.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PhantasmitePickaxeItem extends PickaxeItem {

    public PhantasmitePickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        // When a player mines an Ore Block with the Soulblaze Pickaxe, they heal 1 heart.
        if(state.getBlock() instanceof OreBlock || state.getBlock() instanceof RedstoneOreBlock) {
            if(miner.getHealth() < miner.getMaxHealth()) {
                miner.heal(2.0f);
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.PARTICLE_SOUL_ESCAPE, SoundCategory.BLOCKS, 1.0f, 1.0f);

                // SFX on the server
                if(world instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.SOUL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, 0, 0, 0, 0);
                }
            }
        }

        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText("postmateria.translation.phantasmite_pickaxe"));
    }
}
