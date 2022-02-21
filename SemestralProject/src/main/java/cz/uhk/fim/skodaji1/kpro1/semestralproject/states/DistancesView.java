/*
 * Copyright (C) 2021 Jiri Skoda <jiri.skoda@student.upce.cz>
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
package cz.uhk.fim.skodaji1.kpro1.semestralproject.states;

import cz.uhk.fim.skodaji1.kpro1.semestralproject.Controller;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.data.Station;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.help.Help;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.help.HelpFactory;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.screens.HTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing state of program which can display set distances between stations
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class DistancesView extends State
{

    /**
     * Creates new state of program with viewer of set distances between stations
     * @param controller 
     */
    public DistancesView(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/distances/view";
        this.screen = new HTMLTemplateScreen("distances", "distances.html");
        this.name = "distances-view";
        this.strict = false;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("<nazev nebo zkratka stanice>", Color.YELLOW, "Stanice pro zobrazeni vzdalenosti");
        this.helps[1] = HelpFactory.CreateSimpleHelp("back", Color.MAGENTA, "Zpet");
    }
    
    @Override
    public Screen GetScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("stations_tr", cz.uhk.fim.skodaji1.kpro1.semestralproject.data.Stations.GetInstance().GenerateTableRows());
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }

    @Override
    public void HandleInput(String input)
    {
        if (input.toLowerCase().equals("back"))
        {
            this.controller.ChangeState("distances");
        }
        else
        {
            Station s = cz.uhk.fim.skodaji1.kpro1.semestralproject.data.Stations.GetInstance().GetStation(input);
            if (s == null)
            {
                this.controller.ShowError("Neznama stanice '" + input + "'!");
            }
            else
            {
                Map<String, String> data = new HashMap<>();
                data.put("station", s.GetAbbrevation());
                this.controller.ChangeState("distances-view-station", data);
            }
        }
    }
    
}
