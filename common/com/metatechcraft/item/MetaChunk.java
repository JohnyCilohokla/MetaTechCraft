package com.metatechcraft.item;

import java.util.List;

import com.metatechcraft.lib.ModInfo;
import com.metatechcraft.mod.MetaTechCraft;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaChunk extends Item {

	private static final String[] CHUNK_NAMES = new String[] { "White", "Black", "Red", "Green", "Blue" };
	private static final int CHUNK_NUMBER = MetaChunk.CHUNK_NAMES.length - 1;

	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public MetaChunk(int id) {
		super(id);
		setUnlocalizedName("Meta Chunk");
		LanguageRegistry.addName(this, "Meta Chunk");
		setHasSubtypes(true);
		this.maxStackSize = 64;
		setCreativeTab(MetaTechCraft.tabs);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {

		int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, MetaChunk.CHUNK_NUMBER);
		return super.getUnlocalizedName() + MetaChunk.CHUNK_NAMES[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Gets an icon index based on an item's damage value
	 */
	public Icon getIconFromDamage(int meta) {

		int j = MathHelper.clamp_int(meta, 0, MetaChunk.CHUNK_NUMBER);
		return this.icons[j];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {

		this.icons = new Icon[MetaChunk.CHUNK_NAMES.length];

		for (int i = 0; i < MetaChunk.CHUNK_NAMES.length; ++i) {
			this.icons[i] = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + "chunk/meta" + MetaChunk.CHUNK_NAMES[i]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {

		int meta = MathHelper.clamp_int(stack.getItemDamage(), 0, MetaChunk.CHUNK_NUMBER);

		if (meta <= MetaChunk.CHUNK_NUMBER) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getItemDisplayName(ItemStack itemStack) {

		int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, MetaChunk.CHUNK_NUMBER);

		switch (meta) {
		case 0:
			return EnumChatFormatting.AQUA + "Meta Chunk";
		case 1:
			return EnumChatFormatting.DARK_GRAY + "Meta Chunk";
		case 2:
			return EnumChatFormatting.RED + "Meta Chunk";
		case 3:
			return EnumChatFormatting.GREEN + "Meta Chunk";
		case 4:
			return EnumChatFormatting.BLUE + "Meta Chunk";
		default:
			return EnumChatFormatting.WHITE + "Meta Chunk(undefined?)";
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
		for (int meta = 0; meta < (MetaChunk.CHUNK_NUMBER + 1); meta++) {
			list.add(new ItemStack(id, 1, meta));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World par2World, EntityPlayer player) {
		if (player.isSneaking()) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
		return super.onItemRightClick(itemStack, par2World, player);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8,
			float par9, float par10) {
		/*
				if (itemStack.getItemDamage() == 2) {
					if ((par2EntityPlayer.ridingEntity == null) && (par2EntityPlayer.riddenByEntity == null) && ((par2EntityPlayer instanceof EntityPlayerMP))) {
						EntityPlayerMP thePlayer = (EntityPlayerMP) par2EntityPlayer;
						if (thePlayer.timeUntilPortal > 0) {
							thePlayer.timeUntilPortal = 10;
						} else if (thePlayer.dimension != MetaTechCraft.metaDimID) {
							thePlayer.timeUntilPortal = 10;
							thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, MetaTechCraft.metaDimID,
									new MetaTeleporter(thePlayer.mcServer.worldServerForDimension(MetaTechCraft.metaDimID)));
						} else {
							thePlayer.timeUntilPortal = 10;
							thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, 0,
									new MetaTeleporter(thePlayer.mcServer.worldServerForDimension(0)));
						}
					}
				} else if (itemStack.getItemDamage() == 1) {
					MetaBlocks.metaPortalBlock.tryToCreatePortal(par3World, par4, par5, par6);
				}
		*/
		// Create new TagCompound
		if (itemStack.getTagCompound() == null) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = itemStack.getTagCompound();
		tag.setBoolean("mtPos", true);
		tag.setInteger("mtDim", par2EntityPlayer.getAge());
		tag.setInteger("mtX", par4);
		tag.setInteger("mtY", par5);
		tag.setInteger("mtZ", par6);
		tag.setInteger("mtDir", par7);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, @SuppressWarnings("rawtypes") List par3List, boolean par4) {

		if (itemStack.getTagCompound() == null) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = itemStack.getTagCompound();
		if (tag.getBoolean("mtPos")) {
			par3List.add("Dim: " + tag.getInteger("mtDim"));
			par3List.add("X: " + tag.getInteger("mtX"));
			par3List.add("Y: " + tag.getInteger("mtY"));
			par3List.add("Z: " + tag.getInteger("mtZ"));
			par3List.add("Dir: " + tag.getInteger("mtDir"));
		}

		super.addInformation(itemStack, par2EntityPlayer, par3List, par4);
	}
}