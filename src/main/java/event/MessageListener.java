package event;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import utils.*;
import utils.database.InitializeDB;

import java.sql.SQLException;
import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;

import static utils.Command.slashCommand;


public class MessageListener extends ListenerAdapter {
    private static JDA jda;
    private static GetMemberCount nCntAPI = new GetMemberCount();
    static TimerTask task = new TimerTask() {
        @Override
        public void run() {
//            String currentMember = nCntAPI.get("http://114.71.48.94:8080/nCnt");
//            System.out.println("동방 인원 갱신" + currentMember);
//
//            jda.getGuildById("1022860897477206016")
//                    .getTextChannelById("1039160004244160562")
//                    .getManager().setName("\uD83D\uDCC8동방인원 "+currentMember+"명")
//                    .queue();
        }
    };


    public static void main(String[] args) throws SQLException {
        jda = JDABuilder.createLight("NzExNDc0NDYwODEyNjQwMjU3.G_DEAe.Ou6X_MaOuNu1W0xzskhUFKxAlWQBz1bfDiEyjE", EnumSet.noneOf(GatewayIntent.class))
                .addEventListeners(new MessageListener())
                .build();
        CommandListUpdateAction commands = jda.updateCommands();
        slashCommand(commands);

        InitializeDB.set();

        new Timer().scheduleAtFixedRate(task, 3000, 1000 * 60);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getGuild() == null) return;
        switch (event.getName()) {
            case "대출":
//                SelectedMenu.getAllOptions(event, "http://localhost:8000/api/book");
                if (event.getOptions().size() >= 1) {
                    Buttons.argsSearch(event, event.getOption("검색어").getAsString());
                } else {
                    Buttons.notArgsSearch(event);
                }
                break;
            case "반납":
                RequestApiGet.send("http://localhost:8000/api/removeALL");
                event.reply("반납 완료 :)").queue();
                break;
            default:
                event.reply("지원하지 않는 명령어입니다 :(").setEphemeral(true).queue();
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
//        event.deferEdit().queue();
        if (event.getComponentId().equalsIgnoreCase("by_field")) {
            String uri = String.format("http://localhost:8000/api/book?tag=%s", event.getValues().get(0));
            SelectedMenu.getAllOptions(event, uri);
        } else {
            RequestApiGet.send("http://localhost:8000/api/takeout/"+event.getValues().get(0));
            event.getHook().deleteMessageById(event.getMessage().getId()).queue();
            event.reply("성공적으로 ``" + event.getSelectedOptions().get(0).getLabel() + "`` 책을 대출하였습니다").queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String[] args = event.getComponentId().split(":");

        if (args[0].equalsIgnoreCase("takeout")) {
            String cmd = args[1];
            switch (cmd) {
                case "all":
                    ButtonInterection.all_search(event);
                    break;
                case "by_field":
                    ButtonInterection.field_search(event);
                    break;
                case "by_title":
                    ButtonInterection.title_search(event);
                    break;
            }
        }

    }


}