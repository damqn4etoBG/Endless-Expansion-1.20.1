package net.damqn4etobg.endlessexpansion.datagen;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.item.ModItems;
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
    }
}
