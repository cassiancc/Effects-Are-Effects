//? fabric {
package cc.cassian.effects.fabric;

import cc.cassian.effects.registry.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class FabricClientEntrypoint implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		//? if <26.4 && >1.21.2
		net.minecraft.client.renderer.entity.EntityRenderers.register(ModEntityTypes.ICE_BALL, ThrownItemRenderer::new);
		//? if =1.21.1
		//net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(ModEntityTypes.ICE_BALL, ThrownItemRenderer::new);
	}
}
//?}