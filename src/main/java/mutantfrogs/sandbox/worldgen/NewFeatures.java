package mutantfrogs.sandbox.worldgen;

import mutantfrogs.sandbox.Sandbox;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeModificationEvent;
import net.modificationstation.stationapi.api.worldgen.feature.VolumetricScatterFeature;

public class NewFeatures {
    public static final Feature COPPER_ORE = new OreFeature(Sandbox.copperOreBlock.id, 10);
    public static final Feature COPPER_ORE_SCATTERED = new VolumetricScatterFeature(COPPER_ORE, 20, 128);

    @EventListener
    public static void init(BiomeModificationEvent event){
        Biome biome = event.biome;

        //add features to all overworld biomes
        if(biome != null && event.world.dimension instanceof OverworldDimension) {
            biome.addFeature(COPPER_ORE_SCATTERED);
        }
    }
}
