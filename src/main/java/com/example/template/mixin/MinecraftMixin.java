package com.example.template.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if FABRIC==1
//$$ @Mixin(net.minecraft.client.MinecraftClient.class)
//#else
@Mixin(net.minecraft.client.Minecraft.class)
//#endif
public class MinecraftMixin {
    @Inject(
            //#if MODERN==0
            method = "startGame", at = @At("HEAD")
            //#else
            //$$ method = "<init>", at = @At(value = "INVOKE",
                //#if FABRIC==1
                //$$ target = "Lnet/minecraft/client/MinecraftClient;onResolutionChanged()V"
                //#else
                //$$ target = "Lnet/minecraft/client/Minecraft;resizeDisplay()V"
                //#endif
            //$$ , shift = At.Shift.AFTER)
            //#endif
    )
    private void sayHello(
            //#if MODERN==1
            //$$ @org.spongepowered.asm.mixin.injection.Coerce Object a,
            //#endif
            CallbackInfo ci) {
        System.out.println("Hello, world!");
    }
}
