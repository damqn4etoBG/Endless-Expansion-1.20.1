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
import net.damqn4etobg.endlessexpansion.recipe.InfuserRecipe;
import net.damqn4etobg.endlessexpansion.screen.renderer.FluidTankRenderer;
import net.damqn4etobg.endlessexpansion.util.MouseUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Optional;

public class InfuserCategory implements IRecipeCategory<InfuserRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(EndlessExpansion.MODID, "infusing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(EndlessExpansion.MODID,
            "textures/gui/infuser_gui.png");
    public static final RecipeType<InfuserRecipe> INFUSING_TYPE =
            new RecipeType<>(UID, InfuserRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;
    private final FluidTankRenderer fluidTankRenderer;

    public InfuserCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 14, 13, 139, 59); // 176 width 85 height default
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.INFUSER.get()));
        this.fluidTankRenderer = new FluidTankRenderer(256, false, 4, 54);
    }

    @Override
    public RecipeType<InfuserRecipe> getRecipeType() {
        return INFUSING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipetype.endlessexpansion.infuser");
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
    public void setRecipe(IRecipeLayoutBuilder builder, InfuserRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 12, 29).addIngredients(recipe.getIngredients().get(0)); // 26. 42
        builder.addSlot(RecipeIngredientRole.INPUT, 120, 29).addIngredients(recipe.getIngredients().get(1)); // 134, 42
        builder.addSlot(RecipeIngredientRole.OUTPUT, 66, 29).addItemStack(recipe.getResultItem(null)); // 80, 42
    }

    @Override
    public void draw(InfuserRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        FluidStack luminiteEssence = new FluidStack(ModFluids.SOURCE_LUMINITE_ESSENCE.get(), 256);
        int fluidX = 3; // org 17
        int fluidY = 3; // org 16
        fluidTankRenderer.render(guiGraphics, fluidX, fluidY, luminiteEssence);

        // Check if the mouse is hovering over the fluid area
        if (isMouseAboveArea(mouseX, mouseY, fluidX, fluidY, fluidTankRenderer.getWidth(), fluidTankRenderer.getHeight())) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, fluidTankRenderer.getTooltip(luminiteEssence, TooltipFlag.Default.NORMAL), Optional.empty(), (int) mouseX, (int) mouseY);
        }
    }

    private boolean isMouseAboveArea(double mouseX, double mouseY, int areaX, int areaY, int areaWidth, int areaHeight) {
        return mouseX >= areaX && mouseX < areaX + areaWidth && mouseY >= areaY && mouseY < areaY + areaHeight;
    }
}
