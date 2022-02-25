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
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing state of program which displays dialog for setting distance between station (with destination selected)
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUIDistancesSetTo extends TextUIState
{

    /**
     * Origin station for setting distance
     */
    private Station origin;
    
    /**
     * Creates new state of program which displays dialog for setting distance between station (with destination selected)
     * @param controller Controller of program
     */
    public TextUIDistancesSetTo(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data/distances/set:to";
        this.screen = new TextUIHTMLTemplateScreen("distances-set-to", "distances-set-to.html");
        this.name = "distances-set-to";
        this.strict = false;
        
        this.helps = new ITextUIHelp[2];
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("<nazev nebo zkratka stanice>", Color.YELLOW, "Cilova stanice pro nastaveni vzdalenosti");
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
    }
    
    @Override
    public ITextUIScreen getScreen(Map<String, String> data)
    {
        this.origin = cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GetStation(data.get("station_from"));
        data.put("stations_distances_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Distances.GetInstance().GenerateDistancesRows(
                this.origin
        ));
        data.put("station_from", this.origin.getName() + " (" + this.origin.getAbbrevation() + ")");
        ((TextUIHTMLTemplateScreen)this.screen).setContent(data);
        return this.screen;
    }

    @Override
    public void handleInput(String input)
    {
        if (input.toLowerCase().equals("cancel"))
        {
            this.controller.changeState("distances");
        }
        else
        {
            Station s = cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GetStation(input);
            if (s == null)
            {
                this.controller.showError("Neznama stanice '" + input + "'!");
            }
            else
            {
                Map<String, String> data = new HashMap<>();
                data.put("station_from", this.origin.getAbbrevation());
                data.put("station_to", s.getAbbrevation());
                this.controller.changeState("distances-set-distance", data);
            }
        }
    }
    
}
