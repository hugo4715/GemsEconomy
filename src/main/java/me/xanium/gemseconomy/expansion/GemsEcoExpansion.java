/*
 * Copyright Xanium Development (c) 2013-2018. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Xanium Development. Distribution, reproduction, taking snippets or claiming
 * any contents as your own will break the terms of the license, and void any agreements with you, the third party.
 * Thank you.
 */

package me.xanium.gemseconomy.expansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.xanium.gemseconomy.GemsEconomy;
import me.xanium.gemseconomy.api.GemsEconomyAPI;
import me.xanium.gemseconomy.currency.Currency;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class GemsEcoExpansion extends PlaceholderExpansion {

    private GemsEconomyAPI gemsEconomyAPI;

    @Override
    public boolean register() {
        if(!canRegister()){
            return false;
        }

        gemsEconomyAPI = new GemsEconomyAPI();

        return super.register();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "gemseconomy";
    }

    @Override
    public String getAuthor() {
        return "Xanium";
    }

    @Override
    public String getVersion() {
        return "1.5";
    }

    @Override
    public String getRequiredPlugin(){
        return "GemsEconomy";
    }

    @Override
    public String onRequest(OfflinePlayer player, String s) {
        if (player == null) {
            return "";
        }

        UUID id = player.getUniqueId();
        Currency dc = GemsEconomy.getInstance().getCurrencyManager().getDefaultCurrency();
        s = s.toLowerCase();

        if(s.equalsIgnoreCase("balance_default")){
            String amount = "";
            double balance = gemsEconomyAPI.getBalance(id, dc);
            return String.valueOf(Math.round(balance));
        }else if(s.equalsIgnoreCase("balance_default_formatted")){
            Currency defaultCurrency = GemsEconomy.getInstance().getCurrencyManager().getDefaultCurrency();
            double balance = gemsEconomyAPI.getBalance(id, defaultCurrency);
            return defaultCurrency.format(balance);
        }else if(s.startsWith("balance_") || !s.startsWith("balance_default")) {
            String[] currencyArray = s.split("_");
            Currency c = gemsEconomyAPI.getCurrency(currencyArray[1]);
            if (s.equalsIgnoreCase("balance_" + currencyArray[1] + "_formatted")) {
                return c.format(gemsEconomyAPI.getBalance(id, c));
            } else {
                String amount = "";
                return amount + Math.round(gemsEconomyAPI.getBalance(id,c));
            }
        }

        return null;
    }
}
