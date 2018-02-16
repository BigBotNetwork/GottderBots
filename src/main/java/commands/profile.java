package commands;

import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.sound.sampled.LineEvent;
import java.awt.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;

import static core.Main.urlempty;
import static util.SECRETS.VERSION;

public class profile implements Command {
    String Nick;
    String Game;
    Member user;
    String useruser;
    String Cookies;
    String Punkte;
    String Level;
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
                useruser = args[0].replace("<", "").replace("@", "").replace(">", "").replace("!","");
                user = event.getGuild().getMemberById(useruser);
            if (useruser.equals(event.getMember().getUser().getId())) {
                event.getTextChannel().sendMessage("Was bringt es sich selbst zu hinzuschreiben?? egal... mach es nächstes mal einfach mit -profile :wink: ").queue();
            }

        }catch ( ArrayIndexOutOfBoundsException e) {
            user = event.getMember();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user.getGame() == null) Game = "Es gibt kein Aktuell gespieltes Spiel";
            else Game  = ""+user.getGame().getName();
        if (user.getNickname() == null) Nick = "Es gibt keinen Nicknamen";
            else Nick = user.getNickname();
            int i=0;
            String Rollen="";
            int end = user.getRoles().size()-1;
            while (i<user.getRoles().size()) {
                if (i<end) {
                    Rollen += user.getRoles().get(i).getName() + ", ";
                    i++;
                } else {
                    Rollen += user.getRoles().get(i).getName();
                    i++;
                }
            }
            try {
                //Cookies
                Connection con = DriverManager.getConnection(urlempty + "cookiebot" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM `user` WHERE ID='"+event.getAuthor().getId()+"'");
                ResultSet rs = pst.executeQuery();
                if (!rs.next()) {
                    Cookies= "Da musst du dich wohl mit -register noch bei dem Bot registrieren!";
                } else {
                    Cookies=rs.getInt(2)+"";
                }
            } catch (SQLException e) {}
            //Level
        try {
            Connection con = DriverManager.getConnection(urlempty + "lvl" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
            PreparedStatement pst = con.prepareStatement("SELECT * FROM `user` WHERE ID='"+event.getAuthor().getId()+"'");
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                Punkte="Da musst du dich wohl mit -register noch bei dem Bot registrieren!";
               Level= "Da musst du dich wohl mit -register noch bei dem Bot registrieren!";
            } else {
                Punkte=rs.getInt(2)+"";
                Level=rs.getInt(3)+"";
            }
        } catch (SQLException e) {}

        EmbedBuilder eb = new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setColor(Color.GREEN).setTitle("Your Profile");
            eb.addField("Name", user.getUser().getName(), true);
            eb.addField("Nickname", Nick, true);
            eb.addField("Game", Game, true);
            eb.addField("Rollen", Rollen, true);
            eb.addField("Server betreten", user.getJoinDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss")), true);
            eb.addField("Status", user.getOnlineStatus().toString(), true);

            eb.setThumbnail(user.getUser().getAvatarUrl());
        event.getTextChannel().sendMessage(eb.build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
