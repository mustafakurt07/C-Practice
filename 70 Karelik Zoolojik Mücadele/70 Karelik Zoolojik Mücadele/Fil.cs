
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

public class Fil : Hayvan
{
    public override void move()
    {
        base.move();
        if (rnd_number < 0.5)
        {
            pos += 2;
        }
        else if (rnd_number > 0.7)
        {
            pos += 4;
        }
        if (pos < 0) pos = 0;
    }

    public override void checkCond(Hayvan x)
    {
        base.checkCond(x);
        if (x is Devekusu)
        {
            if (rnd_number < 0.1)
            {
                x.kill();
            }
        }
    }
}

