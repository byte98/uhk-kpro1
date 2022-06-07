/*
 * Copyright (C) 2022 Jiri Skoda <jiri.skoda@student.upce.cz>
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

import cz.uhk.fim.skodaji1.kpro1.jticket.data.DistanceTariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Station;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.TariffType;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.ZoneTariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.WindowUI;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.tables.WindowUIReadOnlyTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * Class representing window with tariff detail
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class WindowUITariffDetailWindow extends JDialog
{
    
    /**
     * Tariff which detail will be displayed
     */
    private Tariff tariff;
    
    
    /**
     * Creates new window for displaying tariff detail 
     * @param t Tariff which detail will be displayed
     */
    public WindowUITariffDetailWindow(Tariff t)
    {
        this.tariff = t;
        this.initializeComponents();
        super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        super.setModal(true);
        super.setSize(new Dimension(400, 500));
        super.setResizable(false);
        super.setIconImage(new ImageIcon(WindowUI.PATH + "/detail-s.png").getImage());
        super.setTitle("Detail tarifu");
        super.setLocation(
                ((Toolkit.getDefaultToolkit().getScreenSize().width - super.getWidth()) / 2),
                ((Toolkit.getDefaultToolkit().getScreenSize().height - super.getHeight()) / 2)

        );
    }
    
    /**
     * Initializes window components
     */
    private void initializeComponents()
    {
        //<editor-fold defaultstate="collapsed" desc="Content of window">
        //JPanel content = new JPanel(new CardLayout());
        super.getContentPane().setLayout(new BorderLayout());
        JTabbedPane content = new JTabbedPane();
        //<editor-fold defaultstate="collapsed" desc="Basic information">
        JPanel basics = new JPanel();
        basics.setLayout(new BoxLayout(basics, BoxLayout.Y_AXIS));
        basics.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel h1 = new JLabel("Zkratka tarifu");
        h1.setFont(h1.getFont().deriveFont(Font.BOLD));
        basics.add(h1);
        basics.add(new JLabel(this.tariff.getAbbr()));
        basics.add(new JLabel(System.lineSeparator()));
        JLabel h2 = new JLabel("Název tarifu");
        h2.setFont(h2.getFont().deriveFont(Font.BOLD));
        basics.add(h2);
        basics.add(new JLabel(this.tariff.getName()));
        basics.add(new JLabel(System.lineSeparator()));
        JLabel h3 = new JLabel("Druh tarifu");
        h3.setFont(h3.getFont().deriveFont(Font.BOLD));
        basics.add(h3);
        basics.add(new JLabel(
                (this.tariff.getType() == TariffType.DISTANCE
                        ? "VZDÁLENOSTNÍ"
                        : "ZÓNOVÝ")
        ));
        basics.add(new JLabel(
                (this.tariff.getType() == TariffType.DISTANCE
                        ? "* Cena jízdenky je vypočítána podle projeté vzdálenosti "
                        : "* Cena jízdenky je vypočítána podle projetých zón ")
        ));
        basics.add(new JLabel("  mezi výchozí a cílovou stanicí."));
        content.add(basics, "Základní informace");
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Table with zones">
        if (this.tariff.getType() == TariffType.ZONE)
        {
            ZoneTariff t = (ZoneTariff)this.tariff;
            Station[] s = Stations.GetInstance().GetAllStations();
            JTable zonesTable = new JTable();
            Object[][] zonesData = new Object[s.length][];
            for (int i = 0; i < s.length; i++)
            {
                zonesData[i] = new Object[3];
                zonesData[i][0] = s[i].getAbbrevation();
                zonesData[i][1] = s[i].getName();
                zonesData[i][2] = t.getZone(s[i]);
            }
            zonesTable.setModel(new WindowUIReadOnlyTableModel(
                        zonesData,
                        new String[]{"ZKRATKA", "STANICE", "ZÓNA"}
                ));
            zonesTable.setAutoCreateRowSorter(true);
            zonesTable.getTableHeader().setReorderingAllowed(true);
            zonesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane zones = new JScrollPane(zonesTable,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            content.add(zones, "Zóny");
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Table with prices">
        JTable pricesTable = new JTable();
        Object[][] pricesData;
        if (this.tariff.getType() == TariffType.ZONE)
        {
            ZoneTariff zt = (ZoneTariff)this.tariff;
            pricesData = new Object[zt.GetAllPrices().keySet().size()][];
            int i = 0;
            for(int key: zt.GetAllPrices().keySet())
            {
                pricesData[i] = new Object[2];
                pricesData[i][0] = key;
                pricesData[i][1] = String.format("%d Kč", zt.GetAllPrices().get(key));
                i++;
            }
        }
        else
        {
            DistanceTariff dt = (DistanceTariff)this.tariff;
            pricesData = new Object[dt.getPriceList().keySet().size()][];
            int i = 0;
            for (int key: dt.getPriceList().keySet())
            {
                pricesData[i] = new Object[2];
                pricesData[i][0] = String.format("%d km", key);
                pricesData[i][1] = String.format("%d Kč", dt.getPriceList().get(key));
                i++;
            }
        }
        pricesTable.setModel(new WindowUIReadOnlyTableModel(
                pricesData,
                new String[]{(this.tariff.getType() == TariffType.DISTANCE ? "VZDÁLENOST" : "POČET ZÓN"), "CENA"}
        ));
        pricesTable.setAutoCreateRowSorter(true);
        pricesTable.getTableHeader().setReorderingAllowed(true);
        pricesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane pricesScroll = new JScrollPane(pricesTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        content.add(pricesScroll, "Ceník");
        //</editor-fold>
        super.getContentPane().add(content, BorderLayout.CENTER);
        //<editor-fold defaultstate="collapsed" desc="Footer of window">
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.TRAILING, 10, 10));
        //<editor-fold defaultstate="collapsed" desc="Exit button">
        JButton exitBtn = new JButton("Zavřít");
        exitBtn.addActionListener((e) -> {
            this.dispose();
        });
        footer.add(exitBtn);
        //</editor-fold>
        super.getContentPane().add(footer, BorderLayout.SOUTH);
        //</editor-fold>
        //</editor-fold>
    }
}
