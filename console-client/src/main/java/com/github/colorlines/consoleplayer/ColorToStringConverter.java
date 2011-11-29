package com.github.colorlines.consoleplayer;

import com.github.colorlines.domain.Color;

/**
 * Created by IntelliJ IDEA.
 * User: Alex Lenkevich
 * Date: 27.11.11
 * Time: 13:01
 */
class ColorToStringConverter {

    public String convert(Color color) {
        switch (color) {
            case BLACK:
                return "D";
            case BLUE:
                return "B";
            case GREEN:
                return "G";
            case YELLOW:
                return "Y";
            case RED:
                return "R";
        }
        return "!";
    }

}
