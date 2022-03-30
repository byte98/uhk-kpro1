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
package cz.uhk.fim.skodaji1.kpro1.jticket.data;

import cz.uhk.fim.skodaji1.kpro1.jticket.jTicket;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.TicketTemplate;
import java.util.Calendar;
import java.util.Date;

/**
 * Class representing ticket
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Ticket
{
    /**
     * Tariff which can compute price of the ticket
     */
    private Tariff tariff;
    
    /**
     * Origin station of ticket
     */
    private Station from;
    
    /**
     * Destination station of ticket
     */
    private Station to;
    
    /**
     * Template for ticket printing
     */
    private TicketTemplate template;
    
    
    /**
     * Identifier of ticket
     */
    private String id;
    
    /**
     * Number of ticket
     */
    private int ticketNr;
    
    /**
     * Creates new ticket
     */
    public void Ticket()
    {
        Configuration config = Configuration.getInstance(jTicket.CONFIG_FILE);
        this.template = new TicketTemplate(config.ticketBackground, config.ticketTemplate);
        config.ticketNr = config.ticketNr + 1;
        this.ticketNr = config.ticketNr;
        config.saveToFile();
        this.id = String.format("JT%012d", this.ticketNr);
    }
    
    /**
     * Sets tariff of ticket
     * @param t Tariff which can compute price of ticket
     */
    public void setTariff(Tariff t)
    {
        this.tariff = t;
        this.id = String.format("JT" + this.tariff.getAbbr() + "%09d", this.ticketNr);
    }
    
    /**
     * Sets origin station of ticket
     * @param s Origin station of ticket
     */
    public void setOrigin(Station s)
    {
        this.from = s;
    }
    
    /**
     * Sets destination station of ticket
     * @param s Destination station of ticket
     */
    public void setDestination(Station s)
    {
        this.to = s;
    }
    
    /**
     * Gets distance between stations in ticket
     * @return Distance between stations in ticket
     *         or -1 if not both origin and destination has been set
     */
    public int getDistance()
    {
        int reti = -1;
        if (this.from != null && this.to != null)
        {
            reti = Distances.GetInstance().GetDistance(this.from, this.to);
        }
        return reti;
    }
    
    /**
     * Gets textual representation of distance between stations
     * @return Formatted string with distance between stations
     */
    public String getDistanceText()
    {
        StringBuilder reti = new StringBuilder();
        int distance = this.getDistance();
        if (distance > -1)
        {
            reti.append(distance);
            reti.append(" km");
            if (this.tariff.getType() == TariffType.ZONE)
            {
                ZoneTariff zt = (ZoneTariff) this.tariff;
                reti.append("(počet zón: ");
                reti.append(Math.abs(zt.getZone(this.from) - zt.getZone(this.to)));
                reti.append(")");
            }
        }
        else
        {
            reti.append(" km");
        }
        return reti.toString();
    }
    
    /**
     * Gets price of the ticket in Czech Korunas
     * @return Price of the ticket in Czech Korunas
     *         or -1 if not both origin and destination has been set
     */
    public int getPrice()
    {
        int reti = -1;
        if (this.from != null && this.to != null)
        {
            reti = this.tariff.GetPrice(this.from, this.to);
        }
        return reti;
    }
    
    /**
     * Gets text representation of price of ticket
     * @return Text representation of price of ticket
     */
    public String getPriceText()
    {
        String reti = " ";
        if (this.getPrice() > -1)
        {
            reti = String.valueOf(this.getPrice());
        }
        return reti;
    }
    
    /**
     * Gets identifier of ticket
     * @return Identifier of ticket
     */
    public String getId()
    {
        return this.id;
    }
    
    /**
     * Gets text representing tariff of ticket
     * @return Text representing tariff of ticket
     */
    public String getTariffText()
    {
        String reti = " ";
        if (this.tariff != null)
        {
            reti = this.tariff.getName();
        }
        return reti;
    }
    
    /**
     * Gets text representing origin station of ticket
     * @return Text representing origin station of ticket
     */
    public String getFromText()
    {
        StringBuilder reti = new StringBuilder(" ");
        if (this.from != null)
        {
            reti.replace(0, 1, this.from.getName());
            if (this.tariff != null && this.tariff.getType() == TariffType.ZONE)
            {
                reti.append("(");
                reti.append(((ZoneTariff)this.tariff).getZone(this.from));
                reti.append(")");
            }
        }
        return reti.toString();
    }
    
    /**
     * Gets text representing destination station of ticket
     * @return Text representing destination station of ticket
     */
    public String getToText()
    {
        StringBuilder reti = new StringBuilder(" ");
        if (this.to != null)
        {
            reti.replace(0, 1, this.to.getName());
            if (this.tariff != null && this.tariff.getType() == TariffType.ZONE)
            {
                reti.append("(");
                reti.append(((ZoneTariff)this.tariff).getZone(this.to));
                reti.append(")");
            }
        }
        return reti.toString();
    }
    
    /**
     * Gets text representing validity of ticket
     * @return Text representing validity of ticket
     */
    public String getValidityText()
    {
        Configuration cfg = Configuration.getInstance(jTicket.CONFIG_FILE);
        Calendar validTo = Calendar.getInstance();
        validTo.setTime(new Date());
        validTo.add(Calendar.HOUR_OF_DAY, cfg.ticketValidity);
        StringBuilder validity = new StringBuilder();
        validity.append(String.format("%02d", validTo.getTime().getDate()));
        validity.append(".");
        validity.append(String.format("%02d", validTo.getTime().getMonth()));
        validity.append(".");
        validity.append(validTo.getTime().getYear() + 1900);
        validity.append(" ");
        validity.append(String.format("%02d", validTo.getTime().getHours()));
        validity.append(":");
        validity.append(String.format("%02d", validTo.getTime().getMinutes()));
        return validity.toString();
    }
    
    /**
     * Prints ticket to PDF
     * @param output Path to file to which ticket will be printed into
     */
    public void printToPdf(String output)
    {
        Configuration cfg = Configuration.getInstance(jTicket.CONFIG_FILE);
        if (this.template == null)
        {
            this.template = new TicketTemplate(cfg.ticketBackground, cfg.ticketTemplate);
        }        
        Date date = new Date();        
        StringBuilder issued = new StringBuilder();
        issued.append(String.format("%02d", date.getDate()));
        issued.append(".");
        issued.append(String.format("%02d", date.getMonth()));
        issued.append(".");
        issued.append(date.getYear() + 1900);
        this.template.setValue("issued", issued.toString());
        this.template.setValue("id", this.id);
        this.template.setValue("tariff", this.tariff.getName());
        this.template.setValue("from", this.from.getName());
        this.template.setValue("to", this.to.getName());        
        this.template.setValue("validity", this.getValidityText());
        this.template.setValue("distance", this.getDistanceText());
        double price = this.getPrice();
        this.template.setValue("price-wo-vat", String.format("%.2f", (price - (price * (cfg.VAT / 100)))));
        this.template.setValue("vat-price", String.format("%.2f", price * (cfg.VAT / 100)));
        this.template.setValue("price", String.format("%d", (long)price));
        
        this.template.generatePDF(cfg.outputDirectory + output);
    }
}
        
