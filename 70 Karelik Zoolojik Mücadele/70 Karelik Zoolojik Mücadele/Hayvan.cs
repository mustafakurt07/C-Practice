
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

public class Hayvan
{
    protected int pos;
    protected int pre_pos;
    protected bool disq;
    protected static Random rnd = new Random();
    protected double rnd_number;
    public Hayvan()
    {
       
        pos = 0;
        disq = false;
    }
    public int Position
    {
        get
        {
            return pos;
        }
       
    }

    public bool Disq
    {
        get
        {
            return disq;
        }
    }

    public void kill()
    {
        disq = true;
    }

    
    public virtual void move()
    {
       pre_pos = pos;
       rnd_number = rnd.NextDouble();
    }

    public virtual void checkCond(Hayvan x)
    {
        rnd_number = rnd.NextDouble();
        if (x.Disq) return;
    }
}

