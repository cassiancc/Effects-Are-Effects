//? fabric {
package cc.cassian.effects.fabric;

import cc.cassian.effects.registry.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
//? if <26.4 {
import cc.cassian.effects.client.FreezingParticle;
import cc.cassian.effects.client.FrostbiteRenderer;
import cc.cassian.effects.client.ModModelLayers;
import cc.cassian.effects.registry.ModParticleTypes;
//?}
//? if >=26 && <26.4 {
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
//?} else if <26 {
/*import cc.cassian.effects.client.LegacySpawnEgg;
import cc.cassian.effects.registry.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.rendertype.RenderType;
*///?}

public class FabricClientEntrypoint implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		//? if <26.4 && >1.21.2
		EntityRenderers.register(ModEntityTypes.ICE_BALL, ThrownItemRenderer::new);
		//? if =1.21.1
		//EntityRendererRegistry.register(ModEntityTypes.ICE_BALL, ThrownItemRenderer::new);

		//? if >=26 && <26.4 {
		ModModelLayers.createLayerDefinitions().forEach((layer, definition) -> ModelLayerRegistry.registerModelLayer(layer, definition::get));
		EntityRenderers.register(ModEntityTypes.FROSTBITE, FrostbiteRenderer::new);
		ParticleProviderRegistry.getInstance().register(ModParticleTypes.FREEZING, FreezingParticle.Provider::new);
		//?} else if <26 {
		/*ModModelLayers.createLayerDefinitions().forEach((layer, definition) -> EntityModelLayerRegistry.registerModelLayer(layer, definition::get));
		EntityRendererRegistry.register(ModEntityTypes.FROSTBITE, FrostbiteRenderer::new);
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.FREEZING, FreezingParticle.Provider::new);
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BRITTLE_ICE, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_CRYSTAL, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICICLE, RenderType.cutout());
		LegacySpawnEgg.register();
		*///?}
	}
}
//?}
