@file:Suppress("UnstableApiUsage")

plugins {
	id("dev.kikugie.loom-back-compat")
	id("dev.kikugie.postprocess.jsonlang")
	id("me.modmuss50.mod-publish-plugin")
	id("maven-publish")
}

val loader = sc.current.project.substringAfter('-')
val fabric = loader == "fabric"
val minecraft = stonecutter.current.version
val mcVersion = stonecutter.current.project.substringBeforeLast('-')
val edgeRelease = !fabric
val unobfuscated = stonecutter.eval(mcVersion, ">26")

tasks.named<ProcessResources>("processResources") {
	fun prop(name: String) = project.property(name) as String

	val props = HashMap<String, String>().apply {
		this["mod_version"] = prop("mod.version") + "+" + prop("deps.minecraft")
		this["minecraft"] = prop("deps.minecraft")
		this["mod_name"] = prop("mod.name")
		this["mod_description"] = prop("mod.description")
		this["mod_license"] = prop("mod.license")
	}

	filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "META-INF/mods.toml")) {
		expand(props)
	}

}

tasks.named("processResources") {
	dependsOn(":${stonecutter.current.project}:stonecutterGenerate")
}

version = "${property("mod.version")}+${property("deps.minecraft")}-${loader}"
base.archivesName = property("mod.id") as String

//loom {
//    accessWidenerPath = rootProject.file("src/main/resources/${property("mod.id")}.accesswidener")
//}

jsonlang {
	languageDirectories = listOf("assets/${property("mod.id")}/lang")
	prettyPrint = true
}

