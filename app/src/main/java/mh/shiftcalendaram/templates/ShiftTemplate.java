package mh.shiftcalendaram.templates;

/**
 * Created by Martin on 02.02.2016.
 */
public class ShiftTemplate extends ShiftSymbolTemplates {

    int position;

    public ShiftTemplate(String name, String shortTitle, int color, String desc) {
        super(name, shortTitle, color, desc);
        this.position = -1;
    }

    public ShiftTemplate(String name, String shortTitle, int color, int position, String desc) {
        super(name, shortTitle, color, desc);
        this.position = position;
    }

    public int getPosition()
    {
        return position;
    }



}
