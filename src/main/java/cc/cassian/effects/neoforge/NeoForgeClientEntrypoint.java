//? neoforge {
/*package cc.cassian.effects.neoforge;

import cc.cassian.effects.registry.ModEntityTypes;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
//? if <26.4 {
import cc.cassian.effects.client.FreezingParticle;
import cc.cassian.effects.client.FrostbiteRenderer;
import cc.cassian.effects.client.ModModelLayers;
import cc.cassian.effects.registry.ModParticleTypes;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
//?}

@EventBusSubscriber(value = Dist.CLIENT)
public class NeoForgeClientEntrypoint {
	@SubscribeEvent
	public static void register(EntityRenderersEvent.RegisterRenderers event) {
		//? if <26.4 {
		event.registerEntityRenderer(ModEntityTypes.ICE_BALL, ThrownItemRenderer::new);
		event.registerEntityRenderer(ModEntityTypes.FROSTBITE, FrostbiteRenderer::new);
		//?}
	}

	//? if <26.4 {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		ModModelLayers.createLayerDefinitions().forEach(event::registerLayerDefinition);
	}

	@SubscribeEvent
	public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(ModParticleTypes.FREEZING, FreezingParticle.Provider::new);
	}
	//?}
}
*///?}
