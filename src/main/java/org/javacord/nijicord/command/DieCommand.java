package org.javacord.nijicord.command;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Random;

public class DieCommand implements MessageCreateListener {
    Random rng;
    String prefix;

    public DieCommand(String pfx) {
        super();
        this.prefix = pfx;
        rng = new Random();
    }

    public String multiDice(int dices, int sides, int bonus) {
        String text = String.format("Rolling %s x %s-sided dice: ", dices, sides);
        int total = 0;
        for (int i = 0; i < dices; i++) {
            int roll = rng.nextInt(sides) + 1;
            text += " " + roll;
            total += roll;
        }
        if (bonus != 0) {
            text += " adding " + bonus;
            total += bonus;
        }
        return text + " Total: **" + total + "**";
    }


    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String[] command = event.getMessageContent().split(" ");

        if (command[0].equalsIgnoreCase(prefix + "roll")) {
            int dice, bonus;
            String[] die = command[1].split("d");
            if(die.length==2 && die[0].equals("")) {
                if(!die[1].equalsIgnoreCase("0")) {
                    if (command.length == 2) {
                        dice = 1;
                        bonus = 0;
                        event.getChannel().sendMessage(multiDice(dice, Integer.parseInt(die[1]), bonus));
                        return;
                    }
                    if (command.length == 3) {
                        dice = 1;
                        bonus = Integer.parseInt(command[2]);
                        event.getChannel().sendMessage(multiDice(dice, Integer.parseInt(die[1]), bonus));
                        return;

                    }
                }
                else{
                    event.getChannel().sendMessage("```The number of sides must **NOT** be 0```");
                    return;
                }
            }

            if (die.length==2) {
                if (!die[0].equalsIgnoreCase("0") && !die[1].equalsIgnoreCase("0")) {
                    if (command.length == 2) {
                        dice = Integer.parseInt(die[0]);
                        bonus = 0;
                        event.getChannel().sendMessage(multiDice(dice, Integer.parseInt(die[1]), bonus));
                        return;
                    }

                    if (command.length == 3) {
                        dice = Integer.parseInt(die[0]);
                        bonus = Integer.parseInt(command[2]);
                        event.getChannel().sendMessage(multiDice(dice, Integer.parseInt(die[1]), bonus));
                        return;
                    }
                }
                else{
                    event.getChannel().sendMessage("```The number of side and/or the amount of dice must NOT be 0```");
                    return;
                }
            }

        }
    }
}
