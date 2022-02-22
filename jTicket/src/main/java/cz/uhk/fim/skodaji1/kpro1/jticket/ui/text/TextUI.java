/*
 * Copyright (C) 2022 Jiri Skoda <skodaji1@uhk.cz.cz>
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
package cz.uhk.fim.skodaji1.kpro1.jticket.ui.text;

import cz.uhk.fim.skodaji1.kpro1.jticket.Controller;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.HelpWindow;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.IUserInterface;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.MainWindow;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Class representing textual user interface
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUI implements IUserInterface
{
    
    /**
     * Main window of program
     */
    private TextUIMainWindow mainWindow;
    
    /**
     * Window with help
     */
    private TextUIHelpWindow helpWindow;
    
    @Override
    public void prepare()
    {
        this.mainWindow = new TextUIMainWindow();
        this.mainWindow.pack();
        
        this.helpWindow = new TextUIHelpWindow();
        this.helpWindow.pack();
    }

    @Override
    public void start()
    {
        // Run UI in separate thread
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                mainWindow.setVisible(true);
            }
        });
    }
    
}
