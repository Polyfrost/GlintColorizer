plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "1.8.9" /* [SC] DO NOT EDIT */

stonecutter {
    tasks {
        order("publishModrinth")
    }

    parameters {
        replacements {
            string(eval(current.version, "= 1.8.9")) {
                replace(
                    "com.mojang.blaze3d.platform.InputConstants",
                    "org.polyfrost.oneconfig.internal.legacy.InputConstants"
                )
                replace(
                    "net.minecraft.server.Bootstrap",
                    "net.minecraft.Bootstrap"
                )
                replace(
                    "net.minecraft.client.player.KeyboardInput",
                    "net.minecraft.client.entity.living.player.KeyboardInput"
                )
                replace(
                    "net.minecraft.world.entity.Entity",
                    "net.minecraft.entity.Entity"
                )
            }
        }
    }
}

stonecutter parameters {
    constants {
        val legacy =  current.version == "1.8.9"
        this["legacy"] = legacy
        this["modern"] = !legacy
    }

    replacements {
        string(current.parsed < "26.1") {
            replace("classTweaker v1 official", "classTweaker v1 named")
        }
    }
}