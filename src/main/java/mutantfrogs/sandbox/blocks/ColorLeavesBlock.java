package mutantfrogs.sandbox.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.template.block.TemplateLeavesBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class ColorLeavesBlock extends TemplateLeavesBlock {
    private final int leavesColor;

    public ColorLeavesBlock(Identifier identifier, int color){
        super(identifier, 52);
        this.leavesColor = color;
        this.renderSides = true;
    }

    @Override
    public int getColor(int meta) {
        return leavesColor;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public int getTexture(int side, int meta) {
        return this.textureId;
    }

    @Override
    public int getColorMultiplier(BlockView blockView, int x, int y, int z) {
       return getColor(0);
    }

    @Environment(EnvType.CLIENT)
    public void setFancyGraphics(boolean bl) {
        this.renderSides = bl;
        this.textureId = bl ? 52 : 53;
    }
}
