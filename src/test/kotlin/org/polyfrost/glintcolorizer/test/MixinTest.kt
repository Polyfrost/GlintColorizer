package org.polyfrost.glintcolorizer.test

//? if modern {
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.SharedConstants
import net.minecraft.server.Bootstrap
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.spongepowered.asm.mixin.MixinEnvironment
import org.spongepowered.asm.mixin.MixinEnvironment.Option
import org.spongepowered.asm.mixin.transformer.IMixinTransformer

class MixinTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun setupEnvironment() {
            SharedConstants.tryDetectVersion()
            Bootstrap.bootStrap()
        }
    }

    @Test
    fun `mixins load successfully`() {
        Assertions.assertEquals(EnvType.CLIENT, FabricLoader.getInstance().environmentType)
        val environment = MixinEnvironment.getCurrentEnvironment()
        Assertions.assertInstanceOf(
            IMixinTransformer::class.java,
            environment.activeTransformer,
        )
        environment.setOption(Option.REFMAP_REMAP, false)
        environment.audit()
    }
}
//?}
