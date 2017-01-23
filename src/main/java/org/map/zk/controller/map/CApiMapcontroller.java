package org.map.zk.controller.map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.event.MapMouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;

public class CApiMapcontroller extends SelectorComposer<Component> {

	private static final long serialVersionUID = 2633033198331901614L;
	
	
    @Listen("onMapClick = #gmaps")
    public void onMapClick(MapMouseEvent event) {
        Gmarker gmarker = event.getGmarker();
        if(gmarker != null) {
            gmarker.setOpen(true);
        }
    }

}
