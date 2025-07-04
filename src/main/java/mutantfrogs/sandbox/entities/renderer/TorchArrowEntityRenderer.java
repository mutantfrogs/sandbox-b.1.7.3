package mutantfrogs.sandbox.entities.renderer;

import mutantfrogs.sandbox.entities.TorchArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class TorchArrowEntityRenderer extends EntityRenderer {
	public void render(Entity arrowEntity, double d, double e, double f, float g, float h) {
		if (arrowEntity.prevYaw != 0.0F || arrowEntity.prevPitch != 0.0F) {
			this.bindTexture("/item/arrows.png");
			GL11.glPushMatrix();
			GL11.glTranslatef((float)d, (float)e, (float)f);
			GL11.glRotatef(arrowEntity.prevYaw + (arrowEntity.yaw - arrowEntity.prevYaw) * h - 90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(arrowEntity.prevPitch + (arrowEntity.pitch - arrowEntity.prevPitch) * h, 0.0F, 0.0F, 1.0F);
			Tessellator var10 = Tessellator.INSTANCE;
			byte var11 = 0;
			float var12 = 0.0F;
			float var13 = 0.5F;
			float var14 = (float)(0 + var11 * 10) / 32.0F;
			float var15 = (float)(5 + var11 * 10) / 32.0F;
			float var16 = 0.0F;
			float var17 = 0.15625F;
			float var18 = (float)(5 + var11 * 10) / 32.0F;
			float var19 = (float)(10 + var11 * 10) / 32.0F;
			float var20 = 0.05625F;
			GL11.glEnable(32826);

			GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(var20, var20, var20);
			GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
			GL11.glNormal3f(var20, 0.0F, 0.0F);
			var10.startQuads();
			var10.vertex(-7.0, -2.0, -2.0, (double)var16, (double)var18);
			var10.vertex(-7.0, -2.0, 2.0, (double)var17, (double)var18);
			var10.vertex(-7.0, 2.0, 2.0, (double)var17, (double)var19);
			var10.vertex(-7.0, 2.0, -2.0, (double)var16, (double)var19);
			var10.draw();
			GL11.glNormal3f(-var20, 0.0F, 0.0F);
			var10.startQuads();
			var10.vertex(-7.0, 2.0, -2.0, (double)var16, (double)var18);
			var10.vertex(-7.0, 2.0, 2.0, (double)var17, (double)var18);
			var10.vertex(-7.0, -2.0, 2.0, (double)var17, (double)var19);
			var10.vertex(-7.0, -2.0, -2.0, (double)var16, (double)var19);
			var10.draw();

			for (int var23 = 0; var23 < 4; var23++) {
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glNormal3f(0.0F, 0.0F, var20);
				var10.startQuads();
				var10.vertex(-8.0, -2.0, 0.0, (double)var12, (double)var14);
				var10.vertex(8.0, -2.0, 0.0, (double)var13, (double)var14);
				var10.vertex(8.0, 2.0, 0.0, (double)var13, (double)var15);
				var10.vertex(-8.0, 2.0, 0.0, (double)var12, (double)var15);
				var10.draw();
			}

			GL11.glDisable(32826);
			GL11.glPopMatrix();
		}
	}
}
