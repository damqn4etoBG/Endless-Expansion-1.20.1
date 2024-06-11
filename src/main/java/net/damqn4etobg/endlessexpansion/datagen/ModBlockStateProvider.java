package net.damqn4etobg.endlessexpansion.datagen;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class    ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EndlessExpansion.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        logBlock(((RotatedPillarBlock) ModBlocks.ARBOR_LOG.get()));
//        axisBlock((RotatedPillarBlock) ModBlocks.ARBOR_WOOD.get(), blockTexture(ModBlocks.ARBOR_LOG.get()), blockTexture(ModBlocks.ARBOR_LOG.get()));
//        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_ARBOR_LOG.get(), new ResourceLocation(EndlessExpansion.MODID, "block/stripped_arbor_log"),
//                new ResourceLocation(EndlessExpansion.MODID, "block/stripped_arbor_log_top"));
//        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_ARBOR_WOOD.get(), new ResourceLocation(EndlessExpansion.MODID, "block/stripped_arbor_log"),
//                new ResourceLocation(EndlessExpansion.MODID, "block/stripped_arbor_log"));
//
//        blockWithItem(ModBlocks.ARBOR_PLANKS);
//        blockWithItem(ModBlocks.ARBOR_LEAVES);
//        saplingBlock(ModBlocks.ARBOR_SAPLING);
//
//        simpleBlockItem(ModBlocks.ARBOR_LOG.get(), models().withExistingParent("endlessexpansion:arbor_log", "minecraft:block/cube_column"));
//        simpleBlockItem(ModBlocks.ARBOR_WOOD.get(), models().withExistingParent("endlessexpansion:arbor_wood", "minecraft:block/cube_column"));
//        simpleBlockItem(ModBlocks.STRIPPED_ARBOR_LOG.get(), models().withExistingParent("endlessexpansion:stripped_arbor_log", "minecraft:block/cube_column"));
//        simpleBlockItem(ModBlocks.STRIPPED_ARBOR_WOOD.get(), models().withExistingParent("endlessexpansion:stripped_arbor_wood", "minecraft:block/cube_column"));
//
//        simpleBlockWithItem(ModBlocks.MYSTICAL_EVERBLUE_OCRHID.get(), models().cross(blockTexture(ModBlocks.MYSTICAL_EVERBLUE_OCRHID.get()).getPath(),
//                blockTexture(ModBlocks.MYSTICAL_EVERBLUE_OCRHID.get())).renderType("cutout"));
//        simpleBlockWithItem(ModBlocks.POTTED_MYSTICAL_EVERBLUE_OCRHID.get(), models().singleTexture("potted_mystical_everblue_orchid", new ResourceLocation("flower_pot_cross"), "plant",
//                blockTexture(ModBlocks.MYSTICAL_EVERBLUE_OCRHID.get())).renderType("cutout"));
//
//        blockWithItem(ModBlocks.GLACIER_BRICKS);
//        blockWithItem(ModBlocks.DEEPSLATE_LUMINITE_ORE);
//        blockWithItem(ModBlocks.DEEPSLATE_COBALT_ORE);
//        blockWithItem(ModBlocks.COBALT_ORE);
        blockWithItem(ModBlocks.COBALT_BLOCK);
        blockWithItem(ModBlocks.CELESTIAL_BLOCK);

        stairsBlock(((StairBlock) ModBlocks.ARBOR_STAIRS.get()), blockTexture(ModBlocks.ARBOR_PLANKS.get()));
        slabBlock(((SlabBlock) ModBlocks.ARBOR_SLAB.get()), blockTexture(ModBlocks.ARBOR_PLANKS.get()), blockTexture(ModBlocks.ARBOR_PLANKS.get()));

        buttonBlock(((ButtonBlock) ModBlocks.ARBOR_BUTTON.get()), blockTexture(ModBlocks.ARBOR_PLANKS.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.ARBOR_PRESSURE_PLATE.get()), blockTexture(ModBlocks.ARBOR_PLANKS.get()));

        fenceBlock(((FenceBlock) ModBlocks.ARBOR_FENCE.get()), blockTexture(ModBlocks.ARBOR_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) ModBlocks.ARBOR_FENCE_GATE.get()), blockTexture(ModBlocks.ARBOR_PLANKS.get()));

        doorBlockWithRenderType(((DoorBlock) ModBlocks.ARBOR_DOOR.get()), modLoc("block/arbor_door_bottom"), modLoc("block/arbor_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.ARBOR_TRAPDOOR.get()), modLoc("block/arbor_trapdoor"), true, "cutout");

        blockWithItem(ModBlocks.PYRONIUM_ORE);
        blockWithItem(ModBlocks.BLACKSTONE_PYRONIUM_ORE);

        simpleBlock(ModBlocks.INFUSER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/infuser")));

        signBlock(((StandingSignBlock) ModBlocks.ARBOR_SIGN.get()), ((WallSignBlock) ModBlocks.ARBOR_WALL_SIGN.get()),
                blockTexture(ModBlocks.ARBOR_PLANKS.get()));

        hangingSignBlock(ModBlocks.ARBOR_HANGING_SIGN.get(), ModBlocks.ARBOR_WALL_HANGING_SIGN.get(), blockTexture(ModBlocks.ARBOR_PLANKS.get()));

        blockWithItem(ModBlocks.DEEPSLATE_BLACK_OPAL_ORE);
        blockWithItem(ModBlocks.LUMINITE_BLOCK);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }
    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
