/*
 * Copyright (C) 2021 Jiri Skoda <skodaji1@uhk.cz>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.state;

import cz.uhk.fim.skodaji1.kpro1.jticket.data.ZoneTariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.ITextUIHelp;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.ITextUIScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIController;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIHTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIHelpFactory;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing creating new zone tariff (with setting prices to zones)
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUITariffsZonePrices extends TextUIState {

    /**
     * Tariff which will be edited
     */
    private ZoneTariff tariff;
    
    /**
     * Actually selected zone
     */
    private int actZone = 0;
    
    /**
     * Maximal selected zone
     */
    private int maxZone = 0;
    
    /**
     * Creates new dialog for creating new zone tariff (with setting prices to zones)
     * @param controller Controller of program
     */
    public TextUITariffsZonePrices(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs/";
        this.screen = new TextUIHTMLTemplateScreen("tariffs-zone-prices", "tariffs-zone-prices.html");
        this.name = "tariffs-zone-prices";
        this.strict = false;
        
        this.helps = new ITextUIHelp[2];
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("<cele cislo>", Color.YELLOW, "Cena za projete zony");
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
    }
    
    @Override
    public ITextUIScreen getScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("zones_count", Integer.toString(this.actZone));
        data.put("tariff_name", this.tariff.getName());
        data.put("zones_prices", this.getZonePrices());
        ((TextUIHTMLTemplateScreen)this.screen).setContent(data);
        return this.screen;
    }
    
    @Override
    public ITextUIScreen getScreen(Map<String, String> data)
    {
        if (this.tariff == null)
        {
            this.tariff = (ZoneTariff) cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs.GetInstance().GetTariff(data.get("tariff_abbr"));
            this.commandPrefix = "/data/tariffs/zone/" + data.get("tariff_abbr").toLowerCase();
            int min = -1, max = -1;
            for (int zone: this.tariff.GetAllZones().values())
            {
                if (min == -1)
                {
                    min = zone;
                }
                if (max == -1)
                {
                    max = zone;
                }
                if (zone < min)
                {
                    min = zone;
                }
                if (zone > max)
                {
                    max = zone;
                }
            }
        this.maxZone = max - min;
        }
        
        data.put("zones_count", Integer.toString(this.actZone));
        data.put("tariff_name", this.tariff.getName());
        data.put("zones_prices", this.getZonePrices());
        ((TextUIHTMLTemplateScreen)this.screen).setContent(data);
        return this.screen;
    }
    
    /**
     * Checks, whether input contains only integer
     * @param input Input which will be checked
     * @return <code>TRUE</code> if input contains integer only, <code>FALSE</code> otherwise
     * @author Jonas K https://stackoverflow.com/questions/237159/whats-the-best-way-to-check-if-a-string-represents-an-integer-in-java
     */
    private boolean checkInt(String input)
    {
        if (input == null)
        {
            return false;
        }
        int length = input.length();
        if (length == 0)
        {
            return false;
        }
        int i = 0;
        if (input.charAt(0) == '-')
        {
            if (length == 1)
            {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++)
        {
            char c = input.charAt(i);
            if (c < '0' || c > '9')
            {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void handleInput(String input)
    {
        if (input.toLowerCase().equals("cancel"))
        {
            this.controller.changeState("tariffs");
        }
        else if (this.checkInt(input))
        {
            int price = Integer.parseInt(input);
            if (price >= 0)
            {
                this.tariff.SetPrice(this.actZone, price);
                this.controller.showSucess("Cena pro " + this.actZone + " zony byla nastavena.");
                this.actZone++;
                if (this.actZone > this.maxZone)
                {
                    this.controller.showSucess("Ceny pro vsechny zony byly uspesne nastaveny!");
                    this.controller.changeState("tariffs");
                }
                else
                {
                    this.controller.reDraw();
                }
            }
            else
            {
                this.controller.showError("Cislo zony musi byt nezaporne cislo!");
            }
        }
        else
        {
            this.controller.showError("Neznamy prikaz '" + input + "'!");
        }
    }
    
    /**
     * Gets HTML table rows with zone prices
     * @return String with HTML table rows with zone prices
     */
    private String getZonePrices()
    {
        String reti = new String();
        
        for (int i = 0; i <= this.maxZone; i++)
        {
            reti += "<tr><td>" + i + "</td><td style='color: white;'>";
            reti += (this.tariff.GetAllPrices().get(i) == null ? " " : this.tariff.GetAllPrices().get(i) + " Kc");
            reti += "</td></tr>";
        }
        return reti;
    }
}
