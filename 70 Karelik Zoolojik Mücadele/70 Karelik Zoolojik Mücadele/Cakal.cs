
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

public class Cakal : Hayvan
{
    public override void move()
    {
        base.move();
        if (rnd_number < 0.3)
        {
            pos += 3;
        }
        else if(rnd_number<0.5)
        {
            pos -= 6;
        }
        else
        {
            pos += 2;
        }
        if (pos < 0) pos = 0; 
    }

    public override void checkCond(Hayvan x)
    {
        base.checkCond(x);
        if (x is Devekusu)
        {
            if (this.pos - this.pre_pos > 0)
            {
                if (this.pos>=x.Position && rnd_number < 0.8)
                {
                    x.kill();
                }
            }
        }
        else if (x is Fil)
        {
            if (this.pos - this.pre_pos > 0)
            {
                if (this.pos >= x.Position && rnd_number < 0.7)
                {
                    this.pos = x.Position - 1;
                }
            }
        }
    }
}

