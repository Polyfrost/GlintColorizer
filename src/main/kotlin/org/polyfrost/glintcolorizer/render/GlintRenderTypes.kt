package org.polyfrost.glintcolorizer.render

//? if modern {
import com.mojang.blaze3d.vertex.DefaultVertexFormat

//? if <1.21.11 {
/*import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
*///?} else {
import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier
//?}
//? if >=1.21.11 && <26.3
//import net.minecraft.client.renderer.rendertype.OutputTarget

//? if <1.21.5 {
/*import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.renderer.RenderStateShard
*///?}
//? if =1.21.1
//import net.minecraft.client.renderer.GameRenderer
//? if =1.21.4 {
/*import net.minecraft.client.renderer.CoreShaders
import net.minecraft.util.TriState
*///?}

//? if >=1.21.5 {
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.renderer.RenderPipelines
//?}
//? if >=1.21.5 && <1.21.8 {
/*import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.util.TriState
*///?}
//? if >=1.21.8 && <1.21.11
//import net.minecraft.client.renderer.RenderStateShard
//? if >=1.21.5 && <26.3 {
/*import com.mojang.blaze3d.pipeline.BlendFunction
import com.mojang.blaze3d.pipeline.RenderPipeline
*///?}
//? if >=1.21.5 && <26.1 {
/*import com.mojang.blaze3d.platform.DepthTestFunction
import com.mojang.blaze3d.shaders.UniformType
import com.mojang.blaze3d.vertex.VertexFormat
*///?}
//? if =26.1 {
/*import com.mojang.blaze3d.pipeline.ColorTargetState
import com.mojang.blaze3d.pipeline.DepthStencilState
import com.mojang.blaze3d.platform.CompareOp
import com.mojang.blaze3d.shaders.UniformType
import com.mojang.blaze3d.vertex.VertexFormat
*///?}
//? if =26.2 {
/*import com.mojang.blaze3d.PrimitiveTopology
import com.mojang.blaze3d.pipeline.ColorTargetState
import com.mojang.blaze3d.pipeline.DepthStencilState
import com.mojang.blaze3d.platform.CompareOp
import net.minecraft.client.renderer.BindGroupLayouts
*///?}
//? if >=26.3 {
import com.mojang.renderpearl.api.pipeline.BlendFunction
import com.mojang.renderpearl.api.pipeline.ColorTargetState
import com.mojang.renderpearl.api.pipeline.CompareOp
import com.mojang.renderpearl.api.pipeline.DepthStencilState
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology
import com.mojang.renderpearl.api.pipeline.RenderPipeline
import net.minecraft.client.renderer.BindGroupLayouts
//?}

object GlintRenderTypes {
    //? if <1.21.11 {
    //@JvmField val TEXTURE: ResourceLocation = ResourceLocation.fromNamespaceAndPath("glintcolorizer", "textures/misc/enchanted_item_glint.png")
    //?} else
    @JvmField val TEXTURE: Identifier = Identifier.fromNamespaceAndPath("glintcolorizer", "textures/misc/enchanted_item_glint.png")

    //? if >=1.21.5 {
    //? if =1.21.5 {
    //private val SHADER = ResourceLocation.fromNamespaceAndPath("glintcolorizer", "core/glint_1_21_5")
    //?} else if <1.21.11 {
    //private val SHADER = ResourceLocation.fromNamespaceAndPath("glintcolorizer", "core/glint_1_21_8")
    //?} else if <26.3 {
    //private val SHADER = Identifier.fromNamespaceAndPath("glintcolorizer", "core/glint_1_21_8")
    //?} else
    private val SHADER = Identifier.fromNamespaceAndPath("glintcolorizer", "core/glint_26_3")

