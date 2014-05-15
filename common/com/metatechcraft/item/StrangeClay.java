package com.metatechcraft.item;

import com.forgetutorials.lib.utilities.ItemWithInfo;
import com.forgetutorials.multientity.InfernosMultiEntityStatic;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;
import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StrangeClay extends ItemWithInfo {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public StrangeClay() {
		super();
		MetaTechCraft.registry.registerItem(this, "StrangeClay", "Strange Clay");
		setCreativeTab(MetaTechCraft.tabs);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "strangeClay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {

		Block block = world.getBlock(x, y, z);
		int blockMeta = world.getBlockMetadata(x, y, z);
		TileEntity entity = world.getTileEntity(x, y, z);
		InfernosProxyEntityBase proxy = null;
		if (entity instanceof InfernosMultiEntityStatic) {
			proxy = ((InfernosMultiEntityStatic) entity).getProxyEntity();
		}

		for (int i = 1; i < 50; i++) {
			world.setBlock(x, y + i, z, block, blockMeta, 3);
			if (proxy != null) {
				InfernosMultiEntityStatic newEntity = (InfernosMultiEntityStatic) world.getTileEntity(x, y + i, z);
				newEntity.newEntity(proxy.getTypeName());
				newEntity.onBlockPlaced(world, par2EntityPlayer, side, x, y + i, z, hitX, hitY, hitZ, blockMeta);
			} else if (entity != null) {
				NBTTagCompound par1NBTTagCompound = new NBTTagCompound();
				entity.writeToNBT(par1NBTTagCompound);
				world.getTileEntity(x, y + i, z).readFromNBT(par1NBTTagCompound);
			}

		}

		return super.onItemUse(par1ItemStack, par2EntityPlayer, world, x, y, z, side, hitX, hitY, hitZ);
	}
}