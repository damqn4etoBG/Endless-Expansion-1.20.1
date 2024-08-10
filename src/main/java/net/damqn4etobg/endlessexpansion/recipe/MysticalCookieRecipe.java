package net.damqn4etobg.endlessexpansion.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class MysticalCookieRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;

    public MysticalCookieRecipe(ResourceLocation id, ItemStack output) {
        this.id = id;
        this.output = output;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return !pLevel.isClientSide();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.create(); // No ingredients
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess access) {
        return output.copy();
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

    public static class Type implements RecipeType<MysticalCookieRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "mystical_cookie_generating";
    }

    public static class Serializer implements RecipeSerializer<MysticalCookieRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(EndlessExpansion.MODID, "mystical_cookie_generating");

        @Override
        public MysticalCookieRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            return new MysticalCookieRecipe(pRecipeId, output);
        }

        @Override
        public @Nullable MysticalCookieRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            ItemStack output = buf.readItem();
            return new MysticalCookieRecipe(id, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MysticalCookieRecipe recipe) {
            buf.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
        }
    }
}
