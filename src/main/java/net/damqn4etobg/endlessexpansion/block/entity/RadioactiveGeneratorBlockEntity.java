package net.damqn4etobg.endlessexpansion.block.entity;

import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.damqn4etobg.endlessexpansion.capability.temperature.ITemperature;
import net.damqn4etobg.endlessexpansion.capability.temperature.ModTemperature;
import net.damqn4etobg.endlessexpansion.fluid.ModFluids;
import net.damqn4etobg.endlessexpansion.item.ModItems;
import net.damqn4etobg.endlessexpansion.networking.ModMessages;
import net.damqn4etobg.endlessexpansion.networking.packet.EnergySyncS2CPacket;
import net.damqn4etobg.endlessexpansion.networking.packet.FluidSyncS2CPacket;
import net.damqn4etobg.endlessexpansion.networking.packet.FluidWasteSyncS2CPacket;
import net.damqn4etobg.endlessexpansion.networking.packet.TemperatureSyncS2CPacket;
import net.damqn4etobg.endlessexpansion.recipe.RadioactiveGeneratorRecipe;
import net.damqn4etobg.endlessexpansion.screen.RadioactiveGeneratorMenu;
import net.damqn4etobg.endlessexpansion.util.ModCapabilities;
import net.damqn4etobg.endlessexpansion.util.ModEnergyStorage;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RadioactiveGeneratorBlockEntity extends BlockEntity implements MenuProvider, BlockEntityTicker<RadioactiveGeneratorBlockEntity> {
    public static final RegistryAccess access = RegistryAccess.EMPTY;
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            //Checks to see if you can put an item in a slot
            List<Item> validIngotsAndBlocks = Arrays.asList(ModItems.URANIUM_INGOT.get(),
                    ModBlocks.URANIUM_BLOCK.get().asItem());

            return switch (slot) {
                case 0 -> validIngotsAndBlocks.contains(stack.getItem());
                case 1 -> stack.getItem() == Items.WATER_BUCKET || stack.getItem() == Items.BUCKET;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(100000000, 100000000) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    private final ModTemperature TEMPERATURE = new ModTemperature(1500, 1000) {
        @Override
        public void onTemperatureChanged() {
            setChanged();
            ModMessages.sendToClients(new TemperatureSyncS2CPacket(this.getTemperature(), getBlockPos()));

            if (getTemperature() >= 1500) {
                explode();
            }
        }
    };


    public static final int ENERGY_REQ = 1;

    private final FluidTank FLUID_TANK = new FluidTank(256000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.WATER;
        }
    };

    private final FluidTank FLUID_TANK_WASTE = new FluidTank(256000) {
        @Override
        protected void onContentsChanged() {
            setChanged();

            if (getFluidAmount() >= getCapacity()) {
                explode();
            }

            if(!level.isClientSide()) {
                ModMessages.sendToClients(new FluidWasteSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stackWaste) {
            return stackWaste.getFluid() == ModFluids.SOURCE_NUCLEAR_WASTE.get();
        }
    };

    private void explode() {
        // Destroy the block
        level.removeBlock(worldPosition, false);
        // Create an explosion at the block's position
        level.explode(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 10, Level.ExplosionInteraction.TNT);
    }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public void setFluidWaste(FluidStack stackWaste) {
        this.FLUID_TANK_WASTE.setFluid(stackWaste);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    public FluidStack getFluidStackWaste() {
        return this.FLUID_TANK_WASTE.getFluid();
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidWasteHandler = LazyOptional.empty();
    private LazyOptional<ITemperature> lazyTemperatureHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    public RadioactiveGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RADIOACTIVE_GENERATOR.get(), pos, state);
        this.data = new ContainerData() {

            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> RadioactiveGeneratorBlockEntity.this.progress;
                    case 1 -> RadioactiveGeneratorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> RadioactiveGeneratorBlockEntity.this.progress = value;
                    case 1 -> RadioactiveGeneratorBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.radioactive_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new RadioactiveGeneratorMenu(id, inventory, this, this.data);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public ITemperature getTemperature() {
        return TEMPERATURE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    public void setTemperatureLevel(int temperature) {
        this.TEMPERATURE.setTemperature(temperature);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        FaceInfo faceInfo = FaceInfo.UP;

        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            if (side == Direction.WEST) {
                return lazyFluidHandler.cast();
            }
        }

        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            if(side == Direction.EAST) {
                return lazyFluidWasteHandler.cast();
            }
        }

        if(cap == ModCapabilities.TEMPERATURE) {
            return lazyTemperatureHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
        lazyFluidWasteHandler = LazyOptional.of(() -> FLUID_TANK_WASTE);
        lazyTemperatureHandler = LazyOptional.of(() -> TEMPERATURE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
        lazyFluidWasteHandler.invalidate();
        lazyTemperatureHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("radioactive_generator.progress", this.progress);
        nbt.putInt("radioactive_generator.energy", ENERGY_STORAGE.getEnergyStored());
        nbt.putInt("radioactive_generator.temp", TEMPERATURE.getTemperature());
        nbt = FLUID_TANK.writeToNBT(nbt);
        nbt = FLUID_TANK_WASTE.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("radioactive_generator.progress");
        ENERGY_STORAGE.setEnergy(nbt.getInt("radioactive_generator.energy"));
        FLUID_TANK.readFromNBT(nbt);
        FLUID_TANK_WASTE.readFromNBT(nbt);
        TEMPERATURE.setTemperature(nbt.getInt("radioactive_generator.temp"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, RadioactiveGeneratorBlockEntity pEntity) {
        transferItemFluidToFluidTank(pEntity);

        if(hasRecipe(pEntity) && hasEnoughEnergy(pEntity) && hasEnoughFluid(pEntity)) {
            pEntity.progress++;
            extractEnergy(pEntity);
            setChanged(level, pos, state);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            } else {
                pEntity.resetProgress();
                setChanged(level, pos, state);
            }
        }

        if (pEntity.TEMPERATURE.getTemperature() <= 0 && pEntity.FLUID_TANK.getFluidAmount() <= 0) {
            while (pEntity.TEMPERATURE.getTemperature() <= 28) {
                pEntity.TEMPERATURE.receiveTemperature(pEntity.TEMPERATURE.getTemperature() + 1, false);
            }
        }

    }

    private static boolean hasEnoughFluid(RadioactiveGeneratorBlockEntity pEntity) {
        return pEntity.FLUID_TANK.getFluidAmount() >= 500;
    }

    private static void transferItemFluidToFluidTank(RadioactiveGeneratorBlockEntity pEntity) {
        pEntity.itemHandler.getStackInSlot(1).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int drainAmount = Math.min(pEntity.FLUID_TANK.getSpace(), 1000);

            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if(pEntity.FLUID_TANK.isFluidValid(stack)) {
                stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithFluid(pEntity, stack, handler.getContainer());
            }
        });
    }

    private static void fillTankWithFluid(RadioactiveGeneratorBlockEntity pEntity, FluidStack stack, ItemStack container) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);
        pEntity.FLUID_TANK_WASTE.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        pEntity.itemHandler.extractItem(1, 1 , false);
        pEntity.itemHandler.insertItem(1, container, false);
    }

    private static void extractEnergy(RadioactiveGeneratorBlockEntity pEntity) {
        pEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasEnoughEnergy(RadioactiveGeneratorBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ * pEntity.maxProgress;
    }

    private static boolean hasUraniumInSlot(RadioactiveGeneratorBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.URANIUM_INGOT.get();
    }

    private static boolean hasUraniumBlockInSlot(RadioactiveGeneratorBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getItem() == ModBlocks.URANIUM_BLOCK.get().asItem();
    }

    public boolean isWestSide(BlockPos blockPos) {
        return blockPos.getX() < blockPos.getX();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(RadioactiveGeneratorBlockEntity pEntity) {
        if(hasRecipe(pEntity)) {

        }
    }

    public static boolean hasRecipe(RadioactiveGeneratorBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<RadioactiveGeneratorRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(RadioactiveGeneratorRecipe.Type.INSTANCE, inventory, level);

        if (entity.ENERGY_STORAGE.getEnergyStored() < entity.getEnergyStorage().getMaxEnergyStored()) {
            if (hasUraniumInSlot(entity)) {
                FluidStack wasteFluid = new FluidStack(ModFluids.SOURCE_NUCLEAR_WASTE.get(), 128);
                try {
                    Thread.sleep(50); // delay for 1 second (1000 milliseconds)
                } catch (InterruptedException e) {
                    // handle exception
                }
                entity.ENERGY_STORAGE.receiveEnergy(16384, false);
                entity.FLUID_TANK.drain(256, IFluidHandler.FluidAction.EXECUTE);
                entity.FLUID_TANK_WASTE.fill(wasteFluid, IFluidHandler.FluidAction.EXECUTE);
                entity.itemHandler.extractItem(0, 1, false);
                entity.TEMPERATURE.receiveTemperature(50, false);
            }

            if (hasUraniumBlockInSlot(entity)) {
                FluidStack wasteFluid = new FluidStack(ModFluids.SOURCE_NUCLEAR_WASTE.get(), 512);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {

                }
                entity.ENERGY_STORAGE.receiveEnergy(147456, false);
                entity.FLUID_TANK.drain(2304, IFluidHandler.FluidAction.EXECUTE);
                entity.FLUID_TANK_WASTE.fill(wasteFluid, IFluidHandler.FluidAction.EXECUTE);
                entity.itemHandler.extractItem(0, 1, false);
                entity.TEMPERATURE.receiveTemperature(100, false);
            }
        }

        if (entity.TEMPERATURE.getTemperature() >= 0 && entity.FLUID_TANK.getFluidAmount() >= 50) {
            entity.TEMPERATURE.extractTemperature(10, false);
            entity.FLUID_TANK.drain(50, IFluidHandler.FluidAction.EXECUTE);
        }

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem(access));
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(1).getItem() == stack.getItem() || inventory.getItem(1).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(1).getMaxStackSize() > inventory.getItem(1).getCount();
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

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }
}
