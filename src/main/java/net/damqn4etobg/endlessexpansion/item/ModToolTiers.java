package net.damqn4etobg.endlessexpansion.item;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.tag.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier COBALT = TierSortingRegistry.registerTier(
            new ForgeTier(4, 1700, 8f, 3f, 20,
                ModTags.Blocks.NEEDS_COBALT_TOOL, () -> Ingredient.of(ModItems.COBALT_INGOT.get())),
            new ResourceLocation(EndlessExpansion.MODID, "cobalt"), List.of(Tiers.DIAMOND), List.of());

    public static final Tier CELESTIAL = TierSortingRegistry.registerTier(
            new ForgeTier(5, 3000, 9f, 6f, 25,
                    ModTags.Blocks.NEEDS_CELESTIAL_TOOL, () -> Ingredient.of(ModItems.CELESTIAL_INGOT.get())),
            new ResourceLocation(EndlessExpansion.MODID, "celestial"), List.of(ModToolTiers.COBALT), List.of());

    public static final Tier SHADOWSTEEL = TierSortingRegistry.registerTier(
            new ForgeTier(5, 4000, 12f, 6f, 25,
                    ModTags.Blocks.NEEDS_SHADOWSTEEL_TOOL, () -> Ingredient.of(ModItems.SHADOWSTEEL_INGOT.get())),
            new ResourceLocation(EndlessExpansion.MODID, "shadowsteel"), List.of(ModToolTiers.CELESTIAL), List.of());
}
