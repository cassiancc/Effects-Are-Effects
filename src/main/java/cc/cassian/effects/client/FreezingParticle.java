//? if <26.4 {
package cc.cassian.effects.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
//? if >=26 {
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.RandomSource;
//?} else {
/*import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
*///?}

//? if >=26 {
public class FreezingParticle extends SingleQuadParticle {
//?} else {
/*public class FreezingParticle extends TextureSheetParticle {
*///?}
	private final float rotSpeed;
	private boolean landed;

	//? if >=26 {
	protected FreezingParticle(final ClientLevel level, final double x, final double y, final double z, final TextureAtlasSprite sprite) {
		super(level, x, y, z, 0.0, 0.0, 0.0, sprite);
	//?} else {
	/*protected FreezingParticle(final ClientLevel level, final double x, final double y, final double z) {
		super(level, x, y, z, 0.0, 0.0, 0.0);
	*///?}
		this.xd = (this.random.nextFloat() * 2.0F - 1.0F) * 0.01F;
		this.yd = (this.random.nextFloat() * 2.0F - 1.0F) * 0.01F * 0.25F;
		this.zd = (this.random.nextFloat() * 2.0F - 1.0F) * 0.01F;
		this.gravity = 0.125F;
		this.friction = 1.0F;
		this.quadSize *= 0.80999994F;
		int baseLifetime = (int) (32.0 / (this.random.nextFloat() * 0.8 + 0.2));
		this.lifetime = (int) Math.max(baseLifetime * 0.9F, 10.0F);
		this.rotSpeed = (this.random.nextFloat() - 0.5F) * 0.1F;
		this.roll = this.random.nextFloat() * (float) (Math.PI * 2);
	}

	//? if >=26 {
	@Override
	public SingleQuadParticle.Layer getLayer() {
		return SingleQuadParticle.Layer.OPAQUE;
	}
	//?} else {
	/*@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}
	*///?}

	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
			return;
		}

		this.oRoll = this.roll;
		this.roll += (float) Math.PI * this.rotSpeed * 2.0F * (float) Math.exp(this.age / -8.0F);
		if (this.onGround && !this.landed) {
			this.lifetime = this.age + 20;
			this.landed = true;
		}

		this.move(this.xd, this.yd, this.zd);
		this.yd -= 0.003F;
		this.yd = Math.max(this.yd, -0.14F);
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(final SpriteSet sprites) {
			this.sprites = sprites;
		}

		@Override
		//? if >=26 {
		public Particle createParticle(final SimpleParticleType options, final ClientLevel level, final double x, final double y, final double z,
									   final double xAux, final double yAux, final double zAux, final RandomSource random) {
			return new FreezingParticle(level, x, y, z, this.sprites.get(random));
		}
		//?} else {
		/*public Particle createParticle(final SimpleParticleType options, final ClientLevel level, final double x, final double y, final double z,
									   final double xAux, final double yAux, final double zAux) {
			FreezingParticle particle = new FreezingParticle(level, x, y, z);
			particle.pickSprite(this.sprites);
			return particle;
		}
		*///?}
	}
}
//?}
