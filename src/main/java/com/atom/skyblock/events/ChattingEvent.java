package com.atom.skyblock.events;

import com.atom.skyblock.cmd.cmd;
import com.atom.skyblock.ultilidadesfodas.Database;
import org.bukkit.event.player.*;
import com.atom.skyblock.cmd.*;
import com.atom.skyblock.ultilidadesfodas.*;
import org.bukkit.event.*;

public class ChattingEvent implements Listener
{
    @EventHandler
    public void onSim(final AsyncPlayerChatEvent ev) {
        if (cmd.waitingForConf.contains(ev.getPlayer().getUniqueId())) {
            final String upperCase = ev.getMessage().toUpperCase();
            switch (upperCase) {
                case "YES":
                case "SIM": {
                    Database.fullReset();
                    ev.setCancelled(true);
                    cmd.waitingForConf.remove(ev.getPlayer().getUniqueId());
                    ev.getPlayer().sendMessage("§4Very well. Let the fun begin, again.");
                    break;
                }
                case "NO":
                case "NÃO": {
                    ev.setCancelled(true);
                    cmd.waitingForConf.remove(ev.getPlayer().getUniqueId());
                    ev.getPlayer().sendMessage("§aAlright. If you have announced there will be a reset, please make sure to reannounce you're not going to reset anything.");
                    break;
                }
            }
        }
    }
}
