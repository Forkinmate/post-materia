package draylar.postmateria;

import draylar.postmateria.client.renderer.SoulSurferRenderer;
import draylar.postmateria.registry.PMEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class PostMateriaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(PMEntities.SOUL_SURFER, SoulSurferRenderer::new);
    }
}
