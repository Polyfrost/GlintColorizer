import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.loom) // Required By Fabric
    alias(libs.plugins.blossom) // Required for Token Replacements
    alias(libs.plugins.ksp) // Required by Fletching Table
    alias(libs.plugins.fletchingtable.fabric)
}

class ModData {
    val id = property("mod.id").toString()
    val name = property("mod.name")
    val version = property("mod.version")
    val group = property("mod.group").toString()
    val description = property("mod.description")
    val source = property("mod.source")
    val issues = property("mod.issues")
    val license = property("mod.license") as String
}

class Dependencies {
    val fabricLoaderVersion = property("deps.fabric_loader_version")
    val fabricApiVersion = property("deps.fabric_api_version") as String?
    val oneConfigVersion = property("deps.oneconfig_version")
}

class McData {
    val version = property("mod.minecraft_version")
    val versionRange = property("mod.minecraft_version_range") as String
}

val mc = McData()
val mod = ModData()
val deps = Dependencies()

version = "${mod.version}+${mc.version}-fabric"
group = mod.group
base { archivesName.set(mod.id) }

blossom {
    replaceToken("@MODID@", mod.id)
    replaceToken("@MOD_NAME@", mod.name)
    replaceToken("@MOD_VERSION@", mod.version)
}

loom {
    silentMojangMappingsLicense()

    runConfigs.all {
        ideConfigGenerated(stonecutter.current.isActive)
        runDir = "../../run"
    }

    runConfigs.remove(runConfigs["server"]) // Removes server run configs
}

loom.runs {
    afterEvaluate {
        val mixinJarFile = configurations.runtimeClasspath.get().incoming.artifactView {
            componentFilter {
                it is ModuleComponentIdentifier && it.group == "net.fabricmc" && it.module == "sponge-mixin"
            }
        }.files.first()

        configureEach {
            vmArg("-javaagent:$mixinJarFile")

            property("mixin.hotSwap", "true")
            property("mixin.debug.export", "true") // Puts mixin outputs in /run/.mixin.out
        }
    }
}

fletchingTable {
    mixins.create("main") {
        mixin("default", "${mod.id}.mixins.json")
    }

    lang.create("main") {
        patterns.add("assets/${mod.id}/lang/**")
    }
}

repositories {
    maven("https://maven.parchmentmc.org") // Parchment
    maven("https://repo.polyfrost.org/releases") // OmniCore
    maven("https://repo.polyfrost.org/snapshots") // OmniCore
    maven("https://api.modrinth.com/maven") // Modrinth
}

dependencies {
    minecraft("com.mojang:minecraft:${mc.version}")

    @Suppress("UnstableApiUsage")
    mappings(loom.layered {
        // MojMap mappings
        officialMojangMappings()

        // Parchment mappings (it adds parameter mappings & javadoc)
        optionalProp("deps.parchment_version") {
            parchment("org.parchmentmc.data:parchment-${mc.version}:$it@zip")
        }
    })

    modImplementation("net.fabricmc:fabric-loader:${deps.fabricLoaderVersion}")!!
    modImplementation("net.fabricmc.fabric-api:fabric-api:${deps.fabricApiVersion}")

    val modules = listOf("${mc.version}-fabric", "commands", "config", "config-impl", "events", "internal", "ui", "utils", "hud")
    for (module in modules) {
        modImplementation("org.polyfrost.oneconfig:$module:${deps.oneConfigVersion}")
    }
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks {
    processResources {
        val props = buildMap {
            put("id", mod.id)
            put("name", mod.name)
            put("version", mod.version)
            put("description", mod.description)
            put("source", mod.source)
            put("issues", mod.issues)
            put("license", mod.license)
            put("minecraft_version_range", mc.versionRange)
            put("fabric_loader_version", deps.fabricLoaderVersion)
        }

        props.forEach(inputs::property)

        filesMatching("**/lang/en_us.json") { // Defaults description to English translation
            expand(props)
            filteringCharset = "UTF-8"
        }

        filesMatching("fabric.mod.json") {
            expand(props)
        }
    }

    withType<JavaCompile>().configureEach {
        options.release.set(21)
    }

    withType<KotlinCompile>().configureEach {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
    }

    jar {
        inputs.property("archivesName", base.archivesName)
        from("LICENSE") {
            rename { "${it}_${inputs.properties["archivesName"]}" }
        }
    }
}

if (stonecutter.current.isActive) {
    rootProject.tasks.register("buildActive") {
        group = "project"
        dependsOn(tasks.named("build"))
    }
}

fun <T> optionalProp(property: String, block: (String) -> T?): T? =
    findProperty(property)?.toString()?.takeUnless { it.isBlank() }?.let(block)
