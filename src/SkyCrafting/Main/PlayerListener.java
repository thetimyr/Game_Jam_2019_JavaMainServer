package SkyCrafting.Main;

import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.util.Vector;

import java.util.*;
import org.bukkit.block.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.event.block.*;
import org.bukkit.event.weather.*;
import org.bukkit.event.inventory.*;
import org.bukkit.potion.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener
{
    Map<String, Boolean> lineIsOut;
    Map<String, Fish> hookDest;
    Map<String, PlayerFishEvent> fishEventMap;
    ArrayList<Integer> noFallEntities;
    boolean teleportHooked;
    
    public PlayerListener() {
        this.lineIsOut = new HashMap<String, Boolean>();
        this.hookDest = new HashMap<String, Fish>();
        this.fishEventMap = new HashMap<String, PlayerFishEvent>();
        this.noFallEntities = new ArrayList<Integer>();
        this.teleportHooked = false;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(final EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && this.noFallEntities.contains(event.getEntity().getEntityId())) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void throwHook(final ProjectileLaunchEvent event) {
        final Fish hook;
        if (event.getEntity() instanceof Fish && (hook = (Fish)event.getEntity()).getShooter() != null && hook.getShooter() instanceof Player) {
            final Player player = (Player)hook.getShooter();
            final String itemd = player.getItemInHand().getType().toString();
            if (itemd.equals("FISHING_ROD")) {
                this.setLineOut(player, true);
                this.hookDest.put(player.getName(), hook);
            }
        }
    }
    
    public void setLineOut(final Player player, final Boolean b) {
        this.lineIsOut.put(player.getName(), b);
    }
    
    public Boolean getLineOut(final Player player) {
        if (this.lineIsOut.containsKey(player.getName())) {
            return this.lineIsOut.get(player.getName());
        }
        return false;
    }
    
    public void setFishEvent(final Player player, final PlayerFishEvent e) {
        this.fishEventMap.put(player.getName(), e);
    }
    
    public PlayerFishEvent getFishEvent(final Player player) {
        if (this.fishEventMap.containsKey(player.getName())) {
            return this.fishEventMap.get(player.getName());
        }
        return null;
    }
    
    @EventHandler
    public void onConsume(final PlayerItemConsumeEvent e) {
        final Player player = e.getPlayer();
        if (e.getItem().getTypeId() == 373) {
            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin)Main.instance, (Runnable)new Runnable() {
                @Override
                public void run() {
                    player.setItemInHand(new ItemStack(Material.AIR));
                }
            }, 1L);
        }
    }
    
    @EventHandler
    public void fishEvent(final PlayerFishEvent event) {
        final Player player = event.getPlayer();
        final String itemd = player.getItemInHand().getType().toString();
        if (itemd.equals("FISHING_ROD")) {
            if (event.getState() == PlayerFishEvent.State.IN_GROUND) {
                if (this.hookDest.get(player.getName()) == null) {
                    return;
                }
                final Location loc = this.hookDest.get(player.getName()).getLocation();
                if (this.teleportHooked) {
                    loc.setPitch(player.getLocation().getPitch());
                    loc.setYaw(player.getLocation().getYaw());
                    player.teleport(loc);
                }
                else {
                    for (final Entity ent : event.getHook().getNearbyEntities(1.5, 1.0, 1.5)) {
                        if (ent instanceof Item) {
                            final PlayerGrappleEvent e = new PlayerGrappleEvent(player, ent, player.getLocation());
                            Main.instance.getServer().getPluginManager().callEvent((Event)e);
                            return;
                        }
                    }
                    this.pullEntityToLocation((Entity)player, loc);
                }
                player.getWorld().playSound(loc, Sound.MAGMACUBE_JUMP, 10.0f, 1.0f);
            }
            else if (event.getState() == PlayerFishEvent.State.FAILED_ATTEMPT) {
                Location loc = this.hookDest.get(player.getName()).getLocation();
                final Block toPort = loc.getBlock().getRelative(BlockFace.DOWN);
                if (toPort.getType() != Material.AIR && toPort.getType() != Material.WATER && toPort.getType() != Material.STATIONARY_WATER) {
                    if (this.teleportHooked) {
                        loc = toPort.getLocation();
                        loc.setPitch(player.getLocation().getPitch());
                        loc.setYaw(player.getLocation().getYaw());
                        player.getWorld().playSound(loc, Sound.MAGMACUBE_JUMP, 10.0f, 1.0f);
                        player.teleport(loc);
                    }
                    else {
                        this.pullEntityToLocation((Entity)player, toPort.getLocation());
                    }
                }
            }
            else if (event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {
                if (!this.teleportHooked) {
                    event.getCaught().setVelocity(new Vector(0, 0, 0));
                    this.pullEntityToLocation(event.getCaught(), player.getLocation());
                }
                else {
                    event.getCaught().setVelocity(new Vector(0, 0, 0));
                    event.getCaught().teleport(player.getLocation());
                }
            }
            if (event.getState() != PlayerFishEvent.State.FISHING) {
                this.setLineOut(player, false);
                this.setFishEvent(player, null);
            }
            else {
                this.setFishEvent(player, event);
            }
        }
    }
    
    @EventHandler
    public void onPlayerFish(final PlayerFishEvent event) {
        final Item item;
        final ItemStack is;
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH && event.getCaught().getType() == EntityType.DROPPED_ITEM && ((is = (item = (Item)event.getCaught()).getItemStack()).getType() == Material.ENCHANTED_BOOK || is.getType() == Material.LEATHER_BOOTS || is.getType() == Material.FISHING_ROD || is.getType() == Material.RAW_FISH || is.getType() == Material.BOW || is.getType() == Material.BONE || is.getType() == Material.SADDLE || is.getType() == Material.TRIPWIRE_HOOK || is.getType() == Material.WATER_LILY || is.getType() == Material.BOWL || is.getType() == Material.LEATHER || is.getType() == Material.ROTTEN_FLESH || is.getType() == Material.STICK || is.getType() == Material.STRING || is.getType() == Material.POTION || is.getType() == Material.INK_SACK)) {
            item.setItemStack(new ItemStack(Material.AIR));
            item.remove();
        }
    }
    
    private void pullEntityToLocation(final Entity e, final Location loc) {
        final Location entityLoc = e.getLocation();
        final Vector velocity = loc.toVector().subtract(entityLoc.subtract(0.0, 1.0, 0.0).toVector()).normalize().multiply(new Vector(2, 2, 2));
        if (Math.abs(loc.getBlockY() - entityLoc.getBlockY()) < 2 && loc.distance(entityLoc) > 4.0) {
            e.setVelocity(velocity.multiply(new Vector(1, 1, 1)));
            Main.instance.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.instance, (Runnable)new Runnable() {
                @Override
                public void run() {
                    e.setVelocity(velocity.multiply(new Vector(1, 1, 1)));
                }
            }, 1L);
        }
        else {
            e.setVelocity(velocity);
            Main.instance.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.instance, (Runnable)new Runnable() {
                @Override
                public void run() {
                    e.setVelocity(velocity.multiply(new Vector(1, 1, 1)));
                }
            }, 0L);
        }
        this.addNoFall(e, 100);
    }
    
    public void addNoFall(final Entity e, final int ticks) {
        final Integer id = new Integer(e.getEntityId());
        if (!this.noFallEntities.contains(id)) {
            this.noFallEntities.add(id);
        }
        Main.instance.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.instance, (Runnable)new Runnable() {
            @Override
            public void run() {
                if (PlayerListener.this.noFallEntities.contains(id)) {
                    PlayerListener.this.noFallEntities.remove(id);
                }
            }
        }, (long)ticks);
    }
    
    @EventHandler
    public void onPlayerDropItem(final PlayerDropItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        final ArrayList<Material> nodrop = new ArrayList<Material>();
        nodrop.add(Material.PAPER);
        nodrop.add(Material.QUARTZ);
        nodrop.add(Material.INK_SACK);
        nodrop.add(Material.RECORD_4);
        nodrop.add(Material.RECORD_7);
        nodrop.add(Material.BONE);
        nodrop.add(Material.FIREBALL);
        nodrop.add(Material.RECORD_10);
        nodrop.add(Material.RECORD_11);
        nodrop.add(Material.CAKE);
        nodrop.add(Material.SHEARS);
        nodrop.add(Material.IRON_SWORD);
        nodrop.add(Material.WOOD_SWORD);
        nodrop.add(Material.STONE_SWORD);
        nodrop.add(Material.DIAMOND_SWORD);
        nodrop.add(Material.GOLD_SWORD);
        nodrop.add(Material.COAL);
        nodrop.add(Material.NETHER_STAR);
        nodrop.add(Material.STICK);
        nodrop.add(Material.SADDLE);
        final Player p = e.getPlayer();
        for (final Material q : nodrop) {
            if (e.getItemDrop().getItemStack().getType() == q) {
                e.setCancelled(true);
                p.sendTitle("§c","§cВы не можете это выкинуть!");
            }
        }
    }
    @EventHandler
    public void onDeath(final PlayerDeathEvent e) {
        e.setKeepInventory(true);
        e.getEntity().closeInventory();
        if (e.getEntity().getItemOnCursor() != null && !e.getEntity().getItemOnCursor().getType().equals((Object)Material.AIR)) {
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), e.getEntity().getItemOnCursor());
            e.getEntity().getItemOnCursor().setType(Material.AIR);
        }
        if (e.getEntity().getEquipment().getBoots() != null) {
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), e.getEntity().getEquipment().getBoots());
            e.getEntity().getEquipment().setBoots(new ItemStack(Material.AIR));
        }
        if (e.getEntity().getEquipment().getHelmet() != null && !e.getEntity().getEquipment().getHelmet().getType().equals((Object)Material.CARPET) && !e.getEntity().getEquipment().getHelmet().getType().equals((Object)Material.IRON_ORE)) {
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), e.getEntity().getEquipment().getHelmet());
            e.getEntity().getEquipment().setHelmet(new ItemStack(Material.AIR));
        }
        /*if (e.getEntity().getEquipment().getHelmet() != null && !e.getEntity().getEquipment().getHelmet().getType().equals((Object)Material.SAPLING)) {
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), e.getEntity().getEquipment().getHelmet());
            e.getEntity().getEquipment().setHelmet(new ItemStack(Material.AIR));
        }*/
        if (e.getEntity().getEquipment().getChestplate() != null) {
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), e.getEntity().getEquipment().getChestplate());
            e.getEntity().getEquipment().setChestplate(new ItemStack(Material.AIR));
        }
        if (e.getEntity().getEquipment().getLeggings() != null) {
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), e.getEntity().getEquipment().getLeggings());
            e.getEntity().getEquipment().setLeggings(new ItemStack(Material.AIR));
        }
        for (final ItemStack is : e.getEntity().getInventory()) {
            if (is != null && !is.getType().equals((Object)Material.AIR)) {
                if (!is.hasItemMeta()) {
                    e.getEntity().getInventory().remove(is);
                    e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), is);
                }
                else {
                    if (!is.hasItemMeta()) {
                        continue;
                    }
                    if (is.getItemMeta().getLore() != null) {
                        if (is.getItemMeta().getLore().contains("nodrop")) {
                            continue;
                        }
                        e.getEntity().getInventory().remove(is);
                        e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), is);
                    }
                    else {
                        e.getEntity().getInventory().remove(is);
                        e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), is);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onFallDamage(final EntityDamageEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final Player player = (Player)e.getEntity();
        if (player.getWorld().getName().equals("starwars") && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onCraftItem(final CraftItemEvent e) {
        if (e.getClickedInventory().getType() == InventoryType.CRAFTING && e.getClickedInventory().getSize() == 5) {
            e.setCancelled(true);
        }
    }
  
    @EventHandler
    public void AntiPickUp(final PlayerArmorStandManipulateEvent e) {
        final ArmorStand en = e.getRightClicked();
        if (en.getName().contains("Saber")) {
            e.setCancelled(true);
        }
    }
  
    
    
    @EventHandler
    public void onPlace(final BlockBurnEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockIgnite(final BlockIgniteEvent event) {
        event.setCancelled(true);
    }
    
    public void cher(final ItemStack item1, final Iterator<ItemStack> it, final ArrayList<ItemStack> item, final Material mat) {
        if (item1.getType() == mat) {
            item.add(item1);
            it.remove();
        }
    }
    
    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        final ArrayList<ItemStack> item = new ArrayList<ItemStack>();
        final Iterator<ItemStack> it = event.getDrops().iterator();
        while (it.hasNext()) {
            final ItemStack item2 = it.next();
            this.cher(item2, it, item, Material.AIR);
            this.cher(item2, it, item, Material.FISHING_ROD);
            if (item2.getType() == Material.SAPLING && item2.getDurability() == 2) {
                item.add(item2);
                it.remove();
            }
            if (item2.getType() == Material.CARPET) {
                item.add(item2);
                it.remove();
            }
        }
        if (!item.isEmpty()) {
        	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
        	Player player;
                
                @Override
                public void run() {
                    for (final ItemStack itemes : item) {
                        if (itemes.getType() == Material.AIR) {
                            this.player.getInventory().setItem(0, itemes);
                        }
                        else if (itemes.getType() == Material.FISHING_ROD) {
                            this.player.getInventory().addItem(new ItemStack[] { itemes });
                        }
                        else if (itemes.getType() == Material.SAPLING) {
                            this.player.getInventory().setHelmet(itemes);
                        }
                        else {
                            if (itemes.getType() != Material.CARPET) {
                                continue;
                            }
                            this.player.getInventory().setHelmet(itemes);
                        }
                    }
                }
            });
        }
    }
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase(ChatColor.AQUA + "Казино Вулкан")) {
            final Player p = (Player)e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 14) {
                p.closeInventory();
            }
        }
        else if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase(ChatColor.DARK_AQUA + "Игра началась")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
        }
    }
    
    @EventHandler
    public void onDenyOpenTables(final PlayerInteractEvent e) {
        if (e.getPlayer().getItemInHand().getType() == Material.RABBIT_HIDE) {
            return;
        }
        if (!e.hasBlock()) {
            return;
        }
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        final Player p = e.getPlayer();
        final Material type = e.getClickedBlock().getType();
        if (type == Material.EMERALD) {
            return;
        }
        if (type == Material.ENCHANTMENT_TABLE ||
        	type == Material.FURNACE ||
        	type == Material.JUKEBOX ||
        	type == Material.DROPPER ||
        	type == Material.DISPENSER ||
        	type == Material.HOPPER ||
        	type == Material.ANVIL ||
        	type == Material.WORKBENCH)
            {
            p.sendMessage("§4Вы кто ?");
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerFish1(final WeatherChangeEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void zel(final PlayerItemConsumeEvent e) {
        final Player p = e.getPlayer();
        p.getInventory().remove(new ItemStack(Material.GLASS_BOTTLE));
    }
    
    @EventHandler
    public void onInventoryOpen(final InventoryOpenEvent e) {
        if (e.getInventory().getType() == InventoryType.FURNACE || e.getInventory().getType() == InventoryType.ENCHANTING) {
            e.setCancelled(true);
        }
    }
    
    private void addDeathEffects(final Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 400, 1));
    }
    
    @EventHandler
    public void Respawn(final PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        this.addDeathEffects(p);
    }
    
    @EventHandler
    public void onMobClearExperience(final EntityDeathEvent e) {
        e.setDroppedExp(0);
    }
    
    @EventHandler
    public void onWorldChange(final PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        if (player.getWorld().getName().equals("starwars")) {
            final PotionEffectType type = PotionEffectType.JUMP;
            final PotionEffect effect = new PotionEffect(type, 999999999, 4, false, false);
            player.addPotionEffect(effect);
        }
        else {
            final PotionEffectType type = PotionEffectType.JUMP;
            player.removePotionEffect(type);
        }
    }
    
    
    @EventHandler
    public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile) {
            final Projectile bullet = (Projectile)event.getDamager();
            if (!event.isCancelled()) {
                if (bullet.hasMetadata("damage")) {
                    event.setDamage(bullet.getMetadata("damage").get(0).asDouble());
                }
                if (bullet.hasMetadata("force")) {
                    event.getEntity().setVelocity(event.getDamager().getLocation().getDirection().multiply(-1.0 * bullet.getMetadata("force").get(0).asDouble()));
                    event.getEntity().setVelocity(event.getDamager().getVelocity().multiply(bullet.getMetadata("force").get(0).asDouble()));
                }
            }
        }
    }
    
    @EventHandler
    public void noGrenadePickup(final PlayerPickupItemEvent event) {
        final ItemStack is = event.getItem().getItemStack();
        if (is.getType() == Material.SLIME_BALL && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().equalsIgnoreCase("clone_grenade")) {
            event.setCancelled(true);
        }
    }
}