    private val PIPELINE: RenderPipeline = RenderPipelines.register(
        RenderPipeline.builder()
            //? if <1.21.11 {
            //.withLocation(ResourceLocation.fromNamespaceAndPath("glintcolorizer", "pipeline/glint"))
            //?} else
            .withLocation(Identifier.fromNamespaceAndPath("glintcolorizer", "pipeline/glint"))
            .withVertexShader(SHADER)
            .withFragmentShader(SHADER)
            //? if =1.21.5 {
            /*.withUniform("ModelViewMat", UniformType.MATRIX4X4)
            .withUniform("ProjMat", UniformType.MATRIX4X4)
            .withUniform("FogStart", UniformType.FLOAT)
            .withUniform("FogEnd", UniformType.FLOAT)
            .withUniform("FogShape", UniformType.INT)
            *///?} else if <26.2 {
            /*.withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER)
            .withUniform("Projection", UniformType.UNIFORM_BUFFER)
            .withUniform("Fog", UniformType.UNIFORM_BUFFER)
            *///?} else if =26.2 {
            /*.withBindGroupLayout(BindGroupLayouts.GLOBALS)
            .withBindGroupLayout(BindGroupLayouts.MATRICES_PROJECTION)
            .withBindGroupLayout(BindGroupLayouts.FOG)
            *///?} else {
            .withBindGroupLayout(BindGroupLayouts.GLOBALS)
            .withBindGroupLayout(BindGroupLayouts.PROJECTION)
            .withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
            .withBindGroupLayout(BindGroupLayouts.FOG)
            //?}
            //? if <26.2 {
            //.withSampler("Sampler0")
            //?} else
            .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
            .withCull(false)
            //? if <26.1 {
            /*.withBlend(BlendFunction.GLINT)
            .withDepthTestFunction(DepthTestFunction.EQUAL_DEPTH_TEST)
            .withDepthWrite(false)
            *///?} else {
            .withColorTargetState(ColorTargetState(BlendFunction.GLINT))
            .withDepthStencilState(DepthStencilState(CompareOp.EQUAL, false))
            //?}
            //? if <26.2 {
            //.withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
            //?} else {
            .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            //?}
            .build()
    )
    //?}

    //? if =1.21.5 {
    /*private val SHINY_PIPELINE = shinyPipeline("shiny_effect", BlendFunction.GLINT)
    private val SHINY_OPAQUE_PIPELINE = shinyPipeline("shiny_effect_opaque", BlendFunction.TRANSLUCENT)

    private fun shinyPipeline(name: String, blend: BlendFunction): RenderPipeline = RenderPipelines.register(
        RenderPipeline.builder()
            .withLocation(ResourceLocation.fromNamespaceAndPath("glintcolorizer", "pipeline/$name"))
            .withVertexShader(SHADER)
            .withFragmentShader(SHADER)
            .withUniform("ModelViewMat", UniformType.MATRIX4X4)
            .withUniform("ProjMat", UniformType.MATRIX4X4)
            .withUniform("FogStart", UniformType.FLOAT)
            .withUniform("FogEnd", UniformType.FLOAT)
            .withUniform("FogShape", UniformType.INT)
            .withSampler("Sampler0")
            .withCull(false)
            .withBlend(blend)
            .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
            .withDepthWrite(false)
            .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
            .build()
    )
    *///?}

    //? if >=1.21.5 {
    init {
        if (FabricLoader.getInstance().isModLoaded("iris")) {
            //? if =1.21.5 {
            /*IrisCompat.assignGlintPipelines(PIPELINE, SHINY_PIPELINE, SHINY_OPAQUE_PIPELINE)
            *///?} else
            IrisCompat.assignGlintPipelines(PIPELINE)
        }
    }
    //?}

    //? if >=1.21.6 {
    val GUI_SHINY: RenderPipeline = RenderPipelines.register(
        RenderPipeline.builder(RenderPipelines.GUI_TEXTURED_SNIPPET)
            //? if <1.21.11 {
            //.withLocation(ResourceLocation.fromNamespaceAndPath("glintcolorizer", "pipeline/gui_shiny_effect"))
            //?} else
            .withLocation(Identifier.fromNamespaceAndPath("glintcolorizer", "pipeline/gui_shiny_effect"))
            //? if <26.1 {
            //.withBlend(BlendFunction.GLINT)
            //?} else
            .withColorTargetState(ColorTargetState(BlendFunction.GLINT))
            .build()
    )
    //?} else {
    /*val SHINY: RenderType = createShiny("shiny_effect", false)
    val SHINY_OPAQUE: RenderType = createShiny("shiny_effect_opaque", true)
    *///?}

