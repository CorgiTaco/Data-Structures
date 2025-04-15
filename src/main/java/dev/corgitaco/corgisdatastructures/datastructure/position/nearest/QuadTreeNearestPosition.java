/*
 * Copyright (c) 2025 Corgi Taco.
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.corgitaco.corgisdatastructures.datastructure.position.nearest;

import dev.corgitaco.corgisdatastructures.coord.position.Position;

public class QuadTreeNearestPosition<POINT extends Position> extends QuadTreeNearestPositionData<POINT, POINT> {


    public QuadTreeNearestPosition() {
        super();
    }

    public Target<POINT, POINT> setPosition(POINT point) {
        return setPosition(point, point);
    }

    public boolean didSetPosition(POINT point) {
        return didSetPosition(point, point);
    }

    @Override
    public <TARGET extends Target<POINT, POINT> & NearestPosition<POINT, POINT>> TARGET targetFactory(POINT point, POINT o) {
        return (TARGET) new PositionTarget<>(point);
    }
}
