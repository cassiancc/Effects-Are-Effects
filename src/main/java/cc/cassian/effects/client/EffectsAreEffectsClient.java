package cc.cassian.effects.client;

import cc.cassian.effects.registry.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class EffectsAreEffectsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		//? if <26.4
		EntityRenderers.register(ModEntityTypes.ICE_BALL, ThrownItemRenderer::new);
	}
}
