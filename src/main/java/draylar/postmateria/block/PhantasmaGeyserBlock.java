package draylar.postmateria.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class PhantasmaGeyserBlock extends Block {

    public PhantasmaGeyserBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(world.random.nextInt(2) == 0) {
            for (int i = 0; i < 20; i++) {
                world.addParticle(ParticleTypes.DRAGON_BREATH, pos.getX(), pos.getY() + i / 10f, pos.getZ(), 0.05 - world.random.nextDouble() * 0.1, 0.25 + world.random.nextDouble(), 0.05 - world.random.nextDouble() * 0.1);
            }
        } else {
            for (int i = 0; i < 20; i++) {
                world.addParticle(ParticleTypes.FLAME, pos.getX(), pos.getY() + i / 10f, pos.getZ(), 0.05 - world.random.nextDouble() * 0.1, 0.25 + world.random.nextDouble(), 0.05 - world.random.nextDouble() * 0.1);
            }
        }
    }
}
