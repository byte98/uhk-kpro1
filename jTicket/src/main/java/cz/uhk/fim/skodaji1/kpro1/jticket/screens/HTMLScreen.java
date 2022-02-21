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
package cz.uhk.fim.skodaji1.kpro1.jticket.screens;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing screen which content is saved as HTML file
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class HTMLScreen implements Screen {
    
    /**
     * File containing content of screen
     */
    protected final String fileName;
    
    /**
     * Name of screen
     */
    protected final String name;
    
    /**
     * Content of the screen
     */
    protected String content = null;
    
    /**
     * Creates new instance of screen which content is saved in HTML file
     * @param name Name of screen
     * @param fileName Name of file with screen content
     */
    public HTMLScreen(String name, String fileName)
    {
        this.name = name;
        this.fileName = fileName;
    }

    @Override
    public String GetContent()
    {
        if (this.content == null) // Load content only once
        {
            try
            {
                File html = new File("resources/" + this.fileName);
                Scanner reader = new Scanner(html);
                while (reader.hasNextLine())
                {
                    this.content += reader.nextLine();
                }
                reader.close();
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(HTMLScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.content;        
    }

    @Override
    public String GetName()
    {
        return this.name;
    }
    
}
