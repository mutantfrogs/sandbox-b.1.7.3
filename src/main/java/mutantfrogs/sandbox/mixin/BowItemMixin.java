package mutantfrogs.sandbox.mixin;

import mutantfrogs.sandbox.Sandbox;
import mutantfrogs.sandbox.entities.TorchArrowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BowItem.class)
public class BowItemMixin extends Item{
    public BowItemMixin(int i) {
        super(i);
        this.maxCount = 1;
        this.setMaxDamage(384);
    }

    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        for (int index = 0; index < user.inventory.main.length; index++) {
            if (user.inventory.main[index] != null && user.inventory.main[index].itemId == Item.ARROW.id) {
                user.inventory.remove(Item.ARROW.id);
                world.playSound(user, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
                if (!world.isRemote) {
                    world.spawnEntity(new ArrowEntity(world, user));
                }
                break;
            }
            else if(user.inventory.main[index] != null && user.inventory.main[index].itemId == Sandbox.torchArrow.id){
                user.inventory.remove(Sandbox.torchArrow.id);
                world.playSound(user, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
                if (!world.isRemote) {
                    world.spawnEntity(new TorchArrowEntity(world, user));
                }
                break;
            }
        }

        return stack;
    }
}
