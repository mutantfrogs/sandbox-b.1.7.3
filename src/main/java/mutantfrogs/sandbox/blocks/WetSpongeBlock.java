package mutantfrogs.sandbox.blocks;

import mutantfrogs.sandbox.Sandbox;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class WetSpongeBlock extends TemplateBlock {
    public WetSpongeBlock(Identifier identifier) {
        super(identifier, Material.SPONGE);
    }

    public int getDroppedItemId(int blockMeta, Random random) {
        return Sandbox.wetSpongeBlock.id;
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        super.onPlaced(world, x, y, z);
        if(world.dimension.isNether){
            world.setBlock(x,y,z, 19); //19 is sponge block
            for (int particleCount = 0; particleCount < 10; particleCount++) {
                double velocityX = world.random.nextGaussian() * 0.05;
                double velocityY = world.random.nextGaussian() * 0.05;
                double velocityZ = world.random.nextGaussian() * 0.05;
                world
                        .addParticle(
                                "explode",
                                x + (double)(world.random.nextFloat()),
                                y + (double)(world.random.nextFloat()),
                                z + (double)(world.random.nextFloat()),
                                velocityX,
                                velocityY,
                                velocityZ
                        );
            }
                world.playSound((double)x + 0.5, (double)y + 0.5, (double)z + 0.5,"random.fizz",1.0f, world.random.nextFloat() * 0.4F + 1F);
        }
    }
}
