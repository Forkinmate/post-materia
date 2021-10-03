package draylar.postmateria.api.client;

import draylar.postmateria.item.PhantasmiteArmorItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

public class PhantasmiteTooltipHelper {

    /**
     * Returns {@code true} if the current client has a full set of Soulblaze Armor equipped.
     */
    public static boolean hasFullSet() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player != null) {
            for (ItemStack armorItem : player.getArmorItems()) {
                if (!(armorItem.getItem() instanceof PhantasmiteArmorItem)) {
                    return false;
                }
            }
        }

        return true;
    }
}
