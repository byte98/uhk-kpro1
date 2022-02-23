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
 * Class representing add station form (with selected name option)
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class StationsAddName extends TextUIState
{

    /**
     * Creates new add station form (with selected name option)
     * @param controller Controller of program
     */
    public StationsAddName(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data/stations/add:name";
        this.screen = new TextUIHTMLTemplateScreen("stations-add-name", "stations-add-name.html");
        this.name = "stations-add-name";
        this.strict = false;
        
        this.helps = new ITextUIHelp[2];
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("<nazev stanice>", Color.YELLOW, "Nazev stanice");
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
        
    }

    @Override
    public void handleInput(String input)
    {
        if ("cancel".equals(input.toLowerCase()))
        {
            this.controller.changeState("stations");   
        }
        else
        {
            if (cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().CheckFreeName(input))
            {
                Map<String, String> data = new HashMap<>();
                data.put("station_name", input);
                this.controller.changeState("stations-add-abbr", data);
            }
            else
            {
                this.controller.showError("Stanice '" + input + "' jiz existuje!");
            }
            
        }
    }
    
    @Override
    public ITextUIScreen getScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("stations_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GenerateTableRows());
        ((TextUIHTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
}
