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

import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Class representing window with exception details
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class WindowUIExceptionDialog extends WindowUIDialog
{
    /**
     * Exception which will be displayed
     */
    private Exception exception;
    
    /**
     * Creates new dialog for displaying exception
     * @param ex Exception which will be displayed
     */
    public WindowUIExceptionDialog(Exception ex)
    {
        super("Neočekáváná chyba", null, WindowUIDialogType.ERROR, WindowUIButtonType.OK);
    }
    
    @Override
    protected JPanel initializeBody()
    {
        JPanel reti = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        reti.add(this.initializeIcon());
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(new JLabel("Byla zachycena neočekáváná výjimka. Podrobnosti o výjimce naleznete níže."));
        content.add(new JLabel("Pokud se rozhodnete pokračovat v práci s aplikací, mohou se vyskytnout potíže."));
        content.add(new JLabel("Výjimka: " + (this.exception == null ? "<neznámá vyjímka>" : this.exception.toString())));
        JTextArea exInfo = new JTextArea();
        exInfo.setEditable(false);
        if (this.exception != null)
        {
            for (StackTraceElement elem: this.exception.getStackTrace())
            {
                exInfo.append(elem.toString());
            }
        }
        else
        {
            exInfo.append("<žádné informace nejsou k dispozici>");
        }
        content.add(exInfo);
        reti.add(content);
        return reti;
    }
}
