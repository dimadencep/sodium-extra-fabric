package me.flashyreese.mods.sodiumextra.mixin.particle;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import it.unimi.dsi.fastutil.ints.IntList;
import me.flashyreese.mods.sodiumextra.client.SodiumExtraClientMod;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireworksSparkParticle.FireworkParticle.class)
public class MixinFireworkParticle {
    @Unique
    private final Identifier fireworkIdentifier = Identifier.of("minecraft", "firework");

    @Inject(method = "addExplosionParticle", at = @At(value = "HEAD"), cancellable = true)
    public void addExplosionParticle(double x, double y, double z, double velocityX, double velocityY, double velocityZ, IntList colors, IntList targetColors, boolean trail, boolean flicker, CallbackInfo ci) {
        if (!SodiumExtraClientMod.options().particleSettings.otherMap.getOrDefault(this.fireworkIdentifier, true) || !SodiumExtraClientMod.options().particleSettings.particles) {
            ci.cancel();
        }
    }

    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;setColor(FFF)V"))
    public boolean tick(Particle instance, float red, float green, float blue) {
        return instance != null;
    }
}
