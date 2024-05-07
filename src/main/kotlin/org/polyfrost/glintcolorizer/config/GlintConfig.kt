package org.polyfrost.glintcolorizer.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.config.migration.VigilanceMigrator
import net.minecraft.client.Minecraft
import org.polyfrost.glintcolorizer.GlintColorizer
import java.io.File

object GlintConfig : Config(
    Mod(
        GlintColorizer.NAME, ModType.UTIL_QOL, "/glintcolorizer_dark.svg", VigilanceMigrator(
            File(File(File("./W-OVERFLOW"), GlintColorizer.NAME), GlintColorizer.ID + ".toml").path
        )
    ), GlintColorizer.ID + ".json"
) {

    @Button(
        name = "Reset Color",
        category = "Color",
        subcategory = "Color Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var reset: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        strokeOne = OneColor(-8372020)
        strokeTwo = OneColor(-8372020)
        GlintConfig.save()
        openGui()
    })

    @Button(
        name = "Sync Armor Glint With Item Glint",
        category = "Color",
        subcategory = "Color Configuration",
        text = "Sync",
        description = "Syncs the armor glint color with the item glint color."
    )
    var syncAtoI: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        strokeOne = OneColor(-8372020)
        strokeTwo = OneColor(-8372020)
        save()
        openGui()
    })

    @Button(
        name = "Sync Item Glint With Armor Glint",
        category = "Color",
        subcategory = "Color Configuration",
        text = "Sync",
        description = "Syncs the item glint color with the armor glint color ."
    )
    var syncItoA: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        strokeOne = OneColor(-8372020)
        strokeTwo = OneColor(-8372020)
        save()
        openGui()
    })

    @Switch(
        name = "Modify glint strokes invidiually",
        category = "Color"
    )
    var individualStrokes: Boolean = false

    @Color(
        name = "Held Item Glint Color",
        category = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var heldColor: OneColor = OneColor(-8372020)

    @Color(
        name = "GUI Item Glint Color",
        category = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var gUIcolor: OneColor = OneColor(-8372020)

    @Color(
        name = "Dropped Item Glint Color",
        category = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var droppedColor: OneColor = OneColor(-8372020)

    @Color(
        name = "Framed Item Glint Color",
        category = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var framedColor: OneColor = OneColor(-8372020)

    @Color(
        name = "Armor Glint Color",
        category = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var armorColor: OneColor = OneColor(-8372020)

    @Color(
        name = "Stroke 1 Color",
        category = "Color",
        description = "Modifies one of the enchantment glint stroke colors."
    )
    var strokeOne: OneColor = OneColor(-8372020)

    @Color(
        name = "Stroke 2 Color",
        category = "Color",
        description = "Modifies one of the enchantment glint stroke colors."
    )
    var strokeTwo: OneColor = OneColor(-8372020)

    @Switch(
        name = "Shiny Potions",
        category = "Shiny Pots"
    )
    var potionGlint: Boolean = false

    @Checkbox(
        name = "Render Over Full Slot",
        category = "Shiny Pots"
    )
    var potionGlintSize: Boolean = false

    @Checkbox(
        name = "Background Slot Only",
        category = "Shiny Pots"
    )
    var potionGlintBackground: Boolean = false

    @Switch(
        name = "Potion Color Based Glint",
        category = "Shiny Pots"
    )
    var potionBasedColor: Boolean = false

    init {
        initialize()

        addDependency("potionGlintType", "potionGlint")
        addDependency("strokeOne", "individualStrokes")
        addDependency("strokeTwo", "individualStrokes")
    }

}