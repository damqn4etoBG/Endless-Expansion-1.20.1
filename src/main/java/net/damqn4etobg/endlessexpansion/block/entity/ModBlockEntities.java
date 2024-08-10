package net.damqn4etobg.endlessexpansion.block.entity;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EndlessExpansion.MODID);

    public static final RegistryObject<BlockEntityType<RadioactiveGeneratorBlockEntity>> RADIOACTIVE_GENERATOR =
            BLOCK_ENTITIES.register("radioactive_generator",
                    () -> BlockEntityType.Builder.of(RadioactiveGeneratorBlockEntity::new, ModBlocks.RADIOACTIVE_GENERATOR.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<InfuserBlockEntity>> INFUSER =
            BLOCK_ENTITIES.register("infuser",
                    () -> BlockEntityType.Builder.of(InfuserBlockEntity::new, ModBlocks.INFUSER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<ArborSignBlockEntity>> ARBOR_SIGN =
            BLOCK_ENTITIES.register("arbor_sign", () ->
                    BlockEntityType.Builder.of(ArborSignBlockEntity::new,
                            ModBlocks.ARBOR_SIGN.get(), ModBlocks.ARBOR_WALL_SIGN.get()).build(null));

    public static final RegistryObject<BlockEntityType<ArborHangingSignBlockEntity>> ARBOR_HANGING_SIGN =
            BLOCK_ENTITIES.register("arbor_hanging_sign", () ->
                    BlockEntityType.Builder.of(ArborHangingSignBlockEntity::new,
                            ModBlocks.ARBOR_HANGING_SIGN.get(), ModBlocks.ARBOR_WALL_HANGING_SIGN.get()).build(null));

    public static final RegistryObject<BlockEntityType<MysticalCookieJarBlockEntity>> MYSTICAL_COOKIE_JAR =
            BLOCK_ENTITIES.register("mystical_cookie_jar",
                    () -> BlockEntityType.Builder.of(MysticalCookieJarBlockEntity::new, ModBlocks.MYSTICAL_COOKIE_JAR.get())
                            .build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
