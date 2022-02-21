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
package cz.uhk.fim.skodaji1.kpro1.jticket;

import cz.uhk.fim.skodaji1.kpro1.jticket.data.Configuration;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.UIMode;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.IUserInterface;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUI;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Entry point of whole program
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class Main
{    
    /**
     * Main function of program
     * @param args Arguments of program
     */
    public static void main(String[] args)
    {
        //Controller controller = Controller.GetController();
        //controller.Start();
        Configuration config = Configuration.getInstance("config.ini");
        IUserInterface ui = null;
        switch (config.uiMode)
        {
            case TEXT: ui = new TextUI(); break;
        }
        if (ui != null)
        {
            ui.prepare();
            ui.start();
        }
        else
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Unknown UI MODE");
        }
        config.uiMode = UIMode.GRAPHICS;
        config.saveToFile();
    }
}
