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

import cz.uhk.fim.skodaji1.kpro1.jticket.screens.HTMLTemplateScreen;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * Class representing helper window of textual user interface
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUIHelpWindow extends JFrame
{
    /**
     * Content of help window
     */
    private final JTextPane content;
    
    /**
     * Scroll bar for content
     */
    private final JScrollPane scrollBar;
    
    /**
     * Screen containing list of all available stations
     */
    private final HTMLTemplateScreen stations; 
    
    /**
     * Screen containing list of all available tariffs
     */
    private final HTMLTemplateScreen tariffs;
    
    /**
     * HTML string which will be added before any HTML document
     */
    private final String defaultHTML = "<style type='text/css'>*{font-family: 'Lucida Console'; color: white; background-color: black;}</style>";
    
    /**
     * Creates new window with help needed when selling tickets
     */
    public TextUIHelpWindow()
    {
        super("JTicket - Help");
        this.setLayout(new GridLayout());
        this.setPreferredSize(new Dimension(400, 600));
        
        this.content = new JTextPane();
        this.content.setEditable(false);
        this.content.setContentType("text/html");
        this.content.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        this.getContentPane().add(this.content);
        
        this.scrollBar = new JScrollPane(this.content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.getContentPane().add(this.scrollBar);
        
        this.stations = new HTMLTemplateScreen("help-stations", "help-stations.html");
        this.tariffs = new HTMLTemplateScreen("help-tariffs", "help-tariffs.html");
        
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
