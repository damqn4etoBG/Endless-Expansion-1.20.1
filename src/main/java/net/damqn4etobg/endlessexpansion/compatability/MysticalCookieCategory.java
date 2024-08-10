package net.damqn4etobg.endlessexpansion.compatability;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.damqn4etobg.endlessexpansion.fluid.ModFluids;
import net.damqn4etobg.endlessexpansion.recipe.MysticalCookieRecipe;
import net.damqn4etobg.endlessexpansion.screen.renderer.FluidTankRenderer;
import net.damqn4etobg.endlessexpansion.screen.renderer.SimpleValueInfoArea;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;

public class MysticalCookieCategory implements IRecipeCategory<MysticalCookieRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(EndlessExpansion.MODID, "mystical_cookie_generating");
    public static final ResourceLocation TEXTURE = new ResourceLocation(EndlessExpansion.MODID,
            "textures/gui/mystical_cookie_jar_gui.png");
    public static final RecipeType<MysticalCookieRecipe> MYSTICAL_COOKIE_GENERATING_TYPE =
            new RecipeType<>(UID, MysticalCookieRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;
    private SimpleValueInfoArea progressInfoArea;

    public MysticalCookieCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85); // 176 width 85 height default
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MYSTICAL_COOKIE_JAR.get()));
    }

    @Override
    public RecipeType<MysticalCookieRecipe> getRecipeType() {
        return MYSTICAL_COOKIE_GENERATING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipetype.endlessexpansion.mystical_cookie_generating");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MysticalCookieRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 42).addItemStack(recipe.getResultItem(null)); // 80, 42
    }

    @Override
    public void draw(MysticalCookieRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Component TIME = Component.literal("1min 30s");
        int timeWidth = Minecraft.getInstance().font.width(TIME);
        guiGraphics.drawString(Minecraft.getInstance().font, TIME, getWidth() - timeWidth - 8, getHeight() - 10, 4210752, false);
    }
}
