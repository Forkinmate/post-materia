package draylar.postmateria;

import draylar.postmateria.client.renderer.SoulSurferRenderer;
import draylar.postmateria.registry.PMBlocks;
import draylar.postmateria.registry.PMEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.renderer.v1.model.SpriteFinder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PostMateriaClient implements ClientModInitializer {

    private static final Identifier SOUL_FIRE_0_ID = new Identifier("block/soul_fire_0");
    private static final Identifier SOUL_FIRE_1_ID = new Identifier("block/soul_fire_1");
    public static final SpriteIdentifier SOUL_FIRE_0 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, SOUL_FIRE_0_ID);
    public static final SpriteIdentifier SOUL_FIRE_1 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, SOUL_FIRE_1_ID);

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(PMEntities.SOUL_SURFER, SoulSurferRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(PMBlocks.PHANTASMA_SPAWNER, RenderLayer.getCutout());

        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(SOUL_FIRE_0_ID);
            registry.register(SOUL_FIRE_1_ID);
        });
    }
}
