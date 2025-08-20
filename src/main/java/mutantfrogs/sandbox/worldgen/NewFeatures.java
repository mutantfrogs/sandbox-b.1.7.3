package mutantfrogs.sandbox.worldgen;

import mutantfrogs.sandbox.Sandbox;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeModificationEvent;
import net.modificationstation.stationapi.api.worldgen.feature.HeightScatterFeature;
import net.modificationstation.stationapi.api.worldgen.feature.VolumetricScatterFeature;
import net.modificationstation.stationapi.api.worldgen.feature.WeightedFeature;

public class NewFeatures {
    public static final Feature COPPER_ORE = new OreFeature(Sandbox.copperOreBlock.id, 10);
    public static final Feature COPPER_ORE_SCATTERED = new VolumetricScatterFeature(COPPER_ORE, 10, 128);

    public static final Feature AUTUMN_TREE = new AutumnalTreesFeature();
    public static final Feature AUTUMN_TREE_SCATTERED = new HeightScatterFeature(AUTUMN_TREE, 3);

    public static final Feature LARGE_PUMPKIN = new LargePumpkinFeature();
    public static final Feature LARGE_PUMPKIN_SCATTERED = new HeightScatterFeature(new WeightedFeature(LARGE_PUMPKIN, 6), 1);

    @EventListener
    public static void init(BiomeModificationEvent event){
        Biome biome = event.biome;

        //add features to all overworld biomes
        if(biome != null && event.world.dimension instanceof OverworldDimension) {
            biome.addFeature(COPPER_ORE_SCATTERED);
            biome.addFeature(LARGE_PUMPKIN_SCATTERED);
        }
    }
}
