
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

public class Devekusu : Hayvan
{
    public override void move()
    {
        base.move();
        if (rnd_number > 0.2 && rnd_number < 0.4)
        {
            pos += 9;
        }
        else if (rnd_number < 0.5)
        {
            pos -= 12;
        }
        else if (rnd_number < 0.8)
        {
            pos += 1;
        }
        else
        {
            pos -= 2;
        }
        if (pos < 0) pos = 0;
    }
    public override void checkCond(Hayvan x)
    {
        base.checkCond(x);
        if(x is Fil)
        {
            if(rnd_number<0.1)
            {
                this.kill();
            }
        }
    }
}

