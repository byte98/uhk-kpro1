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
import cz.uhk.fim.skodaji1.kpro1.jticket.data.DistanceTariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Station;
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
 * Class representing tariff viewer for distance tariffs
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TariffsDistView extends State {

    /**
     * Tariff which is displayed
     */
    private DistanceTariff tariff;
    
    /**
     * Creates new tariff viewer for distance tariffs
     * @param controller Controller of program
     */
    public TariffsDistView(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs/";
        this.screen = new HTMLTemplateScreen("tariffs-dist-view", "tariffs-dist-view.html");
        this.name = "tariffs-dist-view";
        this.strict = true;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("delete", Color.RED, "Smazat tarif");
        this.helps[1] = HelpFactory.CreateSimpleHelp("back", Color.MAGENTA, "Zpet");
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
        this.tariff =(DistanceTariff) cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs.GetInstance().GetTariff(data.get("tariff_abbr"));
        if (tariff != null)
        {
            int min = -1, max = 1;
            for (Station from: cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GetAllStations())
            {
                for (Station to: cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GetAllStations())
                {
                    int dist = cz.uhk.fim.skodaji1.kpro1.jticket.data.Distances.GetInstance().GetDistance(from, to);
                    if (min == -1)
                    {
                        min = dist;
                    }
                    if (max == -1)
                    {
                        max = dist;
                    }
                    if (dist > max)
                    {
                        max = dist;
                    }
                    if (dist < min)
                    {
                        min = dist;
                    }
                }
            }
            data.put("tariff_name", this.tariff.GetName());
            data.put("tariff_prices", this.tariff.GeneratePriceListRows(min, max));
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
                this.controller.ChangeState("tariffs-dist-delete", data); 
                break;
        }
    }    
}
