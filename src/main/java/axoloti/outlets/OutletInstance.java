/**
 * Copyright (C) 2013, 2014, 2015 Johannes Taelman
 *
 * This file is part of Axoloti.
 *
 * Axoloti is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Axoloti is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Axoloti. If not, see <http://www.gnu.org/licenses/>.
 */
package axoloti.outlets;

import axoloti.Net;
import axoloti.atom.AtomDefinitionController;
import axoloti.atom.AtomInstance;
import axoloti.datatypes.DataType;
import axoloti.mvc.AbstractController;
import axoloti.mvc.AbstractDocumentRoot;
import axoloti.mvc.AbstractModel;
import axoloti.object.AxoObjectInstance;
import axoloti.object.AxoObjectInstanceAbstract;
import java.beans.PropertyChangeEvent;
import org.simpleframework.xml.*;

/**
 *
 * @author Johannes Taelman
 */
@Root(name = "source")
public class OutletInstance<T extends Outlet> extends AbstractModel implements Comparable<OutletInstance>, AtomInstance<T> {

    @Attribute(name = "outlet", required = false)
    public String outletname;
    @Deprecated
    @Attribute(required = false)
    public String name;
    @Attribute(name = "obj", required = false)
    public String objname;

    private final T outlet;

    protected AxoObjectInstanceAbstract axoObj;

    @Override
    public AxoObjectInstanceAbstract getObjectInstance() {
        return this.axoObj;
    }

    public String getOutletname() {
        return outletname;

    }

    @Override
    public T getDefinition() {
        return outlet;
    }

    public OutletInstance() {
        this.outlet = null;
        this.axoObj = null;
    }

    public OutletInstance(T outlet, AxoObjectInstance axoObj) {
        this.outlet = outlet;
        this.axoObj = axoObj;
        RefreshName();
    }

    public DataType getDataType() {
        return outlet.getDatatype();
    }

    public String GetLabel() {
        return outlet.getName();
    }

    public String GetCName() {
        return outlet.GetCName();
    }

    @Override
    public int compareTo(OutletInstance t) {
        return axoObj.compareTo(t.axoObj);
    }

    public Outlet getOutlet() {
        return outlet;
    }

    public void RefreshName() {
        name = axoObj.getInstanceName() + " " + outlet.getName();
        objname = axoObj.getInstanceName();
        outletname = outlet.getName();
        name = null;
    }

    public String getObjname() {
        return this.objname;
    }

    public boolean isConnected() {
        if (axoObj == null) {
            return false;
        }

        // FIXME: return (axoObj.getPatchModel().GetNet(this) != null);
        return false;
    }

    @Override
    public OutletInstanceController createController(AbstractDocumentRoot documentRoot) {
        return new OutletInstanceController(this, documentRoot);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        // triggered by a model definition change, triggering instance view changes
        if (evt.getPropertyName().equals(AtomDefinitionController.ATOM_NAME)
                || evt.getPropertyName().equals(AtomDefinitionController.ATOM_DESCRIPTION)) {
            firePropertyChange(
                    evt.getPropertyName(),
                    evt.getOldValue(),
                    evt.getNewValue());
        }
    }

    @Override
    public AtomDefinitionController getController() {
        return outlet.createController(null);
    }
    
}
