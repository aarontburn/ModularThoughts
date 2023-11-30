package com.aarontburn.modularthoughts.dependencies.flexboxfx;

import java.util.ArrayList;

/**
 * <a href="https://github.com/onexip/FlexBoxFX">...</a>
 * <p>
 * This is a creation by onexip on github. I had to take the code because I couldn't figure out how to add it to a build jar.
 * I have not, and will not modify any contents.
 * <p>
 * Created by TB on 11.10.16.
 */
public class FlexBoxRow
{
    public double rowMinWidth = 0;
    private ArrayList<FlexBoxItem> nodes;
    public double flexGrowSum = 0;

    public void addFlexBoxItem(FlexBoxItem flexBoxItem)
    {
        if (nodes == null)
        {
            nodes = new ArrayList<FlexBoxItem>();
        }
        nodes.add(flexBoxItem);
    }

    public ArrayList<FlexBoxItem> getNodes()
    {
        if (nodes == null)
        {
            nodes = new ArrayList<FlexBoxItem>();
        }
        return nodes;
    }
}
