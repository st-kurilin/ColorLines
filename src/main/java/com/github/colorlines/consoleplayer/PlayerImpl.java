package com.github.colorlines.consoleplayer;

import com.github.colorlines.domain.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Alex Lenkevich
 * Date: 27.11.11
 * Time: 12:52
 */
public class PlayerImpl implements Player {

    private final ColorToStringConverter colorToStringConverter = new ColorToStringConverter();
    private final static Pattern movePattern = Pattern.compile("(\\d+)\\s+(\\d+)\\s*->\\s*(\\d+)\\s+(\\d+)");

    public Turn turn(Area area, TurnValidator validator) {
        StringBuffer buf = new StringBuffer();

        for (int y = 0; y < Position.HEIGHT_RANGE.upperEndpoint(); y++) {
            for (int x = 0; x < Position.HEIGHT_RANGE.lowerEndpoint(); x++) {
                Position position = new Position(x, y);
                buf.append("|").append(
                        area.contains(position)
                                ? colorToStringConverter.convert(area.take(position).color())
                                : " "
                ).append("|");
            }
            buf.append("\n");
        }
        System.out.println(buf);
        while (true) {
            System.out.println("Your turn (X Y -> X Y) :");
            Scanner scanner = new Scanner(System.in);
            String moveText = scanner.nextLine();
            Matcher matcher = movePattern.matcher(moveText);
            if (matcher.matches()) {
                int xs = Integer.parseInt(matcher.group(1));
                int ys = Integer.parseInt(matcher.group(2));
                int xd = Integer.parseInt(matcher.group(3));
                int yd = Integer.parseInt(matcher.group(4));

                Position endPos = new Position(xd, yd);
                Position startPos = new Position(xs, ys);

                if (area.contains(startPos)) {
                    Ball ball = area.take(startPos);
                    Turn turn = new TurnImpl(ball, endPos);
                    if (validator.isValid(area, turn)) {
                        return turn;
                    }
                }
            }
            System.out.println("Not corrected move");
        }
    }
}
