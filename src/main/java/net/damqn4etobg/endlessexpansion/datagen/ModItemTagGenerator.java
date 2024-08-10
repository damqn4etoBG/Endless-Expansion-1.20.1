package net.damqn4etobg.endlessexpansion.datagen;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.damqn4etobg.endlessexpansion.item.ModItems;
import net.damqn4etobg.endlessexpansion.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, EndlessExpansion.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.COBALT_HELMET.get(),
                        ModItems.COBALT_CHESTPLATE.get(),
                        ModItems.COBALT_LEGGINGS.get(),
                        ModItems.COBALT_BOOTS.get(),
                        ModItems.CELESTIAL_HELMET.get(),
                        ModItems.CELESTIAL_CHESTPLATE.get(),
                        ModItems.CELESTIAL_LEGGINGS.get(),
                        ModItems.CELESTIAL_BOOTS.get(),
                        ModItems.SHADOWSTEEL_HELMET.get(),
                        ModItems.SHADOWSTEEL_CHESTPLATE.get(),
                        ModItems.SHADOWSTEEL_LEGGINGS.get(),
                        ModItems.SHADOWSTEEL_BOOTS.get());

        this.tag(ItemTags.SWORDS)
                .add(ModItems.COBALT_SWORD.get(),
                ModItems.CELESTIAL_SWORD.get(),
                ModItems.SHADOWSTEEL_SWORD.get());

        this.tag(ItemTags.PICKAXES)
                .add(ModItems.COBALT_PICKAXE.get(),
                        ModItems.CELESTIAL_PICKAXE.get(),
                        ModItems.SHADOWSTEEL_PICKAXE.get());

        this.tag(ItemTags.AXES)
                .add(ModItems.COBALT_AXE.get(),
                        ModItems.CELESTIAL_AXE.get(),
                        ModItems.SHADOWSTEEL_AXE.get());

        this.tag(ItemTags.SHOVELS)
                .add(ModItems.COBALT_SHOVEL.get(),
                        ModItems.CELESTIAL_SHOVEL.get(),
                        ModItems.SHADOWSTEEL_SHOVEL.get());

        this.tag(ItemTags.HOES)
                .add(ModItems.COBALT_HOE.get(),
                        ModItems.CELESTIAL_HOE.get(),
                        ModItems.SHADOWSTEEL_HOE.get());

        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.ARBOR_PLANKS.get().asItem());

        this.tag(ItemTags.BOATS)
                .add(ModItems.ARBOR_BOAT.get());

        this.tag(ItemTags.CHEST_BOATS)
                .add(ModItems.ARBOR_CHEST_BOAT.get());

        this.tag(ItemTags.SIGNS)
                .add(ModBlocks.ARBOR_SIGN.get().asItem());

        this.tag(ItemTags.HANGING_SIGNS)
                .add(ModBlocks.ARBOR_HANGING_SIGN.get().asItem());

        this.tag(ItemTags.STAIRS)
                .add(ModBlocks.ARBOR_STAIRS.get().asItem());

        this.tag(ItemTags.SLABS)
                .add(ModBlocks.ARBOR_SLAB.get().asItem());

        this.tag(ItemTags.FENCES)
                .add(ModBlocks.ARBOR_FENCE.get().asItem());

        this.tag(ItemTags.FENCE_GATES)
                .add(ModBlocks.ARBOR_FENCE_GATE.get().asItem());

        this.tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ARBOR_PRESSURE_PLATE.get().asItem());

        this.tag(ItemTags.BUTTONS)
                .add(ModBlocks.ARBOR_BUTTON.get().asItem());

        this.tag(ItemTags.DOORS)
                .add(ModBlocks.ARBOR_DOOR.get().asItem());

        this.tag(ItemTags.TRAPDOORS)
                .add(ModBlocks.ARBOR_TRAPDOOR.get().asItem());

        this.tag(ItemTags.DIRT)
                .add(ModBlocks.TITANUM_SOIL.get().asItem())
                .add(ModBlocks.TITANUM_GRASS_BLOCK.get().asItem());

        this.tag(ItemTags.FLOWERS)
                .add(ModBlocks.MYSTICAL_EVERBLUE_OCRHID.get().asItem());

        this.tag(ItemTags.LEAVES)
                .add(ModBlocks.ARBOR_LEAVES.get().asItem());

        this.tag(ItemTags.LOGS)
                .add(ModBlocks.ARBOR_LOG.get().asItem())
                .add(ModBlocks.ARBOR_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_ARBOR_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_ARBOR_WOOD.get().asItem());

        this.tag(ModTags.Items.ARBOR_LOGS)
                .add(ModBlocks.ARBOR_LOG.get().asItem())
                .add(ModBlocks.ARBOR_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_ARBOR_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_ARBOR_WOOD.get().asItem());

        this.tag(ModTags.Items.COBALT_ORES)
                .add(ModBlocks.COBALT_ORE.get().asItem())
                .add(ModBlocks.DEEPSLATE_COBALT_ORE.get().asItem());

        this.tag(ModTags.Items.PYRONIUM_ORES)
                .add(ModBlocks.PYRONIUM_ORE.get().asItem())
                .add(ModBlocks.BLACKSTONE_PYRONIUM_ORE.get().asItem());

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.ARBOR_LOG.get().asItem())
                .add(ModBlocks.ARBOR_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_ARBOR_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_ARBOR_WOOD.get().asItem());
    }
}
