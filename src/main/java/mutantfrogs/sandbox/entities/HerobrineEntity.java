package mutantfrogs.sandbox.entities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class HerobrineEntity extends LivingEntity {

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
        }
        else{
            this.markDead();
        }
    }

}
