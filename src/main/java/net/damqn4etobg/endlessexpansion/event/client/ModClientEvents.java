package net.damqn4etobg.endlessexpansion.event.client;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.EndlessExpansionConfig;
import net.damqn4etobg.endlessexpansion.dimension.ModDimensions;
import net.damqn4etobg.endlessexpansion.effect.ModMobEffects;
import net.damqn4etobg.endlessexpansion.networking.ModMessages;
import net.damqn4etobg.endlessexpansion.networking.packet.FreezeC2SPacket;
import net.damqn4etobg.endlessexpansion.particle.ModParticles;
import net.damqn4etobg.endlessexpansion.screen.ModTitleScreen;
import net.damqn4etobg.endlessexpansion.sound.ModSoundOptions;
import net.damqn4etobg.endlessexpansion.sound.ModSounds;
import net.damqn4etobg.endlessexpansion.util.KeyBinding;
import net.damqn4etobg.endlessexpansion.worldgen.biome.ModBiomes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = EndlessExpansion.MODID, value = Dist.CLIENT)
public class ModClientEvents {
    private static int dashTicksElapsed;
    private static boolean canDash;
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            Level world = player.level();

            boolean inWater = player.isInWater();
            boolean inWorldBeyond = world.dimension() == ModDimensions.WORLD_BEYOND_LEVEL_KEY;

            BlockPos playerPos = player.blockPosition();
            Holder<Biome> playerBiome = player.level().getBiome(playerPos);
            RandomSource random = RandomSource.create();

            boolean inSpecificBiome = playerBiome == ModBiomes.FROZEN_WASTES;
            if (inSpecificBiome && inWater && inWorldBeyond && event.player.getRandom().nextFloat() < 0.5f) {
                ModMessages.sendToServer(new FreezeC2SPacket());
            }

            if (inSpecificBiome && inWorldBeyond && event.player.getRandom().nextFloat() < 0.15f) {
                ModMessages.sendToServer(new FreezeC2SPacket());
            }

            if (!inSpecificBiome && inWorldBeyond && event.player.getRandom().nextFloat() < 0.005f) { // about 10 secs avg 10 sec = 0.005f
                ModMessages.sendToServer(new FreezeC2SPacket());
            }

            if(ClientFreezeData.getPlayerFreeze() >= 10) {
                player.addEffect(new MobEffectInstance(ModMobEffects.FREEZING.get(), 100, 1, false, false));
            }

            double x = player.getX() + random.nextDouble() - 0.5;
            double y = player.getY() + random.nextDouble() + 0.5;
            double z = player.getZ() + random.nextDouble() - 0.5;

            if (player.hasEffect(ModMobEffects.FREEZING.get())) {
                if(random.nextFloat() < 0.25f) { // 25% chance
                    Minecraft.getInstance().level.addParticle(ModParticles.SNOWFLAKE.get(), x, y, z, 0d, 0.025d, 0d);
                }
            }
            if (player.hasEffect(ModMobEffects.SHADOW_STATE.get())) {
                player.setInvisible(true); // doing this on the end of the tick ensures that we don't get that flash between the effect expiring and being renewed
                if(random.nextFloat() < 0.125f) {
                    Minecraft.getInstance().level.addParticle(ModParticles.SHADOW_ORB.get(), x, y, z, 0d, 0.025d, 0d);
                }
                if(random.nextFloat() < 0.125f) {
                    Minecraft.getInstance().level.addParticle(ModParticles.SHADOW_STRIP.get(), x, y, z, 0d, 0.025d, 0d);
                }
                if(dashTicksElapsed <= 60) {
                    dashTicksElapsed++;
                }
                if(!canDash && dashTicksElapsed >= 60) {
                    dashTicksElapsed = 0;
                    canDash = true;
                    if(!ModSoundOptions.OFF()) {
                        //player.level().playSound(player, player.blockPosition(), ModSounds.DASH_INDICATOR.get(), SoundSource.AMBIENT, 1f, 1f); // broken
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if(player != null && player.hasEffect(ModMobEffects.SHADOW_STATE.get()) && KeyBinding.DASHING_KEY.consumeClick() && canDash) {
            canDash = false;
            double speed = 1.5D;
            double lookAngle = player.getYRot();
            double dx = -Math.sin(Math.toRadians(lookAngle)) * speed;
            double dz = Math.cos(Math.toRadians(lookAngle)) * speed;
            RandomSource random = RandomSource.create();

            player.setDeltaMovement(dx, player.getDeltaMovement().y, dz);
            if(!ModSoundOptions.OFF()) {
                Minecraft.getInstance().level.playSound(player, player.blockPosition(), ModSounds.DASH.get(), SoundSource.AMBIENT, 1f, 1f);
            }
            for (int i = 0; i < 10; i++) {
                Minecraft.getInstance().level.addParticle(ModParticles.SHADOW_SMOKE.get(), player.getX(), player.getY(), player.getZ(),
                        random.nextGaussian() * 0.15, random.nextGaussian() * 0.1, random.nextGaussian() * 0.15);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(modid = EndlessExpansion.MODID, value = Dist.CLIENT)
    public static class ModOnlyInClientEvents {
        @SubscribeEvent
        public static void onGuiOpened(ScreenEvent.Init event) {
            if (event.getScreen() instanceof TitleScreen) {
                if (!(event.getScreen() instanceof ModTitleScreen) && EndlessExpansionConfig.loadConfig().isCustomMainMenu()) {
                    Minecraft.getInstance().setScreen(new ModTitleScreen(false));
                    EndlessExpansion.LOGGER.info("Setting Mod Title Screen");
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = EndlessExpansion.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModClientBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("freeze", FreezingHudOverlay.HUD_FREEZE);
            EndlessExpansion.LOGGER.info("Registering Freeze Overlay");
        }
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.DASHING_KEY);
        }
    }
}
