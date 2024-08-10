package net.damqn4etobg.endlessexpansion.compatability;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.damqn4etobg.endlessexpansion.recipe.InfuserRecipe;
import net.damqn4etobg.endlessexpansion.recipe.MysticalCookieRecipe;
import net.damqn4etobg.endlessexpansion.screen.InfuserMenu;
import net.damqn4etobg.endlessexpansion.screen.InfuserScreen;
import net.damqn4etobg.endlessexpansion.screen.ModMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIEndExpPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(EndlessExpansion.MODID, "jei_plugin");
    }
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new InfuserCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MysticalCookieCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<InfuserRecipe> infusingRecipes = recipeManager.getAllRecipesFor(InfuserRecipe.Type.INSTANCE);
        registration.addRecipes(InfuserCategory.INFUSING_TYPE, infusingRecipes);
        List<MysticalCookieRecipe> cookieRecipes = recipeManager.getAllRecipesFor(MysticalCookieRecipe.Type.INSTANCE);
        registration.addRecipes(MysticalCookieCategory.MYSTICAL_COOKIE_GENERATING_TYPE, cookieRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(InfuserScreen.class, 51, 47, 22, 7,
                InfuserCategory.INFUSING_TYPE);
        registration.addRecipeClickArea(InfuserScreen.class, 105, 47, 22, 7,
                InfuserCategory.INFUSING_TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(InfuserMenu.class, ModMenuTypes.INFUSER_MENU.get(), InfuserCategory.INFUSING_TYPE,
                37, 2, 0, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.INFUSER.get()), InfuserCategory.INFUSING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.MYSTICAL_COOKIE_JAR.get()), MysticalCookieCategory.MYSTICAL_COOKIE_GENERATING_TYPE);
    }
}
