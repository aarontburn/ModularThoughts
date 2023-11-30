package com.aarontburn.modularthoughts.dependencies.flexboxfx;

import javafx.scene.Node;

/**
 * <a href="https://github.com/onexip/FlexBoxFX">...</a>
 * <p>
 * This is a creation by onexip on github. I had to take the code because I couldn't figure out how to add it to a build jar.
 * I have not, and will not modify any contents.
 * <p>
 * Created by TB on 11.10.16.
 */
class FlexBoxItem
{
    public int order = 0;
    public double grow = 0;
    public Node node;
    public double minWidth=0;

    public FlexBoxItem(Node node)
    {
        this.node = node;
    }
}
