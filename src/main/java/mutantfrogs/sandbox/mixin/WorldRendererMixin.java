package mutantfrogs.sandbox.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ItemParticle;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow
    private Minecraft client;

    //define more particles in addParticle
    @Inject(method = "addParticle", at = @At("TAIL"))
    public void addParticle(String particle, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfo ci){
        if (particle.equals("brickpoof")) {

            if (client != null && client.particleManager != null && client.camera != null) {
                double dx = client.camera.x - x;
                double dy = client.camera.y - y;
                double dz = client.camera.z - z;

                if (dx * dx + dy * dy + dz * dz <= 256.0) { // 16 blocks
                    client.particleManager.addParticle(new ItemParticle(client.world, x, y, z, Item.BRICK));
                }
            }
        }
    }
}
