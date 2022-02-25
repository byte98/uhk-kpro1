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

import cz.uhk.fim.skodaji1.kpro1.jticket.data.Station;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.ITextUIHelp;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.ITextUIScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIController;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIHTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIHelpFactory;
import java.awt.Color;
import java.util.Map;

/**
 * Class representing state of program which displays table of distances from station
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUIDistancesViewStation extends TextUIState
{

    /**
     * Origin station from which distances will be displayed
     */
    private Station origin;
    
    /**
     * Creates new state of program with table of distances from station
     * @param controller 
     */
    public TextUIDistancesViewStation(TextUIController controller)
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
           data.put("station_from", s.getName() + " (" + s.getAbbrevation() + ")");
           data.put("stations_distances_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Distances.GetInstance().GenerateDistancesRows(s));
           this.origin = s;
           this.commandPrefix = "/data/distances/view/" + s.getAbbrevation().toLowerCase();
           ((TextUIHTMLTemplateScreen) this.screen).setContent(data);           
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
