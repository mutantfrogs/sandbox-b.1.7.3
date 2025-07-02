package mutantfrogs.sandbox.items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ExampleItem extends TemplateItem {
    public ExampleItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {

        return stack;
    }
}

