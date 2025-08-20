package mutantfrogs.sandbox.worldgen;

import mutantfrogs.sandbox.Sandbox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.PumpkinPatchFeature;
import net.modificationstation.stationapi.api.worldgen.BiomeAPI;
import net.modificationstation.stationapi.api.worldgen.biome.BiomeBuilder;
import net.modificationstation.stationapi.api.worldgen.biome.ClimateBiomeProvider;
import net.modificationstation.stationapi.api.worldgen.feature.DefaultFeatures;
import net.modificationstation.stationapi.api.worldgen.feature.WeightedFeature;

public class AutumnalForestBiome {
    public static Biome exampleBiome;
    public static ClimateBiomeProvider climateBiomeProvider;

    public static void registerBiome(){
        BiomeBuilder biomeBuilder;

        biomeBuilder = BiomeBuilder.start("Example Biome");
        biomeBuilder.fogColor(0xD8C9A6);
        biomeBuilder.leavesColor(0xF05500);
        biomeBuilder.grassColor(0xD9C448);
        biomeBuilder.feature(NewFeatures.AUTUMN_TREE_SCATTERED);
        biomeBuilder.feature(NewFeatures.LARGE_PUMPKIN_SCATTERED);
        biomeBuilder.feature(new WeightedFeature(new PumpkinPatchFeature(), 4));
        exampleBiome = biomeBuilder.build();
    }

    public static void registerBiomeProvider(){
        climateBiomeProvider = new ClimateBiomeProvider();
        climateBiomeProvider.addBiome(exampleBiome,6.0F, 0.3F, 1.0F, 0.4F);
        BiomeAPI.addOverworldBiomeProvider(Sandbox.NAMESPACE.id("climate_biome_provider"), climateBiomeProvider);
    }
}
