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


import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.ITextUIHelp;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.ITextUIScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIController;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIHTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIHelpFactory;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing edit station form
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUIStationsEdit extends TextUIState
{
    
    /**
     * Abbreavation of station
     */
    private String stationAbbr = null;
    
    /**
     * Name of station
     */
    private String stationName = null;
    
    /**
     * New abbreavation of station
     */
    private String newStationAbbr = null;
    
    /**
     * New name of station
     */
    private String newStationName = null;

    /**
     * Creates new edit station form
     * @param controller Controller of program
     */
    public TextUIStationsEdit(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data/stations/edit?";
        this.screen = new TextUIHTMLTemplateScreen("stations-edit", "stations-edit.html");
        this.name = "stations-edit";
        this.strict = true;
        
        this.helps = new ITextUIHelp[2];
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("yes", Color.GREEN, "Udaje jsou v poradku, zmenit stanici");
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("no", Color.RED, "Zrusit");
        
    }

    @Override
    public void handleInput(String input)
    {
        switch (input.toLowerCase())
        {
            case "no":
                this.controller.changeState("stations");
                break;
            case "yes":
                cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().EditStation(cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GetStation(this.stationAbbr), this.newStationName, this.newStationAbbr);
                this.controller.showSucess("Stanice '" + this.stationName + " (" + this.stationAbbr + ")' byla uspesne zmenena.");
                this.controller.changeState("stations");
                break;
        }
    }
    
    @Override
    public ITextUIScreen getScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("stations_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GenerateTableRows());
        ((TextUIHTMLTemplateScreen)this.screen).setContent(data);
        return this.screen;
    }
    
    @Override
    public ITextUIScreen getScreen(Map<String, String> data)
    {
        data.put("stations_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GenerateTableRows());
        this.stationAbbr = data.get("station_abbr");
        this.stationName = data.get("station_name");
        this.newStationAbbr = data.get("station_new_abbr");
        this.newStationName = data.get("station_new_name");
        ((TextUIHTMLTemplateScreen)this.screen).setContent(data);
        return this.screen;
    }
}
