package com.example.imc;

import java.text.DecimalFormat;

public class Personne {
    public static final int HOMME = 0;
    public static final int FEMME = 1;
    private float poids;
    private float taille;
    private int age;

    public Personne(float poids, float taille, int age) {
        this.poids = poids;
        this.taille = taille;
        this.age = age;
    }

    public float getPoids() {
        return poids;
    }

    public float getTaille() {
        return taille;
    }

    public int getAge() {
        return age;
    }

    public double imc() {
        return poids / Math.pow(taille, 2);
    }

    public double getPoidsIdeal(boolean homme) {
        if (homme)
            return taille * 100 - 100 - (taille * 100 - 150) / 4;
        else
            return taille * 100 - 100 - (taille * 100 - 150) / 2.5;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00"); //2chiffres aprés le virgule
        return poids+"Kg,"+taille+"m,"+age+"ans,"+"imc:"+df.format(imc());
    }
}
