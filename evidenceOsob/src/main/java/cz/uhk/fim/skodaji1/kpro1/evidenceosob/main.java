/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.uhk.fim.skodaji1.kpro1.evidenceosob;
import  cz.uhk.fim.skodaji1.kpro1.evidenceosob.model.Osoba;
import java.time.LocalDate;

/**
 *
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        
        Osoba osoba = new Osoba("John", "Doe", LocalDate.of(1970, 1, 1));
    }
}
