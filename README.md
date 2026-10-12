# GlintColorizer

[![Modrinth downloads](https://img.shields.io/modrinth/dt/SevsTfWN?style=flat&logo=modrinth&label=Modrinth&color=1bd96a)](https://modrinth.com/mod/glintcolorizer)

GlintColorizer changes the color, speed, rotation and size of the enchantment glint. Items in your hand, in GUI slots, on the ground and in item frames each keep their own settings.

## Features

- Pick one glint color, or turn on "Modify Strokes Individually" to color the two moving layers of the glint separately.
- Set the glint speed between 0.1 and 10 times the vanilla speed.
- Rotate each layer between -180 and 180 degrees. The defaults are -50 and 10.
- Scale the glint pattern between 0 and 8 times its vanilla size.
- Held Item, GUI Item, Dropped Item and Framed Item each have their own copy of these settings.
- Armor has its own glint color and a switch that removes the glint from armor.
- On the Global page, pick a color and press Apply to copy it to every category. A second button applies the Minecraft 1.7 glint color to held, dropped and framed items.
- Run `/glintcolorizer` to open the settings.

## Shiny Pots

Shiny Pots changes how potions with effects look in GUI slots. Every option here is off by default.

- "Shiny Potions" turns the feature on.
- "Render Over Full Slot" stretches the glint over the whole slot.
- "Render Shiny Effect Only" draws the glint behind the bottle and removes the normal glint from the potion.
- "Opaque Shiny Effect" draws that background solid, so the slot behind it cannot shift its color.
- "Custom Shiny Effect Color" gives potions their own color, speed, rotation and scale settings.
- "Potion Color Based Glint" colors the glint to match the potion's liquid.

## Compatibility

- With Argentum installed, the GUI Item settings also apply to Argentum's batched GUI glint.
- With Argentum installed, Shiny Pots draws potions outside Argentum's GUI item atlas. This can lower FPS in inventories that hold many potions.
- The glint colors also apply to the 1.7 and 1.15 glint styles of OverflowAnimations.

## Supported versions

- Minecraft 1.8.9 on Ornithe.

GlintColorizer needs [OneConfig](https://modrinth.com/mod/oneconfig) and Fabric Language Kotlin.

## Support

- Report bugs and ask questions on the [Polyfrost Discord](https://polyfrost.org/discord).
- Check [Polyfrost Status](https://status.polyfrost.org/) if a Polyfrost service is down.

## Building

```sh
./gradlew ":1.8.9:build"
```

GlintColorizer only builds for Minecraft 1.8.9. The build needs JDK 21 and JDK 25 installed.
