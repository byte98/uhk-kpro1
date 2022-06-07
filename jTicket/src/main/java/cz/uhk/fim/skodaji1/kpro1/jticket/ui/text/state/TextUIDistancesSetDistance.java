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
 * Class representing state of program which displays dialog for setting distance between station (with distance selected)
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUIDistancesSetDistance extends TextUIState
{

    /**
     * Origin station for setting distance
     */
    private Station origin;
    
    /**
     * Destination for setting distance
     */
    private Station destination;
    
    /**
     * Creates new state of program which displays dialog for setting distance between station (with distance selected)
     * @param controller Controller of program
     */
    public TextUIDistancesSetDistance(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data/distances/set:distance";
        this.screen = new TextUIHTMLTemplateScreen("distances-set-distance", "distances-set-distance.html");
        this.name = "distances-set-distance";
        this.strict = false;
        
        this.helps = new ITextUIHelp[2];
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("<cele cislo>", Color.YELLOW, "Vzdalenost mezi vychozi a cilovou stanici");
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
    }
    
    @Override
    public ITextUIScreen getScreen(Map<String, String> data)
    {
        this.origin = cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GetStation(data.get("station_from"));
        this.destination = cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GetStation(data.get("station_to"));
        data.put("stations_distances_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Distances.GetInstance().GenerateDistancesRows(
                this.origin
        ));
        data.put("station_from", this.origin.getName() + " (" + this.origin.getAbbrevation() + ")");
        data.put("station_to", this.destination.getName() + " (" + this.destination.getAbbrevation() + ")");
        int dist = cz.uhk.fim.skodaji1.kpro1.jticket.data.Distances.GetInstance().GetDistance(this.origin, this.destination);
        data.put("distance", Integer.toString(dist));
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
            if (this.checkInt(input))
            {
                int value = Integer.parseInt(input);
                if (value < 0)
                {
                    this.controller.showError("Vzdalenost musi byt nezaporne cislo!");
                }
                else
                {
                    Map<String, String> data = new HashMap<>();
                    data.put("station_from", this.origin.getAbbrevation());
                    data.put("station_to",this.destination.getAbbrevation());
                    data.put("distance", input);
                    this.controller.changeState("distances-set", data);
                }
            }
            else
            {
                this.controller.showError("Zadany vstup '" + input + "' neni cislo!");
            }
        }
    }
    
    /**
     * Checks, whether input contains only integer
     * @param input Input which will be checked
     * @return <code>TRUE</code> if input contains integer only, <code>FALSE</code> otherwise
     * @author Jonas K https://stackoverflow.com/questions/237159/whats-the-best-way-to-check-if-a-string-represents-an-integer-in-java
     */
    private boolean checkInt(String input)
    {
        if (input == null)
        {
            return false;
        }
        int length = input.length();
        if (length == 0)
        {
            return false;
        }
        int i = 0;
        if (input.charAt(0) == '-')
        {
            if (length == 1)
            {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++)
        {
            char c = input.charAt(i);
            if (c < '0' || c > '9')
            {
                return false;
            }
        }
        return true;
    }
    
}
