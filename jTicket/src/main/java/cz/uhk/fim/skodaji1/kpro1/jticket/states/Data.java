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
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Distances;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Station;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.Help;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.HelpFactory;
import cz.uhk.fim.skodaji1.kpro1.jticket.screens.TextUIHTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.screens.Screen;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class representing data menu
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class Data extends TextUIState {

    /**
     * Creates new data menu
     * @param controller Controller of program
     */
    public Data(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data";
        this.screen = new TextUIHTMLTemplateScreen("data", "data.html");
        this.name = "data";
        
        this.helps = new ITextUIHelp[4];
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("stations", Color.YELLOW, "Rezim upravy stanice");
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("distances", Color.YELLOW, "Rezim upravy vzdalenosti mezi stanicemi");
        this.helps[2] = TextUIHelpFactory.createSimpleHelp("tariffs", Color.YELLOW, "Rezim upravy tarifu");
        this.helps[3] = TextUIHelpFactory.createSimpleHelp("back", Color.MAGENTA, "Zpet");
    }

    @Override
    public ITextUIScreen getScreen()
    {
        Map<String, String> data = new HashMap<>();
        String stString = "";
        int idx = 0;        
        List<Station> stations = new ArrayList<>(Arrays.asList(Stations.GetInstance().GetAllStations()));
        Collections.shuffle(stations);
        Iterator<Station> it = stations.iterator();
        while (it.hasNext())
        {
            Station st = it.next();
            if (st != null)
            {
                stString += st.GetName();
                if (idx < stations.size() - 1)
                {
                    stString += ",";
                }
            } 
            idx++;
        }
        stString = Controller.TrimString(stString, 128);
        data.put("station_list", stString);
        
        String distString = new String();
        Station sts[] = Stations.GetInstance().GetAllStations();
        while (distString.length() <= 128)
        {
            if (sts.length < 1)
            {
                break;
            }
            else
            {
                int f = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, sts.length);
                Station from = sts[f];
                int t = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, sts.length);
                Station to = sts[t];
                distString += "[" + from.GetAbbrevation() + " -> " + to.GetAbbrevation() + ": " + Distances.GetInstance().GetDistance(from, to) + " km] ";
            }
        }
        data.put("distances_list", Controller.TrimString(distString, 128));
        
        String tariffsString = new String();
        Tariff Ts[] = Tariffs.GetInstance().GetAllTariffs();
        while (tariffsString.length() <= 128)
        {
            if (Ts.length < 1)
            {
                break;
            }
            else
            {
                int Ti = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, Ts.length);
                tariffsString += Ts[Ti].GetName() + ", ";
            }
        }
        data.put("tariffs_list", Controller.TrimString(tariffsString, 128));
        
        ((TextUIHTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public void handleInput(String input)
    {
        switch (input.toLowerCase())
        {
            case "back": this.controller.changeState("welcome"); break;
            case "stations": this.controller.changeState("stations"); break;
            case "distances": this.controller.changeState("distances"); break;
            case "tariffs": this.controller.changeState("tariffs"); break;
        }
    }
    
}
