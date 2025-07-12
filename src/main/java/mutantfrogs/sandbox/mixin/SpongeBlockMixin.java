package mutantfrogs.sandbox.mixin;

import mutantfrogs.sandbox.Sandbox;
import net.minecraft.block.Block;
import net.minecraft.block.SpongeBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpongeBlock.class)
public class SpongeBlockMixin extends Block {

    public SpongeBlockMixin(int id) {
        super(id, Material.SPONGE);
        this.textureId = 48;
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        byte range = 4 ; // formerly var5
        for (int dx = x - range; dx <= x + range; dx++) {
            for (int dy = y - range; dy <= y + range; dy++) {
                for (int dz = z - range; dz <= z + range; dz++) {
                    if (world.getMaterial(dx, dy, dz) == Material.WATER) {
                        world.setBlock(dx,dy,dz, 0);
                        world.setBlock(x,y,z, Sandbox.wetSpongeBlock.id);
                        for (int particleCount = 0; particleCount < 5; particleCount++) {
                            double velocityX = world.random.nextGaussian() * 0.01;
                            double velocityY = world.random.nextGaussian() * 0.01;
                            double velocityZ = world.random.nextGaussian() * 0.01;
                            world
                                    .addParticle(
                                            "splash",
                                            dx + (double)(world.random.nextFloat()),
                                            dy + (double)(world.random.nextFloat()),
                                            dz + (double)(world.random.nextFloat()),
                                            velocityX,
                                            velocityY,
                                            velocityZ
                                    );
                        }
                    }
                }
            }
        }
    }
}
