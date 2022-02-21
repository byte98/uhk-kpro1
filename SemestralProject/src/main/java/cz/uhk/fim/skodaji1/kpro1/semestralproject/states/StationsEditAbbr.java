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
import cz.uhk.fim.skodaji1.kpro1.semestralproject.help.Help;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.help.HelpFactory;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.screens.HTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing edit station form (with selected abbreaviation option)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class StationsEditAbbr extends State
{
    
    /**
     * Abbreaviation of station
     */
    private String stationAbbr = null;
    
    /**
     * Name of station
     */
    private String stationName = null;
    
    /**
     * New name of station
     */
    private String newStationName = null;

    /**
     * Creates new edit station form (with selected abbreaviation option)
     * @param controller Controller of program
     */
    public StationsEditAbbr(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/stations/edit:abbr";
        this.screen = new HTMLTemplateScreen("stations-edit-abbr", "stations-edit-abbr.html");
        this.name = "stations-edit-abbr";
        this.strict = false;
        
        this.helps = new Help[3];
        this.helps[0] = HelpFactory.CreateSimpleHelp("<zkratka stanice>", Color.YELLOW, "Nova zkratka stanice");
        this.helps[1] = HelpFactory.CreateSimpleHelp("delete", Color.MAGENTA, "Smazat stanici");
        this.helps[2] = HelpFactory.CreateSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
        
    }

    @Override
    public void HandleInput(String input)
    {
        if ("cancel".equals(input.toLowerCase()))
        {
            this.controller.ChangeState("stations");   
        }
        else if ("delete".equals(input.toLowerCase()))
        {
            Map<String, String> data = new HashMap<>();
            data.put("station_name", this.stationName);
            data.put("station_abbr", this.stationAbbr);
            this.controller.ChangeState("stations-delete", data);
        }
        else
        {
            if (cz.uhk.fim.skodaji1.kpro1.semestralproject.data.Stations.GetInstance().CheckFreeAbbr(input.toLowerCase()) == false && !input.toLowerCase().equals(this.stationAbbr.toLowerCase()))
            {
                this.controller.ShowError("Zkratka '" + input + "' je jiz obsazena!");
            }
            else
            {
                Map<String, String> data = new HashMap<>();
                data.put("station_name", this.stationName);
                data.put("station_abbr", this.stationAbbr);
                data.put("station_new_name", this.newStationName);
                data.put("station_new_abbr", input);
                this.controller.ChangeState("stations-edit", data);
            }            
        }
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
    public Screen GetScreen(Map<String, String> data)
    {
        data.put("stations_tr", cz.uhk.fim.skodaji1.kpro1.semestralproject.data.Stations.GetInstance().GenerateTableRows());
        this.stationAbbr = data.get("station_abbr");
        this.stationName = data.get("station_name");
        this.newStationName = data.get("station_new_name");
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
}
