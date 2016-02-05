package mh.smenovykalendararcelormittal20.templates;

import java.util.ArrayList;

/**
 * Created by Martin on 02.02.2016
 */
public abstract class ListTemplates{

    String title;
    String shortTitle;
    String desc;
    int color;

    public ListTemplates(String name, String shortTitle, int color, String desc)
    {
        this.title =name;
        this.shortTitle = shortTitle;
        this.color = color;
        this.desc = desc;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getTitle()
    {
        return title;
    }

    public String getShortTitle()
    {
        return shortTitle;
    }

    public int getColor()
    {
        return color;
    }
}
