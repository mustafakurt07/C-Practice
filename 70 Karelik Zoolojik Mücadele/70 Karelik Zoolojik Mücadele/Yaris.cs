
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;

public class Yaris
{
    Hayvan[] yarismacilar;
    public Yaris()
    {
        yarismacilar = new Hayvan[4];
        yarismacilar[0] = new Cakal();
        yarismacilar[1] = new Devekusu();
        yarismacilar[2] = new Fil();
        yarismacilar[3] = new Cakal();
    }
    public void Start()
    {
        foreach(Hayvan x in yarismacilar)
        {
            x.move();
        }
        while(!foto_finish())
        {
           for(int i=0;i<yarismacilar.Length;i++)
            {
                yarismacilar[i].move();
                for(int j=0;j<yarismacilar.Length;j++)
                {
                    if (i != j) yarismacilar[i].checkCond(yarismacilar[j]);
                }

            }
        }
        yarismacilar=yarismacilar.OrderBy(x => x.Position).ToArray();
        for(int i=0;i<yarismacilar.Length;i++)
        {
            Console.WriteLine("{0}. yarismaci: {1} - pozisyon: {2} ", i + 1, yarismacilar[i].GetType().Name, yarismacilar[i].Position);
        }
    }
    private bool foto_finish()
    {
        bool t = false;
        foreach(Hayvan x in yarismacilar)
        {
            if(x.Position>=70)
            {
                t = true;
                break;
            }
        }
        return t;
    }

}

