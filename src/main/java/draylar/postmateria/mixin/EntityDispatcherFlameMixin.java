package draylar.postmateria.mixin;

import draylar.postmateria.PostMateriaClient;
import draylar.postmateria.api.SoulBlazeHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityDispatcherFlameMixin {

    @Shadow
    public Camera camera;

    @Inject(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;doesRenderOnFire()Z"))
    private void renderPhantasmBlaze(Entity entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if(entity instanceof LivingEntity living) {
            if (SoulBlazeHelper.isSoulBlazed(living)) {
                renderSoulFire(matrices, vertexConsumers, entity);
            }
        }
    }

    @Unique
    private void renderSoulFire(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity) {
        Sprite baseSprite = PostMateriaClient.SOUL_FIRE_0.getSprite();
        Sprite overSprite = PostMateriaClient.SOUL_FIRE_1.getSprite();
        matrices.push();
        float fireScale = entity.getWidth() * 1.4F;
        matrices.scale(fireScale, fireScale, fireScale);
        float g = 0.5F;
        float h = 0.0F;
        float i = entity.getHeight() / fireScale;
        float j = 0.0F;
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-this.camera.getYaw()));
        matrices.translate(0.0D, 0.0D, (-0.3F + (float) ((int) i) * 0.02F));
        float k = 0.0F;
        int l = 0;
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(TexturedRenderLayers.getEntityCutout());

        for (MatrixStack.Entry entry = matrices.peek(); i > 0.0F; ++l) {
            Sprite sprite3 = l % 2 == 0 ? baseSprite : overSprite;
            float m = sprite3.getMinU();
            float n = sprite3.getMinV();
            float o = sprite3.getMaxU();
            float p = sprite3.getMaxV();
            if (l / 2 % 2 == 0) {
                float q = o;
                o = m;
                m = q;
            }

            drawSoulFlameTexture(entry, vertexConsumer, g - 0.0F, 0.0F - j, k, o, p);
            drawSoulFlameTexture(entry, vertexConsumer, -g - 0.0F, 0.0F - j, k, m, p);
            drawSoulFlameTexture(entry, vertexConsumer, -g - 0.0F, 1.4F - j, k, m, n);
            drawSoulFlameTexture(entry, vertexConsumer, g - 0.0F, 1.4F - j, k, o, n);
            i -= 0.45F;
            j -= 0.45F;
            g *= 0.9F;
            k += 0.03F;
        }

        matrices.pop();
    }

    @Unique
    private static void drawSoulFlameTexture(MatrixStack.Entry entry, VertexConsumer vertices, float x, float y, float z, float u, float v) {
        vertices.vertex(entry.getModel(), x, y, z)
                .color(255, 255, 255, 255) // Easy way to get a soul flame texture!
                .texture(u, v)
                .overlay(0, 10)
                .light(LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE)
                .normal(entry.getNormal(), 0.0F, 1.0F, 0.0F)
                .next();
    }
}
