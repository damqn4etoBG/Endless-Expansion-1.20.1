package net.damqn4etobg.endlessexpansion.datagen;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.tag.ModTags;
import net.damqn4etobg.endlessexpansion.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBiomeTagGenerator extends BiomeTagsProvider {
    List<ResourceKey<Biome>> worldBeyondBiomes = List.of(ModBiomes.TITANIC_FOREST, ModBiomes.FROZEN_WASTES,
            ModBiomes.SUNKEN_WASTES, ModBiomes.SCORCHED_WASTES, ModBiomes.VOLCANIC_WASTES, ModBiomes.ZERZURA, ModBiomes.ABYSSAL_OCEAN);

    public ModBiomeTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, EndlessExpansion.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Biomes.IS_WORLD_BEYOND)
                .add(worldBeyondBiomes.toArray(new ResourceKey[0]));
        this.tag(ModTags.Biomes.IS_TITANIC_FOREST)
                .add(ModBiomes.TITANIC_FOREST);
        this.tag(ModTags.Biomes.IS_FROZEN_WASTES)
                .add(ModBiomes.FROZEN_WASTES);
        this.tag(ModTags.Biomes.IS_BEACH)
                .add(ModBiomes.BEACH)
                .add(ModBiomes.BASALT_BEACH)
                .add(ModBiomes.FROZEN_BEACH)
                .add(ModBiomes.GRAVEL_BEACH)
                .add(ModBiomes.ROCKY_BEACH);
    }

    @Override
    public String getName() {
        return "Biome Tags";
    }
}
