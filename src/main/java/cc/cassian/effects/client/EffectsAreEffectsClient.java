package cc.cassian.effects.client;

import cc.cassian.effects.registry.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class EffectsAreEffectsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		//? if <26.4 && >1.21.2
		EntityRenderers.register(ModEntityTypes.ICE_BALL, ThrownItemRenderer::new);
		//? if =1.21.1
		//net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(ModEntityTypes.ICE_BALL, ThrownItemRenderer::new);
	}
}
