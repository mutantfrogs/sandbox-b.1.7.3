package mutantfrogs.sandbox.blocks;

import mutantfrogs.sandbox.Sandbox;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class CopperOreBlock extends TemplateBlock {
    public CopperOreBlock(Identifier identifier) {
        super(identifier, Material.STONE);
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return Sandbox.rawCopper.id;
    }

    @Override
    public int getDroppedItemCount(Random random) {
        int count = random.nextInt(5);

        if(count == 0){
            count++;
        }

        return count;
    }
}