    @JvmField val ITEM_FIRST: RenderType = create("item_glint_1", false)
    @JvmField val ITEM_SECOND: RenderType = create("item_glint_2", false)
    @JvmField val ARMOR_FIRST: RenderType = create("armor_glint_1", false)
    @JvmField val ARMOR_SECOND: RenderType = create("armor_glint_2", false)
    //? if <26.3 {
    /*@JvmField val ITEM_TRANSLUCENT_FIRST: RenderType = create("item_glint_translucent_1", true)
    @JvmField val ITEM_TRANSLUCENT_SECOND: RenderType = create("item_glint_translucent_2", true)
    //? if <1.21.6 {
    /*@JvmField val ALL: Array<RenderType> = arrayOf(SHINY_OPAQUE, SHINY, ITEM_FIRST, ITEM_SECOND, ITEM_TRANSLUCENT_FIRST, ITEM_TRANSLUCENT_SECOND, ARMOR_FIRST, ARMOR_SECOND)
    *///?} else
    @JvmField val ALL: Array<RenderType> = arrayOf(ITEM_FIRST, ITEM_SECOND, ITEM_TRANSLUCENT_FIRST, ITEM_TRANSLUCENT_SECOND, ARMOR_FIRST, ARMOR_SECOND)
    *///?}

    //? if <1.21.5 {
    /*private fun createShiny(name: String, opaque: Boolean): RenderType = RenderType.create(
        "glintcolorizer_$name",
        DefaultVertexFormat.NEW_ENTITY,
        VertexFormat.Mode.QUADS,
        1536,
        RenderType.CompositeState.builder()
            //? if =1.21.1 {
            /*.setShaderState(RenderStateShard.ShaderStateShard(GameRenderer::getRendertypeEyesShader))
            .setTextureState(RenderStateShard.TextureStateShard(TEXTURE, true, false))
            *///?} else {
            .setShaderState(RenderStateShard.ShaderStateShard(CoreShaders.RENDERTYPE_EYES))
            .setTextureState(RenderStateShard.TextureStateShard(TEXTURE, TriState.DEFAULT, false))
            //?}
            .setTransparencyState(if (opaque) RenderStateShard.TRANSLUCENT_TRANSPARENCY else RenderStateShard.GLINT_TRANSPARENCY)
            .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
            .setCullState(RenderStateShard.NO_CULL)
            .setWriteMaskState(RenderStateShard.COLOR_WRITE)
            .setOutputState(RenderStateShard.MAIN_TARGET)
            .createCompositeState(false)
    )
    *///?} else if =1.21.5 {
    /*private fun createShiny(name: String, opaque: Boolean): RenderType = RenderType.create(
        "glintcolorizer_$name",
        1536,
        if (opaque) SHINY_OPAQUE_PIPELINE else SHINY_PIPELINE,
        RenderType.CompositeState.builder()
            .setTextureState(RenderStateShard.TextureStateShard(TEXTURE, TriState.DEFAULT, false))
            .setOutputState(RenderStateShard.MAIN_TARGET)
            .createCompositeState(false)
    )
    *///?}

    private fun create(name: String, translucent: Boolean): RenderType {
        //? if <1.21.5 {
        /*return RenderType.create(
            "glintcolorizer_$name",
            DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.QUADS,
            1536,
            RenderType.CompositeState.builder()
                //? if =1.21.1 {
                /*.setShaderState(RenderStateShard.ShaderStateShard(GameRenderer::getRendertypeEyesShader))
                .setTextureState(RenderStateShard.TextureStateShard(TEXTURE, true, false))
                *///?} else {
                .setShaderState(RenderStateShard.ShaderStateShard(CoreShaders.RENDERTYPE_EYES))
                .setTextureState(RenderStateShard.TextureStateShard(TEXTURE, TriState.DEFAULT, false))
                //?}
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setCullState(RenderStateShard.NO_CULL)
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setOutputState(if (translucent) RenderStateShard.ITEM_ENTITY_TARGET else RenderStateShard.MAIN_TARGET)
                .createCompositeState(false)
        )
        *///?} else if <1.21.11 {
        /*return RenderType.create(
            "glintcolorizer_$name",
            1536,
            PIPELINE,
            RenderType.CompositeState.builder()
                //? if <1.21.8 {
                //.setTextureState(RenderStateShard.TextureStateShard(TEXTURE, TriState.DEFAULT, false))
                //?} else
                .setTextureState(RenderStateShard.TextureStateShard(TEXTURE, false))
                .setOutputState(if (translucent) RenderStateShard.ITEM_ENTITY_TARGET else RenderStateShard.MAIN_TARGET)
                .createCompositeState(false)
        )
        *///?} else {
        val setup = RenderSetup.builder(PIPELINE).withTexture("Sampler0", TEXTURE)
        //? if <26.3 {
        /*if (translucent) {
            setup.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
        }
        *///?}
        return RenderType.create("glintcolorizer_$name", setup.createRenderSetup())
        //?}
    }
}
//?}
