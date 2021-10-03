package draylar.postmateria.api;

import draylar.postmateria.impl.SoulBlazing;
import net.minecraft.entity.LivingEntity;

public class SoulBlazeHelper {

    public static boolean isSoulBlazed(LivingEntity entity) {
        return ((SoulBlazing) entity).postMateria_isSoulBlazed();
    }

    public static void setSoulBlazed(LivingEntity entity, int duration) {
        ((SoulBlazing) entity).postMateria_setSoulBlaze(duration);
    }
}
