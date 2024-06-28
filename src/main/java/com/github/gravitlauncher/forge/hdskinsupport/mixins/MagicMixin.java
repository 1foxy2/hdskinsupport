package com.github.gravitlauncher.forge.hdskinsupport.mixins;

import com.github.gravitlauncher.forge.hdskinsupport.HdSkinSupportMod;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.HttpTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({HttpTexture.class})
public class MagicMixin {
    public MagicMixin() {
    }

    @Inject(method = "processLegacySkin", at = @At(value = "HEAD"), cancellable = true)
    private void allowHdSkin(NativeImage image, CallbackInfoReturnable<NativeImage> cir) {
        int height = image.getHeight();
        int width = image.getWidth();
        int scale = width / 8;
        int scaleX64 = scale * 8;
        int scaleX32 = scale * 4;
        int scaleX16 = scale * 2;
        int scaleX8 = scale;
        int scaleX4 = scale / 2;
        int scaleX12 = scaleX8 + scaleX4;
        int scaleX20 = scaleX16 + scaleX4;
        int scaleX24 = scaleX20 + scaleX4;
        int scaleX48 = scale * 6;
        int scaleX44 = scaleX48 - scaleX4;
        int scaleX40 = scale * 5;
        int scaleX52 = scaleX48 + scaleX4;
        if (width == scaleX64 && (height == scaleX32 || height == scaleX64)) {
            boolean legacy = height == scaleX32;
            if (legacy) {
                NativeImage newImage = new NativeImage(scaleX64, scaleX64, true);
                newImage.copyFrom(image);
                image.close();
                image = newImage;
                newImage.fillRect(0, scaleX32, scaleX64, scaleX32, 0);
                newImage.copyRect(scaleX4, scaleX16, scaleX16, scaleX32, scaleX4, scaleX4, true, false);
                newImage.copyRect(scaleX8, scaleX16, scaleX16, scaleX32, scaleX4, scaleX4, true, false);
                newImage.copyRect(0, scaleX20, scaleX24, scaleX32, scaleX4, scaleX12, true, false);
                newImage.copyRect(scaleX4, scaleX20, scaleX16, scaleX32, scaleX4, scaleX12, true, false);
                newImage.copyRect(scaleX8, scaleX20, scaleX8, scaleX32, scaleX4, scaleX12, true, false);
                newImage.copyRect(scaleX12, scaleX20, scaleX16, scaleX32, scaleX4, scaleX12, true, false);
                newImage.copyRect(scaleX44, scaleX16, -scaleX8, scaleX32, scaleX4, scaleX4, true, false);
                newImage.copyRect(scaleX48, scaleX16, -scaleX8, scaleX32, scaleX4, scaleX4, true, false);
                newImage.copyRect(scaleX40, scaleX20, 0, scaleX32, scaleX4, scaleX12, true, false);
                newImage.copyRect(scaleX44, scaleX20, -scaleX8, scaleX32, scaleX4, scaleX12, true, false);
                newImage.copyRect(scaleX48, scaleX20, -scaleX16, scaleX32, scaleX4, scaleX12, true, false);
                newImage.copyRect(scaleX52, scaleX20, -scaleX8, scaleX32, scaleX4, scaleX12, true, false);
            }

            HttpTexture.setNoAlpha(image, 0, 0, scaleX32, scaleX16);
            if (legacy) {
                HttpTexture.doNotchTransparencyHack(image, scaleX32, 0, scaleX64, scaleX32);
            }

            HttpTexture.setNoAlpha(image, 0, scaleX16, scaleX64, scaleX32);
            HttpTexture.setNoAlpha(image, scaleX16, scaleX48, scaleX48, scaleX64);
            cir.setReturnValue(image);
        } else {
            image.close();
            HdSkinSupportMod.LOGGER.warn("Skip incorrectly sized ({}x{}) skin texture", width, height);
            cir.setReturnValue(null);
        }
        cir.cancel();
    }
}
