package net.damqn4etobg.endlessexpansion.dimension;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;

public class ModLevelGen extends NoiseRouterData {
    protected static final NoiseSettings WORLD_BEYOND_NOISE_SETTINGS = NoiseSettings.create(-64, 448, 1, 3);

    public static final ResourceKey<NoiseGeneratorSettings> WORLD_BEYOND = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(EndlessExpansion.MODID, "world_beyond_noise"));

    public static NoiseGeneratorSettings worldBeyond(BootstapContext<?> pContext, boolean pAmplified, boolean pLarge) {
        return new NoiseGeneratorSettings(WORLD_BEYOND_NOISE_SETTINGS, Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(), NoiseRouterData.overworld(pContext.lookup(Registries.DENSITY_FUNCTION),
                pContext.lookup(Registries.NOISE), true, true), ModSurfaceRules.makeRules(), (new OverworldBiomeBuilder()).spawnTarget(),
                63, false, true, true, false);
    }

    public static void bootstrapNoiseGen(BootstapContext<NoiseGeneratorSettings> pContext) {
        pContext.register(WORLD_BEYOND, worldBeyond(pContext, false, true));
    }
}