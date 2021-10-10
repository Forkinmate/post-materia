package draylar.postmateria.api;

import draylar.postmateria.mixin.ServerWorldAccessor;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class ParticleHelper {

    public static <T extends ParticleEffect> void spawnForcedParticles(ServerWorld world, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        ParticleS2CPacket packet = new ParticleS2CPacket(particle, false, x, y, z, (float) deltaX, (float) deltaY, (float) deltaZ, (float) speed, count);
        for (ServerPlayerEntity player : world.getPlayers()) {
            ((ServerWorldAccessor) world).callSendToPlayerIfNearby(player, true, x, y, z, packet);
        }
    }
}
