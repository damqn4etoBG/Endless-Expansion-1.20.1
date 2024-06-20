package net.damqn4etobg.endlessexpansion.block.entity;

import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.damqn4etobg.endlessexpansion.fluid.ModFluids;
import net.damqn4etobg.endlessexpansion.item.ModItems;
import net.damqn4etobg.endlessexpansion.networking.ModMessages;
import net.damqn4etobg.endlessexpansion.networking.packet.FluidSyncS2CPacket;
import net.damqn4etobg.endlessexpansion.recipe.InfuserRecipe;
import net.damqn4etobg.endlessexpansion.screen.InfuserMenu;
import net.damqn4etobg.endlessexpansion.sound.ModSoundOptions;
import net.damqn4etobg.endlessexpansion.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class InfuserBlockEntity extends BlockEntity implements MenuProvider {
    private static final int INPUT_SLOT_1 = 1;
    private static final int INPUT_SLOT_2 = 3;
    private static final int OUTPUT_SLOT = 2;
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getItem() == ModItems.LUMINITE.get() || stack.getItem() == ModBlocks.LUMINITE_BLOCK.get().asItem();
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final FluidTank FLUID_TANK = new FluidTank(12000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == ModFluids.SOURCE_LUMINITE_ESSENCE.get();
        }
    };

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }
    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 39; //original 78
    public InfuserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INFUSER.get(), pos, state);
        this.data = new ContainerData() {

            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> InfuserBlockEntity.this.progress;
                    case 1 -> InfuserBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> InfuserBlockEntity.this.progress = value;
                    case 1 -> InfuserBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public ItemStack getRenderStackOutput() {
        return itemHandler.getStackInSlot(OUTPUT_SLOT);
    }

    public ItemStack getRenderStackInput1() {
        return itemHandler.getStackInSlot(INPUT_SLOT_1);
    }

    public ItemStack getRenderStackInput2() {
        return itemHandler.getStackInSlot(INPUT_SLOT_2);
    }

    public ItemStack getRenderStackLuminite() {
        return itemHandler.getStackInSlot(0);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.endlessexpansion.infuser");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new InfuserMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("infuser.progress", this.progress);
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("infuser.progress");
        FLUID_TANK.readFromNBT(nbt);
    }

    public void drops() {
        BlockEntity blockentity = level.getBlockEntity(getBlockPos());
        if (blockentity instanceof InfuserBlockEntity infuserBlockEntity) {
            ItemStack itemStack = new ItemStack(ModBlocks.INFUSER.get().asItem());
            infuserBlockEntity.saveToItem(itemStack);

            Containers.dropItemStack(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), itemStack);
        }
    }

    public void dropsItems() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private boolean soundPlayed = false;
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        RandomSource random = RandomSource.create();
        makeLuminiteEssence();
        if (hasRecipe() && FLUID_TANK.getFluidAmount() >= 256) {
            if (!soundPlayed && ModSoundOptions.ON()) {
                pLevel.playSound(null, pPos, ModSounds.INFUSER_INFUSING.get(), SoundSource.BLOCKS, 0.2f, 1f);
                soundPlayed = true;
            }
            double x = pPos.getX() + random.nextDouble() - 0.5;
            double y = pPos.getY() + random.nextDouble() + 1.5;
            double z = pPos.getZ() + random.nextDouble() - 0.5;
            for (int i = 0; i < 10; i++) {
                pLevel.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0.02D, 0);
            }
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);
            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
                FLUID_TANK.drain(256, IFluidHandler.FluidAction.EXECUTE);
                soundPlayed = false;
            }
        } else {
            soundPlayed = false;
            resetProgress();
        }
    }
    private void resetProgress() {
        progress = 0;
    }

    private void makeLuminiteEssence() {
        FluidStack luminiteEssenceStack = new FluidStack(ModFluids.SOURCE_LUMINITE_ESSENCE.get(), 128);
        FluidStack luminiteEssenceBlockStack = new FluidStack(ModFluids.SOURCE_LUMINITE_ESSENCE.get(), 1152);
        if(FLUID_TANK.getFluidAmount() < 12000 && hasLuminiteInSlot()) {
            this.FLUID_TANK.fill(luminiteEssenceStack,  IFluidHandler.FluidAction.EXECUTE);
            this.itemHandler.extractItem(0, 1, false);
        }
        if(FLUID_TANK.getFluidAmount() <= 10848 && hasLuminiteBlockInSlot()) {
            this.FLUID_TANK.fill(luminiteEssenceBlockStack,  IFluidHandler.FluidAction.EXECUTE);
            this.itemHandler.extractItem(0, 1, false);
        }
    }
    private static void fillTankWithFluid(InfuserBlockEntity pEntity, FluidStack stack) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);
        pEntity.itemHandler.extractItem(0, 1 , false);
    }

    private void craftItem() {
        Optional<InfuserRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        this.itemHandler.extractItem(INPUT_SLOT_1, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_2, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasRecipe() {
        Optional<InfuserRecipe> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<InfuserRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(InfuserRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;

    }

    private boolean hasLuminiteInSlot() {
        return this.itemHandler.getStackInSlot(0).getItem() == ModItems.LUMINITE.get();
    }

    private boolean hasLuminiteBlockInSlot() {
        return this.itemHandler.getStackInSlot(0).getItem() == ModBlocks.LUMINITE_BLOCK.get().asItem();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
