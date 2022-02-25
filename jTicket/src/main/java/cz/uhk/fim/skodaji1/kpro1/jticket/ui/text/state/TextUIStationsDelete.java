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
 * Class representing delete station dialog
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUIStationsDelete extends TextUIState
{
    /**
     * Abbrevation of station
     */
    private String stationAbbr;
    
    /**
     * Name of the station
     */
    private String stationName;
    
    /**
     * Creates new delete station dialog
     * @param controller Controller of program
     */
    public TextUIStationsDelete(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data/stations/delete?";
        this.screen = new TextUIHTMLTemplateScreen("stations-delete", "stations-delete.html");
        this.name = "stations-delete";
        
        this.helps = new ITextUIHelp[2];
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("no", Color.GREEN, "Zrusit");
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("yes", Color.RED, "Smazat stanici");
        
    }

    @Override
    public void handleInput(String input)
    {
        switch (input.toLowerCase())
        {
            
            case "no": 
                Map<String, String> data = new HashMap<>();
                data.put("station_name", this.stationName);
                data.put("station_abbr", this.stationAbbr);
                this.controller.changeState("stations-edit-name", data);
                break;
            case "yes":
                cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().DeleteStation(cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GetStation(this.stationAbbr));
                this.controller.showSucess("Stanice '" + this.stationName + "' byla uspesne vymazana.");
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
        this.stationAbbr = data.get("station_abbr");
        this.stationName = data.get("station_name");
        data.put("stations_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GenerateTableRows());
        ((TextUIHTMLTemplateScreen)this.screen).setContent(data);
        return this.screen;
    }
}
