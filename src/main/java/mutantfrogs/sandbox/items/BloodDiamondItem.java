package mutantfrogs.sandbox.items;

import mutantfrogs.sandbox.iWorld;
import net.minecraft.block.Block;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BloodDiamondItem extends TemplateItem {
    public BloodDiamondItem(Identifier identifier) {
        super(identifier);
    }

    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        herobrineShrineCheck:
        if (world.getBlockId(x, y, z) == Block.NETHERRACK.id && world.getBlockId(x, y - 1, z) == Block.GOLD_BLOCK.id) {
            //gold check
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (world.getBlockId(x + dx, y - 1, z + dz) != Block.GOLD_BLOCK.id) {
                        break herobrineShrineCheck;
                    }
                }
            }

            //redstone torch check
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (Math.abs(dx) + Math.abs(dz) == 1) {
                        if (world.getBlockId(x + dx, y, z + dz) != Block.LIT_REDSTONE_TORCH.id) {
                            break herobrineShrineCheck;
                        }
                    }
                }
            }
            stack.count--;
            world.setBlock(x, y + 1, z, Block.FIRE.id);
            LightningEntity lightning = new LightningEntity(world, x, y, z);
            world.spawnEntity(lightning);
            world.playSound(x,y,z, "sandbox:scream", 0.2f, 1f);
            ((iWorld) world).setWeatherThundering();
        }
        return true;
    }
}