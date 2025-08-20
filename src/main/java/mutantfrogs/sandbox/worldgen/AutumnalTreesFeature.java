package mutantfrogs.sandbox.worldgen;

import java.util.Random;

import mutantfrogs.sandbox.Sandbox;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

public class AutumnalTreesFeature extends Feature {
    Block leaves;
    int trunk;

    public void chooseTreeType(Random random){
        //log meta, since oak and birch have the same block
        switch(random.nextInt(3)){
            case 0:
                trunk = 0;
                break;
            case 1:
                trunk = 2;
                break;
        }

        switch(random.nextInt(3)){
            case 0:
                leaves = Sandbox.redLeaves;
                break;
            case 1:
                leaves = Sandbox.orangeLeaves;
                break;
            case 2:
                leaves = Sandbox.yellowLeaves;
                break;
        }
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        chooseTreeType(random);
        int treeHeight = random.nextInt(3) + 4;
        boolean canGenerate = true;
        //if in world height bounds
        if (y >= 1 && y + treeHeight + 1 <= 128) {
            //check space for existing blocks
            for (int var8 = y; var8 <= y + 1 + treeHeight; var8++) {
                byte var9 = 1;
                if (var8 == y) {
                    var9 = 0;
                }

                if (var8 >= y + 1 + treeHeight - 2) {
                    var9 = 2;
                }

                for (int var10 = x - var9; var10 <= x + var9 && canGenerate; var10++) {
                    for (int var11 = z - var9; var11 <= z + var9 && canGenerate; var11++) {
                        if (var8 >= 0 && var8 < 128) {
                            int var12 = world.getBlockId(var10, var8, var11);
                            if (var12 != 0 && (var12 != Block.LEAVES.id && var12 != leaves.id)) {
                                canGenerate = false;
                            }
                        } else {
                            canGenerate = false;
                        }
                    }
                }
            }

            //check ground for grass or dirt blocks
            if (!canGenerate) {
                return false;
            } else {
                int var16 = world.getBlockId(x, y - 1, z);
                if ((var16 == Block.GRASS_BLOCK.id || var16 == Block.DIRT.id) && y < 128 - treeHeight - 1) {
                    world.setBlockWithoutNotifyingNeighbors(x, y - 1, z, Block.DIRT.id);

                    //generate leaves
                    for (int var17 = y - 3 + treeHeight; var17 <= y + treeHeight; var17++) {
                        int var19 = var17 - (y + treeHeight);
                        int var21 = 1 - var19 / 2;

                        for (int var22 = x - var21; var22 <= x + var21; var22++) {
                            int var13 = var22 - x;

                            for (int var14 = z - var21; var14 <= z + var21; var14++) {
                                int var15 = var14 - z;
                                if ((Math.abs(var13) != var21 || Math.abs(var15) != var21 || random.nextInt(2) != 0 && var19 != 0)
                                        && !Block.BLOCKS_OPAQUE[world.getBlockId(var22, var17, var14)]) {
                                    world.setBlockWithoutNotifyingNeighbors(var22, var17, var14, leaves.id);
                                }
                            }
                        }
                    }

                    //generate trunk
                    for (int var18 = 0; var18 < treeHeight; var18++) {
                        int var20 = world.getBlockId(x, y + var18, z);
                        if (var20 == 0 || var20 == leaves.id) {
                            world.setBlockWithoutNotifyingNeighbors(x, y + var18, z, Block.LOG.id, trunk);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}

