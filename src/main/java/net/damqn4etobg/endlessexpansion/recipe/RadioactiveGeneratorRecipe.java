package net.damqn4etobg.endlessexpansion.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.util.FluidJSONUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class RadioactiveGeneratorRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final FluidStack fluidStack;

    public RadioactiveGeneratorRecipe(ResourceLocation id, ItemStack output,
                                      NonNullList<Ingredient> recipeItems, FluidStack fluidStack) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.fluidStack = fluidStack;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        return recipeItems.get(0).test(pContainer.getItem(1));
    }

    public FluidStack getFluid() {
        return fluidStack;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess access) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<RadioactiveGeneratorRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "radioactive_generating";
    }


    public static class Serializer implements RecipeSerializer<RadioactiveGeneratorRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(EndlessExpansion.MODID, "radioactive_generating");

        @Override
        public RadioactiveGeneratorRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            FluidStack fluid = FluidJSONUtil.readFluid(pSerializedRecipe.get("fluid").getAsJsonObject());

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new RadioactiveGeneratorRecipe(pRecipeId, output, inputs, fluid);
        }

        @Override
        public @Nullable RadioactiveGeneratorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            FluidStack fluid = buf.readFluidStack();

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new RadioactiveGeneratorRecipe(id, output, inputs, fluid);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, RadioactiveGeneratorRecipe recipe) {
            RegistryAccess access = RegistryAccess.EMPTY;
            buf.writeInt(recipe.getIngredients().size());
            buf.writeFluidStack(recipe.fluidStack);

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(access), false);
        }
    }
}