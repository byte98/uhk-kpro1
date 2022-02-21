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
package cz.uhk.fim.skodaji1.kpro1.jticket.states;

import cz.uhk.fim.skodaji1.kpro1.jticket.Controller;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.ZoneTariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.Help;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.HelpFactory;
import cz.uhk.fim.skodaji1.kpro1.jticket.screens.HTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing tariff viewer for zone tariffs
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TariffsZoneView extends State {

    /**
     * Tariff which is displayed
     */
    private ZoneTariff tariff;
    
    /**
     * Creates new tariff viewer for zone tariffs
     * @param controller Controller of program
     */
    public TariffsZoneView(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs/";
        this.screen = new HTMLTemplateScreen("tariffs-zone-view", "tariffs-zone-view.html");
        this.name = "tariffs-zone-view";
        this.strict = true;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("delete", Color.RED, "Smazat tarif");
        this.helps[1] = HelpFactory.CreateSimpleHelp("back", Color.MAGENTA, "Zpet");
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
        this.tariff =(ZoneTariff) cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs.GetInstance().GetTariff(data.get("tariff_abbr"));
        if (tariff != null)
        {
            data.put("tariff_name", this.tariff.GetName());
            data.put("tariff_zones", this.tariff.GenerateZonesTr());
            data.put("tariff_prices", this.GetTariffPrices());
            this.commandPrefix = "/data/tariffs/" + this.tariff.GetAbbr().toLowerCase();
        }
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public void HandleInput(String input)
    {
        switch(input.toLowerCase())
        {
            case "back": this.controller.ChangeState("tariffs"); break;
            case "delete": 
                Map<String, String> data = new HashMap<>();
                data.put("tariff_abbr", this.tariff.GetAbbr());
                this.controller.ChangeState("tariffs-zone-delete", data); 
                break;
        }
    }
    
    /**
     * Gets table rows with tariff row prices
     * @return 
     */
    private String GetTariffPrices()
    {
        String reti = new String();
        reti = this.tariff.GetAllPrices().keySet().stream().map(zone -> "<tr><td>" + zone + "</td><td style='color: white;'>" + this.tariff.GetAllPrices().get(zone) + " Kc</td></tr").reduce(reti, String::concat);
        return reti;
    }
    
}
