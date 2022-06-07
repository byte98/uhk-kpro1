/*
 * Copyright (C) 2022 Jiri Skoda <skodaji1@uhk.cz>
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
package cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.windows;

import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.windows.dialogs.WindowUIDialogType;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.windows.dialogs.WindowUIButtonType;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.windows.dialogs.WindowUIDialog;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.WindowUI;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Class representing main window of program
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class WindowUIMainWindow extends WindowUIWindow
{    
    /**
     * Item in menu which opens settings window
     */
    private JMenuItem itemSettings;
    
    /**
     * Data menu in top menu bar
     */
    private JMenu dataMenu;
        
    /**
     * Item in menu which opens stations settings window
     */
    private JMenuItem itemStations;
    
    /**
     * Item in menu which opens distances settings window
     */
    private JMenuItem itemDistances;
    
    /**
     * Item in menu which opens tariffs settings window
     */
    private JMenuItem itemTariffs;
    
    /**
     * Window with stations
     */
    private final WindowUIStationsWindow stationsWindow;
 
    /**
     * Creates new main window of program
     */
    public WindowUIMainWindow()
    {
        super("jTicket", "default.png");
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.stationsWindow = new WindowUIStationsWindow();
        this.stationsWindow.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                setEnabled(true);
            }
        });
        super.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent ev)
            {
                WindowUIDialog exitDialog = new WindowUIDialog("Ukončit aplikaci", "Opravdu chcete ukončit aplikaci?", WindowUIDialogType.QUESTION, WindowUIButtonType.YES, WindowUIButtonType.NO);
                exitDialog.showDialog();
                if (exitDialog.getResult() == WindowUIButtonType.YES)
                {
                    dispose();
                    System.exit(0);
                }
            }
        });
        
    }
    
    @Override
    protected void prepareTopMenu()
    {        
        super.prepareTopMenu();
        
        // Settings of program
        this.itemSettings = new JMenuItem();
        this.itemSettings.setText("Nastavení");
        this.itemSettings.setIcon(new ImageIcon(WindowUI.PATH + "/settings.png"));
        this.fileMenu.add(this.itemSettings, 0);
                
        // Data menu
        this.dataMenu = new JMenu();
        this.dataMenu.setText("Data");
        this.dataMenu.setIcon(new ImageIcon(WindowUI.PATH + "/data.png"));
        
        // Stations menu item
        this.itemStations = new JMenuItem();
        this.itemStations.setText("Stanice");
        this.itemStations.setIcon(new ImageIcon(WindowUI.PATH + "/dataitem.png"));
        this.itemStations.addActionListener((ActionEvent e) -> {
            stationsWindow.setData(Stations.GetInstance().GetAllStations());
            stationsWindow.setVisible(true);
            stationsWindow.setPreferredSize(getPreferredSize());
            setEnabled(false);
            stationsWindow.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosed(WindowEvent e)
                {
                    toFront();
                }
                
            });
        });
        this.dataMenu.add(this.itemStations);
        
        // Distances menu item
        this.itemDistances = new JMenuItem();
        this.itemDistances.setText("Vzdálenosti");
        this.itemDistances.setIcon(new ImageIcon(WindowUI.PATH + "/dataitem.png"));
        this.itemDistances.addActionListener((e) -> {
            WindowUIDistancesWindow distancesWindow = new WindowUIDistancesWindow();
            distancesWindow.setVisible(true);
        });
        this.dataMenu.add(this.itemDistances);
        
        // Tariffs menu item
        this.itemTariffs = new JMenuItem();
        this.itemTariffs.setText("Tarify");
        this.itemTariffs.setIcon(new ImageIcon(WindowUI.PATH + "/dataitem.png"));
        this.itemTariffs.addActionListener((e) -> {
            WindowUITariffsWindow tariffsWindow = new WindowUITariffsWindow();
            tariffsWindow.setVisible(true);
        });
        this.dataMenu.add(this.itemTariffs);
        
        this.topMenu.add(this.dataMenu);
    }
}
