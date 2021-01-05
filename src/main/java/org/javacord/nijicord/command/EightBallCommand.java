package org.javacord.nijicord.command;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class EightBallCommand implements MessageCreateListener {
    private final String[] a = {
            "As I see it, yes",
            "Better not tell you now",
            "Cannot predict now",
            "Don't count on it",
            "If you say so",
            "In your dreams",
            "It is certain",
            "Most likely",
            "My CPU is saying no",
            "My CPU is saying yes",
            "Out of psychic coverage range",
            "Signs point to yes",
            "Sure, sure",
            "Very doubtful",
            "When life gives you lemon, you drink it",
            "Without a doubt",
            "Wow, Much no, very yes, so maybe",
            "Yes, definitely",
            "Yes, unless you run out of memes",
            "You are doomed",
            "You can't handle the truth"};



    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String[] command = event.getMessageContent().split(" ");
        if (command.length != 1) {
            if (command[0].equalsIgnoreCase("!8ball")) {
                event.getChannel().sendMessage(":crystal_ball: " + a[(int) (Math.random() * a.length)]);
                return;
            }
        }
        else{
            event.getChannel().sendMessage("What should I predict master? Just use me as it fits. !8ball <question>");
            return;
        }

    }
}
