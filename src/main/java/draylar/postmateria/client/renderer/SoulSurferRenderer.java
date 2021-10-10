package draylar.postmateria.client.renderer;

import draylar.postmateria.entity.SoulSurferEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class SoulSurferRenderer extends EntityRenderer<SoulSurferEntity> {

    public SoulSurferRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(SoulSurferEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        ItemStack stack = entity.asItemStack();

        // Translations to go from our item model to a projectile in 3D space
        matrices.scale(1.5F, 1.5F, 1.5F);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 45.0F));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));

        // Render the item
        MinecraftClient.getInstance().getItemRenderer().renderItem(
                stack,
                ModelTransformation.Mode.FIXED,
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                0);

        // Client-side particles - no need for the server to broadcast these
        double lerpedX = MathHelper.lerp(tickDelta, entity.prevX, entity.getX());
        double lerpedY = MathHelper.lerp(tickDelta, entity.prevY, entity.getY());
        double lerpedZ = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ());
        entity.world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, true, lerpedX, lerpedY, lerpedZ, 0, 0, 0);

        matrices.pop();
    }

    @Override
    public Identifier getTexture(SoulSurferEntity entity) {
        return null;
    }
}
