package net.damqn4etobg.endlessexpansion.recipe;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, EndlessExpansion.MODID);

    public static final RegistryObject<RecipeSerializer<InfuserRecipe>> INFUSER_SERIALIZER =
            SERIALIZERS.register("infusing", () -> InfuserRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<MysticalCookieRecipe>> MYSTICAL_COOKIE_GENERATING_SERIALIZER =
            SERIALIZERS.register("mystical_cookie_generating", () -> MysticalCookieRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
