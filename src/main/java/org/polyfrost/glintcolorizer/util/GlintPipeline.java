package org.polyfrost.glintcolorizer.util;

import org.polyfrost.glintcolorizer.GlintColorizer;
import org.polyfrost.glintcolorizer.config.GlintColorizerConfig;
import org.polyfrost.glintcolorizer.mixins.accessor.RenderTypeCompositeStateBuilderAccessor;
import org.polyfrost.glintcolorizer.mixins.accessor.RenderTypeAccessor;
//? if >=1.21.6 {
import org.polyfrost.glintcolorizer.mixins.accessor.RenderPipelinesAccessor;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.shaders.UniformType;
//?}
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
//? if <1.21.6
/*import net.minecraft.util.TriState;*/
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

public class GlintPipeline {
    public static final ResourceLocation GLINT_TEXTURE_PATH = GlintColorizer.id("textures/misc/enchanted_item_glint.png");

    //? if <1.21.6 {
    /*public static final ShaderProgram GLINT_SHADER = new ShaderProgram(
            GlintColorizer.id("core/glint"),
            DefaultVertexFormat.POSITION_TEX,
            ShaderDefines.EMPTY
    );
    public static final RenderStateShard.ShaderStateShard GLINT_SHADER_SHARD = new RenderStateShard.ShaderStateShard(GLINT_SHADER);
    static {
        CoreShaders.getProgramsToPreload().add(GLINT_SHADER);
    }
    *///?} else {
    private static final RenderPipeline.Snippet GLINT_PIPELINE_SNIPPET =
            RenderPipeline.builder(RenderPipelinesAccessor.glintcolorizer$getMatricesColorFogSnippet())
                    .withVertexShader(GlintColorizer.id("core/glint"))
                    .withFragmentShader(GlintColorizer.id("core/glint"))
                    .withBlend(BlendFunction.GLINT)
                    .withDepthTestFunction(DepthTestFunction.EQUAL_DEPTH_TEST)
                    .withSampler("Sampler0")
                    //? if >=1.21.6 {
                    .withUniform("Glint", UniformType.UNIFORM_BUFFER)
                    //?} else {
                    /*.withUniform("GlintColor", UniformType.INT)
                    *///?}
                    .withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS)
                    .buildSnippet();

    // Item
    private static final RenderPipeline.Snippet ITEM_GLINT_PIPELINE_SNIPPET =
            RenderPipeline.builder(GLINT_PIPELINE_SNIPPET)
                    .withColorWrite(true, false)
                    .withCull(true)
                    .buildSnippet();

    public static final RenderPipeline ITEM_GLINT_1ST_LAYER_PIPELINE = RenderPipelines.register(
            RenderPipeline.builder(ITEM_GLINT_PIPELINE_SNIPPET)
                    .withLocation(GlintColorizer.id("pipeline/item_glint_layer_1"))
                    .build());

    public static final RenderPipeline SHINY_ITEM_GLINT_1ST_LAYER_PIPELINE = RenderPipelines.register(
            RenderPipeline.builder(ITEM_GLINT_PIPELINE_SNIPPET)
                    .withLocation(GlintColorizer.id("pipeline/shiny_item_glint_layer_1"))
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                    .build());

    public static final RenderPipeline ITEM_GLINT_2ND_LAYER_PIPELINE = RenderPipelines.register(
            RenderPipeline.builder(ITEM_GLINT_PIPELINE_SNIPPET)
                    .withLocation(GlintColorizer.id("pipeline/item_glint_layer_2"))
                    .build());

    public static final RenderPipeline SHINY_ITEM_GLINT_2ND_LAYER_PIPELINE = RenderPipelines.register(
            RenderPipeline.builder(ITEM_GLINT_PIPELINE_SNIPPET)
                    .withLocation(GlintColorizer.id("pipeline/shiny_item_glint_layer_2"))
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                    .build());
     //?}

    public static final RenderType ITEM_GLINT_1ST_LAYER_RENDERTYPE = makeItemGlintLayer(new RenderStateShard.TexturingStateShard(
            "item_glint_layer_1_texturing",
            GlintPipeline::updateGlintLayer1Texturing,
            RenderSystem::resetTextureMatrix
    ), GlintLayer.FIRST, false);

    public static final RenderType SHINY_ITEM_GLINT_1ST_LAYER_RENDERTYPE = makeItemGlintLayer(new RenderStateShard.TexturingStateShard(
            "shiny_item_glint_layer_1_texturing",
            GlintPipeline::updateGlintLayer1Texturing,
            RenderSystem::resetTextureMatrix
    ), GlintLayer.FIRST, true);

    public static final RenderType ITEM_GLINT_2ND_LAYER_RENDERTYPE = makeItemGlintLayer(new RenderStateShard.TexturingStateShard(
            "item_glint_layer_2_texturing",
            GlintPipeline::updateGlintLayer2Texturing,
            RenderSystem::resetTextureMatrix
    ), GlintLayer.SECOND, false);

    public static final RenderType SHINY_ITEM_GLINT_2ND_LAYER_RENDERTYPE = makeItemGlintLayer(new RenderStateShard.TexturingStateShard(
            "shiny_item_glint_layer_2_texturing",
            GlintPipeline::updateGlintLayer2Texturing,
            RenderSystem::resetTextureMatrix
    ), GlintLayer.SECOND, true);

    private static RenderType makeItemGlintLayer(RenderStateShard.TexturingStateShard texturingStateShard, GlintLayer layer, boolean shiny) {
        RenderTypeCompositeStateBuilderAccessor compositeStateBuilder = (RenderTypeCompositeStateBuilderAccessor) RenderType.CompositeState.builder();
        //? if <1.21.6
        /*compositeStateBuilder.withShaderState(GLINT_SHADER_SHARD);*/
        compositeStateBuilder.withTextureState(new RenderStateShard.TextureStateShard(
                GLINT_TEXTURE_PATH,
                //? <1.21.6
                /*TriState.DEFAULT,*/
                false)
        );
        compositeStateBuilder.withTexturingState(texturingStateShard);
        //? if <1.21.6 {
        /*compositeStateBuilder.withDepthTestState(shiny ? RenderType.NO_DEPTH_TEST : RenderType.EQUAL_DEPTH_TEST);
        compositeStateBuilder.withWriteMaskState(RenderType.COLOR_WRITE);
        compositeStateBuilder.withCullState(RenderType.CULL);
        compositeStateBuilder.withBlendState(RenderType.GLINT_TRANSPARENCY);
        *///?}
        return RenderTypeAccessor.createRenderType(
                (shiny ? "shiny_" : "") + "item_glint_layer_" + (layer.ordinal() + 1),
                //? <1.21.6 {
                /*DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                *///?}
                1536,
                //? >=1.21.6
                layer == GlintLayer.FIRST ? (shiny ? SHINY_ITEM_GLINT_1ST_LAYER_PIPELINE : ITEM_GLINT_1ST_LAYER_PIPELINE) : (shiny ? SHINY_ITEM_GLINT_2ND_LAYER_PIPELINE : ITEM_GLINT_2ND_LAYER_PIPELINE),
                compositeStateBuilder.buildCompositeState(false));
    }

    private static void updateGlintLayer1Texturing() {
        final float tilt = (getSystemTime() % 3000L) / 3000.0F / 8.0F;
        RenderSystem.setTextureMatrix(new Matrix4f()
                .scale(8.0F * GlintMetadata.getRenderingOptions().scale)
                .translate(tilt * GlintMetadata.getRenderingOptions().speed, 0.0F, 0.0F)
                .rotateZ((float) Math.toRadians(GlintMetadata.getRenderingOptions().strokeOneRotation)));
    }

    private static void updateGlintLayer2Texturing() {
        final float tilt = (getSystemTime() % 4873L) / 4873.0F / 8.0F;
        RenderSystem.setTextureMatrix(new Matrix4f()
                .scale(8.0F * GlintMetadata.getRenderingOptions().scale)
                .translate(-tilt * GlintMetadata.getRenderingOptions().speed, 0.0F, 0.0F)
                .rotateZ((float) Math.toRadians(GlintMetadata.getRenderingOptions().strokeTwoRotation)));
    }

    // Armor
    //? if >=1.21.6 {
    private static final RenderPipeline.Snippet ARMOR_GLINT_PIPELINE_SNIPPET =
            RenderPipeline.builder(GLINT_PIPELINE_SNIPPET)
                    .withDepthWrite(false)
                    .withCull(false)
                    .buildSnippet();

    public static final RenderPipeline ARMOR_GLINT_1ST_LAYER_PIPELINE = RenderPipelines.register(
            RenderPipeline.builder(ARMOR_GLINT_PIPELINE_SNIPPET)
                    .withLocation(GlintColorizer.id("pipeline/armor_glint_layer_1"))
                    .build());

    public static final RenderPipeline ARMOR_GLINT_2ND_LAYER_PIPELINE = RenderPipelines.register(
            RenderPipeline.builder(ARMOR_GLINT_PIPELINE_SNIPPET)
                    .withLocation(GlintColorizer.id("pipeline/armor_glint_layer_2"))
                    .build());
    //?}

    public static final RenderType ARMOR_GLINT_1ST_LAYER_RENDERTYPE = makeArmorGlintLayer(new RenderStateShard.TexturingStateShard(
            "armor_glint_layer_1_texturing",
            () -> {
                final float scale = 0.33333334F * GlintColorizerConfig.armorGlint.scale;
                RenderSystem.setTextureMatrix(new Matrix4f()
                        .scale(scale)
                        .rotateZ((float) Math.toRadians(30.0F - GlintColorizerConfig.armorGlint.strokeOneRotation))
                        .translate(0.0F, getArmorTilt() * 0.001F * 20.0F, 0.0F));
            },
            RenderSystem::resetTextureMatrix
    ), GlintLayer.FIRST);

    public static final RenderType ARMOR_GLINT_2ND_LAYER_RENDERTYPE = makeArmorGlintLayer(new RenderStateShard.TexturingStateShard(
            "armor_glint_layer_2_texturing",
            () -> {
                final float scale = 0.33333334F * GlintColorizerConfig.armorGlint.scale;
                RenderSystem.setTextureMatrix(new Matrix4f()
                        .scale(scale)
                        .rotateZ((float) Math.toRadians(30.0F - GlintColorizerConfig.armorGlint.strokeTwoRotation))
                        .translate(0.0F, getArmorTilt() * (0.001F + 0.003F) * 20.0F, 0.0F));
            },
            RenderSystem::resetTextureMatrix
    ), GlintLayer.SECOND);

    private static RenderType makeArmorGlintLayer(RenderStateShard.TexturingStateShard texturingStateShard, GlintLayer layer) {
        RenderTypeCompositeStateBuilderAccessor compositeStateBuilder = (RenderTypeCompositeStateBuilderAccessor) RenderType.CompositeState.builder();
        //? <1.21.6
        /*compositeStateBuilder.withShaderState(GLINT_SHADER_SHARD);*/
        compositeStateBuilder.withTextureState(new RenderStateShard.TextureStateShard(
                GLINT_TEXTURE_PATH,
                //? <1.21.6
                /*TriState.DEFAULT,*/
                false)
        );
        compositeStateBuilder.withTexturingState(texturingStateShard);
        compositeStateBuilder.withLayeringState(RenderType.VIEW_OFFSET_Z_LAYERING);
        //? <1.21.6 {
        /*compositeStateBuilder.withDepthTestState(RenderType.EQUAL_DEPTH_TEST);
        compositeStateBuilder.withWriteMaskState(RenderType.COLOR_WRITE);
        compositeStateBuilder.withCullState(RenderType.NO_CULL);
        compositeStateBuilder.withBlendState(RenderType.GLINT_TRANSPARENCY);
        *///?}
        return RenderTypeAccessor.createRenderType(
                "armor_glint_layer_" + (layer.ordinal() + 1),
                //? if <1.21.6 {
                /*DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                *///?}
                1536,
                //? if >=1.21.6
                layer == GlintLayer.FIRST ? ARMOR_GLINT_1ST_LAYER_PIPELINE : ARMOR_GLINT_2ND_LAYER_PIPELINE,
                compositeStateBuilder.buildCompositeState(false));
    }

    // Utility
    private static float getArmorTilt() {
        return (float) ((Util.getMillis() * GlintColorizerConfig.armorGlint.speed * 8.0) % 300000L) / 500.0F;
    }

    private static float getSystemTime() {
        return (float) (GLFW.glfwGetTime() * 1000.0F);
    }
}
