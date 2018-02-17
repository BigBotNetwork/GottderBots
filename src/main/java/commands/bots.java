package commands;

import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.sql.*;
import static core.Main.urlempty;
import static util.SECRETS.VERSION;

public class bots implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
        Connection con = DriverManager.getConnection(urlempty+"bank" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
        PreparedStatement pst = con.prepareStatement("SELECT * FROM `list`");
        ResultSet rs = pst.executeQuery();

        String out = "";

        while (rs.next()) {
            out += "``"+rs.getString(6) + "`` Invite Link: ``" + rs.getString(5) + "``\n";
        }
            event.getChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setTitle("Alle Bots").setDescription("Hier eine übersicht aller Bots: \n" + out).build()).queue();
        } catch (SQLException e) {}
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
