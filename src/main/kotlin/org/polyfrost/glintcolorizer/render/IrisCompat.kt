package org.polyfrost.glintcolorizer.render

//? if >=1.21.5 {
//? if <26.3 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline
*///?} else
import com.mojang.renderpearl.api.pipeline.RenderPipeline
import net.irisshaders.iris.api.v0.IrisApi
import net.irisshaders.iris.api.v0.IrisProgram
//? if >=26.1
import net.irisshaders.iris.api.v0.IrisShadowProgram

internal object IrisCompat {
    fun assignGlintPipelines(vararg pipelines: RenderPipeline) {
        val api = IrisApi.getInstance()
        for (pipeline in pipelines) {
            api.assignPipeline(pipeline, IrisProgram.ARMOR_GLINT)
            //? if >=26.1
            api.assignPipelineShadow(pipeline, IrisShadowProgram.SHADOW_ENTITIES)
        }
    }
}
//?}
