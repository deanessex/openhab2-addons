/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.tankerkoenig.internal;

import static org.openhab.binding.tankerkoenig.internal.TankerkoenigBindingConstants.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.openhab.binding.tankerkoenig.internal.handler.StationHandler;
import org.openhab.binding.tankerkoenig.internal.handler.WebserviceHandler;
import org.osgi.service.component.annotations.Component;

/**
 * The {@link TankerkoenigHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Dennis Dollinger - Initial contribution
 */
@Component(service = ThingHandlerFactory.class, configurationPid = "binding.tankerkoenig")
public class TankerkoenigHandlerFactory extends BaseThingHandlerFactory {
    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections.unmodifiableSet(Stream
            .concat(BRIDGE_THING_TYPES_UIDS.stream(), TankerkoenigBindingConstants.SUPPORTED_THING_TYPES_UIDS.stream())
            .collect(Collectors.toSet()));

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();
        if (thingTypeUID.equals(BRIDGE_THING_TYPE)) {
            WebserviceHandler handler = new WebserviceHandler((Bridge) thing);
            return handler;
        } else if (thingTypeUID.equals(THING_TYPE_TANKSTELLE)) {
            return new StationHandler(thing);
        }
        return null;
    }
}
