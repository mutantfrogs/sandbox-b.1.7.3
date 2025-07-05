package mutantfrogs.sandbox.mixin;

import mutantfrogs.sandbox.entities.BrickEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {


    @Inject(method = "use", at = @At("HEAD"))
    public void use(ItemStack stack, World world, PlayerEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if(stack.itemId == Item.BRICK.id){
            stack.count--;
            if (!world.isRemote) {
                world.spawnEntity(new BrickEntity(world, user));
            }
        }
    }
}
