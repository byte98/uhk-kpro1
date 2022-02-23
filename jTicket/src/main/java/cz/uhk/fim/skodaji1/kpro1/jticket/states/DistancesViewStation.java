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
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Station;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.Help;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.HelpFactory;
import cz.uhk.fim.skodaji1.kpro1.jticket.screens.TextUIHTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing state of program which displays table of distances from station
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class DistancesViewStation extends TextUIState
{

    /**
     * Origin station from which distances will be displayed
     */
    private Station origin;
    
    /**
     * Creates new state of program with table of distances from station
     * @param controller 
     */
    public DistancesViewStation(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data/distances/view/";
        this.screen = new TextUIHTMLTemplateScreen("distances-view-station", "distances-view-station.html");
        this.name = "distances-view-station";
        this.strict = true;
        
        this.helps = new ITextUIHelp[1];
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("back", Color.MAGENTA, "Zpet");
    }
    
    @Override
    public ITextUIScreen getScreen(Map<String, String> data)
    {
       Station s = cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GetStation(data.get("station"));
       if (s != null)
       {
           data.put("station_from", s.GetName() + " (" + s.GetAbbrevation() + ")");
           data.put("stations_distances_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Distances.GetInstance().GenerateDistancesRows(s));
           this.origin = s;
           this.commandPrefix = "/data/distances/view/" + s.GetAbbrevation().toLowerCase();
           ((TextUIHTMLTemplateScreen) this.screen).SetContent(data);           
       }
       return this.screen;
    }
    
    
    @Override
    public void handleInput(String input)
    {
        if (input.toLowerCase().equals("back"))
        {
            this.controller.changeState("distances-view");
        }
    }
    
    
}
