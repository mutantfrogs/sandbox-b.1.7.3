package mutantfrogs.sandbox.mixin;

import mutantfrogs.sandbox.iWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(World.class)
public abstract class WorldMixin implements iWorld {

    @Shadow protected WorldProperties properties;

    @Override
    public void setWeatherThundering() {
        this.properties.setRaining(true);
        this.properties.setThundering(true);
        this.properties.setRainTime(168000);
        this.properties.setThunderTime(168000);
    }

    @Override
    public void setWeatherClear() {
        this.properties.setRaining(false);
        this.properties.setThundering(false);
        this.properties.setRainTime(0);
        this.properties.setThunderTime(0);
    }
}
