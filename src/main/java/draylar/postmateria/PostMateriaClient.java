package draylar.postmateria;

import draylar.postmateria.client.renderer.SoulSurferRenderer;
import draylar.postmateria.registry.PMBlocks;
import draylar.postmateria.registry.PMEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class PostMateriaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(PMEntities.SOUL_SURFER, SoulSurferRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(PMBlocks.PHANTASMA_SPAWNER, RenderLayer.getCutout());
    }
}
