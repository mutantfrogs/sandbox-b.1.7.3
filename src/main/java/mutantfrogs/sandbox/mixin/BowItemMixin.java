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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowItem.class)
public class BowItemMixin extends Item{
    public BowItemMixin(int i) {
        super(i);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci){
        this.maxCount = 1;
        this.setMaxDamage(384);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        for (int index = 0; index < user.inventory.main.length; index++) {
            if (user.inventory.main[index] != null && user.inventory.main[index].itemId == Item.ARROW.id) {
                stack.damage(1, user);
                user.inventory.remove(Item.ARROW.id);
                world.playSound(user, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
                if (!world.isRemote) {
                    world.spawnEntity(new ArrowEntity(world, user));
                }
                break;
            }
            else if(user.inventory.main[index] != null && user.inventory.main[index].itemId == Sandbox.torchArrow.id){
                stack.damage(1, user);
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