repositories {
	maven {
		name = "shedaniel (Cloth Config)"
		url = uri("https://maven.shedaniel.me/")
		content {
			includeGroupAndSubgroups("me.shedaniel")
		}
	}
	maven {
		name = "Terraformers (Mod Menu)"
		url = uri("https://maven.terraformersmc.com/releases/")
		content {
			includeGroupAndSubgroups("com.terraformersmc")
			includeGroup("dev.emi")
		}
	}
	maven {
		name = "Modrinth"
		url = uri("https://api.modrinth.com/maven")
		content {
			includeGroupAndSubgroups("maven.modrinth")
		}
	}
	maven {
		name = "Sisby Maven"
		url = uri("https://repo.sleeping.town/")
		content {
			includeGroupAndSubgroups("folk.sisby")
		}
	}
	maven {
		name = "Parchment Mappings"
		url = uri("https://maven.parchmentmc.org")
		content {
			includeGroupAndSubgroups("org.parchmentmc")
		}
	}
	maven {
		name = "Xander Maven"
		url = uri("https://maven.isxander.dev/releases")
		content {
			includeGroupAndSubgroups("dev.isxander")
			includeGroupAndSubgroups("org.quiltmc.parsers")
		}
	}
	maven {
		name = "Fuzs Mod Resources"
		url = uri("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
		content {
			includeGroupAndSubgroups("fuzs")
		}
	}
	maven {
		name = "FzzyMaven"
		url = uri("https://maven.fzzyhmstrs.me/")
		content {
			includeGroup("me.fzzyhmstrs")
		}
	}
	maven {
		name = "Cardinal Components"
		url = uri("https://maven.ladysnake.org/releases")
		content {
			includeGroupAndSubgroups("dev.onyxstudios")
			includeGroupAndSubgroups("org.ladysnake")
		}
	}
	exclusiveContent {
		forRepository {
			maven {
				url = uri("https://cursemaven.com")
			}
		}
		filter {
			includeGroup ("curse.maven")
		}
	}
	maven {
		name = "Gegy"
		url = uri("https://maven.gegy.dev/releases/")
		content {
			includeGroupAndSubgroups("dev.lambdaurora")
		}
	}
	maven {
		name = "Nucleoid Maven (Trinkets)"
		url = uri("https://maven.nucleoid.xyz")
		content {
			includeGroupAndSubgroups("eu.pb4")
			includeGroupAndSubgroups("xyz.nucleoid")
		}
	}
	maven {
		name = "Sinytra"
		url = uri("https://maven.sinytra.org")
		content {
			includeGroupAndSubgroups("org.sinytra")
		}
	}
	maven {
		name = "Cassian's Maven"
		url = uri("https://maven.cassian.cc")
		content {
			includeGroupAndSubgroups("cc.cassian")
			includeGroupAndSubgroups("folk.sisby")
		}
	}
	mavenLocal()
}

dependencies {
	minecraft("com.mojang:minecraft:${property("deps.minecraft")}")
	if (!unobfuscated) {
		mappings(loom.layered {
			officialMojangMappings()
			parchment("org.parchmentmc.data:parchment-${property("deps.parchment")}@zip")
			mappings("dev.lambdaurora:yalmm-mojbackward:${property("deps.minecraft")}+build.${property("deps.mojbackward")}")
		})
		implementation("org.jspecify:jspecify:1.0.0")
	}
	if (fabric) {
		modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
		modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
	} else {
		forgeUserdev("net.neoforged:neoforge:${property("deps.neoforge")}:userdev")
	}

	// Configs
	implementation("folk.sisby:kaleido-config:${property("deps.kaleido")}")
	include("folk.sisby:kaleido-config:${property("deps.kaleido")}")

	// McQoy
	if (hasProperty("deps.mcqoy")) {
		localRuntime("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-${loader}")
		localRuntime("maven.modrinth:mcqoy:${property("deps.mcqoy")}")
	}

	if (hasProperty("deps.modmenu")) {
        modLocalRuntime("com.terraformersmc:modmenu:${property("deps.modmenu")}") {
            exclude("net.fabricmc")
        }
    }

	if (hasProperty("deps.rrv")) {
        localRuntime("cc.cassian.rrv:reliable-recipe-viewer-${loader}:${property("deps.rrv")}") {
            isTransitive = false
        }
    }

	// Mixin Constraints - embedded
//	implementation("com.moulberry:mixinconstraints:1.0.9")
//	include("com.moulberry:mixinconstraints:1.0.9")

}

configurations.all {
	resolutionStrategy {
		force("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
	}
}

stonecutter {
	replacements.string {
		direction = eval(current.version, ">26")
		replace("GuiGraphics", "GuiGraphicsExtractor")
	}
	replacements.string {
		direction = eval(current.version, ">26")
		replace("guiGraphics.drawString", "guiGraphics.text")
	}
	replacements.string {
		direction = eval(current.version, ">26")
		replace("guiGraphics.renderFakeItem", "guiGraphics.fakeItem")
	}
	replacements.string {
		direction = eval(current.version, ">1.21.2")
		replace("pushPose", "pushMatrix")
	}
	replacements.string {
		direction = eval(current.version, ">1.21.2")
		replace("popPose", "popMatrix")
	}
}

tasks {

	register<Copy>("buildAndCollect") {
		group = "build"
		from(loomx.modJar.map { it.archiveFile })
		into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
		dependsOn("build")
	}

	if (stonecutter.eval(mcVersion, ">=26.4")) {
		processResources {
			exclude("**/freezing*", "**/ice_ball*")
		}
	}
	if (stonecutter.eval(mcVersion, "<26.3")) {
		processResources {
			exclude("**/recipe/brewing*")
		}
	}
}

java {
	withSourcesJar()
	val javaCompat = if (stonecutter.eval(stonecutter.current.version, ">=26")) {
		JavaVersion.VERSION_25
	} else if (stonecutter.eval(stonecutter.current.version, ">=1.21")) {
		JavaVersion.VERSION_21
	} else {
		JavaVersion.VERSION_17
	}
	sourceCompatibility = javaCompat
	targetCompatibility = javaCompat
}

val additionalVersionsStr = findProperty("publish.additionalVersions") as String?
val additionalVersions: List<String> = additionalVersionsStr
	?.split(",")
	?.map { it.trim() }
	?.filter { it.isNotEmpty() }
	?: emptyList()

publishMods {
	file = loomx.modJar.map { it.archiveFile.get() }
	additionalFiles.from(loomx.modSourcesJar.map { it.archiveFile.get() })

	// one of BETA, ALPHA, STABLE
	type = if (edgeRelease) {
		BETA
	} else {
		STABLE
	}
	displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version}"
	version = "${property("mod.version")}+${property("deps.minecraft")}-${loader}"
	changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
	modLoaders.add(loader)

	modrinth {
		projectId = property("publish.modrinth") as String
		accessToken = env.MODRINTH_API_KEY.orNull()
		minecraftVersions.add(property("deps.minecraft") as String)
		minecraftVersions.addAll(additionalVersions)
		if (fabric)
			requires("fabric-api")
		optional("mcqoy")
		if (hasProperty("deps.rrv")){
			optional("rrv")
		}
		environment = CLIENT_AND_SERVER
	}
}