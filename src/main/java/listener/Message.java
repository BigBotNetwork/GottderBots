package listener;

import core.HelpMenu;
import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

import static util.SECRETS.VERSION;

public class Message extends ListenerAdapter {
    public void onPrivateMessageReactionAdd(PrivateMessageReactionAddEvent event) {
        if (!event.getUser().isBot() && event.getChannel().getMessageById(event.getMessageId()).complete().getEmbeds().get(0).getTitle().contains("Help") && event.getUser().getIdLong() != event.getJDA().getSelfUser().getIdLong()) {
            Thread t = new Thread(() -> {
                System.out.println(event.getUser().getName()+"("+event.getUser().getId()+") has reacted with "+ event.getReaction().getReactionEmote().getName()+ " on a Helpmessage");
                HelpMenu.Help(event.getReactionEmote().getName());
                int Reaction = 0;
                int User = 0;
                net.dv8tion.jda.core.entities.Message Message = event.getChannel().getMessageById(event.getMessageId()).complete();
                while (Message.getReactions().size() - 1 >= Reaction) {
                    if (Message.getReactions().get(Reaction).getUsers().complete().get(User).getIdLong() == event.getJDA().getSelfUser().getIdLong()) {
                        Message.getReactions().get(Reaction).removeReaction().queue();
                        User = 0;
                        if (Message.getReactions().size() - 1 >= Reaction) {
                            Reaction++;
                            User = 0;
                        }
                    } else {
                        if (Message.getReactions().get(Reaction).getUsers().complete().size() - 1 > User) {
                            User++;
                        } else {
                            Reaction++;
                            User = 0;
                        }
                    }
                }
                event.getChannel().editMessageById(event.getMessageId(), new EmbedBuilder().setDescription(HelpMenu.Message).setTitle(HelpMenu.Title).setColor(Color.GREEN).setFooter(Main.Footer, Main.Footer2).build()).queue();
                int i = 1;
                while (HelpMenu.Emoji.length > i) {
                    event.getChannel().addReactionById(event.getMessageId(), HelpMenu.Emoji[i]).queue();
                    HelpMenu.Emoji[i] = null;
                    i++;
                }
            });
            t.setName("HelpMessage");
            t.start();
        }
    }


    public void onPrivateMessageReactionRemove(PrivateMessageReactionRemoveEvent event) {
        if (!event.getUser().isBot() && event.getChannel().getMessageById(event.getMessageId()).complete().getEmbeds().get(0).getTitle().contains("Help") && event.getUser().getIdLong() != event.getJDA().getSelfUser().getIdLong()) {
            Thread t = new Thread(() -> {
                HelpMenu.Help(event.getReactionEmote().getName());
                int Reaction = 0;
                int User = 0;
                net.dv8tion.jda.core.entities.Message Message = event.getChannel().getMessageById(event.getMessageId()).complete();
                while (Message.getReactions().size() - 1 >= Reaction) {
                    if (Message.getReactions().get(Reaction).getUsers().complete().get(User).getIdLong() == event.getJDA().getSelfUser().getIdLong()) {
                        Message.getReactions().get(Reaction).removeReaction().queue();
                        User = 0;
                        if (Message.getReactions().size() - 1 >= Reaction) {
                            Reaction++;
                            User = 0;
                        }
                    } else {
                        if (Message.getReactions().get(Reaction).getUsers().complete().size() - 1 > User) {
                            User++;
                        } else {
                            Reaction++;
                            User = 0;
                        }
                    }
                }
                event.getChannel().editMessageById(event.getMessageId(), new EmbedBuilder().setDescription(HelpMenu.Message).setTitle(HelpMenu.Title).setColor(Color.GREEN).setFooter(Main.Footer, Main.Footer2).build()).queue();
                int i = 1;
                while (HelpMenu.Emoji.length > i) {
                    event.getChannel().addReactionById(event.getMessageId(), HelpMenu.Emoji[i]).queue();
                    HelpMenu.Emoji[i] = null;
                    i++;
                }
            });
            t.setName("HelpMessage");
            t.start();
        }
    }

}
