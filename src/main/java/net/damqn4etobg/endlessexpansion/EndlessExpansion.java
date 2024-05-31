package net.damqn4etobg.endlessexpansion;

import com.mojang.logging.LogUtils;
import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.damqn4etobg.endlessexpansion.block.entity.ModBlockEntities;
import net.damqn4etobg.endlessexpansion.effect.ModMobEffects;
import net.damqn4etobg.endlessexpansion.entity.ModEntities;
import net.damqn4etobg.endlessexpansion.entity.client.ArborBoatRenderer;
import net.damqn4etobg.endlessexpansion.entity.client.WraithRenderer;
import net.damqn4etobg.endlessexpansion.fluid.ModFluidTypes;
import net.damqn4etobg.endlessexpansion.fluid.ModFluids;
import net.damqn4etobg.endlessexpansion.item.ModCreativeModeTabs;
import net.damqn4etobg.endlessexpansion.item.ModItems;
import net.damqn4etobg.endlessexpansion.item.alchemy.ModPotions;
import net.damqn4etobg.endlessexpansion.networking.ModMessages;
import net.damqn4etobg.endlessexpansion.recipe.ModRecipes;
import net.damqn4etobg.endlessexpansion.screen.InfuserScreen;
import net.damqn4etobg.endlessexpansion.screen.ModMenuTypes;
import net.damqn4etobg.endlessexpansion.screen.RadioactiveGeneratorScreen;
import net.damqn4etobg.endlessexpansion.sound.ModSounds;
import net.damqn4etobg.endlessexpansion.util.BetterBrewingRecipe;
import net.damqn4etobg.endlessexpansion.util.ModWoodTypes;
import net.damqn4etobg.endlessexpansion.worldgen.feature.ModTreeFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EndlessExpansion.MODID)
public class EndlessExpansion {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "endlessexpansion";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final double Version = 1.0;
    public EndlessExpansion() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModPotions.register(modEventBus);
        ModMobEffects.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);

        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);

        ModTreeFeatures.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.MYSTICAL_EVERBLUE_OCRHID.getId(), ModBlocks.POTTED_MYSTICAL_EVERBLUE_OCRHID);

            BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD,
                    ModItems.MYSTICAL_EVERBLUE_POWDER.get(), ModPotions.MYSTICAL_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD,
                    ModItems.LUMINITE.get(), ModPotions.GLOWING_POTION.get()));
        });
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_NUCLEAR_WASTE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_NUCLEAR_WASTE.get(), RenderType.translucent());

            EntityRenderers.register(ModEntities.WRAITH.get(), WraithRenderer::new);

            EntityRenderers.register(ModEntities.ARBOR_BOAT.get(), pContext -> new ArborBoatRenderer(pContext, false));
            EntityRenderers.register(ModEntities.ARBOR_CHEST_BOAT.get(), pContext -> new ArborBoatRenderer(pContext, true));
            Sheets.addWoodType(ModWoodTypes.ARBOR);

            MenuScreens.register(ModMenuTypes.RADIOACTIVE_GENERATOR_MENU.get(), RadioactiveGeneratorScreen::new);
            MenuScreens.register(ModMenuTypes.INFUSER_MENU.get(), InfuserScreen::new);

        }
    }
}
