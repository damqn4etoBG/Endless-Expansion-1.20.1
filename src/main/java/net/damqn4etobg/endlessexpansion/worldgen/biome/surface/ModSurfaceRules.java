package net.damqn4etobg.endlessexpansion.worldgen.biome.surface;

import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.damqn4etobg.endlessexpansion.worldgen.biome.ModBiomes;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

public class ModSurfaceRules {
    // HUGE THANKS TO ElvenWhiskers/Starfall FOR MAKING THIS POSSIBLE <3
    // https://github.com/ElvenWhiskers/Starfall

    // Surface Rules are the most confusing thing ever, I hope no one has to deal with this shit
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource TITANUM_GRASS = makeStateRule(ModBlocks.TITANUM_GRASS_BLOCK.get());
    private static final SurfaceRules.RuleSource TITANUM_SOIL = makeStateRule(ModBlocks.TITANUM_SOIL.get());
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
    private static final SurfaceRules.RuleSource SNOW = makeStateRule(Blocks.SNOW_BLOCK);
    private static final SurfaceRules.RuleSource PACKED_SNOW = makeStateRule(ModBlocks.PACKED_SNOW_BLOCK.get());
    private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);
    private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource BASALT = makeStateRule(Blocks.BASALT);
    private static final SurfaceRules.RuleSource SMOOTH_BASALT = makeStateRule(Blocks.SMOOTH_BASALT);
    private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);
    private static final SurfaceRules.RuleSource ICE = makeStateRule(Blocks.ICE);
    private static final SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);
    private static final SurfaceRules.RuleSource COBBLESTONE = makeStateRule(Blocks.COBBLESTONE);
    private static final SurfaceRules.RuleSource ANDESITE = makeStateRule(Blocks.ANDESITE);

    public static RuleSource makeRules() {
        ConditionSource isAtOrAboveWaterLevel = waterBlockCheck(-1, 0);
        RuleSource OCEAN_FLOOR = sequence(ifTrue(ON_FLOOR, sequence(ifTrue(isBiome(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, ModBiomes.SCORCHED_WASTES), sequence(ifTrue(ON_CEILING, SANDSTONE), SAND)), sequence(ifTrue(ON_CEILING, STONE), GRAVEL))));
        RuleSource NON_OCEAN_FLOOR = sequence(ifTrue(ON_FLOOR, sequence(ifTrue(isBiome(ModBiomes.BEACH, ModBiomes.ROCKY_BEACH, ModBiomes.GRAVEL_BEACH, ModBiomes.TITANIC_FOREST, ModBiomes.SUNKEN_WASTES, ModBiomes.VOLCANIC_WASTES, ModBiomes.ZERZURA), sequence(ifTrue(ON_CEILING, STONE), DIRT)))));

        return sequence(
                ifTrue(abovePreliminarySurface(), ifTrue(stoneDepthCheck(0, false, 0, CaveSurface.FLOOR), ifTrue(isAtOrAboveWaterLevel, sequence(
                        ifTrue(isBiome(ModBiomes.TITANIC_FOREST), ifTrue(ON_FLOOR, TITANUM_GRASS)),
                        ifTrue(isBiome(ModBiomes.FROZEN_WASTES), ifTrue(ON_FLOOR, SNOW)),
                        ifTrue(isBiome(ModBiomes.SCORCHED_WASTES), ifTrue(ON_FLOOR, SAND)),
                        ifTrue(isBiome(ModBiomes.VOLCANIC_WASTES), ifTrue(ON_FLOOR, BASALT)),
                        ifTrue(isBiome(ModBiomes.SUNKEN_WASTES), ifTrue(ON_FLOOR, MUD)),
                        ifTrue(isBiome(ModBiomes.ZERZURA), ifTrue(ON_FLOOR, GRASS_BLOCK)),
                        ifTrue(isBiome(ModBiomes.ABYSSAL_OCEAN), ifTrue(ON_FLOOR, SAND)),
                        ifTrue(isBiome(ModBiomes.BEACH), ifTrue(ON_FLOOR, SAND)),
                        ifTrue(isBiome(ModBiomes.FROZEN_BEACH), ifTrue(ON_FLOOR, SNOW)),
                        ifTrue(isBiome(ModBiomes.BASALT_BEACH), ifTrue(ON_FLOOR, SMOOTH_BASALT)),
                        ifTrue(isBiome(ModBiomes.GRAVEL_BEACH), ifTrue(ON_FLOOR, GRAVEL)),
                        ifTrue(isBiome(ModBiomes.ROCKY_BEACH), ifTrue(ON_FLOOR, STONE))
                )))),

                sequence(ifTrue(isBiome(ModBiomes.TITANIC_FOREST), ifTrue(yStartCheck(VerticalAnchor.absolute(64),0),
                        ifTrue(stoneDepthCheck(0, true, 0, CaveSurface.FLOOR), TITANUM_SOIL)))),
                sequence(ifTrue(isBiome(ModBiomes.FROZEN_WASTES), ifTrue(yStartCheck(VerticalAnchor.absolute(64),0),
                        ifTrue(stoneDepthCheck(0, true, 2, CaveSurface.FLOOR), PACKED_SNOW)))),

                // Surface before underground
                sequence(ifTrue(isBiome(ModBiomes.SCORCHED_WASTES), ifTrue(yStartCheck(VerticalAnchor.absolute(64),0),
                        ifTrue(stoneDepthCheck(0, true, 1, CaveSurface.FLOOR), SAND)))),
                sequence(ifTrue(isBiome(ModBiomes.SCORCHED_WASTES), ifTrue(yStartCheck(VerticalAnchor.absolute(62),0),
                        ifTrue(stoneDepthCheck(0, true, 8, CaveSurface.FLOOR), SANDSTONE)))),

                sequence(ifTrue(isBiome(ModBiomes.SUNKEN_WASTES), ifTrue(yStartCheck(VerticalAnchor.absolute(64),0),
                        ifTrue(stoneDepthCheck(0, true, 2, CaveSurface.FLOOR), MUD)))),
                sequence(ifTrue(isBiome(ModBiomes.SUNKEN_WASTES), ifTrue(yStartCheck(VerticalAnchor.absolute(61),0),
                        ifTrue(stoneDepthCheck(0, true, 0, CaveSurface.FLOOR), STONE)))),

                sequence(ifTrue(isBiome(ModBiomes.VOLCANIC_WASTES), ifTrue(yStartCheck(VerticalAnchor.absolute(64),0),
                        ifTrue(stoneDepthCheck(0, true, 0, CaveSurface.FLOOR), SMOOTH_BASALT)))),

                sequence(ifTrue(isBiome(ModBiomes.ZERZURA), ifTrue(yStartCheck(VerticalAnchor.absolute(64),0),
                        ifTrue(stoneDepthCheck(0, true, 0, CaveSurface.FLOOR), DIRT)))),

                sequence(ifTrue(isBiome(ModBiomes.SUNKEN_WASTES), ifTrue(yBlockCheck(VerticalAnchor.absolute(60), 0),
                        ifTrue(not(yBlockCheck(VerticalAnchor.absolute(63), 0)), ifTrue(noiseCondition(Noises.SWAMP, 0.0D), WATER))))),

                sequence(ifTrue(isBiome(ModBiomes.BEACH), ifTrue(yStartCheck(VerticalAnchor.absolute(64),0),
                        ifTrue(stoneDepthCheck(0, true, 1, CaveSurface.FLOOR), SAND)))),

                sequence(ifTrue(isBiome(ModBiomes.FROZEN_BEACH), ifTrue(yStartCheck(VerticalAnchor.absolute(64),0),
                        ifTrue(stoneDepthCheck(0, true, 1, CaveSurface.FLOOR), SNOW)))),

                sequence(ifTrue(isBiome(ModBiomes.GRAVEL_BEACH), ifTrue(yStartCheck(VerticalAnchor.absolute(64),0),
                        ifTrue(stoneDepthCheck(0, true, 1, CaveSurface.FLOOR), GRAVEL)))),

                sequence(ifTrue(isBiome(ModBiomes.BASALT_BEACH), ifTrue(yStartCheck(VerticalAnchor.absolute(64),0),
                        ifTrue(stoneDepthCheck(0, true, 1, CaveSurface.FLOOR), SMOOTH_BASALT)))),

                //sequence(ifTrue(isBiome(ModBiomes.ABYSSAL_OCEAN), ifTrue(verticalGradient("abyssal_depth", VerticalAnchor.absolute(-50), VerticalAnchor.absolute(63)), WATER))),
                ifTrue(abovePreliminarySurface(), NON_OCEAN_FLOOR),
                ifTrue(abovePreliminarySurface(), OCEAN_FLOOR),

//                ifTrue(isBiome(ModBiomes.ROCKY_BEACH), sequence(ifTrue(noiseCondition(Noises.CALCITE, -0.1D, 0.1D), sequence(ifTrue(ON_CEILING, STONE), COBBLESTONE)), STONE)),
//                ifTrue(isBiome(ModBiomes.ROCKY_BEACH), sequence(ifTrue(noiseCondition(Noises.CALCITE, -0.1D, 0.1D), sequence(ifTrue(ON_CEILING, STONE), ANDESITE)), STONE)),

                // Bedrock and Deepslate Gradient
                ifTrue(verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
                ifTrue(verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), DEEPSLATE)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}