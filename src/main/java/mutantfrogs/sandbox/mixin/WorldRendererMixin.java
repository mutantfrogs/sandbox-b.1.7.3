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

    //removes modID from new streaming sounds when played on a jukebox
    @Inject(method = "playStreaming", at = @At("HEAD"), cancellable = true)
    public void playStreaming(String stream, int x, int y, int z, CallbackInfo ci){
        if (stream != null) {
            if(stream.startsWith("sandbox:")){
              String[] streamWithoutModId = stream.split(":");
              this.client.inGameHud.setRecordPlayingOverlay("C418 - " + streamWithoutModId[1]);
            }
            else{
                this.client.inGameHud.setRecordPlayingOverlay("C418 - " + stream);
            }
        }

        this.client.soundManager.playStreaming(stream, (float)x, (float)y, (float)z, 1.0F, 1.0F);
        ci.cancel();
    }

    //define more particles in addParticle
    @Inject(method = "addParticle", at = @At("TAIL"))
    //adding more particles strings that addParticle can reference
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
