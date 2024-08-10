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
                .add(ModBlocks.PYRONIUM_ORE.get())
                .add(ModBlocks.BLACKSTONE_PYRONIUM_ORE.get());

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
                .add(ModBlocks.PYRONIUM_ORE.get())
                .add(ModBlocks.BLACKSTONE_PYRONIUM_ORE.get());

        this.tag(ModTags.Blocks.ORES_BLACK_OPAL)
                .add(ModBlocks.DEEPSLATE_BLACK_OPAL_ORE.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.URANIUM_BLOCK.get())
                .add(ModBlocks.RADIOACTIVE_GENERATOR.get())
                .add(ModBlocks.GLACIER_BRICKS.get())
                .add(ModBlocks.DEEPSLATE_LUMINITE_ORE.get())
                .add(ModBlocks.COBALT_ORE.get())
                .add(ModBlocks.DEEPSLATE_COBALT_ORE.get())
                .add(ModBlocks.COBALT_BLOCK.get())
                .add(ModBlocks.CELESTIAL_BLOCK.get())
                .add(ModBlocks.PYRONIUM_ORE.get())
                .add(ModBlocks.BLACKSTONE_PYRONIUM_ORE.get())
                .add(ModBlocks.INFUSER.get())
                .add(ModBlocks.DEEPSLATE_BLACK_OPAL_ORE.get())
                .add(ModBlocks.LUMINITE_BLOCK.get())
                .add(ModBlocks.SAPPHIRE_CLUSTER.get())
                .add(ModBlocks.MYSTICAL_COOKIE_JAR.get())
                .add(ModBlocks.MYSTICAL_GLASS.get())
                .add(ModBlocks.MYSTICAL_GLASS_PANE.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.TITANUM_SOIL.get())
                .add(ModBlocks.TITANUM_GRASS_BLOCK.get())
                .add(ModBlocks.PACKED_SNOW_BLOCK.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.ARBOR_LOG.get())
                .add(ModBlocks.STRIPPED_ARBOR_LOG.get())
                .add(ModBlocks.ARBOR_WOOD.get())
                .add(ModBlocks.STRIPPED_ARBOR_WOOD.get())
                .add(ModBlocks.ARBOR_PLANKS.get())
                .add(ModBlocks.ARBOR_STAIRS.get())
                .add(ModBlocks.ARBOR_SLAB.get())
                .add(ModBlocks.ARBOR_FENCE.get())
                .add(ModBlocks.ARBOR_FENCE_GATE.get())
                .add(ModBlocks.ARBOR_PRESSURE_PLATE.get())
                .add(ModBlocks.ARBOR_BUTTON.get())
                .add(ModBlocks.ARBOR_DOOR.get())
                .add(ModBlocks.ARBOR_TRAPDOOR.get())
                .add(ModBlocks.ARBOR_SIGN.get())
                .add(ModBlocks.ARBOR_WALL_SIGN.get())
                .add(ModBlocks.ARBOR_HANGING_SIGN.get())
                .add(ModBlocks.ARBOR_WALL_HANGING_SIGN.get());

        this.tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.ARBOR_LEAVES.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DEEPSLATE_LUMINITE_ORE.get())
                .add(ModBlocks.LUMINITE_BLOCK.get())
                .add(ModBlocks.SAPPHIRE_CLUSTER.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.DEEPSLATE_COBALT_ORE.get())
                .add(ModBlocks.COBALT_ORE.get())
                .add(ModBlocks.COBALT_BLOCK.get());

        this.tag(ModTags.Blocks.ORES)
                .add(ModBlocks.DEEPSLATE_LUMINITE_ORE.get())
                .add(ModBlocks.COBALT_ORE.get())
                .add(ModBlocks.DEEPSLATE_COBALT_ORE.get())
                .add(ModBlocks.DEEPSLATE_BLACK_OPAL_ORE.get())
                .add(ModBlocks.PYRONIUM_ORE.get())
                .add(ModBlocks.BLACKSTONE_PYRONIUM_ORE.get());

        this.tag(BlockTags.DIRT)
                .add(ModBlocks.TITANUM_GRASS_BLOCK.get())
                .add(ModBlocks.TITANUM_SOIL.get());

        this.tag(BlockTags.FLOWERS)
                .add(ModBlocks.MYSTICAL_EVERBLUE_OCRHID.get());

        this.tag(BlockTags.LEAVES)
                .add(ModBlocks.ARBOR_LEAVES.get());

        this.tag(BlockTags.LOGS)
                .add(ModBlocks.ARBOR_LOG.get())
                .add(ModBlocks.ARBOR_WOOD.get())
                .add(ModBlocks.STRIPPED_ARBOR_LOG.get())
                .add(ModBlocks.STRIPPED_ARBOR_WOOD.get());

        this.tag(BlockTags.SNOW)
                .add(ModBlocks.PACKED_SNOW_BLOCK.get());

        this.tag(ModTags.Blocks.ARBOR_LOGS)
                .add(ModBlocks.ARBOR_LOG.get())
                .add(ModBlocks.ARBOR_WOOD.get())
                .add(ModBlocks.STRIPPED_ARBOR_LOG.get())
                .add(ModBlocks.STRIPPED_ARBOR_WOOD.get());
    }
}
