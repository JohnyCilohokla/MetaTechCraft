package com.metatechcraft.multientity.entites;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import com.forgetutorials.lib.FTA;
import com.forgetutorials.lib.dimension.utilities.BlockWithMetadata;
import com.forgetutorials.lib.dimension.utilities.IncrementalBlock;
import com.forgetutorials.lib.dimension.utilities.IncrementalItemStack;
import com.forgetutorials.lib.dimension.utilities.IncrementalMap;
import com.forgetutorials.lib.network.PacketMultiTileEntity;
import com.forgetutorials.lib.network.SubPacketTileEntityCustom;
import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.lib.renderers.BlockTessallator;
import com.forgetutorials.lib.renderers.GLDisplayList;
import com.forgetutorials.lib.renderers.VertexRenderer;
import com.forgetutorials.multientity.InfernosMultiEntityStatic;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;
import com.metatechcraft.mod.MetaTechCraft;

import cpw.mods.fml.common.network.ByteBufUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class MetaMiner extends InfernosProxyEntityBase {

	public final static String TYPE_NAME = "metatech.MetaMiner";

	@Override
	public String getTypeName() {
		return MetaMiner.TYPE_NAME;
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
	public boolean isDynamiclyRendered() {
		return false;
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(int fortune) {
		ArrayList<ItemStack> droppedItems = new ArrayList<ItemStack>();
		droppedItems.add(getSilkTouchItemStack());
		return droppedItems;
	}

	public MetaMiner(InfernosMultiEntityStatic entity) {
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

	static long i = 0;

	@Override
	public void addToDescriptionPacket(PacketMultiTileEntity packet) {
		MetaMiner.i++;
		System.out.println(MetaMiner.i);
	}

	static GLDisplayList frameBoxList[] = new GLDisplayList[7];

	@Override
	public void renderTileEntityAt(double x, double y, double z) {
	}

	@Override
	public void onBlockPlaced(World world, EntityPlayer player, int side, int direction, int x, int y, int z, float hitX, float hitY, float hitZ, int metadata) {
	}
	
	@Override
	public void onBlockActivated(EntityPlayer entityplayer, World world, int x, int y, int z, int par1, float par2, float par3, float par4) {
		entityplayer.openGui(MetaTechCraft.instance, 0, world, x, y, z);

		if (this.entity.getWorldObj().isRemote) {
			return;
		}
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeInt(1);//Packet 1
		IncrementalMap<IncrementalItemStack> itemStackCountMap = FTA.serverHandler.getWorldGlobals(world).getDropsMap();
		Set<IncrementalItemStack> set = itemStackCountMap.getList();
		buffer.writeInt(set.size());
		for (IncrementalItemStack b : set) {
			buffer.writeLong(b.current);
			ByteBufUtils.writeItemStack(buffer, b.stack);
		}

		IncrementalMap<IncrementalBlock> itemStackCountMap2 = FTA.serverHandler.getWorldGlobals(world).getBlocksMap();
		Set<IncrementalBlock> set2 = itemStackCountMap2.getList();
		buffer.writeInt(set2.size());
		for (IncrementalBlock b : set2) {
			buffer.writeLong(b.current);
			buffer.writeInt(Block.getIdFromBlock(b.block.block));
			buffer.writeInt(b.block.metadata);
		}
		
		
		IncrementalMap<IncrementalItemStack> pickCountMap = FTA.serverHandler.getWorldGlobals(world).getPickMap();
		Set<IncrementalItemStack> set3 = pickCountMap.getList();
		buffer.writeInt(set3.size());
		for (IncrementalItemStack b : set3) {
			buffer.writeLong(b.current);
			ByteBufUtils.writeItemStack(buffer, b.stack);
		}
		

		PacketMultiTileEntity packet = new PacketMultiTileEntity(this.entity.xCoord, this.entity.yCoord, this.entity.zCoord, this.entity.getRawSide(), getTypeName());
			packet.addPacket(new SubPacketTileEntityCustom(buffer));
		
		FTA.packetHandler.sendTo(packet, (EntityPlayerMP) entityplayer);
		FTA.out("onBlockActivated >> Packet Sent");
	}
	
	public ArrayList<IncrementalItemStack> stacks = new ArrayList<IncrementalItemStack>();
	public ArrayList<IncrementalBlock> blocks = new ArrayList<IncrementalBlock>();
	public ArrayList<IncrementalItemStack> picks = new ArrayList<IncrementalItemStack>();
	
	@Override
	public void onDataReceived(ByteBuf data) {
		FTA.out("onDataReceived(ByteBuf data)");
		int type = data.readInt();
		if (type==1){
			stacks.clear();
			int count = data.readInt();
			for (int i=0;i<count;i++){
				long amount = data.readLong();
				ItemStack stack = ByteBufUtils.readItemStack(data);
				stacks.add(new IncrementalItemStack(stack, amount));
			}
			
			int count2 = data.readInt();
			FTA.out("?? "+ count2);
			blocks.clear();
			for (int i=0;i<count2;i++){
				long amount = data.readLong();
				Block block = Block.getBlockById(data.readInt());
				int metadata = data.readInt();
				
				blocks.add(new IncrementalBlock(new BlockWithMetadata(block, metadata),amount));
			}
			
			int count3 = data.readInt();
			picks.clear();
			for (int i=0;i<count3;i++){
				long amount = data.readLong();
				ItemStack stack = ByteBufUtils.readItemStack(data);
				picks.add(new IncrementalItemStack(stack, amount));
			}
		}

		FTA.out("-----------------------------------------------------");
		for (IncrementalItemStack b : stacks) {
			 FTA.out(b.toString());
		}
		FTA.out("-----------------------------------------------------");
	}

	static VertexRenderer v = new VertexRenderer();

	@Override
	public void renderStaticBlockAt(RenderBlocks blockRenderer, int x, int y, int z) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		BlockTessallator.enableOverlay();

		IIcon icon = InfernosRegisteryProxyEntity.INSTANCE.getIcon("MetaTechCraft".toLowerCase() + ":overlay/creeper");
		BlockTessallator.addToRenderer(this.getBlock(), MetaMiner.v, blockRenderer, (x % 16) < 0 ? (16 + x % 16) : (x % 16), (y % 16) < 0 ? (16 + y % 16)
				: (y % 16), (z % 16) < 0 ? (16 + z % 16) : (z % 16), icon, Blocks.lava.getIcon(0, 0), x, y, z);

		MetaMiner.v.render();

		BlockTessallator.disableOverlay();
	}

	public int tick = 0;

	Random rng = new Random();
	static int pos = 0;
	static int dist = 1;
	static int size = 1;
	static int count = 1;

	@Override
	public void tick() {
		if (this.entity.getWorldObj().isRemote) {
			return;
		}
		TileEntity ent = this.entity.getWorldObj().getTileEntity(this.entity.xCoord, this.entity.yCoord+1, this.entity.zCoord);
		if (ent!=null){
			ent.updateEntity();
			ent.updateEntity();
			ent.updateEntity();
			ent.updateEntity();
			ent.updateEntity();
			ent.updateEntity();
			ent.updateEntity();
			ent.updateEntity();
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object[] data) {
		IIcon icon = InfernosRegisteryProxyEntity.INSTANCE.getIcon("MetaTechCraft".toLowerCase() + ":overlay/creeper");
		IIcon icon2 = Blocks.lava.getIcon(0, 0);
		GL11.glPushMatrix();
		BlockTessallator.renderDualTextureBlockAsItem(v, this.getBlock(), type, icon, icon2, data);
		GL11.glPopMatrix();
	}

}