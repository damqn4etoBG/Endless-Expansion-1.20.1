package net.damqn4etobg.endlessexpansion.datagen;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.damqn4etobg.endlessexpansion.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EndlessExpansion.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Blocks.NEEDS_COBALT_TOOL)
                .add(ModBlocks.CELESTIAL_BLOCK.get())
                .add(ModBlocks.PYRONIUM_ORE.get());

        this.tag(ModTags.Blocks.NEEDS_CELESTIAL_TOOL)
                .add(ModBlocks.DEEPSLATE_BLACK_OPAL_ORE.get());

        this.tag(BlockTags.FENCES)
                .add(ModBlocks.ARBOR_FENCE.get());

        this.tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ARBOR_FENCE_GATE.get());

        this.tag(ModTags.Blocks.MINEABLE_WITH_PAXEL)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL);

        this.tag(ModTags.Blocks.ORES_COBALT)
                .add(ModBlocks.COBALT_ORE.get())
                .add(ModBlocks.DEEPSLATE_COBALT_ORE.get());

        this.tag(ModTags.Blocks.ORES_LUMINITE)
                .add(ModBlocks.DEEPSLATE_LUMINITE_ORE.get());

        this.tag(ModTags.Blocks.ORES_PYRONIUM)
                .add(ModBlocks.PYRONIUM_ORE.get());

        this.tag(ModTags.Blocks.ORES_BLACK_OPAL)
                .add(ModBlocks.DEEPSLATE_BLACK_OPAL_ORE.get());
    }
}
