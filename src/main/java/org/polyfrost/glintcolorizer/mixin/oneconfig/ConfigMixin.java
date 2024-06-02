package org.polyfrost.glintcolorizer.mixin.oneconfig;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.core.ConfigUtils;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.elements.BasicOption;
import cc.polyfrost.oneconfig.config.elements.OptionPage;
import cc.polyfrost.oneconfig.internal.config.annotations.Option;
import org.polyfrost.glintcolorizer.config.ColorEntry;
import org.polyfrost.glintcolorizer.config.ColorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

@Mixin(value = Config.class, remap = false)
public class ConfigMixin {

    @Unique
    private transient OptionPage page;
    @Unique
    private transient Object instance;

    @Inject(method = "generateOptionList(Ljava/lang/Object;Ljava/lang/Class;Lcc/polyfrost/oneconfig/config/elements/OptionPage;Lcc/polyfrost/oneconfig/config/data/Mod;Z)V", at = @At("HEAD"))
    private void capture(Object instance, Class<?> targetClass, OptionPage page, Mod mod, boolean migrate, CallbackInfo ci) {
        this.instance = instance;
        this.page = page;
    }

    @ModifyVariable(method = "generateOptionList(Ljava/lang/Object;Ljava/lang/Class;Lcc/polyfrost/oneconfig/config/elements/OptionPage;Lcc/polyfrost/oneconfig/config/data/Mod;Z)V", at = @At(value = "LOAD", ordinal = 0), name = "field")
    private Field generate(Field field) {
        if (field.isAnnotationPresent(ColorEntry.class)) {
            addOptions(page, field, instance);
        }
        return field;
    }

    @Unique
    private void addOptions(OptionPage page, Field field, Object instance) {
        ColorEntry annotation = field.getAnnotation(ColorEntry.class);
        ColorSettings colorSettings = (ColorSettings) ConfigUtils.getField(field, instance);
        for (BasicOption option : glintColorizer$getClassOptions(colorSettings)) {
            ConfigUtils.getSubCategory(page, annotation.category(), option.subcategory).options.add(option);
        }
    }

    @Unique
    private static ArrayList<BasicOption> glintColorizer$getClassOptions(Object object) {
        ArrayList<BasicOption> options = new ArrayList<>();
        ArrayList<Field> fields = ConfigUtils.getClassFields(object.getClass());
        for (Field field : fields) {
            Option option = ConfigUtils.findAnnotation(field, Option.class);
            if (option == null) continue;
            options.add(ConfigUtils.getOption(option, field, object));
        }
        return options;
    }

}