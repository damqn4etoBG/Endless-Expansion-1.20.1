package net.damqn4etobg.endlessexpansion.dimension;

import com.mojang.datafixers.util.Pair;
import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.worldgen.biome.ModBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import java.util.function.Consumer;

public class ModDimensions {
    public static NoiseRouter blankNoiseRouter = new NoiseRouter(ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction, ModDensityFunction.densityFunction);
    public static List<Climate.ParameterPoint> blankSpawnTarget = List.of(new Climate.ParameterPoint(new Climate.Parameter(0, 0), new Climate.Parameter(0, 0), new Climate.Parameter(0, 0), new Climate.Parameter(0, 0), new Climate.Parameter(0, 0), new Climate.Parameter(0, 0), 0));
    public static ResourceKey<LevelStem> WORLD_BEYOND_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(EndlessExpansion.MODID, "world_beyond"));
    public static ResourceKey<Level> WORLD_BEYOND_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(EndlessExpansion.MODID, "world_beyond"));
    public static ResourceKey<DimensionType> WORLD_BEYOND_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(EndlessExpansion.MODID, "world_beyond_type"));
    private static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
    private static final Climate.Parameter[] temperatures = new Climate.Parameter[]{Climate.Parameter.span(-1.0F, -0.45F), Climate.Parameter.span(-0.45F, -0.15F), Climate.Parameter.span(-0.15F, 0.2F), Climate.Parameter.span(0.2F, 0.55F), Climate.Parameter.span(0.55F, 1.0F)};
    private static final Climate.Parameter deepOceanContinentalness = Climate.Parameter.span(-1.05F, -0.455F);
    private static final Climate.Parameter oceanContinentalness = Climate.Parameter.span(-0.455F, -0.19F);
    private static final ResourceKey<Biome>[][] OCEANS = new ResourceKey[][]{
            {Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.WARM_OCEAN},
            {Biomes.FROZEN_OCEAN, Biomes.COLD_OCEAN, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN}
    };
    private static final Climate.Parameter[] humidities = new Climate.Parameter[]{Climate.Parameter.span(-1.0F, -0.35F), Climate.Parameter.span(-0.35F, -0.1F), Climate.Parameter.span(-0.1F, 0.1F), Climate.Parameter.span(0.1F, 0.3F), Climate.Parameter.span(0.3F, 1.0F)};
    private static final Climate.Parameter[] erosions = new Climate.Parameter[]{Climate.Parameter.span(-1.0F, -0.78F), Climate.Parameter.span(-0.78F, -0.375F), Climate.Parameter.span(-0.375F, -0.2225F), Climate.Parameter.span(-0.2225F, 0.05F), Climate.Parameter.span(0.05F, 0.45F), Climate.Parameter.span(0.45F, 0.55F), Climate.Parameter.span(0.55F, 1.0F)};
    private static final Climate.Parameter FROZEN_RANGE = temperatures[0];
    private static final Climate.Parameter UNFROZEN_RANGE = Climate.Parameter.span(temperatures[1], temperatures[4]);
    private static final Climate.Parameter coastContinentalness = Climate.Parameter.span(-0.19F, -0.11F);
    private static final Climate.Parameter inlandContinentalness = Climate.Parameter.span(-0.11F, 0.55F);
    private static final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(-0.11F, 0.03F);
    private static final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.03F, 0.3F);
    private static final Climate.Parameter farInlandContinentalness = Climate.Parameter.span(0.3F, 1.0F);
    private static final ResourceKey<Biome>[][] NEAR_INLAND_BIOMES = new ResourceKey[][]{
            {ModBiomes.SCORCHED_WASTES, ModBiomes.VOLCANIC_WASTES, ModBiomes.SUNKEN_WASTES}
    };

    private static final ResourceKey<Biome>[][] MID_INLAND_BIOMES = new ResourceKey[][]{
            {ModBiomes.TITANIC_FOREST, ModBiomes.FROZEN_WASTES, ModBiomes.ZERZURA}
    };

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(WORLD_BEYOND_DIM_TYPE, new DimensionType(
                OptionalLong.empty(), // fixedTime
                true, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0D, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                -64, // minY
                384, // height
                384, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                0.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        MultiNoiseBiomeSource biomeSource = MultiNoiseBiomeSource.createFromList(
                new Climate.ParameterList<>(generateBiomePoints(biomeRegistry)));

        NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
                biomeSource,
                noiseGenSettings.getOrThrow(ModLevelGen.WORLD_BEYOND));

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.WORLD_BEYOND_DIM_TYPE), noiseBasedChunkGenerator);

        context.register(WORLD_BEYOND_KEY, stem);
    }

    private static List<Pair<Climate.ParameterPoint, Holder<Biome>>> generateBiomePoints(HolderGetter<Biome> biomeRegistry) {
        ArrayList<Pair<Climate.ParameterPoint, Holder<Biome>>> biomePoints = new ArrayList<>();

        addOceans(pair -> biomePoints.add(Pair.of(pair.getFirst(), biomeRegistry.getOrThrow(pair.getSecond()))));
        addShoreBiomes(pair -> biomePoints.add(Pair.of(pair.getFirst(), biomeRegistry.getOrThrow(pair.getSecond()))));
        //addRivers(pair -> biomePoints.add(Pair.of(pair.getFirst(), biomeRegistry.getOrThrow(pair.getSecond()))));
        addInlandBiomes(pair -> biomePoints.add(Pair.of(pair.getFirst(), biomeRegistry.getOrThrow(pair.getSecond()))));
        //addDebugBiomes(pair -> biomePoints.add(Pair.of(pair.getFirst(), biomeRegistry.getOrThrow(pair.getSecond()))));

        return biomePoints;
    }

    private static void addInlandBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
        for (int i = 0; i < temperatures.length; ++i) {
            Climate.Parameter temperature = temperatures[i];

            for (int j = 0; j < humidities.length; ++j) {
                Climate.Parameter humidity = humidities[j];

                ResourceKey<Biome> nearInlandBiome = getTemperatureAppropriateNearInlandBiome(i, j);
                ResourceKey<Biome> midInlandBiome = getTemperatureAppropriateMidInlandBiome(i, j);

                if (nearInlandBiome == ModBiomes.SUNKEN_WASTES) {
                    addSurfaceBiome(consumer, Climate.Parameter.span(temperatures[1], temperatures[2]), humidity, Climate.Parameter.span(nearInlandContinentalness, farInlandContinentalness), erosions[6], FULL_RANGE, 0.0F, nearInlandBiome);
                } else if (nearInlandBiome == ModBiomes.VOLCANIC_WASTES) {
                    addSurfaceBiome(consumer, temperatures[4], humidity, Climate.Parameter.span(nearInlandContinentalness, midInlandContinentalness), FULL_RANGE, FULL_RANGE, 0.0F, nearInlandBiome);
                } else {
                    addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(nearInlandContinentalness, midInlandContinentalness), FULL_RANGE, FULL_RANGE, 0.0F, nearInlandBiome);
                }

                if (midInlandBiome == ModBiomes.FROZEN_WASTES) {
                    addSurfaceBiome(consumer, FROZEN_RANGE, humidity, Climate.Parameter.span(midInlandContinentalness, farInlandContinentalness), FULL_RANGE, FULL_RANGE, 0.0F, midInlandBiome);
                } else {
                    addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(midInlandContinentalness, farInlandContinentalness), FULL_RANGE, FULL_RANGE, 0.0F, midInlandBiome);
                }

                if (nearInlandBiome == ModBiomes.SUNKEN_WASTES && isAppropriateTemperatureForSunkenWastes(i)) {
                    addSurfaceBiome(consumer, Climate.Parameter.span(temperatures[1], temperatures[2]), humidity, Climate.Parameter.span(nearInlandContinentalness, midInlandContinentalness), Climate.Parameter.span(erosions[3], erosions[5]), FULL_RANGE, 0.0F, ModBiomes.SUNKEN_WASTES);
                    addSurfaceBiome(consumer, Climate.Parameter.span(temperatures[1], temperatures[2]), humidity, Climate.Parameter.span(nearInlandContinentalness, farInlandContinentalness), erosions[6], FULL_RANGE, 0.0F, ModBiomes.SUNKEN_WASTES);
                }
            }
        }
    }

    private static ResourceKey<Biome> getTemperatureAppropriateNearInlandBiome(int pTemperature, int humidityIndex) {
        ResourceKey<Biome> biome = NEAR_INLAND_BIOMES[pTemperature % NEAR_INLAND_BIOMES.length][humidityIndex % NEAR_INLAND_BIOMES[0].length];
        return adjustBiomeForTemperature(biome, pTemperature);
    }

    private static ResourceKey<Biome> getTemperatureAppropriateMidInlandBiome(int pTemperature, int humidityIndex) {
        ResourceKey<Biome> biome = MID_INLAND_BIOMES[pTemperature % MID_INLAND_BIOMES.length][humidityIndex % MID_INLAND_BIOMES[0].length];
        return adjustBiomeForTemperature(biome, pTemperature);
    }

    private static ResourceKey<Biome> adjustBiomeForTemperature(ResourceKey<Biome> biome, int pTemperature) {
        if (pTemperature <= 1) {  // Cold
            if (biome == ModBiomes.VOLCANIC_WASTES || biome == ModBiomes.SUNKEN_WASTES || biome == ModBiomes.SCORCHED_WASTES ||
                    biome == ModBiomes.TITANIC_FOREST || biome == ModBiomes.ZERZURA)
                return ModBiomes.FROZEN_WASTES;
        } else if (pTemperature == 2) {  // Moderate
            if (biome == ModBiomes.SCORCHED_WASTES || biome == ModBiomes.VOLCANIC_WASTES || biome == ModBiomes.FROZEN_WASTES)
                return pickTitanicForestOrZerzura(pTemperature);
        } else {  // Hot
            if (biome == ModBiomes.FROZEN_WASTES || biome == ModBiomes.SUNKEN_WASTES) return pickScorchedOrVolcanicWastes(pTemperature);
        }
        return biome;
    }

    private static boolean isAppropriateTemperatureForSunkenWastes(int pTemperature) {
        return pTemperature >= 2 && pTemperature <= 3;
    }

    private static ResourceKey<Biome> pickScorchedOrVolcanicWastes(int pTemperature) {
        if (pTemperature == 3) {
            return ModBiomes.SCORCHED_WASTES;
        }
        return ModBiomes.VOLCANIC_WASTES;
    }

    private static ResourceKey<Biome> pickTitanicForestOrZerzura(int pTemperature) {
        if (pTemperature == 2) {
            return ModBiomes.ZERZURA;
        } else {
            return ModBiomes.TITANIC_FOREST;
        }
    }

    private static void addShoreBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
        for (int i = 0; i < temperatures.length; ++i) {
            Climate.Parameter temperature = temperatures[i];
            for (int j = 0; j < humidities.length; ++j) {
                Climate.Parameter humidity = humidities[j];
                ResourceKey<Biome> beachBiome = pickFrozenOrNormalBeach(i);
                ResourceKey<Biome> beachBiome2 = pickGravelOrRockyBeach(i, j);


                if(beachBiome == ModBiomes.BASALT_BEACH) {
                    addSurfaceBiome(consumer, temperatures[4], humidity, coastContinentalness, Climate.Parameter.span(erosions[2], erosions[5]), FULL_RANGE, 0.0F, beachBiome);
                } else {
                    addSurfaceBiome(consumer, temperature, humidity, coastContinentalness, Climate.Parameter.span(erosions[2], erosions[5]), FULL_RANGE, 0.0F, beachBiome);
                }
                addSurfaceBiome(consumer, temperatures[2], humidity, coastContinentalness, Climate.Parameter.span(erosions[2], erosions[5]), FULL_RANGE, 0.0F, beachBiome2);
            }
        }
    }

    private static void addOceans(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer) {
        for (int i = 0; i < temperatures.length; ++i) {
            Climate.Parameter climate$parameter = temperatures[i];
            addSurfaceBiome(pConsumer, climate$parameter, FULL_RANGE, deepOceanContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, OCEANS[0][i]);
            addSurfaceBiome(pConsumer, climate$parameter, FULL_RANGE, oceanContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, OCEANS[1][i]);

//            if (OCEANS[0][i] == Biomes.DEEP_COLD_OCEAN || OCEANS[1][i] == Biomes.COLD_OCEAN) {
//                addSurfaceBiome(pConsumer, climate$parameter, FULL_RANGE, deepOceanContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, ModBiomes.ABYSSAL_OCEAN);
//                addSurfaceBiome(pConsumer, climate$parameter, FULL_RANGE, deepOceanContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, ModBiomes.ABYSSAL_OCEAN);
//            }

            // more abyssal ocean
//            if (i % 2 == 0 || i % 3 == 0) {
//                addSurfaceBiome(pConsumer, climate$parameter, FULL_RANGE, deepOceanContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, ModBiomes.ABYSSAL_OCEAN);
//                addSurfaceBiome(pConsumer, climate$parameter, FULL_RANGE, deepOceanContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, ModBiomes.ABYSSAL_OCEAN);
//            }
        }
    }

    private static void addDebugBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer) {
        addSurfaceBiome(pConsumer, UNFROZEN_RANGE, FULL_RANGE, deepOceanContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, ModBiomes.ABYSSAL_OCEAN);
    }

    private static void addRivers(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer) {
        for (int i = 0; i < temperatures.length; ++i) {
            Climate.Parameter temperature = temperatures[i];
            Climate.Parameter pParam = Climate.Parameter.span(-0.05F, 0.05F);

            // Adding river biomes with extended ranges for erosion and continentalness to make rivers longer
            addSurfaceBiome(pConsumer, FROZEN_RANGE, FULL_RANGE, Climate.Parameter.span(coastContinentalness, farInlandContinentalness), Climate.Parameter.span(erosions[0], erosions[3]), pParam, 0.0F, Biomes.FROZEN_RIVER);
            addSurfaceBiome(pConsumer, UNFROZEN_RANGE, FULL_RANGE, Climate.Parameter.span(coastContinentalness, farInlandContinentalness), Climate.Parameter.span(erosions[0], erosions[3]), pParam, 0.0F, Biomes.RIVER);
            addSurfaceBiome(pConsumer, FROZEN_RANGE, FULL_RANGE, Climate.Parameter.span(nearInlandContinentalness, farInlandContinentalness), Climate.Parameter.span(erosions[0], erosions[3]), pParam, 0.0F, Biomes.FROZEN_RIVER);
            addSurfaceBiome(pConsumer, UNFROZEN_RANGE, FULL_RANGE, Climate.Parameter.span(nearInlandContinentalness, farInlandContinentalness), Climate.Parameter.span(erosions[0], erosions[3]), pParam, 0.0F, Biomes.RIVER);
            addSurfaceBiome(pConsumer, FROZEN_RANGE, FULL_RANGE, Climate.Parameter.span(coastContinentalness, farInlandContinentalness), Climate.Parameter.span(erosions[3], erosions[5]), pParam, 0.0F, Biomes.FROZEN_RIVER);
            addSurfaceBiome(pConsumer, UNFROZEN_RANGE, FULL_RANGE, Climate.Parameter.span(coastContinentalness, farInlandContinentalness), Climate.Parameter.span(erosions[3], erosions[5]), pParam, 0.0F, Biomes.RIVER);
            addSurfaceBiome(pConsumer, FROZEN_RANGE, FULL_RANGE, Climate.Parameter.span(coastContinentalness, farInlandContinentalness), erosions[6], pParam, 0.0F, Biomes.FROZEN_RIVER);
            addSurfaceBiome(pConsumer, UNFROZEN_RANGE, FULL_RANGE, Climate.Parameter.span(coastContinentalness, farInlandContinentalness), erosions[6], pParam, 0.0F, Biomes.RIVER);
            addSurfaceBiome(pConsumer, FROZEN_RANGE, FULL_RANGE, Climate.Parameter.span(inlandContinentalness, farInlandContinentalness), erosions[6], pParam, 0.0F, Biomes.FROZEN_RIVER);
            addSurfaceBiome(pConsumer, UNFROZEN_RANGE, FULL_RANGE, Climate.Parameter.span(inlandContinentalness, farInlandContinentalness), erosions[6], pParam, 0.0F, Biomes.RIVER);
        }
    }

    private static ResourceKey<Biome> pickGravelOrRockyBeach(int pTemperature, int pHumidity) {
        if(pTemperature == 2) {
            if(pHumidity == 0) {
                return ModBiomes.ROCKY_BEACH;
            } else {
                return ModBiomes.GRAVEL_BEACH;
            }
        } else {
            if(pHumidity < 2) {
                return ModBiomes.GRAVEL_BEACH;
            } else {
                return ModBiomes.ROCKY_BEACH;
            }
        }
    }

    private static ResourceKey<Biome> pickFrozenOrNormalBeach(int pTemperature) {
        if (pTemperature == 0) {
            return ModBiomes.FROZEN_BEACH;
        } else {
            return pTemperature == 4 ? ModBiomes.BASALT_BEACH : ModBiomes.BEACH;
        }
    }

    private static boolean isNearVolcanicWastes(Climate.Parameter temperature, Climate.Parameter humidity) {
        // Placeholder logic to determine if near volcanic wastes
        // This should be replaced with the actual logic to check proximity
        return temperature.max() >= 4.0F && humidity.max() <= 0.1F;
    }

    private static void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pTemperature, Climate.Parameter pHumidity, Climate.Parameter pContinentalness, Climate.Parameter pErosion, Climate.Parameter pDepth, float pWeirdness, ResourceKey<Biome> pKey) {
        pConsumer.accept(Pair.of(Climate.parameters(pTemperature, pHumidity, pContinentalness, pErosion, Climate.Parameter.point(0.0F), pDepth, pWeirdness), pKey));
        pConsumer.accept(Pair.of(Climate.parameters(pTemperature, pHumidity, pContinentalness, pErosion, Climate.Parameter.point(1.0F), pDepth, pWeirdness), pKey));
    }
}