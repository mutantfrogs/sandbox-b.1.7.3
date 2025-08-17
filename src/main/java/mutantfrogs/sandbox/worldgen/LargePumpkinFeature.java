package mutantfrogs.sandbox.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class LargePumpkinFeature extends Feature {
    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        //check if all blocks under are grass or dirt
        for(int dx = 0; dx < 3; dx++){
            for(int dz = 0; dz < 3; dz++){
                int groundId = world.getBlockId(x + dx, y-1, z + dz);
                if(groundId != Block.GRASS_BLOCK.id && groundId != Block.DIRT.id){
                    return false;
                }
            }
        }

        //empty space check
        for(int dx = 0; dx < 3; dx++){
            for(int dy = 0; dy < 3; dy++){
                for(int dz = 0; dz < 3; dz++){
                    if(world.getBlockId(x +dx, y + dy, z + dz) != 0){
                        return false;
                    }
                }
            }
        }

        //place 3x3 pumpkin cube
        for(int dx = 0; dx < 3; dx++){
            for(int dy = 0; dy < 3; dy++){
                for(int dz = 0; dz < 3; dz++){
                    world.setBlock(x+dx,y+dy,z+dz, Block.PUMPKIN.id);
                }
            }
        }
        return true;
    }
}
