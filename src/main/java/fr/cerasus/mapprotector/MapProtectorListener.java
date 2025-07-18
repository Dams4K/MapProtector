package fr.cerasus.mapprotector;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class MapProtectorListener implements Listener {
    public static final String MP_METADATA_KEY = "mpPlayer";

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        Block block = event.getBlock();
        World world = block.getWorld();
        if (!MapProtector.PROTECTED_CONFIG.isProtected(world)) return;

        block.setMetadata(MP_METADATA_KEY, new FixedMetadataValue(MapProtector.INSTANCE, event.getPlayer()));
    }

    @EventHandler
    public void onBlockBroke(BlockBreakEvent event) {
        Block block = event.getBlock();
        World world = event.getBlock().getWorld();
        if (!MapProtector.PROTECTED_CONFIG.isProtected(world)) return;

        event.setCancelled(!block.hasMetadata(MP_METADATA_KEY));
    }

    @EventHandler
    public void onBlockUproot(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        World world = block.getWorld();
        if (!MapProtector.PROTECTED_CONFIG.isProtected(world)) return;

        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {
            event.setCancelled(true);
        }
    }
}
