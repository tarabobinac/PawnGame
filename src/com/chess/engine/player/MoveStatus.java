package com.chess.engine.player;

import java.io.Serializable;

public enum MoveStatus implements Serializable {
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }
    },
    ILLEGAL_MOVE {
        @Override
        public boolean isDone() {
            return false;
        }
    };

    public abstract boolean isDone();
}