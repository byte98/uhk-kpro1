/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.uhk.fim.skodaji1.kpro1.evidenceosob.model;

import java.time.LocalDate;

/**
 *
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class Osoba {
    private String name;
    private String surname;
    private LocalDate birthDay;

    public Osoba(String name, String surname, LocalDate birthDay) {
        this.name = name;
        this.surname = surname;
        this.birthDay = birthDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    
    @Override
    public String toString() {
        return "Osoba{" + "name=" + name + ", surname=" + surname + ", birthDay=" + birthDay + '}';
    }
    
    
}
