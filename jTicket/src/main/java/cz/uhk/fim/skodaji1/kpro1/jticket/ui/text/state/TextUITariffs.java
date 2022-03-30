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

import cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.TariffType;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.ITextUIHelp;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.ITextUIScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIController;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIHTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIHelpFactory;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing tariffs menu
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUITariffs extends TextUIState {

    /**
     * Creates new tariffs menu
     * @param controller Controller of program
     */
    public TextUITariffs(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs";
        this.screen = new TextUIHTMLTemplateScreen("tariffs", "tariffs.html");
        this.name = "tariffs";
        this.strict = false;
        
        this.helps = new ITextUIHelp[4];
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("<jmeno nebo zkratka tarifu>", Color.YELLOW, "Rezim prohlizeni tarifu");
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("zone", Color.YELLOW, "Vytvorit novy zonovy tarif");
        this.helps[2] = TextUIHelpFactory.createSimpleHelp("distance", Color.YELLOW, "Vytvorit novy vzdalenostni tarif");
        this.helps[3] = TextUIHelpFactory.createSimpleHelp("back", Color.MAGENTA, "Zpet");
    }

    @Override
    public ITextUIScreen getScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("tariffs_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs.GetInstance().GenerateTariffsTableRows());
        ((TextUIHTMLTemplateScreen)this.screen).setContent(data);
        return this.screen;
    }
    
    @Override
    public void handleInput(String input)
    {
        switch (input.toLowerCase())
        {
            case "back":
                this.controller.changeState("data");
                break;
            case "zone":
                this.controller.changeState("tariffs-zone-name");
                break;
            case "distance":
                this.controller.changeState("tariffs-dist-name");
                break;
            default:
                Tariff t = cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs.GetInstance().GetTariff(input);
                if (input.equals(""))
                {
                    //pass
                }
                else if (t == null)
                {
                    this.controller.showError("Neznamy prikaz '" + input + "'!");
                }
                else
                {
                    Map<String, String> data = new HashMap<>();
                    data.put("tariff_abbr", t.getAbbr());
                    if (t.getType() == TariffType.ZONE)
                    {
                        this.controller.changeState("tariffs-zone-view", data);
                    }   
                    else if (t.getType() == TariffType.DISTANCE)
                    {
                        this.controller.changeState("tariffs-dist-view", data);
                    }
                }
                break;
        }
    }

    
    
}
