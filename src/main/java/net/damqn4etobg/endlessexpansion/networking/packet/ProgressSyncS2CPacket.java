package net.damqn4etobg.endlessexpansion.networking.packet;

import net.damqn4etobg.endlessexpansion.block.entity.MysticalCookieJarBlockEntity;
import net.damqn4etobg.endlessexpansion.screen.MysticalCookieJarMenu;
import net.damqn4etobg.endlessexpansion.screen.MysticalCookieJarScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ProgressSyncS2CPacket {
    private final int progress;
    private final BlockPos pos;

    public ProgressSyncS2CPacket(int progress, BlockPos pos) {
        this.progress = progress;
        this.pos = pos;
    }

    public ProgressSyncS2CPacket(FriendlyByteBuf buf) {
        this.progress = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(progress);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof MysticalCookieJarBlockEntity blockEntity) {
                blockEntity.setProgress(progress);

                if(Minecraft.getInstance().player.containerMenu instanceof MysticalCookieJarMenu menu && menu.getBlockEntity().getBlockPos().equals(pos)) {
                    blockEntity.setProgress(progress);
                    if(Minecraft.getInstance().screen instanceof MysticalCookieJarScreen screen) {
                        screen.updateProgress(progress, menu.getBlockEntity().getMaxProgress());
                    }
                }
            }
        });
        return true;
    }
}
