package mutantfrogs.sandbox.entities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class HerobrineEntity extends LivingEntity {

    private PlayerEntity lookTarget;

    public HerobrineEntity(World world) {
        super(world);
        this.fireImmune = true;
        this.texture = "assets/sandbox/textures/entities/herobrine.png";
        this.age = 20;
    }

    @Override
    public void tick(){
        if(age != 0){
            age--;
            this.lookAt(lookTarget, 180f, 180f);
        }
        else{
            this.markDead();
        }
    }

    public void setLookTarget(PlayerEntity user){
        this.lookTarget = user;
    }

}
