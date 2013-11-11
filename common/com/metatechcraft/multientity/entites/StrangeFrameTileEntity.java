package com.metatechcraft.multientity.entites;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.forgetutorials.lib.FTA;
import com.forgetutorials.lib.network.PacketMultiTileEntity;
import com.forgetutorials.lib.renderers.BlockTessallator;
import com.forgetutorials.lib.renderers.GLDisplayList;
import com.forgetutorials.lib.renderers.VertexRenderer;
import com.forgetutorials.lib.utilities.ItemStackUtilities;
import com.forgetutorials.multientity.InfernosMultiEntity;
import com.forgetutorials.multientity.InfernosMultiEntityType;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class StrangeFrameTileEntity extends InfernosProxyEntityBase {

	public final static String TYPE_NAME = "metatech.StrangeFrameTileEntity";

	@Override
	public String getTypeName() {
		return StrangeFrameTileEntity.TYPE_NAME;
	}

	@Override
	public boolean hasInventory() {
		return false;
	}

	@Override
	public boolean hasLiquids() {
		return false;
	}

	@Override
	public ItemStack getSilkTouchItemStack() {
		ItemStack stack = new ItemStack(FTA.infernosMultiBlock, 1, 3);
		ItemStackUtilities.addStringTag(stack, "MES", getTypeName());
		return stack;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(int fortune) {
		ArrayList<ItemStack> droppedItems = new ArrayList<ItemStack>();
		droppedItems.add(getSilkTouchItemStack());
		return droppedItems;
	}

	public StrangeFrameTileEntity(InfernosMultiEntity entity) {
		super(entity);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
	}

	@Override
	public void addToDescriptionPacket(PacketMultiTileEntity packet) {
	}

	static GLDisplayList frameBoxList[] = new GLDisplayList[7];

	@Override
	public void renderTileEntityAt(double x, double y, double z) {
		/*
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated(x, y, z);
		int facing = this.entity.getFacingInt();
		if (this.frameBoxList[facing] == null) {
			this.frameBoxList[facing] = new GLDisplayList();
		}
		if (!this.frameBoxList[facing].isGenerated()) {
			this.frameBoxList[facing].generate();
			this.frameBoxList[facing].bind();
			MetaTechCraftModels.squareFrame.render(MetaTechCraftModels.boxFrameTexture, facing == 6 ? null : EnumFacing.getFront(facing));
			this.frameBoxList[facing].unbind();
		}
		this.frameBoxList[facing].render();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		*/
	}

	@Override
	public void onBlockPlaced(World world, EntityPlayer player, int side, int direction, int x, int y, int z, float hitX, float hitY, float hitZ, int metadata) {
		if (y > 10) {
			world.setBlock(x, y - 1, z, FTA.infernosMultiBlockID, InfernosMultiEntityType.BASIC.ordinal(), 3);
			InfernosMultiEntity entity = (InfernosMultiEntity) world.getBlockTileEntity(x, y - 1, z);
			entity.newEntity(StrangeFrameTileEntity.TYPE_NAME);
			entity.onBlockPlaced(world, player, side, x, y - 1, z, hitX, hitY, hitZ, metadata);
		}
	}

	public void enableOverlay() {
		OpenGlHelper.setActiveTexture(33986);
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// Minecraft.getMinecraft().getTextureManager().bindTexture(Minecraft.getMinecraft().entityRenderer.locationLightMap);

		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL13.GL_COMBINE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_COMBINE_RGB, GL13.GL_INTERPOLATE); // Perform
																						// a
																						// blend
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE0_RGB, GL13.GL_PREVIOUS);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND0_RGB, GL11.GL_SRC_COLOR); // this
																						// texture's
																						// colors
																						// (stage
																						// 1,
																						// decal)
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE1_RGB, GL11.GL_TEXTURE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND1_RGB, GL11.GL_SRC_COLOR); // result
																						// from
																						// stage
																						// 0
																						// (lit
																						// texture)

		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE2_RGB, GL13.GL_PREVIOUS);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_COMBINE_ALPHA, GL11.GL_ADD);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE0_ALPHA, GL11.GL_TEXTURE); // result
																						// from
																						// stage
																						// 0
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND0_ALPHA, GL11.GL_SRC_ALPHA); // just
																						// use
																						// the
																						// alpha
																						// value
																						// from
																						// stage
																						// 0
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

	}

	public void disableOverlay() {
		OpenGlHelper.setActiveTexture(33986);

		GL11.glDisable(GL11.GL_TEXTURE_2D);

		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	static VertexRenderer v = new VertexRenderer();

	@Override
	public void renderStaticBlockAt(RenderBlocks blockRenderer, int x, int y, int z) {

		Tessellator.instance.draw();
		int facing = this.entity.getFacingInt();
		/*if (this.frameBoxList[facing] == null) {
			this.frameBoxList[facing] = new GLDisplayList();
		}
		if (!this.frameBoxList[facing].isGenerated()) {
			this.frameBoxList[facing].generate();
			this.frameBoxList[facing].bind();
			MetaTechCraftModels.squareFrame.render(MetaTechCraftModels.boxFrameTexture, facing == 6 ? null : EnumFacing.getFront(facing));
			this.frameBoxList[facing].unbind();
		}*/
		// GL11.glPushMatrix();
		// GL11.glPushAttrib(GL11.GL_ENABLE_BIT); // very evil
		// GL11.glDisable(GL11.GL_LIGHTING);
		// GL11.glTranslated(x%16, y%16, z%16);
		// if (frameBoxList[facing] != null) {
		// frameBoxList[facing].render();
		// }
		// GL11.glEnable(GL11.GL_LIGHTING);
		// GL11.glPopAttrib(); // very evil

		GL11.glPushMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
		// GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

		enableOverlay();

		// Tessellator.instance.startDrawingQuads();
		// BlockTessallator.addToTessallator(Tessellator.instance, x, y, z,
		// Block.cactus.getIcon(0, 0));
		// Tessellator.instance.draw();

		BlockTessallator.addToRenderer(StrangeFrameTileEntity.v, blockRenderer, x % 16, y % 16, z % 16, Block.leaves.getIcon(0, 0),
				Block.lavaMoving.getIcon(0, 0));

		// System.out.println(
		StrangeFrameTileEntity.v.render();
		// );

		/*v.addQuadUV(
				varX1, varY1, varZ1, varU1, varV1,
				varX2, varY2, varZ2, varU2, varV2,
				varX3, varY3, varZ3, varU3, varV3,
				varX4, varY4, varZ4, varU4, varV4);*/

		// GL11.glPopAttrib();
		GL11.glPopMatrix();

		// GL11.glPopMatrix();

		// Clean Up

		// FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		// GL11.glDepthMask(true);
		// GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_ALPHA_TEST);
		// GL11.glEnable(GL11.GL_FOG);
		// GL11.glDisable(GL11.GL_LIGHTING);

		disableOverlay();

		Tessellator.instance.startDrawingQuads();
	}

	@Override
	public void tick() {

	}

	@Override
	public void renderItem(ItemRenderType type) {

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		// GL11.glTranslated(x, y, z);
		if (StrangeFrameTileEntity.frameBoxList[0] == null) {
			for (int facing = 0; facing < 7; facing++) {
				if (StrangeFrameTileEntity.frameBoxList[facing] == null) {
					StrangeFrameTileEntity.frameBoxList[facing] = new GLDisplayList();
				}
				if (!StrangeFrameTileEntity.frameBoxList[facing].isGenerated()) {
					StrangeFrameTileEntity.frameBoxList[facing].generate();
					StrangeFrameTileEntity.frameBoxList[facing].bind();
					Tessellator.instance.startDrawingQuads();
					BlockTessallator.addToTessallator(Tessellator.instance, 0, 0, 0, Block.cactus.getIcon(0, 0));
					FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
					Tessellator.instance.draw();
					// MetaTechCraftModels.squareFrame.render(MetaTechCraftModels.boxFrameTexture,
					// facing == 6 ? null : EnumFacing.getFront(facing));
					StrangeFrameTileEntity.frameBoxList[facing].unbind();
				}
			}
		}
		int facing = 6;
		StrangeFrameTileEntity.frameBoxList[facing].render();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();

	}

}