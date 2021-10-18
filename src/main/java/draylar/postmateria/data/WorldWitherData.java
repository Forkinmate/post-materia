package draylar.postmateria.data;

import draylar.postmateria.api.data.WorldData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;

public class WorldWitherData implements WorldData {

    private final ServerWorld world;
    private boolean witherSlain = false;

    public WorldWitherData(ServerWorld world) {
        this.world = world;
    }

    @Override
    public void writeNbt(NbtCompound root) {
        root.putBoolean("WitherSlain", witherSlain);
    }

    @Override
    public void readNbt(NbtCompound root) {
        witherSlain = root.getBoolean("WitherSlain");
    }

    public boolean isWitherSlain() {
        return witherSlain;
    }

    public void setWitherSlain(boolean witherSlain) {
        this.witherSlain = witherSlain;
        markDirty();
    }

    @Override
    public ServerWorld getWorld() {
        return world;
    }
}
