package com.metatechcraft.multientity;

import org.lwjgl.opengl.GL11;

import com.metatechcraft.models.ModelFrameBox;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class InfernosMultiRenderer extends TileEntitySpecialRenderer {

	private ModelFrameBox frameBox = new ModelFrameBox();
	private final RenderItem customRenderItem;

	public InfernosMultiRenderer() {
		this.customRenderItem = new RenderItem() {

			@Override
			public boolean shouldBob() {

				return false;
			};
		};
		this.customRenderItem.setRenderManager(RenderManager.instance);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		//Tessellator tessellator = Tessellator.instance;

		if (tileentity instanceof InfernosMultiEntity) {
			InfernosMultiEntity tile = (InfernosMultiEntity) tileentity;
			tile.renderTileEntityAt(f);

			/*GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glTranslated(x, y, z);

			this.frameBox.render();

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();*/
		}
	}

}
