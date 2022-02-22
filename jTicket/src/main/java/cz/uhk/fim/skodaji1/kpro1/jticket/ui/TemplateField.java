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
package cz.uhk.fim.skodaji1.kpro1.jticket.ui;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Class representing field in ticket template
 * @author Jiri Skoda <skodaji1@uhk.cz.cz>
 */
public class TemplateField
{
    /**
     * Name of field
     */
    private String name;
    
    /**
     * Content of field
     */
    private String content;
    
    /**
     * Position of field on Y axis
     */
    private int top;
    
    /**
     * Position of field on X axis
     */
    private int left;
    
    /**
     * Font size of field
     */
    private int fontSize;
    
    /**
     * Maximal length of text in field
     */
    private int maxLength;
    
    /**
     * Font of field
     */
    private PDType1Font font;
    
    /**
     * Creates new field in ticket template
     * @param name Name of field
     * @param top Position of field on Y axis
     * @param left Position of field on X axis
     * @param fontSize Size of font of field
     * @param fontWeight Weight of font (allowed values: 'normal' | 'bold')
     * @param maxLength Maximal length of text in field
     */
    public TemplateField(String name, int top, int left, int fontSize, String fontWeight, int maxLength)
    {
        this.name = name;
        this.top = top;
        this.left = left;
        this.fontSize = fontSize;
        this.font = PDType1Font.COURIER;
        if (fontWeight.toLowerCase().equals("bold"))
        {
            this.font = PDType1Font.COURIER_BOLD;
        }
        this.maxLength = maxLength;
    }
    
//<editor-fold defaultstate="collapsed" desc="Getters & setter">
    
    /**
     * Gets name of field
     * @return Name of field
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Gets content of field
     * @return Content of field
     */
    public String getContent()
    {
        return this.content;
    }
    
    /**
     * Sets content of field
     * @param content Content of field
     */
    public void setContent(String content)
    {
        this.content = content;
    }

    /**
     * Gets position of field on Y axis
     * @return Position of field on Y axis
     */
    public int getTop()
    {
        return this.top;
    }

    /**
     * Gets position of field on X axis
     * @return Position of field on X axis
     */
    public int getLeft()
    {
        return this.left;
    }

    /**
     * Gets size of font of field
     * @return Size of font of field
     */
    public int getFontSize()
    {
        return this.fontSize;
    }
    
    /**
     * Gets font of field
     * @return Font of field
     */
    public PDType1Font getFont()
    {
        return this.font;
    }
    
    /**
     * Gets maximal length of text in field
     * @return Maximal length of text in field
     */
    public int getMaxLength()
    {
        return this.maxLength;
    }

//</editor-fold>
}