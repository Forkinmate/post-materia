package draylar.postmateria.entity;

import draylar.postmateria.registry.PMEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class PhantasmaSpawnerBlockEntity extends BlockEntity {

    private static final List<EntityType<? extends HostileEntity>> validSpawns = Arrays.asList(EntityType.ZOMBIE, EntityType.SPIDER, EntityType.SKELETON);
    private int ticks = 0;

    public PhantasmaSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(PMEntities.PHANTASMA_SPAWNER, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, PhantasmaSpawnerBlockEntity spawner) {
        // Checks done here so we can method reference in the block class... might change later
        if(!(world instanceof ServerWorld)) {
            throw new IllegalArgumentException("PhantasmaSpawnerBlockEntity#tick requires a ServerWorld, but a non-server world was provided!");
        }

        // This method is only called on the server and is responsible for spawning hostile mobs around a Phantasma Meteor.
        spawner.ticks++;

        // First, check pre-conditions for a single monster spawn. If any of them do not pass, the spawn fails.
        {
            // Entities can only attempt to spawn once every 10 ticks.
            if(spawner.ticks % 10 != 0) {
                return;
            }

            // A player must be within 128 blocks of the spawner.
            if(world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 128, it -> !it.isSpectator()) == null) {
                return;
            }

            // The nearby monster count maxes at 30.
            if(world.getEntitiesByClass(HostileEntity.class, new Box(pos).expand(30), it -> true).size() >= 30) {
                return;
            }

            // Spawner does not operate in peaceful.
            if(world.getDifficulty() == Difficulty.PEACEFUL) {
                return;
            }
        }

        // Checks have passed, we can spawn a monster. First, determine the type...
        EntityType<? extends HostileEntity> spawnType = validSpawns.get(world.random.nextInt(validSpawns.size()));
        HostileEntity spawned = spawnType.create(world);

        // The monster has to spawn at a valid platform, ideally around this spawner level.
        // To find a valid spawn position, we randomly check positions up to 20 times.
        int tries = 0;
        while(tries <= 20) {
            tries++;
            BlockPos spawnPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos.add(24 - world.random.nextInt(48), 0, 24 - world.random.nextInt(48)));

            // Do not suffocate the mob when it starts spawning, and do not spawn it over lava...
            if(world.isSpaceEmpty(spawnType.createSimpleBoundingBox(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ())) && world.isTopSolid(spawnPos.down(), spawned)) {
                if(spawned != null) {
                    spawned.initialize((ServerWorldAccess) world, world.getLocalDifficulty(spawnPos), SpawnReason.SPAWNER, null, null);
                    spawned.updatePosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                    world.spawnEntity(spawned);

                    // Buff the mob. Spawned mobs have no armor, but have a high level of strength and resistance.
                    // Buffed mobs have fire resistance so Zombies/Skeletons do not burn.
                    spawned.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 5, true, false));
                    spawned.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 2, true, false));
                    spawned.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false));
                    spawned.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 0, true, false));
                }

                return;
            }
        }
    }
}
