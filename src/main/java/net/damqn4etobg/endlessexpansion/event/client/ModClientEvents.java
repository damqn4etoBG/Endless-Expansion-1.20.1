package net.damqn4etobg.endlessexpansion.event.client;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.EndlessExpansionConfig;
import net.damqn4etobg.endlessexpansion.capability.dash.PlayerDashProvider;
import net.damqn4etobg.endlessexpansion.dimension.ModDimensions;
import net.damqn4etobg.endlessexpansion.effect.ModMobEffects;
import net.damqn4etobg.endlessexpansion.networking.ModMessages;
import net.damqn4etobg.endlessexpansion.networking.packet.DashC2SPacket;
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
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = EndlessExpansion.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            Level world = player.level();
            RandomSource random = RandomSource.create();

            if (player.hasEffect(ModMobEffects.FREEZING.get())) {
                spawnFreezeParticles(player, world, random);
            }

            handleFreezingEffect(player, player.level());

            if (player.hasEffect(ModMobEffects.SHADOW_STATE.get())) {
                spawnShadowParticles(player, world, random);
                player.getCapability(PlayerDashProvider.PLAYER_DASH).ifPresent(dash -> {
                    dash.incrementDashTicks();

                    if (dash.getDashTicksElapsed() >= 39 && !dash.canDash()) {
                        if (!ModSoundOptions.OFF()) {
                            world.playSound(player, player.blockPosition(), ModSounds.DASH_INDICATOR.get(), SoundSource.AMBIENT, 1f, 1f);
                        }
                    }
                });
            }
        }
    }

    private static void spawnFreezeParticles(Player player, Level world, RandomSource random) {
        if (random.nextFloat() < 0.25f) {
            double x = player.getX() + random.nextDouble() - 0.5;
            double y = player.getY() + random.nextDouble() + 0.5;
            double z = player.getZ() + random.nextDouble() - 0.5;
            world.addParticle(ModParticles.SNOWFLAKE.get(), x, y, z, 0d, 0.025d, 0d);
        }
    }

    private static void spawnShadowParticles(Player player, Level world, RandomSource random) {
        double x = player.getX() + random.nextDouble() - 0.5;
        double y = player.getY() + random.nextDouble() + 0.5;
        double z = player.getZ() + random.nextDouble() - 0.5;

        if (random.nextFloat() < 0.125f) {
            world.addParticle(ModParticles.SHADOW_ORB.get(), x, y, z, 0d, 0.025d, 0d);
        }

        if (random.nextFloat() < 0.125f) {
            world.addParticle(ModParticles.SHADOW_STRIP.get(), x, y, z, 0d, 0.025d, 0d);
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player != null && player.hasEffect(ModMobEffects.SHADOW_STATE.get()) && KeyBinding.DASHING_KEY.consumeClick()) {
            player.getCapability(PlayerDashProvider.PLAYER_DASH).ifPresent(dash -> {
                if (dash.canDash()) {
                    dash.resetDashTicks();

                    double speed = 1.5D;
                    double lookAngle = player.getYRot();
                    double dx = -Math.sin(Math.toRadians(lookAngle)) * speed;
                    double dz = Math.cos(Math.toRadians(lookAngle)) * speed;

                    player.setDeltaMovement(dx, player.getDeltaMovement().y, dz);
                    spawnDashParticles(player, player.level());
                    ModMessages.sendToServer(new DashC2SPacket());
                }
            });
        }
    }

    private static void spawnDashParticles(Player player, Level world) {
        RandomSource random = RandomSource.create();
        for (int i = 0; i < 10; i++) {
            double x = player.getX() + random.nextGaussian() * 0.15;
            double y = player.getY() + random.nextGaussian() * 0.15;
            double z = player.getZ() + random.nextGaussian() * 0.15;
            world.addParticle(ModParticles.SHADOW_SMOKE.get(), x, y, z,
                    random.nextGaussian() * 0.05, random.nextGaussian() * 0.05, random.nextGaussian() * 0.05);
        }
    }

    private static void handleFreezingEffect(Player player, Level world) {
        boolean inWater = player.isInWater();
        boolean inWorldBeyond = world.dimension() == ModDimensions.WORLD_BEYOND_LEVEL_KEY;
        BlockPos playerPos = player.blockPosition();
        Holder<Biome> playerBiome = world.getBiome(playerPos);
        boolean inSpecificBiome = playerBiome == ModBiomes.FROZEN_WASTES;

        if (inSpecificBiome && inWater && inWorldBeyond && player.getRandom().nextFloat() < 0.5f) {
            ModMessages.sendToServer(new FreezeC2SPacket());
        }

        if (inSpecificBiome && inWorldBeyond && player.getRandom().nextFloat() < 0.15f) {
            ModMessages.sendToServer(new FreezeC2SPacket());
        }

        if (!inSpecificBiome && inWorldBeyond && player.getRandom().nextFloat() < 0.005f) { // about 10 secs avg 10 sec = 0.005f
            ModMessages.sendToServer(new FreezeC2SPacket());
        }

        if(world.isClientSide()) {
            if (ClientFreezeData.getPlayerFreeze() >= 10 && (!player.isCreative() && !player.isSpectator())) {
                player.addEffect(new MobEffectInstance(ModMobEffects.FREEZING.get(), 100, 0, false, false, true));
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
}