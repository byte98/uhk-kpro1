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
package cz.uhk.fim.skodaji1.kpro1.jticket.ui.text;

import cz.uhk.fim.skodaji1.kpro1.jticket.Controller;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.Help;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;

/**
 * Class representing main window for text user interface
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUIMainWindow extends JFrame
{
    
    /**
     * Constant of one second;
     */
    private final static int ONE_SECOND = 1000;
    
    /**
     * Text area representing output console
     */
    private final JEditorPane out;
    
    /**
     * Scroll bar for output text area
     */
    private final JScrollPane outScrollBar;
    
    /**
     * Text area representing input console
     */
    private final JTextPane in;
    
    /**
     * Panel containing input controls
     */
    private final JPanel input;
    
    /**
     * Text area containing help
     */
    private final JTextPane help;
    
    /**
     * Scroll bar for input text area
     */
    private final JScrollPane inScrollBar;
    
    /**
     * Label showing name of program
     */
    private final JLabel nameLabel;
    
    /**
     * Label showing date and time
     */
    private final JLabel dateLabel;
    
    /**
     * Header of program
     */
    private final JPanel header;
    
    /**
     * Default font of window
     */
    private final Font defaultFont;
    
    /**
     * Content of the window
     */
    private final JPanel content;
    
    /**
     * Timer for updating current date and time
     */
    private final Timer timer;
    
    /**
     * Mode of actually entered commands
     */
    private String commandMode;
    
    /**
     * Panel which can only display inputs
     */
    private final JPanel inputViews;
    
    /**
     * Text field which accepts commands
     */
    private final JTextField command;
    
    /**
     * Panel containing command line
     */
    private final JPanel commandLine;
    
    /**
     * Prefix of command
     */
    private final JLabel commandPrefix;
    
    /**
     * HTML string which will be added before any HTML document
     */
    private final String defaultHTML = "<style type='text/css'>*{font-family: 'Lucida Console'; color: white; background-color: black;}</style>";
    
    /**
     * Actually displayed help to the commands
     */
    private Help[] actualHelp;
    
    /**
     * Controller of window
     */
    private Controller controller = null;
    
    /**
     * Flag, whether window should check input or not
     */
    private boolean strict = false;

    
    public TextUIMainWindow()
    {
        super("JTicket");
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(800, 600));        
        this.commandMode = "";
        
        this.defaultFont = new Font("Lucida Console", Font.PLAIN, 18);
        
        this.content = new JPanel(new GridLayout(2, 1));
        this.getContentPane().add(this.content, BorderLayout.CENTER);
        
        this.header = new JPanel(new BorderLayout());
        this.header.setForeground(Color.BLACK);
        this.header.setBackground(Color.LIGHT_GRAY);
        this.header.setFont(this.defaultFont);
        this.getContentPane().add(this.header, BorderLayout.PAGE_START);
        
        this.nameLabel = new JLabel("JTicket");
        this.nameLabel.setForeground(Color.BLACK);
        this.nameLabel.setBackground(Color.LIGHT_GRAY);
        this.nameLabel.setFont(this.defaultFont);
        this.header.add(this.nameLabel, BorderLayout.LINE_START);
        
        this.dateLabel = new JLabel("{date placeholder}");
        this.dateLabel.setForeground(Color.BLACK);
        this.dateLabel.setBackground(Color.LIGHT_GRAY);
        this.dateLabel.setFont(this.defaultFont);
        this.header.add(this.dateLabel, BorderLayout.LINE_END);
        
        this.out = new JEditorPane();
        this.content.add(this.out);
        this.out.setBackground(Color.BLACK);
        this.out.setForeground(Color.LIGHT_GRAY);
        this.out.setFont(this.defaultFont);
        this.out.setEditable(false);
        this.out.setContentType("text/html");
        
        this.outScrollBar = new JScrollPane(this.out, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.content.add(this.outScrollBar);
        this.outScrollBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        this.inputViews  = new JPanel(new GridLayout(2,1));
        
        this.input = new JPanel(new BorderLayout());
        this.content.add(this.input);
        
        this.help = new JTextPane();
        this.help.setBackground(Color.BLACK);
        this.help.setForeground(Color.LIGHT_GRAY);
        this.help.setFont(this.defaultFont);
        this.help.setEditable(false);        
        this.inputViews.add(this.help);
        
        this.in = new JTextPane();
        this.in.setBackground(Color.BLACK);
        this.in.setForeground(Color.LIGHT_GRAY);
        this.in.setFont(this.defaultFont);   
        this.in.setEditable(false);
        this.in.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        this.inputViews.add(this.in);        
        
        this.inScrollBar = new JScrollPane(this.in, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.inputViews.add(this.inScrollBar);
        this.inScrollBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        this.input.add(this.inputViews, BorderLayout.CENTER);
        
        this.commandLine = new JPanel();
        this.commandLine.setLayout(new BoxLayout(this.commandLine, BoxLayout.X_AXIS));
        this.commandLine.setBackground(Color.BLACK);
        this.commandLine.setForeground(Color.LIGHT_GRAY);
        this.commandLine.setFont(this.defaultFont);
        this.input.add(this.commandLine, BorderLayout.PAGE_END);
        
        this.commandPrefix = new JLabel();
        this.commandPrefix.setBackground(Color.BLACK);
        this.commandPrefix.setForeground(Color.LIGHT_GRAY);
        this.commandPrefix.setFont(this.defaultFont);
        this.commandPrefix.setText(this.commandMode + "> ");
        this.commandLine.add(this.commandPrefix);
        
        this.command = new JTextField("cmd");
        this.command.setText("");
        this.command.setBackground(Color.BLACK);
        this.command.setForeground(Color.LIGHT_GRAY);
        this.command.setFont(this.defaultFont);
        this.command.setBorder(BorderFactory.createEmptyBorder());
        this.command.setBorder(BorderFactory.createCompoundBorder(
        command.getBorder(), 
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.commandLine.add(this.command);
        this.command.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                //input handle
            }
        });
        
        this.timer = new Timer(ONE_SECOND, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                String toDisplay = "";
                Date date = new Date();
                toDisplay += (date.getDate() < 10 ? "0" + date.getDate(): date.getDate());
                toDisplay += ".";
                toDisplay += (date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1): (date.getMonth() + 1));
                toDisplay += ".";
                toDisplay += date.getYear() + 1900;
                toDisplay += " ";
                toDisplay += (date.getHours() < 10 ? "0" + date.getHours(): date.getHours());
                toDisplay += (date.getSeconds() % 2 == 0 ? ":" : " ");
                toDisplay += (date.getMinutes() < 10 ? "0" + date.getMinutes(): date.getMinutes());
                toDisplay += (date.getSeconds() % 2 == 0 ? ":" : " ");
                toDisplay += (date.getSeconds() < 10 ? "0" + date.getSeconds(): date.getSeconds());
                dateLabel.setText(toDisplay);
            }
        });
        this.timer.start();
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowOpened(WindowEvent evt)
            {
                command.requestFocus();
            }
        });     
    }
}
